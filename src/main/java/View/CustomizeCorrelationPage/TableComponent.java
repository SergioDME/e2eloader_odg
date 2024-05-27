/*
 * Created by JFormDesigner on Thu Jun 02 18:04:58 CEST 2022
 */

package View.CustomizeCorrelationPage;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class TableComponent extends JPanel {
    public TableComponent() {
        initComponents();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        add = new JButton();
        //uncheck = new JButton();
        panel2 = new JPanel();
        panel3 = new JPanel();
        //KeepValue = new JTextField();
        panel4 = new JPanel();
        //KeepNumReq = new JTextField();
        previousResponsePanel = new JPanel();
        previousResponseComboBox = new JComboBox();
        previousResponseCheckBox = new JCheckBox();
        scrollPane1 = new JScrollPane();
        table = new JTable();

        //======== this ========
        setLayout(new BorderLayout(5, 5));

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout(5, 5));

            //---- add ----
            add.setText("");
            ImageIcon add_icon = new ImageIcon("src/data/img/add-25.png");
            add.setIcon(add_icon);
            panel1.add(add, BorderLayout.WEST);

            //---- uncheck ----
           /* uncheck.setText("uncheck");
            panel1.add(uncheck, BorderLayout.EAST);
            */
            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout(5, 5));

                //======== panel3 ========
                {
                    panel3.setLayout(new BorderLayout(5, 5));

                    //======== panel4 ========
                    {
                        panel4.setLayout(new BorderLayout(5, 5));
                    }
                    panel3.add(panel4, BorderLayout.EAST);
                }
                panel2.add(panel3, BorderLayout.CENTER);
            }
            panel1.add(panel2, BorderLayout.CENTER);

            //======== previousResponsePanel ========
            {
               /* previousResponsePanel.setLayout(new BorderLayout(5, 5));
                previousResponsePanel.add(previousResponseComboBox, BorderLayout.WEST);

                //---- previousResponseCheckBox ----
                previousResponseCheckBox.setText("use previous response");
                previousResponsePanel.add(previousResponseCheckBox, BorderLayout.CENTER);
                */

            }
            //panel1.add(previousResponsePanel, BorderLayout.SOUTH);
        }
        add(panel1, BorderLayout.NORTH);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table);
        }
        add(scrollPane1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JButton add;
    //private JButton uncheck;
    private JPanel panel2;
    private JPanel panel3;
    //private JTextField KeepValue;
    private JPanel panel4;
    //private JTextField KeepNumReq;
    private JPanel previousResponsePanel;
    private JComboBox previousResponseComboBox;
    private JCheckBox previousResponseCheckBox;
    private JScrollPane scrollPane1;
    private JTable table;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JTable getTable() {
        return table;
    }
    private ReplacementTableModel replacementTableModel;
    public JButton getAdd() {
        return add;
    }
    public ReplacementTableModel getReplacementTableModel() {
        return replacementTableModel;
    }

    public void setReplacementTableModel(ReplacementTableModel replacementTableModel) {
        this.replacementTableModel = replacementTableModel;
        table.setModel(this.replacementTableModel);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JPanel getPreviousResponsePanel() {return previousResponsePanel;}

    public JComboBox getPreviousResponseComboBox() {     return previousResponseComboBox;}

    public JCheckBox getPreviousResponseCheckBox() {return previousResponseCheckBox;}

}
