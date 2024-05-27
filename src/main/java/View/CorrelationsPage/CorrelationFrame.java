/*
 * Created by JFormDesigner on Fri Jun 10 15:52:33 CEST 2022
 */

package View.CorrelationsPage;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class CorrelationFrame extends JFrame {
    public CorrelationFrame() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel5 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        deleteButton = new JButton();
        fileComboBox = new JComboBox();
        panel10 = new JPanel();
        addButton = new JButton();
        panel11 = new JPanel();
        createScriptButton = new JButton();
        label1 = new JLabel();
        panel8 = new JPanel();
        scrollPane2 = new JScrollPane();
        CorrelationsTable = new JTable();
        panel9 = new JPanel();
        scrollPane3 = new JScrollPane();
        scriptTable = new JTable();
        saveButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(5, 5));

        //======== panel5 ========
        {
            panel5.setLayout(new BorderLayout(5, 5));

            //======== panel6 ========
            {
                panel6.setLayout(new BorderLayout(4, 4));

                //======== panel7 ========
                {
                    panel7.setLayout(new BorderLayout(5, 5));

                    //---- deleteButton ----
                    deleteButton.setText("");
                    ImageIcon delete_icon = new ImageIcon("src/data/img/delete-30.png");
                    deleteButton.setIcon(delete_icon);
                    panel7.add(deleteButton, BorderLayout.EAST);
                    panel7.add(fileComboBox, BorderLayout.CENTER);

                    //======== panel10 ========
                    {
                        panel10.setLayout(new BorderLayout(4, 4));

                        //---- addButton ----
                        addButton.setText("");
                        ImageIcon add_icon = new ImageIcon("src/data/img/add-25.png");
                        addButton.setIcon(add_icon);
                        //addButton.setBackground(new Color(34,139,34));
                        panel10.add(addButton, BorderLayout.WEST);

                        //======== panel11 ========
                        {
                            panel11.setLayout(new BorderLayout(5, 5));

                            //---- createScriptButton ----
                            createScriptButton.setText("CREATE SCRIPT");
                            createScriptButton.setBackground(new Color(245,121,0));
                            createScriptButton.setForeground(new Color(255,255,255));
                            panel11.add(createScriptButton, BorderLayout.WEST);
                        }
                        panel10.add(panel11, BorderLayout.CENTER);
                    }
                    panel7.add(panel10, BorderLayout.NORTH);

                    //---- label1 ----
                   // label1.setText("Select correlation to delete:");
                    // panel7.add(label1, BorderLayout.WEST);
                }
                panel6.add(panel7, BorderLayout.CENTER);
            }
            panel5.add(panel6, BorderLayout.NORTH);

            //======== panel8 ========
            {
                panel8.setLayout(new BorderLayout(5, 5));

                //======== scrollPane2 ========
                {
                    scrollPane2.setMaximumSize(new Dimension(200, 200));
                    scrollPane2.setMinimumSize(new Dimension(200, 200));
                    scrollPane2.setPreferredSize(new Dimension(100, 100));
                    scrollPane2.setViewportView(CorrelationsTable);
                }
                panel8.add(scrollPane2, BorderLayout.NORTH);

                //======== panel9 ========
                {
                    panel9.setLayout(new BorderLayout(5, 5));

                    //======== scrollPane3 ========
                    {
                        scrollPane3.setPreferredSize(new Dimension(150, 150));
                        scrollPane3.setViewportView(scriptTable);
                    }
                    panel9.add(scrollPane3, BorderLayout.CENTER);

                    //---- saveButton ----
                    saveButton.setText("ADD TO THE WORKLOAD");
                    saveButton.setBackground(new Color(245,121,0));
                    saveButton.setForeground(Color.WHITE);
                    panel9.add(saveButton, BorderLayout.SOUTH);
                }
                panel8.add(panel9, BorderLayout.CENTER);
            }
            panel5.add(panel8, BorderLayout.CENTER);
        }
        contentPane.add(panel5, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JButton deleteButton;
    private JComboBox fileComboBox;
    private JPanel panel10;
    private JButton addButton;
    private JPanel panel11;
    private JButton createScriptButton;
    private JLabel label1;
    private JPanel panel8;
    private JScrollPane scrollPane2;
    private JTable CorrelationsTable;
    private JPanel panel9;
    private JScrollPane scrollPane3;
    private JTable scriptTable;
    private JButton saveButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JButton getCreateScriptButton() {
        return createScriptButton;
    }

    public void setCreateScriptButton(JButton createScriptButton) {
        this.createScriptButton = createScriptButton;
    }

    public JTable getScriptTable() {
        return scriptTable;
    }

    public void setScriptTable(JTable scriptTable) {
        this.scriptTable = scriptTable;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JComboBox getFileComboBox() {
        return fileComboBox;
    }

    public void setFileComboBox(JComboBox fileComboBox) {
        this.fileComboBox = fileComboBox;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JTable getCorrelationsTable() {
        return CorrelationsTable;
    }

    public void setCorrelationsTable(JTable correlationsTable) {
        CorrelationsTable = correlationsTable;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public String getFileHAR() {
        return fileHAR;
    }

    public void setFileHAR(String fileHAR) {
        this.fileHAR = fileHAR;
    }

    public String getFileJSON() {
        return fileJSON;
    }

    public void setFileJSON(String fileJSON) {
        this.fileJSON = fileJSON;
    }

    private String fileHAR;
    private String fileJSON;

    public String getE2eName() {
        return e2eName;
    }

    public void setE2eName(String e2eName) {
        this.e2eName = e2eName;
    }

    private String e2eName;
}
