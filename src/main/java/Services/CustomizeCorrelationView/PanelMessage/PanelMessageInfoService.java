package Services.CustomizeCorrelationView.PanelMessage;

import Entity.*;
import View.CustomizeCorrelationPage.CustomizeCorrelationPage;
import View.CustomizeCorrelationPage.PanelMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import javax.swing.*;
import java.awt.*;

public class PanelMessageInfoService {



    public static void fill_noturl_info_panel(JTable table, CustomizeCorrelationPage frame,  PanelMessage pm, String type, int row, int col){

        /*String varNameFrom="";
        if(!table.getValueAt(row,4).toString().equals("All"))
            varNameFrom = table.getValueAt(row,4).toString().substring(table.getValueAt(row,4).toString().indexOf("{"),table.getValueAt(row,4).toString().indexOf("}"));
        String xpath ="";
        if(varNameFrom.contains("-")) {
            xpath = varNameFrom.replace("-",".") ;
        }

        if(xpath.equals("")) {
            if(!table.getValueAt(row,4).toString().equals("All")) {
                pm.getDescriptionTextArea().setText(" The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                        "of  " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                        "request n°" + (frame.getCurrent_request() + 1) + " is equals to the parameter " + varNameFrom + " \n " +
                        "of the " + table.getValueAt(row, 3) + " response,\n which is the request n° " + table.getValueAt(row, 2));
            }else{
                pm.getDescriptionTextArea().setText("The response of "+table.getValueAt(row, 3) + " request,\n which is the request n° " + table.getValueAt(row, 2)+" will have passed completely! ");
            }
        }else {
        */

        //varNameFrom = varNameFrom.substring(varNameFrom.lastIndexOf("-") + 1, varNameFrom.length());

        CheckableItem checkableItem = frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row);
        if(!type.equals("cookie")) {
            if(!table.getValueAt(row,2).equals("-")) {

                pm.getDescriptionTextArea().setText("The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                        "of " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                        "request n° " + (frame.getCurrent_request()) + " is equals to the parameter " + table.getValueAt(row, 4) +
                        "of the " + table.getValueAt(row, 3) + " response,\n which is the request n° " + table.getValueAt(row, 2));
            }else {

                if(table.getValueAt(row,3).equals("manually_inserted")){
                    pm.getDescriptionTextArea().setText("The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                            "of " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                            "request n° " + (frame.getCurrent_request()) + " was manually set to the value " + table.getValueAt(row, 4));
                }else {
                    CSVNode csvNode = (CSVNode)checkableItem.getEdge().from;
                    pm.getDescriptionTextArea().setText("The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                            "of " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                            "request n° " + (frame.getCurrent_request()) + " was manually set to the parameter " + csvNode.getVariablesName()+
                            " from  " + table.getValueAt(row, 3));
                }
            }
        }else {
            EdgeCookie edgeCookie = (EdgeCookie) checkableItem.getEdge();
            if(!table.getValueAt(row,2).equals("-"))
            {
                if (edgeCookie.getDependency().from_set_cookie) {
                    pm.getDescriptionTextArea().setText("The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                            "of " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                            "request n° " + (frame.getCurrent_request()) + " is equals to the parameter " + table.getValueAt(row, 4) +
                            " setted as cookie in the response of " + table.getValueAt(row, 3) + " ,\n which is the request n° " + table.getValueAt(row, 2));
                } else {
                    pm.getDescriptionTextArea().setText("The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                            "of  " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                            "request n° " + (frame.getCurrent_request()) + " is equals to the parameter " + table.getValueAt(row, 4) +
                            "of the " + table.getValueAt(row, 3) + " response,\n which is the request n° " + table.getValueAt(row, 2));
                }
            }else
            {
                if(table.getValueAt(row,3).equals("manually_inserted")){
                    pm.getDescriptionTextArea().setText("The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                            "of " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                            "request n° " + (frame.getCurrent_request()) + " was manually set to the value " + table.getValueAt(row, 4));
                }else {
                    CSVNode csvNode = (CSVNode)checkableItem.getEdge().from;
                    pm.getDescriptionTextArea().setText("The parameter " + table.getValueAt(row, 1).toString() + " in  the " + type + " \n " +
                            "of " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                            "request n° " + (frame.getCurrent_request()) + " was manually set to the parameter " + csvNode.getVariablesName()+
                            " from  " + table.getValueAt(row, 3));
                }
            }
        }
        pm.getDescriptionTextArea().setFont(new Font(Font.DIALOG,  Font.PLAIN, 15));
        pm.getDescriptionTextArea().setBackground(new Color(255,255,255));
        fill_response_area(table,frame,row,pm,type);
    }


    public static void  fill_response_area(JTable table, CustomizeCorrelationPage frame, int row, PanelMessage pm,String type){
        String indented_response ="";
        try {
            if(!table.getValueAt(row,2).equals("-")){
                int index =0;
                String indexs = table.getValueAt(row,2).toString();
                String numbers = indexs.substring(1,indexs.length()-1);
                if(numbers.contains(",")){
                    String [] numbersArray = numbers.split(",");
                    index = Integer.parseInt(numbersArray[0]);
                }
                else{
                    index = Integer.parseInt(numbers);
                }
                CheckableItem checkableItem = frame.getCheckItemListsRequest().get(frame.getCurrent_request()).get(type).get(row);
                if(!type.equals("cookie")) {

                    String str_response = frame.getCorrelatorHelperApp().getHar().getLog().getEntries()[index].getResponse().getContent().getText();
                    if(!str_response.isEmpty())
                    {
                        Object json = new ObjectMapper().readValue(
                                str_response
                                , Object.class);
                        indented_response = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);
                        pm.getLabelResponse().setText("RESPONSE DATA OF REQUEST N° "+table.getValueAt(row,2));
                    }else{ //the checkableItem came from a setcookie
                        for(int i=0;i < frame.getCorrelatorHelperApp().getHar().getLog().getEntries()[index].getResponse().getCookies().length;i++){
                            Cookie cookie = frame.getCorrelatorHelperApp().getHar().getLog().getEntries()[index].getResponse().getCookies()[i];
                            indented_response+=cookie.toString()+"\n";
                        }
                        pm.getLabelResponse().setText("SETCOOKIE IN THE RESPONSE OF REQUEST N° "+table.getValueAt(row,2));
                    }

                }else{
                    EdgeCookie edgeCookie =(EdgeCookie)  checkableItem.getEdge();
                    if(edgeCookie.getDependency().from_set_cookie){
                        for(int i=0;i < frame.getCorrelatorHelperApp().getHar().getLog().getEntries()[index].getResponse().getCookies().length;i++){
                            Cookie cookie = frame.getCorrelatorHelperApp().getHar().getLog().getEntries()[index].getResponse().getCookies()[i];
                            indented_response+=cookie.toString()+"\n";
                        }
                        pm.getLabelResponse().setText("SETCOOKIE IN THE RESPONSE OF REQUEST N° "+table.getValueAt(row,2));
                    }else{

                        Object json = new ObjectMapper().readValue(
                                frame.getCorrelatorHelperApp().getHar().getLog().getEntries()[index].getResponse().getContent().getText()
                                , Object.class);
                        indented_response = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);
                        pm.getLabelResponse().setText("RESPONSE DATA OF REQUEST N° "+table.getValueAt(row,2));

                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();}

        pm.getResposeTextArea().setText(indented_response);
        pm.getResposeTextArea().setLineWrap(true);
    }


    public static void fill_post_request_area(JTable table, CustomizeCorrelationPage frame, int row, PanelMessage pm){
        String indented_request="";
        try {
            if(!table.getValueAt(row,2).equals("-")) {

                Object json = new ObjectMapper().readValue(
                        frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).request.getPostData().getText()
                        , Object.class);
                indented_request  = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();}

        pm.getLabelRequest().setText("BODY OF REQUEST N° "+(frame.getCurrent_request()));
        pm.getRequestTextArea().setText(indented_request);
        pm.getRequestTextArea().setLineWrap(true);
    }


    public static void fill_general_request_area(JTable table, CustomizeCorrelationPage frame, int row, PanelMessage pm , String type){
        String text =    "";
        String url =  frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl();
        Integer index = (frame.getCurrent_request());
        if(type.equals("headers")) {
            pm.getLabelRequest().setText("HEADER OF REQUEST N° "+index);
            for(Header header : frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).request.getHeaders()){
                text+="("+header.getName()+", "+header.getValue()+" )\n";
            }
        }else if(type.equals("query")){
            pm.getLabelRequest().setText("QUERY PARAMS OF REQUEST N° "+index);
            for(QueryParam queryParam : frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getQueryParams()){
                text+="( "+queryParam.getName()+", "+queryParam.getValue()+" )\n";
            }
        }else if(type.equals("cookie")){
            pm.getLabelRequest().setText("COOKIE OF REQUEST N° "+index);
            for(Cookie cookie : frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getCookies()){
                text+="( "+cookie.getName()+", "+cookie.getValue()+" )\n";
            }
        }

        pm.getRequestTextArea().setText(text);
        pm.getRequestTextArea().setLineWrap(true);
    }


    public static void fill_ur_info_panel(JTable table , CustomizeCorrelationPage frame, int row, PanelMessage pm ,String type){
        String to_name = table.getValueAt(row,4).toString();
        String subpath = table.getValueAt(row,1).toString();
        pm.getDescriptionTextArea().setText(" The value " +subpath+ " in  the " + type + " \n " +
                    "of  " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl()+ " \n " +
                    "request n°" + (frame.getCurrent_request()) + " is equals to the parameter " + to_name + " \n " +
                    "of the " + table.getValueAt(row, 3) + " response,\n which is the request n° " + table.getValueAt(row, 2));

        //                    +", the new url will be "+table.getValueAt(row,4));

       /* }else {
            varNameFrom= varNameFrom.substring(varNameFrom.lastIndexOf("-"),varNameFrom.length());
            pm.getDescriptionTextArea().setText("The value " +value + " in  the " + type + " \n " +
                    "of  " + frame.getCorrelatorHelperApp().getDependencyGraph().nodes.get(frame.getCurrent_request()).getRequest().getUrl() + " \n " +
                    "request n°" + (frame.getCurrent_request())+ " is equals to the parameter " + varNameFrom + " xpath( "+xpath+" ) \n " +
                    "of the " + table.getValueAt(row, 3) + " response,\n which is the request n° " + table.getValueAt(row, 2)
                    +", the new url will be "+table.getValueAt(row,4));
        }*/

        pm.getDescriptionTextArea().setFont(new  Font(Font.DIALOG,  Font.PLAIN, 15));
        pm.getDescriptionTextArea().setBackground(new Color(255,255,255));


    }

}
