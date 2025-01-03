package Services.Dependencies;

import Entity.*;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.ResponseUnstructured;
import Services.ResponseAnalyzer.StructuredObject;

import java.util.HashSet;
import java.util.List;

public class HeaderDependency {

    private final static HashSet<String> blackListHeaders = new HashSet<>() {{
        add("sec-ch-ua");
        add("sec-ch-ua-platform");
        add("Accept-Language");
        add("User-Agent");
        add("sec-ch-ua-mobile");
        add("baggage");
        add("sentry-trace");
    }};
    public static void check_header_dependency(List<ResponseUnstructured> responseUnstructuredList, int req_index, DependencyGraph dependencyGraph, MyNode to,int first_index_response) {
        for( Header header: to.getRequest().getHeaders()){
            if(!blackListHeaders.contains(header.getName())) {
                for (int response_index = first_index_response; response_index < req_index; response_index++) {
                    MyNode from = dependencyGraph.getNodeByIndex(response_index);
                    check_header(header, responseUnstructuredList.get(response_index),to,from,dependencyGraph,req_index,response_index);
                }
            }
        }
    }

    private static void check_header(Header header, Object response, MyNode to, MyNode from, DependencyGraph dependencyGraph,int req_index,int from_index) {
        if(response.getClass() == ResponseUnstructured.class){
            ResponseUnstructured responseUnstructured = (ResponseUnstructured)response;
            for(Object o : responseUnstructured.getObjects()){
                check_header_atomic_evaluation(o,header,null,to,from,dependencyGraph,req_index,from_index);
            }
        }else if(response.getClass() == StructuredObject.class){
            StructuredObject structuredObject = (StructuredObject) response;
            for(Object o : structuredObject.getObjects()){
                check_header_atomic_evaluation(o,header,structuredObject,to,from,dependencyGraph,req_index,from_index);
            }
        }
    }

    private static void check_header_atomic_evaluation(Object o, Header header, StructuredObject father, MyNode to, MyNode from, DependencyGraph dependencyGraph, int req_index,int from_index) {
        if(o.getClass() == AtomicObject.class){
            AtomicObject atomicObject = (AtomicObject) o;
            AtomicDependencyValidator atomicDependencyValidator = new AtomicDependencyValidator();
            if(atomicDependencyValidator.evaluate_atomic_dependencies(atomicObject,header,father,from.getRequest())){
                EdgeHeader edgeHeader = new EdgeHeader(from, to,header.getName(), atomicObject);
                dependencyGraph.edges.add(edgeHeader);
                edgeHeader.setTo_index(req_index);
                edgeHeader.setFrom_index(from_index);
            }
        } else if (o.getClass() == StructuredObject.class) {
            check_header(header,o,to,from,dependencyGraph,req_index,from_index);
        }
    }
}
