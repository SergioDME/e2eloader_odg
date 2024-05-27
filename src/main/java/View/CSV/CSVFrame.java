/*
 * Created by JFormDesigner on Mon Jul 25 17:00:23 CEST 2022
 */

package View.CSV;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class CSVFrame extends JFrame {
    public CSVFrame() {
        initComponents();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        pathCSV = new JTextField();
        CSVButton = new JButton();
        label8 = new JLabel();
        scrollPane1 = new JScrollPane();
        csvArea = new JTextArea();
        panel2 = new JPanel();
        label2 = new JLabel();
        VariableNames = new JTextField();
        panel3 = new JPanel();
        label3 = new JLabel();
        FileEncodingComboBox = new JComboBox();
        panel4 = new JPanel();
        label4 = new JLabel();
        firstLineCheckBox = new JCheckBox();
        panel6 = new JPanel();
        label5 = new JLabel();
        panel7 = new JPanel();
        manuallyCheckBox = new JCheckBox();
        panel8 = new JPanel();
        label6 = new JLabel();
        filenameCSVCreate = new JTextField();
        label7 = new JLabel();
        panel5 = new JPanel();
        saveButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(5, 5));

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout(5, 5));

            //---- label1 ----
            label1.setText("path csv: ");
            panel1.add(label1, BorderLayout.WEST);
            panel1.add(pathCSV, BorderLayout.CENTER);

            //---- CSVButton ----
            CSVButton.setText("Preview");
            panel1.add(CSVButton, BorderLayout.EAST);

            //---- label8 ----
            label8.setText("you can view or create a CSV file in this text area ");
            label8.setFont(new Font("JetBrains Mono", Font.ITALIC, 10));
            panel1.add(label8, BorderLayout.SOUTH);
        }
        contentPane.add(panel1, BorderLayout.NORTH);

        //======== scrollPane1 ========
        {

            //---- csvArea ----
            csvArea.setMaximumSize(new Dimension(200, 200));
            csvArea.setMinimumSize(new Dimension(200, 200));
            csvArea.setPreferredSize(new Dimension(200, 200));
            scrollPane1.setViewportView(csvArea);
        }
        contentPane.add(scrollPane1, BorderLayout.CENTER);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout(7, 7));

            //---- label2 ----
            label2.setText("Variable Names (comma-delimited):");
            panel2.add(label2, BorderLayout.WEST);
            panel2.add(VariableNames, BorderLayout.CENTER);

            //======== panel3 ========
            {
                panel3.setLayout(new BorderLayout(5, 5));

                //---- label3 ----
                label3.setText("File encoding:");
                panel3.add(label3, BorderLayout.WEST);
                panel3.add(FileEncodingComboBox, BorderLayout.CENTER);

                //======== panel4 ========
                {
                    panel4.setLayout(new BorderLayout(5, 5));

                    //---- label4 ----
                    label4.setText("Ignore first line (Only used if Variable Names is not empty):");
                    panel4.add(label4, BorderLayout.WEST);
                    panel4.add(firstLineCheckBox, BorderLayout.CENTER);

                    //======== panel6 ========
                    {
                        panel6.setLayout(new BorderLayout(5, 5));

                        //---- label5 ----
                        label5.setText("csv file manually written");
                        panel6.add(label5, BorderLayout.WEST);

                        //======== panel7 ========
                        {
                            panel7.setLayout(new BorderLayout(5, 5));
                            panel7.add(manuallyCheckBox, BorderLayout.WEST);

                            //======== panel8 ========
                            {
                                panel8.setLayout(new BorderLayout(5, 5));

                                //---- label6 ----
                                label6.setText("filename:");
                                panel8.add(label6, BorderLayout.WEST);
                                panel8.add(filenameCSVCreate, BorderLayout.CENTER);

                                //---- label7 ----
                                label7.setText(".csv");
                                panel8.add(label7, BorderLayout.EAST);
                            }
                            panel7.add(panel8, BorderLayout.CENTER);
                        }
                        panel6.add(panel7, BorderLayout.CENTER);
                    }
                    panel4.add(panel6, BorderLayout.NORTH);
                }
                panel3.add(panel4, BorderLayout.NORTH);
            }
            panel2.add(panel3, BorderLayout.NORTH);

            //======== panel5 ========
            {
                panel5.setLayout(new BorderLayout(4, 4));

                //---- saveButton ----
                saveButton.setText("SAVE");
                saveButton.setBackground(new Color(245,121,0));
                saveButton.setForeground(Color.WHITE);
                panel5.add(saveButton, BorderLayout.CENTER);
            }
            panel2.add(panel5, BorderLayout.SOUTH);
        }
        contentPane.add(panel2, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JTextField pathCSV;
    private JButton CSVButton;
    private JLabel label8;
    private JScrollPane scrollPane1;
    private JTextArea csvArea;
    private JPanel panel2;
    private JLabel label2;
    private JTextField VariableNames;
    private JPanel panel3;
    private JLabel label3;
    private JComboBox FileEncodingComboBox;
    private JPanel panel4;
    private JLabel label4;
    private JCheckBox firstLineCheckBox;
    private JPanel panel6;
    private JLabel label5;
    private JPanel panel7;
    private JCheckBox manuallyCheckBox;
    private JPanel panel8;
    private JLabel label6;
    private JTextField filenameCSVCreate;
    private JLabel label7;
    private JPanel panel5;
    private JButton saveButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JTextField getPathCSV() {
        return pathCSV;
    }

    public JButton getCSVButton() {
        return CSVButton;
    }

    public JTextArea getCsvArea() {
        return csvArea;
    }

    public JTextField getVariableNames() {
        return VariableNames;
    }

    public JComboBox getFileEncodingComboBox() {
        return FileEncodingComboBox;
    }

    public JCheckBox getFirstLineCheckBox() {
        return firstLineCheckBox;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JTextField getFilenameCSVCreate() {
        return filenameCSVCreate;
    }

    public JCheckBox getManuallyCheckBox() {
        return manuallyCheckBox;
    }


}
