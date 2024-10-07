package Services.Dependencies;

import Entity.*;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.ResponseUnstructured;
import Services.ResponseAnalyzer.StructuredObject;

import java.util.List;

public class QueryParameterDependency {

    public static void check_queryParams_dependency(List<ResponseUnstructured> responseUnstructuredList, int req_index, DependencyGraph dependencyGraph, MyNode to,int first_index_response) {
        for( QueryParam queryParam: to.getRequest().getQueryParams()){
            for (int response_index = first_index_response; response_index < req_index; response_index++) {
                MyNode from = dependencyGraph.getNodeByIndex(response_index);
                check_queryParam(queryParam, responseUnstructuredList.get(response_index),from,to,dependencyGraph,req_index,response_index);
            }
        }
    }

    private static void check_queryParam(QueryParam queryParam, Object response, MyNode from, MyNode to, DependencyGraph dependencyGraph,int req_index,int from_index) {
        if(response.getClass() == ResponseUnstructured.class){
            ResponseUnstructured responseUnstructured = (ResponseUnstructured)response;
            for(Object o : responseUnstructured.getObjects()){
                check_queryparam_atomic_evaluation(o,queryParam,null,from,to,dependencyGraph,req_index,from_index);
            }
        }else if(response.getClass() == StructuredObject.class){
            StructuredObject structuredObject = (StructuredObject) response;
            for(Object o : structuredObject.getObjects()){
                check_queryparam_atomic_evaluation(o,queryParam,structuredObject,from,to,dependencyGraph,req_index,from_index);
            }
        }
    }

    private static void check_queryparam_atomic_evaluation(Object o, QueryParam queryParam, StructuredObject father, MyNode from, MyNode to, DependencyGraph dependencyGraph,int req_index,int from_index) {
        if(o.getClass() == AtomicObject.class){
            AtomicObject atomicObject = (AtomicObject) o;
            AtomicDependencyValidator atomicDependencyValidator = new AtomicDependencyValidator();
            if(atomicDependencyValidator.evaluate_atomic_dependencies(atomicObject,queryParam,father,from.getRequest())){
                EdgeQueryParam edgeQueryParam = new EdgeQueryParam(from,to,queryParam.getName(),atomicObject);
                dependencyGraph.edges.add(edgeQueryParam);
                edgeQueryParam.setTo_index(req_index);
                edgeQueryParam.setFrom_index(from_index);
            }
        } else if (o.getClass() == StructuredObject.class) {
            check_queryParam(queryParam,o,from,to,dependencyGraph,req_index,from_index);
        }
    }
}
