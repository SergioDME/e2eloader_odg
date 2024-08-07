package Services.Dependencies;

import Entity.*;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.ResponseUnstructured;
import Services.ResponseAnalyzer.StructuredObject;

import java.util.List;

public class QueryParameterDependency {

    public static void check_queryParams_dependency(List<ResponseUnstructured> responseUnstructuredList, int req_index, DependencyGraph dependencyGraph, MyNode to) {
        for( QueryParam queryParam: to.getRequest().getQueryParams()){
            for (int response_index = 0; response_index < req_index; response_index++) {
                MyNode from = dependencyGraph.getNodeByIndex(response_index);
                check_queryParam(queryParam, responseUnstructuredList.get(response_index),from,to,dependencyGraph);
            }
        }
    }

    private static void check_queryParam(QueryParam queryParam, Object response, MyNode from, MyNode to, DependencyGraph dependencyGraph) {
        if(response.getClass() == ResponseUnstructured.class){
            ResponseUnstructured responseUnstructured = (ResponseUnstructured)response;
            for(Object o : responseUnstructured.getObjects()){
                check_queryparam_atomic_evaluation(o,queryParam,null,from,to,dependencyGraph);
            }
        }else if(response.getClass() == StructuredObject.class){
            StructuredObject structuredObject = (StructuredObject) response;
            for(Object o : structuredObject.getObjects()){
                check_queryparam_atomic_evaluation(o,queryParam,structuredObject,from,to,dependencyGraph);
            }
        }
    }

    private static void check_queryparam_atomic_evaluation(Object o, QueryParam queryParam, StructuredObject father, MyNode from, MyNode to, DependencyGraph dependencyGraph) {
        if(o.getClass() == AtomicObject.class){
            AtomicObject atomicObject = (AtomicObject) o;
            AtomicDependencyValidator atomicDependencyValidator = new AtomicDependencyValidator();
            if(atomicDependencyValidator.evaluate_atomic_dependencies(atomicObject,queryParam,father,from.getRequest())){
                EdgeQueryParam edgeQueryParam = new EdgeQueryParam(from,to,queryParam.getName(),atomicObject);
                dependencyGraph.edges.add(edgeQueryParam);
            }
        } else if (o.getClass() == StructuredObject.class) {
            check_queryParam(queryParam,o,from,to,dependencyGraph);
        }
    }
}
