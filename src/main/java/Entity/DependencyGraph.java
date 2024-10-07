package Entity;

import Services.Dependencies.TypeEdgeAdaptor;
import Services.Dependencies.TypeFromNodeAdaptor;
import Services.ResponseAnalyzer.AtomicObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;
import Properties.Paths;
import org.jfree.data.io.CSV;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DependencyGraph implements Serializable {

    public List<MyNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<MyNode> nodes) {
        this.nodes = nodes;
    }

    @SerializedName("nodes")
    public List<MyNode> nodes;
    @SerializedName("edges")
    public List<Edge> edges;

    public DependencyGraph() {
        this.nodes = new ArrayList<>();
        this.edges= new ArrayList<>();
    }



    public MyNode addRequestoToGraph(Request request, int index)
    {
        for(MyNode node : nodes)
        {
            if(node.request.equals(request)){
                node.indexs.add(index);
                return  node;
            }
        }
        MyNode node = new MyNode(request);
        node.indexs.add(index);
        nodes.add(node);
        return node;
    }

    public void print_graph(){
        System.out.println("\n----------------------------NODES["+nodes.size()+"]-----------------------------------\n");
        for(MyNode node: nodes)
            System.out.println(node);
        System.out.println("\n----------------------------EDGES["+edges.size()+"]-----------------------------------\n");
        for(Edge edge :edges)
            System.out.println(edge);
    }

    public MyNode getNodeByIndex(int responseIndex) {
        MyNode res = null;
        for(MyNode node : nodes){
            if(node.indexs.contains(responseIndex))
                res = node;
        }
        return res;
    }

    public boolean saveDependencyGraph(DependencyGraph dependencyGraph, File file_har){
        dependencyGraph.print_graph();
        // Salvataggio della struttura dati su un file
        int dotIndex = file_har.getName().lastIndexOf('.');
        String file_name =  (dotIndex == -1) ? file_har.getName() : file_har.getName().substring(0, dotIndex);
        Gson gson= new GsonBuilder()
                .registerTypeAdapter(MyNode.class,new TypeFromNodeAdaptor())
                .registerTypeAdapter(Edge.class, new TypeEdgeAdaptor())
       //         .registerTypeAdapterFactory(new EdgeAdapterFactory())
                .create();
        String graph_json = gson.toJson(dependencyGraph);




        boolean res;
        try {
            FileWriter file = new FileWriter(Paths.odp_path+"/"+file_name+".json");
            file.write(graph_json);
            file.flush();
            file.close();
            System.out.println("ODP correctly saved in the file named "+file_name+".json");
            res=true;
        } catch (IOException e) {
            res=false;
            e.printStackTrace();
        }
        return  res;
    }

    public static DependencyGraph parseGraphByFile(File file_json_odg) throws IOException {
        // Parsing della struttura dati da file
        Gson gson= new GsonBuilder()
                .registerTypeAdapter(MyNode.class, new TypeFromNodeAdaptor())
                .registerTypeAdapter(Edge.class, new TypeEdgeAdaptor())
                .create();
        String graph_json_string = FileUtils.readFileToString(file_json_odg, UTF_8);

        return gson.fromJson(graph_json_string,DependencyGraph.class);
    }



    public List<Edge> getDependenciesByNode(MyNode to){
        List<Edge> dependencies = new ArrayList<>();
        for(Edge edge : this.edges){
            if(edge.to.equals(to)){
                dependencies.add(edge);
            }
        }
        return  dependencies;
    }


    public List<Object> getDependenciesToExtractToFromNode(MyNode from,int index){
        List<Object> dependencies = new ArrayList<>();
        //int index_of_index = from.indexs.indexOf(index);
        //if(index_of_index>0)
        //        return dependencies;

        for(Edge edge : this.edges){
            if(edge.from != null && edge.from.equals(from) && edge.from_index==index){
                if(!dependencies.contains(edge.dependency))
                {
                    if(edge.type.equals("bodyjson")){
                        EdgeBodyJSON edgeBodyJSON = (EdgeBodyJSON) edge;
                        if(edgeBodyJSON.isPrimitive()){
                            if(!edgeBodyJSON.dependency.from_set_cookie)
                                dependencies.add(edgeBodyJSON.dependency);
                        }else{
                            dependencies.add(edgeBodyJSON.structuredObject);
                        }
                    }
                    else {
                        if(!edge.dependency.from_set_cookie)
                            dependencies.add(edge.dependency);
                    }
                }
            }
        }
        return  dependencies;
    }

    public MyNode getNodeByUrlandMethod(String url, String method){
        for(MyNode node : nodes){
            if(node.request.getUrl().equals(url)&& node.request.getMethod().equals(method)){
                return node;
            }
        }
        return  null;
    }

    public List<CSVNode> getCSVNodeDependencies() {
        List<CSVNode> results = new ArrayList<>();
        for(Edge edge: this.edges){
            if(edge.from != null && edge.from.getClass().equals(CSVNode.class)){
                if(!results.contains(edge.from)){
                    results.add((CSVNode) edge.from);
                }
            }
        }
        return results;
    }
}
