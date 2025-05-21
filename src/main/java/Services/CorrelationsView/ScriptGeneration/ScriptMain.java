package Services.CorrelationsView.ScriptGeneration;

import Entity.DependencyGraph;
import Entity.MyNode;
import Properties.Paths;
import Services.ResponseAnalyzer.AtomicObject;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptMain {
    public static void main (String []args){

        String correlation_selected= "report_details-example.json";
        String filename = "report_details";
        try {
            System.out.println("after convert!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
        try {
            DependencyGraph dependencyGraph = DependencyGraph.parseGraphByFile(new File(Paths.dep_saved_path+"/"+correlation_selected));
            //JMeterAdaption.runJMeterAdaption(dependencyGraph,Paths.scripts_saved_path+"/"+filename+".jmx",correlation_selected);
            JOptionPane.showMessageDialog(null,"Script created!","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e.toString());
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
