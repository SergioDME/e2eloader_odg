package Services.Dependencies;

import Entity.DependencyGraph;
import Entity.Har;
import Properties.Paths;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String [] args){
        String filename ="AddTravelAtShoppingCart";
        String har_filename ="/home/sergio/Scaricati/AddTravelAtShoppingCart/hars/AddTravelAtShoppingCart.har";
        File har_file = new File(har_filename);
        File file_json_odg = new File(Paths.odp_path+"/"+filename+".json");
        DependencyGraph dependencyGraph =null;
        Har har = null;
        ExtractDependencies extractDependencies = null;
        try {
            extractDependencies = new ExtractDependencies(har_file);
            har = extractDependencies.getHar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file_json_odg.exists()) {
            System.out.println("File exists in the folder.");
            try {
                dependencyGraph = DependencyGraph.parseGraphByFile(file_json_odg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("File does not exist in the folder.");
            try {
                extractDependencies.analyze_responses();
                dependencyGraph = extractDependencies.build_dependencies_graph();
                dependencyGraph.saveDependencyGraph(dependencyGraph, har_file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        dependencyGraph.print_graph();
    }
}
