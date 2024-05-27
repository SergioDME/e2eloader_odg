package Services.Dependencies;

import Entity.*;
import Services.ResponseAnalyzer.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

public class BodyDependency {

    public static void check_body_dependency(List<ResponseUnstructured> responseUnstructuredList, int req_index, DependencyGraph dependencyGraph, Node to) throws IOException {
        if ("application/x-www-form-urlencoded".equals(to.getRequest().getPostData().getMimeType())) {
            check_body_urleconded_dep(responseUnstructuredList, req_index, dependencyGraph, to);
        } else if ("application/json".equals(to.getRequest().getPostData().getMimeType())) {
            String json_body = to.getRequest().getPostData().getText();
            if (json_body.startsWith("{")) {
                JsonObject jsonBody = new JsonParser().parse(json_body).getAsJsonObject();
                check_application_json_dep(responseUnstructuredList, jsonBody, null, req_index, dependencyGraph, to);
            } else if (json_body.startsWith("[")) {
                JsonArray jsonArray = new JsonParser().parse(json_body).getAsJsonArray();
                check_application_json_dep(responseUnstructuredList, jsonArray, null, req_index, dependencyGraph, to);
            }
        }
    }


    private static void check_application_json_dep(List<ResponseUnstructured> responseUnstructuredList, Object object, String name, int req_index, DependencyGraph dependencyGraph, Node to) throws IOException {
        if (object.getClass() == JsonObject.class) { //jsonobject
            JsonObject jsonObject = (JsonObject) object;
            boolean found = search_dep_body_jsonob(responseUnstructuredList, jsonObject, req_index, dependencyGraph, to, name);
            if (!found) {
                for (String key : jsonObject.keySet()) {
                    JsonElement value = jsonObject.get(key);
                    if (value.isJsonPrimitive()) {
                        check_application_json_dep(responseUnstructuredList, value.getAsString(), key, req_index, dependencyGraph, to);
                    } else if (value.isJsonObject()) {
                        check_application_json_dep(responseUnstructuredList, value.getAsJsonObject(), key, req_index, dependencyGraph, to);
                    } else if (value.isJsonArray()) {
                        check_application_json_dep(responseUnstructuredList, value.getAsJsonArray(), key, req_index, dependencyGraph, to);
                    }
                }
            }
        } else if (object.getClass() == String.class) { // primitive case
            check_primitive_body_json(responseUnstructuredList, req_index, dependencyGraph, to, (String) object, name);
        } else if (object.getClass() == JsonArray.class) {//jsonarray
            JsonArray jsonArray = (JsonArray) object;
            for (JsonElement element : jsonArray) {
                if (element.isJsonPrimitive()) {
                    check_application_json_dep(responseUnstructuredList, element.getAsString(), name, req_index, dependencyGraph, to);
                } else if (element.isJsonObject()) {
                    check_application_json_dep(responseUnstructuredList, element.getAsJsonObject(), name, req_index, dependencyGraph, to);
                } else if (element.isJsonArray()) {
                    check_application_json_dep(responseUnstructuredList, element.getAsJsonArray(), name, req_index, dependencyGraph, to);
                }
            }
        }

    }

    private static boolean search_dep_body_jsonob(List<ResponseUnstructured> responseUnstructuredList, JsonObject object, int req_index, DependencyGraph dependencyGraph, Node to, String name) throws IOException {
        boolean res = false;
        for (int response_index = 0; response_index < req_index; response_index++) {
            Node from = dependencyGraph.getNodeByIndex(response_index);
            res = res || check_dep_body_jsonb(object, responseUnstructuredList.get(response_index), dependencyGraph, to, from, name);
        }
        return res;
    }

    private static boolean check_dep_body_jsonb(JsonObject object, Object response, DependencyGraph dependencyGraph, Node to, Node from, String name) throws IOException {
        boolean res = false;
        if (response.getClass() == ResponseUnstructured.class) {
            ResponseUnstructured responseUnstructured = (ResponseUnstructured) response;
            for (Object o : responseUnstructured.getObjects()) {
                res = res || check_dep_body_jsonb(object, o, dependencyGraph, to, from, name);
            }
        } else if (response.getClass() == StructuredObject.class) {
            StructuredObject structuredObject = (StructuredObject) response;
            String json_schema = JsonSchemaGenerator.generateJSONSchema(object.toString());
            if (json_schema.equals(structuredObject.getSchemaString())) {
                JsonObject structured_json_obj = new JsonParser().parse(structuredObject.getValue()).getAsJsonObject();
                if (structured_json_obj.equals(object)) {
                    EdgeBodyJSON edgeBodyJSON = new EdgeBodyJSON(false, name, from,to, null, structuredObject);
                    dependencyGraph.edges.add(edgeBodyJSON);
                    return true;
                }
            }
            for (Object o : structuredObject.getObjects()) {
                res = res || check_dep_body_jsonb(object, o, dependencyGraph, to, from, name);
            }
        }
        return res;
    }

    private static void check_primitive_body_json(List<ResponseUnstructured> responseUnstructuredList, int req_index, DependencyGraph dependencyGraph, Node to, String value, String name) {
        for (int response_index = 0; response_index < req_index; response_index++) {
            Node from = dependencyGraph.getNodeByIndex(response_index);
            check_primitive(name, value, responseUnstructuredList.get(response_index), to, from, dependencyGraph);
        }
    }

    private static void check_primitive(String name, String value, Object response, Node to, Node from, DependencyGraph dependencyGraph) {
        if (response.getClass() == ResponseUnstructured.class) {
            ResponseUnstructured responseUnstructured = (ResponseUnstructured) response;
            for (Object o : responseUnstructured.getObjects()) {
                check_primitive_atomic_evaluation(o, name, value, null, to, from, dependencyGraph);
            }
        } else if (response.getClass() == StructuredObject.class) {
            StructuredObject structuredObject = (StructuredObject) response;
            for (Object o : structuredObject.getObjects()) {
                check_primitive_atomic_evaluation(o, name, value, structuredObject, to, from, dependencyGraph);
            }
        }
    }

    private static void check_primitive_atomic_evaluation(Object o, String name, String value, StructuredObject father, Node to, Node from, DependencyGraph dependencyGraph) {
        if (o.getClass() == AtomicObject.class) {
            AtomicObject atomicObject = (AtomicObject) o;
            AtomicDependencyValidator atomicDependencyValidator = new AtomicDependencyValidator();
            if (atomicDependencyValidator.general_atomic_comparioson(atomicObject, value, name, father, from.getRequest())) {
                EdgeBodyJSON edgeBodyJSON = new EdgeBodyJSON(true, name, from, to,atomicObject, null);
                dependencyGraph.edges.add(edgeBodyJSON);
            }
        } else if (o.getClass() == StructuredObject.class) {
            check_primitive(name, value, o, to, from, dependencyGraph);
        }
    }

    private static void check_body_urleconded_dep(List<ResponseUnstructured> responseUnstructuredList, int req_index, DependencyGraph dependencyGraph, Node to) {
        for (Param param : to.getRequest().getPostData().getParams()) {
            for (int response_index = 0; response_index < req_index; response_index++) {
                Node from = dependencyGraph.getNodeByIndex(response_index);
                check_urlencoded(param, responseUnstructuredList.get(response_index), to, from, dependencyGraph);
            }
        }
    }

    private static void check_urlencoded(Param param, Object response, Node to, Node from, DependencyGraph dependencyGraph) {
        if (response.getClass() == ResponseUnstructured.class) {
            ResponseUnstructured responseUnstructured = (ResponseUnstructured) response;
            for (Object o : responseUnstructured.getObjects()) {
                check_urlencoded_atomic_evaluation(o, param, null, to, from, dependencyGraph);
            }
        } else if (response.getClass() == StructuredObject.class) {
            StructuredObject structuredObject = (StructuredObject) response;
            for (Object o : structuredObject.getObjects()) {
                check_urlencoded_atomic_evaluation(o, param, structuredObject, to, from, dependencyGraph);
            }
        }
    }

    private static void check_urlencoded_atomic_evaluation(Object o, Param param, StructuredObject father, Node to, Node from, DependencyGraph dependencyGraph) {
        if (o.getClass() == AtomicObject.class) {
            AtomicObject atomicObject = (AtomicObject) o;
            AtomicDependencyValidator atomicDependencyValidator = new AtomicDependencyValidator();
            if (atomicDependencyValidator.evaluate_atomic_dependencies(atomicObject, param, father,from.getRequest())) {
                EdgeBodyUE edgeBody = new EdgeBodyUE(from,to, param.getName(), atomicObject);
                dependencyGraph.edges.add(edgeBody);
            }
        } else if (o.getClass() == StructuredObject.class) {
            check_urlencoded(param, o, to, from, dependencyGraph);
        }
    }
}
