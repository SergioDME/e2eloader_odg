package Entity;

import Services.Dependencies.EdgeAdapterFactory;
import Services.Dependencies.TypeEdgeAdaptor;
import Services.Dependencies.TypeFromNodeAdaptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;
import Properties.Paths;
import static java.nio.charset.StandardCharsets.UTF_8;

public class DependencyGraph implements Serializable {

    @SerializedName("nodes")
    public List<Node> nodes;
    @SerializedName("edges")
    public List<Edge> edges;

    public DependencyGraph() {
        this.nodes = new ArrayList<>();
        this.edges= new ArrayList<>();
    }

    public Node addRequestoToGraph(Request request, int index){ // the method returns null if the request was already analyzed
        for(Node node : nodes){
            if(node.request.equals(request)){
                node.indexs.add(index);
                return  null;
            }
        }
        Node node = new Node(request);
        node.indexs.add(index);
        nodes.add(node);
        return node;
    }

    public void print_graph(){
        System.out.println("\n----------------------------NODES["+nodes.size()+"]-----------------------------------\n");
        for(Node node: nodes)
            System.out.println(node);
        System.out.println("\n----------------------------EDGES["+edges.size()+"]-----------------------------------\n");
        for(Edge edge :edges)
            System.out.println(edge);
    }

    public Node getNodeByIndex(int responseIndex) {
        Node res = null;
        for(Node node : nodes){
            if(node.indexs.contains(responseIndex))
                res = node;
        }
        return res;
    }

    public boolean saveDependencyGraph(DependencyGraph dependencyGraph, File file_har){
        // Salvataggio della struttura dati su un file
        int dotIndex = file_har.getName().lastIndexOf('.');
        String file_name =  (dotIndex == -1) ? file_har.getName() : file_har.getName().substring(0, dotIndex);
        Gson gson= new GsonBuilder()
                .registerTypeAdapter(Node.class,new TypeFromNodeAdaptor())
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
                .registerTypeAdapter(Node.class, new TypeFromNodeAdaptor())
                .registerTypeAdapter(Edge.class, new TypeEdgeAdaptor())
                .create();
        String graph_json_string = FileUtils.readFileToString(file_json_odg, UTF_8);

        return gson.fromJson(graph_json_string,DependencyGraph.class);
    }



    public List<Edge> getDependenciesByNode(Node to){
        List<Edge> dependencies = new ArrayList<>();
        for(Edge edge : this.edges){
            if(edge.to.equals(to)){
                dependencies.add(edge);
            }
        }
        return  dependencies;
    }

    public Node getNodeByUrl(String url){
        for(Node node : nodes){
            if(node.request.getUrl().equals(url)){
                return node;
            }
        }
        return  null;
    }
}
