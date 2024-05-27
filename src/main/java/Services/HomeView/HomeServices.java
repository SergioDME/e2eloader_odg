package Services.HomeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Entity.UltimateThreadGroup;
import View.Home;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

public class HomeServices {

    public static ActionListener actionPathSearch(Home home) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String path = home.getPathJField().getText();
                ArrayList<String> results =  new ArrayList<>();
                File[] files = new File(path).listFiles();
                for(File file : files) {
                    if(file.isFile()) {
                        results.add(file.getName());
                    }
                }
                E2eTestTableModel e2eTestTableModel = new E2eTestTableModel(results);
                home.getE2eTestCases().setModel(e2eTestTableModel);
                home.getE2eTestCases().getColumnModel().getColumn(0).setMaxWidth(30);
                home.getE2eTestCases().getColumnModel().getColumn(2).setMaxWidth(30);
            }
        };
    }


    public static ActionListener actionSaveButton(Home home) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = home.getScriptNameJText().getText();
                if(name.equals("")) {
                    JOptionPane.showMessageDialog(null,"The name of script is required!","Error",JOptionPane.ERROR_MESSAGE);
                }else {
                    Boolean validrows = true;
                    for(int i=0; i< home.getScriptChosenTable().getRowCount();i++) {
                        validrows = checkRowValidityScriptChosenTable(home.getScriptChosenTable(),i);
                        if(!validrows);
                        {
                            break;
                        }
                    }
                  /*  if(validrows) {
                        try {
                            JMeterLoadCreator.runJMeterCreator(((ScriptChosenModelTable)home.getScriptChosenTable().getModel()).getThredGroups(),name);
                            JOptionPane.showMessageDialog(null,"Script created!","Success",JOptionPane.INFORMATION_MESSAGE);
                        } catch (ParserConfigurationException | IOException e) {
                            JOptionPane.showMessageDialog(null,e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
                        } catch (TransformerConfigurationException e) {
                            JOptionPane.showMessageDialog(null,e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
                        } catch (TransformerException e) {
                            JOptionPane.showMessageDialog(null,e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
                        }
                    }

                   */
                }
            }
        };
    }

    private static boolean checkRowValidityScriptChosenTable(JTable table, int i) {
        String threadcount = (String) table.getValueAt(i,1);
        String initaldelay = (String) table.getValueAt(i,2);
        String startup = (String) table.getValueAt(i,3);
        String hold = (String )table.getValueAt(i,4);
        String ShutdownTime = (String)table.getValueAt(i,5);
        if(threadcount.equals("")) {
            JOptionPane.showMessageDialog(null,"row "+i+" Threads Count field is required!","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(initaldelay.equals("")) {
            JOptionPane.showMessageDialog(null,"row "+i+" Initial delay field is required!","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(startup.equals("")) {
            JOptionPane.showMessageDialog(null,"row "+i+" Startup Time is required!","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(hold.equals("")) {
            JOptionPane.showMessageDialog(null,"row "+i+" Hold Load For field is required!","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(ShutdownTime.equals("")) {
            JOptionPane.showMessageDialog(null,"row "+i+" Shutdown Time field is required!","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean checkFileExistByExt (String e2etest, String path, String ext){
        File e2e_file = new File(e2etest);
        int dotIndex = e2e_file.getName().indexOf('.');
        String file_name = (dotIndex == -1) ? e2e_file.getName() : e2e_file.getName().substring(0, dotIndex);
        File odg_folder = new File(path);
        File file = new File(odg_folder, file_name + "."+ext);
        return  file.exists();
    }



}
