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

        String correlation_selected= "create_and_delete_room-postdata.json";
        String filename = "create_and_delete_room";
        String e2eTestsPath = "/home/sergio/IdeaProjects/booker-platform-e2e-tests/cypress/e2e";
        try {
            //Converter.runMain(filename,filename+".har",e2eTestsPath);
            System.out.println("after convert!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
        try {
            DependencyGraph dependencyGraph = DependencyGraph.parseGraphByFile(new File(Paths.dep_saved_path+"/"+correlation_selected));
            //String nameCorr = CorrelationNameSelected.split("-")[1].split("\\.")[0];

            JMeterAdaption.runJMeterAdaption(dependencyGraph,Paths.scripts_saved_path+"/"+filename+".jmx",correlation_selected);
                    /*ArrayList<String> list = getFilesByNameAndPath(filename+"-",script_path);
                    correlationFrame.getScriptTable().setModel(
                            new ScriptsTableModel(list)
                    );*/
            JOptionPane.showMessageDialog(null,"Script created!","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
