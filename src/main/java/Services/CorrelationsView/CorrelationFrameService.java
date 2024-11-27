package Services.CorrelationsView;

import Entity.DependencyGraph;
import Entity.Har;
import Entity.UltimateThreadGroup;
import Properties.Paths;
import Services.CorrelationsView.ScriptGeneration.Converter;
import Services.CorrelationsView.ScriptGeneration.JMeterAdaption;
import Services.CustomizeCorrelationView.CorrelatorHelperService;
import Services.Dependencies.ExtractDependencies;
import Services.HomeView.E2eTestTableModel;
import Services.HomeView.WorkloadTableModel;
import Services.Utils;
import View.CorrelationsPage.CorrelationFrame;
import View.CorrelationsPage.CorrelationTableModel;
import View.CorrelationsPage.ScriptsTableModel;
import View.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CorrelationFrameService {

    public static CorrelationFrame openCorrelationFrame (String filename,String harpath,String harfilename,Home home){
        String path = home.getPathJField().getText();
        CorrelationFrame correlationFrame = null;
        try {
            correlationFrame = new CorrelationFrame();
        }catch (Exception e) {

        }
        correlationFrame.setTitle(filename+" - Select the script");

        correlationFrame.getCorrelationsTable().setModel(
                new CorrelationTableModel(Utils.getFilesByNameAndPath(filename, Paths.dep_saved_path))
        );

        correlationFrame.getScriptTable().setModel(
                new ScriptsTableModel(Utils.getFilesByNameAndPath(filename,Paths.scripts_saved_path))
        );

        correlationFrame.getFileComboBox().setModel( new DefaultComboBoxModel(Utils.getFilesByNameAndPath(filename,Paths.dep_saved_path).toArray()));
        correlationFrame.getDeleteButton().addActionListener(deleteButtonAction(correlationFrame));
        correlationFrame.setFileHAR(harpath+"/"+harfilename);
        correlationFrame.getCreateScriptButton().addActionListener( actionListnerScriptButton(path,correlationFrame,harpath+"/"+harfilename,filename));
        //correlationFrame.setFileJSON(correlations_path+"\\"+filename.substring(0,pos)+"-correlations.json");
        correlationFrame.setE2eName(filename);
        correlationFrame.getAddButton().addActionListener(addCorrelationActions(correlationFrame,filename, new File(harpath+"/"+harfilename)));
        correlationFrame.getSaveButton().addActionListener(addSaveButtonAction(correlationFrame,home));

        return correlationFrame;

    }

    private static ActionListener deleteButtonAction(CorrelationFrame correlationFrame){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String filename = correlationFrame.getFileComboBox().getSelectedItem().toString();
                File file = new File(Paths.dep_saved_path+"/"+filename);
                if(file.delete()) {
                    JOptionPane.showMessageDialog(null,filename+" deleted!");
                    ((CorrelationTableModel)correlationFrame.getCorrelationsTable().getModel()).getFilename().remove(filename);
                    ((CorrelationTableModel)correlationFrame.getCorrelationsTable().getModel()).fireTableDataChanged();
                    correlationFrame.getFileComboBox().setModel(new DefaultComboBoxModel(
                            ((CorrelationTableModel)correlationFrame.getCorrelationsTable().getModel()).getFilename().toArray()
                    ));
                }else {
                    JOptionPane.showMessageDialog(null,"Failed!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }


    private static ActionListener addCorrelationActions(CorrelationFrame correlationFrame,String filename,File har_file){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File file_json_odg = new File(Paths.odp_path+"/"+filename+".json");
                DependencyGraph dependencyGraph =null;
                Har har = null;
                ExtractDependencies extractDependencies = null;
                try {
                    extractDependencies = new ExtractDependencies(har_file);
                    har = extractDependencies.getHar();
                    extractDependencies.analyze_responses();
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
                        dependencyGraph = extractDependencies.build_dependencies_graph();
                        dependencyGraph.saveDependencyGraph(dependencyGraph, har_file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                CorrelatorHelperService correlatorHelperService = new CorrelatorHelperService(extractDependencies.getResponseUnstructuredList(),dependencyGraph,har,correlationFrame.getE2eName(),correlationFrame);
                correlatorHelperService.runCustomizeCorrelationFrame();
            }
        };
    }

    private static ActionListener addSaveButtonAction(CorrelationFrame correlationFrame, Home home) {
        return  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String ScripNameChosen="";
                int cnt=0;
                JTable scriptTable = correlationFrame.getScriptTable();
                for(int i = 0; i< scriptTable.getRowCount(); i++) {
                    if((boolean) scriptTable.getValueAt(i,1)==true) {
                        cnt++;
                        ScripNameChosen=(String) scriptTable.getValueAt(i,0);
                    }
                }
                if(cnt>1) {
                    JOptionPane.showMessageDialog(null,"Only a correlations file must be selected","Error",JOptionPane.ERROR_MESSAGE);
                }else if(!ScripNameChosen.equals("")) {

                    ((WorkloadTableModel)home.getScriptChosenTable().getModel()).getThredGroups().add( new UltimateThreadGroup(
                            "","","","","",ScripNameChosen
                    ));
                    ((WorkloadTableModel) home.getScriptChosenTable().getModel()).fireTableDataChanged();

                    String filename = ScripNameChosen.split("_")[0];
                    int index=0;

                    for(String name : ((E2eTestTableModel)home.getE2eTestCases().getModel()).getFilename()) {
                        if(name.contains(filename)){
                            break;
                        }
                        index++;
                    }
                    ((E2eTestTableModel)home.getE2eTestCases().getModel()).getFilenameCheck().set(index,true);
                    ((E2eTestTableModel) home.getE2eTestCases().getModel()).fireTableDataChanged();
                    correlationFrame.dispose();
                }
            }
        };
    }

    private static ActionListener actionListnerScriptButton(String e2eTestsPath, CorrelationFrame correlationFrame,String filehar,String filename) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String correlation_selected= "";
                int cnt=0;
                for(int i=0;i<correlationFrame.getCorrelationsTable().getRowCount();i++) {
                    if((boolean)correlationFrame.getCorrelationsTable().getValueAt(i,1)==true) {
                        cnt++;
                        correlation_selected=(String) correlationFrame.getCorrelationsTable().getValueAt(i,0);
                    }
                }
                if(cnt>1 || correlation_selected.equals("")) {
                    JOptionPane.showMessageDialog(null,"Only a correlations file must be selected","Error",JOptionPane.ERROR_MESSAGE);
                } else
                {
                    try {
                        Converter.runMain(filename,filename+".har",e2eTestsPath);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                        System.out.println(e.toString());
                    }
                    try {
                        DependencyGraph dependencyGraph = DependencyGraph.parseGraphByFile(new File(Paths.dep_saved_path+"/"+correlation_selected));
                        //String nameCorr = CorrelationNameSelected.split("-")[1].split("\\.")[0];
                        JMeterAdaption.runJMeterAdaption(dependencyGraph,Paths.scripts_saved_path+"/"+filename+".jmx",correlation_selected);
                        correlationFrame.getScriptTable().setModel(
                                new ScriptsTableModel(Utils.getFilesByNameAndPath(filename,Paths.scripts_saved_path))
                        );
                        JOptionPane.showMessageDialog(null,"Script created!","Success",JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                 }
            }
        };

    }




}
