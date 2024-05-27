package Services.CustomizeCorrelationView;

import Services.CustomizeCorrelationView.PanelMessage.PanelMessageInfoService;
import View.CustomizeCorrelationPage.CustomizeCorrelationPage;
import View.CustomizeCorrelationPage.PanelMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;


public class JTableAction {

    public static void setActionJTable(JTable table, CustomizeCorrelationPage frame, String type) {
        table.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                int row = table.rowAtPoint(event.getPoint());
                int col = table.columnAtPoint(event.getPoint());
                if(type!="request")
                {
                    if (row >= 0 && col ==0 )
                    {

                        PanelMessage pm = new PanelMessage();
                        pm.getRegExField().setVisible(false);
                        pm.getRegExLabel().setVisible(false);

                        if(type!= "url") {

                            PanelMessageInfoService.fill_noturl_info_panel(table,frame,pm,type,row,col);

                            if(type.equals("postData")) {
                                PanelMessageInfoService.fill_post_request_area(table,frame,row,pm);
                            } else {
                                PanelMessageInfoService.fill_general_request_area(table,frame,row,pm,type);
                            }
                        }else
                        {

                            PanelMessageInfoService.fill_ur_info_panel(table,frame,row,pm,type);
                            PanelMessageInfoService.fill_response_area(table,frame,row,pm,type);

                        }
                        JOptionPane.showConfirmDialog(null,
                                 pm,
                                "Correlation description",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);

                    } else if (col == 5) {

                        boolean prevValue = frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row).isSelected();
                        boolean newValue = !(prevValue);
                        frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row).setSelected(
                                newValue
                        );
                        table.getModel().setValueAt(newValue, row, col);
                    }
                }
                else
                {
                    /*
                    if(row>=0 && col==0) {

                        PanelMessage pm = new PanelMessage();
                        boolean isWebMess =table.getModel().getValueAt(row,4).toString().contains("_webSocketMessages");
                        if (!isWebMess){
                            pm.getRegExLabel().setVisible(false);
                            pm.getRegExField().setVisible(false);
                        }

                        pm.getRegExLabel().setText("Insert RegEx: ");

                        String xpathBy =  table.getValueAt(row,2).toString();
                        String varNameBy = xpathBy.substring(xpathBy.lastIndexOf(".")+1,xpathBy.length());
                        String varNameFrom = table.getValueAt(row,5).toString().substring(table.getValueAt(row,5).toString().indexOf("{")+1,table.getValueAt(row,5).toString().indexOf("}"));
                        String xpathFrom = varNameFrom.replace("-",".") ;

                        varNameFrom= varNameFrom.substring(varNameFrom.lastIndexOf("-")+1,varNameFrom.length());
                        int numReqFrom = Integer.parseInt(table.getValueAt(row,3).toString());
                        System.out.println(numReqFrom);
                        if(numReqFrom == (frame.getCurrent_request()+1)){
                            String numRespMessage = table.getValueAt(row,4).toString().substring(table.getValueAt(row,4).toString().indexOf("[")+1,table.getValueAt(row,4).toString().indexOf("]"));
                            pm.getDescriptionTextArea().setText("The parameter " + varNameBy + " xpath ( " + xpathBy + " ) in  the request message n° " + table.getValueAt(row, 1) + "\n " +
                                    "belonging to " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                                    "request n°" + (frame.getCurrent_request() + 1) + " is equals to the parameter " + varNameFrom + " xpath( " + xpathFrom + " ) \n " +
                                    "of the  response message n°" +numRespMessage+
                                    ".  \n RegEx: " + table.getValueAt(row, 6) + " Due to the dynamic number of response messages, a RegEx is required to identify the correct response message!!");
                        }else {
                            pm.getDescriptionTextArea().setText("The parameter " + varNameBy + " xpath ( " + xpathBy + " ) in  the request message n° " + table.getValueAt(row, 1) + "\n " +
                                    "belonging to " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl()+ " \n " +
                                    "request n°" + (frame.getCurrent_request() + 1) + " is equals to the parameter " + varNameFrom + " xpath( " + xpathFrom + " ) \n " +
                                    "of the " + table.getValueAt(row, 4) + " response,\n which is the request n° " + table.getValueAt(row, 3) +
                                    ".  \n RegEx: " + table.getValueAt(row, 6) + " Due to the dynamic number of response messages, a RegEx is required to identify the correct response message!!");
                        }
                        pm.getDescriptionTextArea().setFont(new  Font(Font.DIALOG,  Font.PLAIN, 15));
                        pm.getDescriptionTextArea().setBackground(new Color(255,255,255));


                        String indented_request ="";
                        try {
                            Object json = new ObjectMapper().readValue(
                                    frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get("request").get(row).getDataRequestWS()
                            ,Object.class);
                            indented_request =  new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();}

                        pm.getRequestTextArea().setText(indented_request);
                        pm.getRequestTextArea().setLineWrap(true);

                        String indented_response ="";
                        try {
                            if(!table.getValueAt(row,3).equals("Value inserted manually")) {
                                Object json = new ObjectMapper().readValue(
                                        frame.getMapResponse().get(table.getValueAt(row, 4)).get(table.getValueAt(row, 3))
                                        , Object.class);
                                indented_response = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();}

                        pm.getResposeTextArea().setText(indented_response);
                        pm.getResposeTextArea().setLineWrap(true);

                        int resultPane = JOptionPane.showConfirmDialog(null,
                                pm,
                                "Correlation",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);
                        if(resultPane == JOptionPane.OK_OPTION && isWebMess){
                            frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row).getItem().setRegEx(
                                    pm.getRegExField().getText()
                            );
                            table.getModel().setValueAt(pm.getRegExField().getText(),row,5);
                        }

                    }else if(col == 7){
                        if(!frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row).getEdge().getRegEx().equals("Required!")) {
                            boolean prevValue = frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row).isSelected();
                            boolean newValue = !(prevValue);
                            frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row).setSelected(
                                    newValue
                            );
                            table.getModel().setValueAt(newValue, row, col);
                        } else {
                            JOptionPane.showMessageDialog(null,"RegEx is required!","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }*/
                }
            }
        });
    }

}
