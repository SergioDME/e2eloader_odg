package Services.CustomizeCorrelationView.CSV;

import Entity.CSVNode;
import Properties.Paths;
import View.AddManuallyCorrelationPage.AddManuallyCorrelationFrame;
import View.AddManuallyCorrelationPage.CustomCorrelationTableModel;
import View.CSV.CSVFrame;
import View.CustomizeCorrelationPage.CustomizeCorrelationPage;
import org.jfree.data.io.CSV;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class CSVFrameService {


    public static ActionListener CSVFrameOpen(AddManuallyCorrelationFrame addManuallyCorrelationFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CSVFrame csvFrame = null;
                try {
                    csvFrame = new CSVFrame();
                } catch (Exception e) {
                }

                String[] encodeType = {"UTF-8", "URF-16", "ISO-8859-15", "US-ASCII", "Edit"};
                csvFrame.getFileEncodingComboBox().setModel(new DefaultComboBoxModel<>(encodeType));
                csvFrame.getCSVButton().addActionListener(ActionPreviewButton(csvFrame));
                csvFrame.getSaveButton().addActionListener(ActionAddButtonCSVFrame(csvFrame,addManuallyCorrelationFrame));
                csvFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                csvFrame.setSize(1000, 800);
                csvFrame.setLocationRelativeTo(null);
                csvFrame.setTitle("Import or create CSV file");
                csvFrame.setVisible(true);
            }
        };
    }


    public static ActionListener ActionPreviewButton(CSVFrame csvFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String path = csvFrame.getPathCSV().getText();

                FileReader reader = null;
                try {
                    reader = new FileReader(path);
                    BufferedReader br = new BufferedReader(reader);
                    String content = "";
                    String strCurrentLine = "";
                    while ((strCurrentLine = br.readLine()) != null) {
                        content = content + "\n" + strCurrentLine;
                    }
                    csvFrame.getCsvArea().setText(content);
                    br.close();
                    csvFrame.getCsvArea().requestFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    public static ActionListener ActionAddButtonCSVFrame(CSVFrame csvFrame, AddManuallyCorrelationFrame addManuallyCorrelationFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //save csv file if entered manually
                String path = "";
                if (csvFrame.getManuallyCheckBox().isSelected()) {
                    String filename = csvFrame.getFilenameCSVCreate().getText();
                    if (filename.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Filename is required!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            FileWriter writer = new FileWriter(Paths.csv_path+"/"+ filename + ".csv");
                            BufferedWriter bw = new BufferedWriter(writer);
                            csvFrame.getCsvArea().write(bw);
                            bw.close();
                            csvFrame.getCsvArea().setText("");
                            csvFrame.getCsvArea().requestFocus();
                            path = Paths.csv_path+"/"+filename+".csv";
                            JOptionPane.showMessageDialog(null,"CSV file "+filename+".csv saved!","",JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }else{

                    if (path.isEmpty()) {
                        if (csvFrame.getPathCSV().getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Path CSV is required!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            path = csvFrame.getPathCSV().getText();
                        }
                    }
                }

                boolean ignorefirstLine = csvFrame.getFirstLineCheckBox().isSelected();
                String encoding = (String) csvFrame.getFileEncodingComboBox().getSelectedItem();
                String variablesName = csvFrame.getVariableNames().getText();
                if (variablesName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Variables name are required!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                CSVNode csvNode = new CSVNode(ignorefirstLine, encoding, variablesName, path);
                addManuallyCorrelationFrame.getCustomCorrelationTableModel().cleanItems();
                addManuallyCorrelationFrame.getCustomCorrelationTableModel().getItems().add(new CustomCorrelationTableModel.ResponseParamCheckable(csvNode));
                //add to correlationHelper
                csvFrame.dispose();
            }
        };
    }

}