package Services.ResponseAnalyzer;

import Entity.Header;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

public class ResponseAnalyzer {

    String url;
    Integer num_req;

    public ResponseAnalyzer (String url, Integer num_req)
    {
        this.url=url;
        this.num_req=num_req;
    }

    public ResponseAnalyzer(){}
    public ResponseUnstructured getUnstructuredResponse(String json_response) {
        ResponseUnstructured responseUnstructured = new ResponseUnstructured();
        if(json_response.startsWith("{")) {
            JsonObject jsonObject = new JsonParser().parse(json_response).getAsJsonObject();
            visitNode(jsonObject, "$",  responseUnstructured.getObjects());
        } else if(json_response.startsWith("[")) {
            JsonArray jsonArray = new JsonParser().parse(json_response).getAsJsonArray();
            visitNode(jsonArray,"$",responseUnstructured.getObjects());
        }
        return responseUnstructured;
    }

    private void visitNode(Object object, String xPath, List<Object> objectList){
        if(object.getClass() == JsonObject.class)
        {
            JsonObject node = (JsonObject)object;
            if(node.isJsonNull()) return;
            for(String key : node.keySet()){
                JsonElement value = node.get(key);
                if(value.isJsonPrimitive() || value.isJsonNull()) {
                   // atomic node
                    objectList.add(new AtomicObject(value.getAsString(),key,String.format("%s.%s", xPath,key)));
                } else if (value.isJsonObject()) {
                    StructuredObject structuredObject = null;
                    structuredObject = new StructuredObject(key,value.getAsJsonObject().toString(),String.format("%s.%s", xPath,key));
                    objectList.add(structuredObject);
                    //visitNode(value.getAsJsonObject(), String.format("%s.%s", xPath,key), "".equals(varName) ? key.toString() :varName+"-"+key.toString(),structuredObject.getObjects());
                    visitNode(value.getAsJsonObject(), String.format("%s.%s", xPath,key), structuredObject.getObjects());
                }else if (value.isJsonArray()){
                    JsonArray jsonArray = value.getAsJsonArray();
                    StructuredObject structuredObject = new StructuredObject(key,jsonArray.toString(),String.format("%s.%s", xPath,key));
                    objectList.add(structuredObject);
                    iterateJSONArray(jsonArray,String.format("%s.%s", xPath,key),key,structuredObject.getObjects());
                }
            }
        }else if(object.getClass() == JsonArray.class){
            JsonArray jsonArray = (JsonArray)object;
            StructuredObject structuredObject = null;
            structuredObject = new StructuredObject("",jsonArray.toString(),xPath);
            objectList.add(structuredObject);
            iterateJSONArray(jsonArray,xPath,"",structuredObject.getObjects());
        }
    }

    private void iterateJSONArray(JsonArray jsonArray,String xPath, String varName, List<Object> objectsList){
        int id = 0;
        for(JsonElement element : jsonArray){
            //String forname =""+id+"";
            if(element.isJsonPrimitive()){
                objectsList.add(new AtomicObject(element.getAsString(),varName,String.format("%s.%s[%d]", xPath,varName,id)));
            } else if (element.isJsonObject()){
                StructuredObject structuredObject = null;
                structuredObject = new StructuredObject(varName,element.getAsJsonObject().toString(),String.format("%s[%d]", xPath, id));
                objectsList.add(structuredObject);
                visitNode(element.getAsJsonObject(), String.format("%s[%d]", xPath, id),structuredObject.getObjects());
            }
            id++;
        }
    }

    public void analyzeResponseHeader(Header[] headers, ResponseUnstructured responseUnstructured) {
        for(int i=0;i<headers.length;i++){
            Header header = headers[i];
            if(header.getName().equals("Set-Cookie")){
                String obj = header.getValue().split(";")[0];
                String [] name_value = obj.split("=");
                AtomicObject atomicObject;
                if(name_value.length==2)
                     atomicObject = new AtomicObject(name_value[1],name_value[0],null,true);
                else
                     atomicObject = new AtomicObject("",name_value[0],null,true);

                responseUnstructured.getObjects().add(atomicObject);
            }
        }
    }
}