/*
 * Created by JFormDesigner on Mon May 09 14:55:57 CEST 2022
 */

package View.AddManuallyCorrelationPage;

import Entity.CheckableItem;
import Services.CustomizeCorrelationView.CorrelatorHelperService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author unknown
 */
public class AddManuallyCorrelationFrame extends JFrame {
    public AddManuallyCorrelationFrame() {
        initComponents();
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel3 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        nameLabel = new JLabel();
        nameComboBox = new JComboBox();
        numReqPanel = new JPanel();
        numReqLabel = new JLabel();
        numReqComboBox = new JComboBox();
        panel8 = new JPanel();
        panel9 = new JPanel();
        fromLabel = new JLabel();
        fromComboBox = new JComboBox();
        panel16 = new JPanel();
        filterJLabel = new JLabel();
        filterJtext = new JTextField();
        filterButton = new JButton();
        panel10 = new JPanel();
        panel11 = new JPanel();
        optionLabel = new JLabel();
        panel12 = new JPanel();
        optionCheckBox = new JCheckBox();
        optionText = new JTextField();
        panel15 = new JPanel();
        importCSVButton = new JButton();
        panel13 = new JPanel();
        panel14 = new JPanel();
        valueLabel = new JLabel();
        panel2 = new JPanel();
        regExLabel = new JLabel();
        regExField = new JTextField();
        panel1 = new JPanel();
        save = new JButton();
        scrollPaneValueTable = new JScrollPane();
        valueTable = new JTable();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(5, 5));

        //======== panel3 ========
        {
            panel3.setLayout(new BorderLayout(5, 5));

            //======== panel6 ========
            {
                panel6.setLayout(new BorderLayout(5, 5));

                //======== panel7 ========
                {
                    panel7.setLayout(new BorderLayout(5, 5));

                    //---- nameLabel ----
                    nameLabel.setText("Name");
                    panel7.add(nameLabel, BorderLayout.WEST);
                    panel7.add(nameComboBox, BorderLayout.CENTER);

                    //======== numReqPanel ========
                    {
                        numReqPanel.setLayout(new BorderLayout(5, 5));

                        //---- numReqLabel ----
                        numReqLabel.setText("Request n\u00b0");
                        numReqPanel.add(numReqLabel, BorderLayout.WEST);
                        numReqPanel.add(numReqComboBox, BorderLayout.CENTER);
                    }
                    panel7.add(numReqPanel, BorderLayout.EAST);
                }
                panel6.add(panel7, BorderLayout.NORTH);

                //======== panel8 ========
                {
                    panel8.setLayout(new BorderLayout());

                    //======== panel9 ========
                    {
                        panel9.setLayout(new BorderLayout(10, 10));

                        //---- fromLabel ----
                        fromLabel.setText("From");
                        panel9.add(fromLabel, BorderLayout.WEST);
                        panel9.add(fromComboBox, BorderLayout.CENTER);

                        //======== panel16 ========
                        {
                            panel16.setLayout(new BorderLayout(5, 5));

                            //---- filterJLabel ----
                            filterJLabel.setText("Filter by name");
                            panel16.add(filterJLabel, BorderLayout.WEST);
                            panel16.add(filterJtext, BorderLayout.CENTER);

                            //---- filterButton ----
                            filterButton.setText("");
                            ImageIcon filter_icon = new ImageIcon("src/data/img/filter-25.png");
                            filterButton.setIcon(filter_icon);
                            panel16.add(filterButton, BorderLayout.EAST);
                        }
                        panel9.add(panel16, BorderLayout.SOUTH);
                    }
                    panel8.add(panel9, BorderLayout.NORTH);

                    //======== panel10 ========
                    {
                        panel10.setLayout(new BorderLayout(5, 5));

                        //======== panel11 ========
                        {
                            panel11.setLayout(new BorderLayout(10, 10));

                            //---- optionLabel ----
                            optionLabel.setText("Enter value manually");
                            panel11.add(optionLabel, BorderLayout.WEST);

                            //======== panel12 ========
                            {
                                panel12.setLayout(new BorderLayout(2, 2));
                                panel12.add(optionCheckBox, BorderLayout.WEST);
                                panel12.add(optionText, BorderLayout.CENTER);

                                //======== panel15 ========
                                {
                                    panel15.setLayout(new BorderLayout(5, 5));

                                    //---- importCSVButton ----
                                    importCSVButton.setText("import csv");
                                    panel15.add(importCSVButton, BorderLayout.CENTER);
                                }
                                panel12.add(panel15, BorderLayout.EAST);
                            }
                            panel11.add(panel12, BorderLayout.CENTER);
                        }
                        panel10.add(panel11, BorderLayout.NORTH);

                        //======== panel13 ========
                        {
                            panel13.setLayout(new BorderLayout(5, 5));

                            //======== panel14 ========
                            {
                                panel14.setLayout(new BorderLayout(5, 5));

                                //---- valueLabel ----
                                valueLabel.setText("Value");
                                panel14.add(valueLabel, BorderLayout.CENTER);

                                //======== panel2 ========
                                {
                                    panel2.setLayout(new BorderLayout(8, 8));

                                    //---- regExLabel ----
                                    regExLabel.setText("text");
                                    panel2.add(regExLabel, BorderLayout.WEST);
                                    panel2.add(regExField, BorderLayout.CENTER);
                                }
                                panel14.add(panel2, BorderLayout.SOUTH);
                            }
                            panel13.add(panel14, BorderLayout.NORTH);

                            //======== panel1 ========
                            {
                                panel1.setLayout(new BorderLayout(2, 2));

                                //---- save ----
                                save.setText("SAVE");
                                save.setBackground(new Color(245,121,0));
                                save.setForeground(Color.WHITE);
                                panel1.add(save, BorderLayout.CENTER);
                            }
                            panel13.add(panel1, BorderLayout.SOUTH);

                            //======== scrollPaneValueTable ========
                            {
                                scrollPaneValueTable.setViewportView(valueTable);
                            }
                            panel13.add(scrollPaneValueTable, BorderLayout.CENTER);
                        }
                        panel10.add(panel13, BorderLayout.CENTER);
                    }
                    panel8.add(panel10, BorderLayout.CENTER);
                }
                panel6.add(panel8, BorderLayout.CENTER);
            }
            panel3.add(panel6, BorderLayout.CENTER);
        }
        contentPane.add(panel3, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel3;
    private JPanel panel6;
    private JPanel panel7;
    private JLabel nameLabel;
    private JComboBox nameComboBox;
    private JPanel numReqPanel;
    private JLabel numReqLabel;
    private JComboBox numReqComboBox;
    private JPanel panel8;
    private JPanel panel9;
    private JLabel fromLabel;
    private JComboBox fromComboBox;
    private JPanel panel16;
    private JLabel filterJLabel;
    private JTextField filterJtext;
    private JButton filterButton;
    private JPanel panel10;
    private JPanel panel11;
    private JLabel optionLabel;
    private JPanel panel12;
    private JCheckBox optionCheckBox;
    private JTextField optionText;
    private JPanel panel15;
    private JButton importCSVButton;
    private JPanel panel13;
    private JPanel panel14;
    private JLabel valueLabel;
    private JPanel panel2;
    private JLabel regExLabel;
    private JTextField regExField;
    private JPanel panel1;
    private JButton save;
    private JScrollPane scrollPaneValueTable;
    private JTable valueTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public void setRegExLabel(JLabel regExLabel) {
        this.regExLabel = regExLabel;
    }
    public void setRegExField(JTextField regExField) {
        this.regExField = regExField;
    }
    public JLabel getRegExLabel() {
        return regExLabel;
    }
    public JTextField getRegExField() {
        return regExField;
    }
    public JPanel getPanel2() {
        return panel2;
    }

    public JPanel getNumReqPanel() {
        return numReqPanel;
    }

    public void setNumReqPanel(JPanel numReqPanel) {
        this.numReqPanel = numReqPanel;
    }

    public JLabel getNumReqLabel() {
        return numReqLabel;
    }

    public void setNumReqLabel(JLabel numReqLabel) {
        this.numReqLabel = numReqLabel;
    }

    public JComboBox getNumReqComboBox() {
        return numReqComboBox;
    }

    public void setNumReqComboBox(JComboBox numReqComboBox) {
        this.numReqComboBox = numReqComboBox;
    }


    public CustomCorrelationTableModel getCustomCorrelationTableModel() {
        return customCorrelationTableModel;
    }

    public void setCustomCorrelationTableModel(CustomCorrelationTableModel customCorrelationTableModel) {
        this.customCorrelationTableModel = customCorrelationTableModel;
        this.valueTable.setModel(this.customCorrelationTableModel);
    }
    
    private CustomCorrelationTableModel customCorrelationTableModel;

    public JCheckBox getOptionCheckBox() {
        return optionCheckBox;
    }

    public void setOptionCheckBox(JCheckBox optionCheckBox) {
        this.optionCheckBox = optionCheckBox;
    }
    public JScrollPane getScrollPaneValueTable() {
        return scrollPaneValueTable;
    }
    public JTextField getOptionText() {
        return optionText;
    }

    public void setOptionText(JTextField optionText) {
        this.optionText = optionText;
    }

    public JLabel getFilterJLabel() {
        return filterJLabel;
    }

    public void setFilterJLabel(JLabel filterJLabel) {
        this.filterJLabel = filterJLabel;
    }

    public JTextField getFilterJtext() {
        return filterJtext;
    }

    public void setFilterJtext(JTextField filterJtext) {
        this.filterJtext = filterJtext;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public void setFilterButton(JButton filterButton) {
        this.filterButton = filterButton;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JComboBox getNameComboBox() {
        return nameComboBox;
    }

    public void setNameComboBox(JComboBox nameComboBox) {
        this.nameComboBox = nameComboBox;
    }

    public JLabel getFromLabel() {
        return fromLabel;
    }

    public void setFromLabel(JLabel fromLabel) {
        this.fromLabel = fromLabel;
    }

    public JComboBox getFromComboBox() {
        return fromComboBox;
    }

    public void setFromComboBox(JComboBox fromComboBox) {
        this.fromComboBox = fromComboBox;
    }

    public JLabel getOptionLabel() {
        return optionLabel;
    }

    public void setOptionLabel(JLabel optionLabel) {
        this.optionLabel = optionLabel;
    }

    
    public JLabel getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(JLabel valueLabel) {
        this.valueLabel = valueLabel;
    }

    public JTable getValueTable() {
        return valueTable;
    }

    public void setValueTable(JTable valueTable) {
        this.valueTable = valueTable;
    }

    public JButton getSave() {
        return save;
    }

    public void setSave(JButton save) {
        this.save = save;
    }

    public JButton getImportCSVButton() {
        return importCSVButton;
    }

    public void setImportCSVButton(JButton importCSVButton) {
        this.importCSVButton = importCSVButton;
    }

    public CorrelatorHelperService getCorrelatorHelperService() {
        return correlatorHelperService;
    }

    public void setCorrelatorHelperService(CorrelatorHelperService correlatorHelperService) {
        this.correlatorHelperService = correlatorHelperService;
    }

    public CorrelatorHelperService correlatorHelperService;

}
