/*
 * Created by JFormDesigner on Mon May 09 13:33:26 CEST 2022
 */

package View.CustomizeCorrelationPage;

import javax.swing.*;
import java.awt.*;

/**
 * @author unknown
 */
public class PanelMessage extends JPanel {



    public PanelMessage() {
        initComponents();
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        regExLabel = new JLabel();
        regExField = new JTextField();
        panel8 = new JPanel();
        label3 = new JLabel();
        scrollPane2 = new JScrollPane();
        descriptionTextArea = new JTextArea();
        panel5 = new JPanel();
        panel6 = new JPanel();
        panelResponse = new JScrollPane();
        resposeTextArea = new JTextArea();
        labelResponse = new JLabel();
        panel7 = new JPanel();
        panelRequest = new JScrollPane();
        requestTextArea = new JTextArea();
        labelRequest = new JLabel();

        //======== this ========
        setLayout(new BorderLayout(5, 5));

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout(5, 5));

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout(5, 5));

                //======== panel3 ========
                {
                    panel3.setLayout(new BorderLayout(5, 5));

                    //======== panel4 ========
                    {
                        panel4.setLayout(new BorderLayout(5, 5));

                        //---- regExLabel ----
                        regExLabel.setText("text");
                        panel4.add(regExLabel, BorderLayout.WEST);
                        panel4.add(regExField, BorderLayout.CENTER);
                    }
                    panel3.add(panel4, BorderLayout.SOUTH);
                }
                panel2.add(panel3, BorderLayout.SOUTH);
            }
            panel1.add(panel2, BorderLayout.SOUTH);

            //======== panel8 ========
            {
                panel8.setLayout(new BorderLayout(4, 4));

                //---- label3 ----
                label3.setText("Description");
                label3.setHorizontalAlignment(SwingConstants.CENTER);
                label3.setFont(label3.getFont().deriveFont(label3.getFont().getStyle() | Font.BOLD));
                panel8.add(label3, BorderLayout.CENTER);
            }
            panel1.add(panel8, BorderLayout.NORTH);

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(descriptionTextArea);
            }
            panel1.add(scrollPane2, BorderLayout.CENTER);
        }
        add(panel1, BorderLayout.NORTH);

        //======== panel5 ========
        {
            panel5.setLayout(new BorderLayout(5, 5));

            //======== panel6 ========
            {
                panel6.setLayout(new BorderLayout(6, 6));

                //======== panelResponse ========
                {
                    panelResponse.setMaximumSize(new Dimension(600, 500));
                    panelResponse.setMinimumSize(new Dimension(600, 500));
                    panelResponse.setPreferredSize(new Dimension(600, 500));

                    //---- resposeTextArea ----
                    resposeTextArea.setPreferredSize(new Dimension(2147483647, 2147483647));
                    panelResponse.setViewportView(resposeTextArea);
                }
                panel6.add(panelResponse, BorderLayout.CENTER);

                //---- labelResponse ----
                labelResponse.setText("RESPONSE DATA");
                labelResponse.setFont(labelResponse.getFont().deriveFont(labelResponse.getFont().getStyle() | Font.BOLD, labelResponse.getFont().getSize() + 2f));
                labelResponse.setHorizontalAlignment(SwingConstants.CENTER);
                panel6.add(labelResponse, BorderLayout.NORTH);
            }
            panel5.add(panel6, BorderLayout.WEST);

            //======== panel7 ========
            {
                panel7.setLayout(new BorderLayout(6, 6));

                //======== panelRequest ========
                {
                    panelRequest.setMaximumSize(new Dimension(600, 500));
                    panelRequest.setMinimumSize(new Dimension(600, 500));
                    panelRequest.setPreferredSize(new Dimension(600, 500));

                    //---- requestTextArea ----
                    requestTextArea.setPreferredSize(new Dimension(2147483647, 2147483647));
                    panelRequest.setViewportView(requestTextArea);
                }
                panel7.add(panelRequest, BorderLayout.CENTER);

                //---- labelRequest ----
                labelRequest.setText("REQUEST DATA");
                labelRequest.setFont(labelRequest.getFont().deriveFont(labelRequest.getFont().getStyle() | Font.BOLD, labelRequest.getFont().getSize() + 2f));
                labelRequest.setHorizontalAlignment(SwingConstants.CENTER);
                panel7.add(labelRequest, BorderLayout.NORTH);
            }
            panel5.add(panel7, BorderLayout.EAST);
        }
        add(panel5, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JLabel regExLabel;
    private JTextField regExField;
    private JPanel panel8;
    private JLabel label3;
    private JScrollPane scrollPane2;
    private JTextArea descriptionTextArea;
    private JPanel panel5;
    private JPanel panel6;
    private JScrollPane panelResponse;
    private JTextArea resposeTextArea;
    private JLabel labelResponse;
    private JPanel panel7;
    private JScrollPane panelRequest;
    private JTextArea requestTextArea;
    private JLabel labelRequest;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public JLabel getRegExLabel() {
        return regExLabel;
    }

    public void setRegExLabel(JLabel regExLabel) {
        this.regExLabel = regExLabel;
    }

    public JTextField getRegExField() {
        return regExField;
    }

    public void setRegExField(JTextField regExField) {
        this.regExField = regExField;
    }


    public JScrollPane getPanelRequest() {
        return panelRequest;
    }

    public void setPanelRequest(JScrollPane panelRequest) {
        this.panelRequest = panelRequest;
    }

    public JScrollPane getPanelResponse() {
        return panelResponse;
    }

    public void setPanelResponse(JScrollPane panelResponse) {
        this.panelResponse = panelResponse;
    }

    public JTextArea getRequestTextArea() {
        return requestTextArea;
    }

    public void setRequestTextArea(JTextArea requestTextArea) {
        this.requestTextArea = requestTextArea;
    }

    public JTextArea getResposeTextArea() {
        return resposeTextArea;
    }

    public void setResposeTextArea(JTextArea resposeTextArea) {
        this.resposeTextArea = resposeTextArea;
    }

    public JTextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public JLabel getLabelResponse() {return labelResponse;}

    public JLabel getLabelRequest() {return labelRequest;}
}
