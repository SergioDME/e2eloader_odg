package Services.Dependencies;

import Entity.*;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.ResponseUnstructured;
import Services.ResponseAnalyzer.StructuredObject;

import java.util.List;

public class CookieDependency {
    public static void check_cookie_dependency(List<ResponseUnstructured> responseUnstructuredList, int req_index, DependencyGraph dependencyGraph, MyNode to) {
        for( Cookie cookie: to.getRequest().getCookies()){
            for (int response_index = 0; response_index < req_index; response_index++) {
                MyNode from = dependencyGraph.getNodeByIndex(response_index);
                check_cookie(cookie, responseUnstructuredList.get(response_index),to,from,dependencyGraph);
            }
        }
    }

    private static void check_cookie(Cookie cookie, Object response, MyNode to, MyNode from, DependencyGraph dependencyGraph) {
        if(response.getClass() == ResponseUnstructured.class){
            ResponseUnstructured responseUnstructured = (ResponseUnstructured)response;
            for(Object o : responseUnstructured.getObjects()){
                check_cookie_atomic_evaluation(o,cookie,null,to,from,dependencyGraph);
            }
        }else if(response.getClass() == StructuredObject.class){
            StructuredObject structuredObject = (StructuredObject) response;
            for(Object o : structuredObject.getObjects()){
                check_cookie_atomic_evaluation(o,cookie,structuredObject,to,from,dependencyGraph);
            }
        }
    }

    private static void check_cookie_atomic_evaluation(Object o, Cookie cookie, StructuredObject father, MyNode to, MyNode from, DependencyGraph dependencyGraph) {
        if(o.getClass() == AtomicObject.class){
            AtomicObject atomicObject = (AtomicObject) o;
            AtomicDependencyValidator atomicDependencyValidator = new AtomicDependencyValidator();
            if(atomicDependencyValidator.evaluate_atomic_dependencies(atomicObject,cookie,father,from.getRequest())){
                EdgeCookie edgeCookie = new EdgeCookie(from,to,cookie.getName(),atomicObject);
                dependencyGraph.edges.add(edgeCookie);
            }
        } else if (o.getClass() == StructuredObject.class) {
            check_cookie(cookie,o,to,from,dependencyGraph);
        }
    }

}
