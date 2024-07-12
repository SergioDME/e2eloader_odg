package Services.CustomizeCorrelationView;

import Entity.CheckableItem;
import Services.CustomizeCorrelationView.CSV.CSVFrameService;
import View.AddManuallyCorrelationPage.AddManuallyCorrelationFrame;
import View.AddManuallyCorrelationPage.CustomCorrelationTableModel;
import View.CustomizeCorrelationPage.CustomizeCorrelationPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ButtonsAction {


    public static ActionListener actionInfoRequestButton (CustomizeCorrelationPage home){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String message ="";
                for(int i=0;i<home.getCorrelatorHelperApp().getDependencyGraph().nodes.size();i++){
                    message +="\n\n "+(i)+")  ["+home.getCorrelatorHelperApp().getDependencyGraph().nodes.get(i).request.getMethod()+"] "+home.getCorrelatorHelperApp().getDependencyGraph().nodes.get(i).getRequest().getUrl()+" "+home.getCorrelatorHelperApp().getDependencyGraph().nodes.get(i).indexs.toString();
                }
                JTextArea textArea = new JTextArea(message);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea){
                    @Override
                    public Dimension getPreferredSize(){
                        return new Dimension(800,500);
                    }
                };
                JOptionPane.showMessageDialog(null,scrollPane,"List of requests that make up the user behavior",JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    public static ActionListener actionSxButton (CustomizeCorrelationPage home) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(home.getCurrent_request() -1 == 0){
                    home.setCurrent_request(home.getCurrent_request()-1);
                    home.getSx().setEnabled(false);

                } else if (home.getCurrent_request() == home.getCorrelatorHelperApp().getDependencyGraph().nodes.size()-1 ) {
                    home.getDx().setEnabled(true);
                    home.setCurrent_request(home.getCurrent_request()-1);
                } else {
                    home.setCurrent_request(home.getCurrent_request()-1);
                }
                home.setUrl();
                home.enableTabByUrl();
                updateTableModel(home);
            }
        };
    }

    public static ActionListener actionDxButton(CustomizeCorrelationPage home) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(home.getCurrent_request()==0) {
                    home.setCurrent_request(home.getCurrent_request()+1);
                    home.getSx().setEnabled(true);

                } else if (home.getCurrent_request() >0 && home.getCurrent_request()+1< home.getCorrelatorHelperApp().getDependencyGraph().nodes.size()-1){
                    home.setCurrent_request(home.getCurrent_request()+1);

                } else if(home.getCurrent_request() +1 == home.getCorrelatorHelperApp().getDependencyGraph().nodes.size()-1) {
                    home.getDx().setEnabled(false);
                    home.setCurrent_request(home.getCurrent_request()+1);
                }
                home.setUrl();
                home.enableTabByUrl();
                updateTableModel(home);
            }
        };
    }

    public static ActionListener actionSaveButton(CorrelatorHelperService home) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               /* try {
                    if(home.getTwoPartNameE2E().equals("")) {
                        JOptionPane.showMessageDialog(null, "The second part of the name file is required!",
                                "Error!",JOptionPane.ERROR_MESSAGE
                        );
                    }else {
                        String filename = home.getNameE2E().getText()+""+home.getTwoPartNameE2E().getText()+".json";
                        ReplacementsJSONParser.saveCorrelationsPreferred(home.getCorrelatorHelperApp().getCsvDataSetConfings(),home.getCorrelatorHelperApp().getReplacementsJSONParser(), home.getCheckItemListsRequest(), home.getCorrelatorHelperApp().getFilename_har(),filename);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
            }
        };
    }

    public static ActionListener actionCustomCorrelationAdd(CustomizeCorrelationPage home,String type) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddManuallyCorrelationFrame addManuallyCorrelationFrame = null;
                try {
                    addManuallyCorrelationFrame = new AddManuallyCorrelationFrame();
                }catch (Exception e) {}

                addManuallyCorrelationFrame.setTitle("Adding custom correlation");
                addManuallyCorrelationFrame.setCorrelatorHelperService(home.getCorrelatorHelperApp());

                if(type.equals("url")) {
                    addManuallyCorrelationFrame.getNameLabel().setText("Path elements");
                    addManuallyCorrelationFrame.getValueLabel().setText("Value");
                }

                List<String> list_from = new ArrayList<>();
                List<String> list_name = new ArrayList<>();

                CustomCorrelationTableModel customCorrelationTableModel = new CustomCorrelationTableModel();
                addManuallyCorrelationFrame.setCustomCorrelationTableModel(customCorrelationTableModel);

                // set action FilterButton
                addManuallyCorrelationFrame.getFilterButton().addActionListener(
                        CustomCorrelationFrameService.actionFilterButton(addManuallyCorrelationFrame)
                );

                // set csv import action
                addManuallyCorrelationFrame.getImportCSVButton().addActionListener(
                        CSVFrameService.CSVFrameOpen(addManuallyCorrelationFrame)
                );

                //set action OptionCheckBox
                addManuallyCorrelationFrame.getOptionCheckBox().addActionListener(
                        CustomCorrelationFrameService.actionOptionCheckBox(addManuallyCorrelationFrame)
                );

                try {

                    //from combo box (the same for all)
                    list_from = CustomCorrelationFrameService.getUrlswithMethods(
                            home.getCorrelatorHelperApp().dependencyGraph,
                            home.getCurrent_request()
                    );

                    //set from comboBox action
                    addManuallyCorrelationFrame.getFromComboBox().addActionListener(
                            CustomCorrelationFrameService.actionFromComboBox(addManuallyCorrelationFrame,home.getCorrelatorHelperApp())
                    );

                    //set from combo box model
                    addManuallyCorrelationFrame.getFromComboBox().setModel(
                            new DefaultComboBoxModel(list_from.toArray())
                    );

                    switch (type) {

                        case "postData": {
                            addManuallyCorrelationFrame.getNumReqPanel().setVisible(false);
                            addManuallyCorrelationFrame.getPanel2().setVisible(false);
                            list_name = CustomCorrelationFrameService.getNamePostData(
                                    home.getCorrelatorHelperApp().dependencyGraph.nodes.get(home.getCurrent_request())
                            );

                            addManuallyCorrelationFrame.getNameComboBox().setModel(
                                    new DefaultComboBoxModel(list_name.toArray())
                            );

                           addManuallyCorrelationFrame.getSave().addActionListener(
                                    CustomCorrelationFrameService.actionSaveButton(
                                            addManuallyCorrelationFrame,
                                            home,
                                            "postData",null
                                    )
                            );
                            break;
                        }
                        case "headers": {

                            addManuallyCorrelationFrame.getNumReqPanel().setVisible(false);
                            addManuallyCorrelationFrame.getPanel2().setVisible(false);
                            //name combo box for headers
                            list_name = CustomCorrelationFrameService.getHeadersNameFrom(
                                    home.getCorrelatorHelperApp().dependencyGraph.nodes.get(home.getCurrent_request())
                            );

                            addManuallyCorrelationFrame.getNameComboBox().setModel(
                                    new DefaultComboBoxModel(list_name.toArray())
                            );

                            //set save action
                            addManuallyCorrelationFrame.getSave().addActionListener(
                                    CustomCorrelationFrameService.actionSaveButton(
                                            addManuallyCorrelationFrame,
                                            home,
                                            "headers",null
                                    )
                            );
                            break;
                        }
                        case "query" : {
                            addManuallyCorrelationFrame.getNumReqPanel().setVisible(false);
                            addManuallyCorrelationFrame.getPanel2().setVisible(false);

                            list_name = CustomCorrelationFrameService.getQueryNameFrom(
                                home.getCorrelatorHelperApp().dependencyGraph.nodes.get(home.getCurrent_request())
                            );

                            addManuallyCorrelationFrame.getNameComboBox().setModel(
                                    new DefaultComboBoxModel(list_name.toArray())
                            );

                           //set save action
                            addManuallyCorrelationFrame.getSave().addActionListener(
                                    CustomCorrelationFrameService.actionSaveButton(
                                            addManuallyCorrelationFrame,
                                            home,
                                            "query",null
                                    )
                            );

                            break;
                        }
                        case "url" : {
                            addManuallyCorrelationFrame.getNumReqPanel().setVisible(false);
                            addManuallyCorrelationFrame.getPanel2().setVisible(false);

                            list_name = CustomCorrelationFrameService.getPathElementFrom(
                                    home.getCorrelatorHelperApp().dependencyGraph.nodes.get(home.getCurrent_request()).request.getUrl()
                            );
                            addManuallyCorrelationFrame.getNameComboBox().setModel(
                                    new DefaultComboBoxModel(list_name.toArray())
                            );

                            //set save action
                            addManuallyCorrelationFrame.getSave().addActionListener(
                                    CustomCorrelationFrameService.actionSaveButton(
                                            addManuallyCorrelationFrame,
                                            home,
                                            "url",null
                                    )
                            );
                            break;
                        }
                        case "cookie": {
                            addManuallyCorrelationFrame.getNumReqPanel().setVisible(false);
                            addManuallyCorrelationFrame.getPanel2().setVisible(false);

                            list_name = CustomCorrelationFrameService.getCookieName(
                                   home.getCorrelatorHelperApp().dependencyGraph.nodes.get(home.getCurrent_request())
                            );
                            addManuallyCorrelationFrame.getNameComboBox().setModel(
                                    new DefaultComboBoxModel(list_name.toArray())
                            );

                            addManuallyCorrelationFrame.getSave().addActionListener(
                                    CustomCorrelationFrameService.actionSaveButton(
                                            addManuallyCorrelationFrame,
                                            home,
                                            "cookie",null
                                    )
                            );

                            break;
                        }
                        case "request" : {
                            /*
                            addManuallyCorrelationFrame.getRegExLabel().setText("Insert RegEx: ");
                            addManuallyCorrelationFrame.getPanel2().setVisible(false);
                            ArrayList<ArrayList<String>> infoWS= CustomCorrelationFrameService.getInfoReqWS(
                                    home.getCorrelatorHelperApp().getReplacementsJSONParser().get(home.getCurrent_request()).url,
                                    (home.getCurrent_request()+1),
                                    home.getCorrelatorHelperApp().getFilename_har()
                                    );
                            ArrayList<String> new_from =CustomCorrelationFrameService.extendsFromListWS(
                                    list_from,
                                    home.getCorrelatorHelperApp().getReplacementsJSONParser().get(home.getCurrent_request()).url,
                                    home.getCorrelatorHelperApp().getFilename_har()
                            );

                            addManuallyCorrelationFrame.setMapResponseValue(CustomCorrelationFrameService.getValues(new_from, home.getCorrelatorHelperApp().getFilename_har()));

                            addManuallyCorrelationFrame.getNumReqComboBox().setModel(
                                    new DefaultComboBoxModel(
                                            infoWS.get(0).toArray()
                                    )
                            );

                            addManuallyCorrelationFrame.getNumReqComboBox().addActionListener(CustomCorrelationFrameService.actionNumReqCheckBox(
                                    addManuallyCorrelationFrame,
                                    infoWS.get(1),
                                    home.getCorrelatorHelperApp().getReplacementsJSONParser().get(home.getCurrent_request()).url,
                                    list_from, home.getCorrelatorHelperApp().getFilename_har()));

                            addManuallyCorrelationFrame.getSave().addActionListener(
                                    CustomCorrelationFrameService.actionSaveButton(
                                            addManuallyCorrelationFrame,
                                            home,
                                            home.getCorrelatorHelperApp().getReplacementsJSONParser().get(home.getCurrent_request()).url,
                                            "request",infoWS.get(1)
                                    )
                            );

                             */
                            break;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                addManuallyCorrelationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addManuallyCorrelationFrame.setSize(1000,800);
                addManuallyCorrelationFrame.setLocationRelativeTo(null);
                addManuallyCorrelationFrame.setVisible(true);
            }
        };
    }

    public static void updateTableModel (CustomizeCorrelationPage home) {

        //HEADERS
        home.getReplacementTableModelHeaders().setItems(
                home.getCheckItemListsRequest().get(home.getCurrent_request()).get("headers")
        );
        home.getReplacementTableModelHeaders().fireTableDataChanged();

        //QUERY
        home.getReplacementTableModelQuery().setItems(
                home.getCheckItemListsRequest().get(home.getCurrent_request()).get("query")
        );
        home.getReplacementTableModelQuery().fireTableDataChanged();
        //URL
        home.getReplacementTableModelUrl().setItems(
                home.getCheckItemListsRequest().get(home.getCurrent_request()).get("url")
        );
        home.getReplacementTableModelUrl().fireTableDataChanged();
        //Cookie
        home.getReplacementTableModelCookie().setItems(
                home.getCheckItemListsRequest().get(home.getCurrent_request()).get("cookie")
        );
        home.getReplacementTableModelCookie().fireTableDataChanged();
        //RequestWS
        /*home.getReplacementTableModelRequest().setItems(
                home.getCheckItemListsRequest().get(home.getCurrent_request()).get("request")
        )
        home.getReplacementTableModelRequest().fireTableDataChanged();
*/
        //POST DATA
        home.getReplacementTablePostData().setItems(
                home.getCheckItemListsRequest().get(home.getCurrent_request()).get("postData")
        );
        home.getReplacementTablePostData().fireTableDataChanged();

    }

    public static ActionListener actionSetPreviousResponsePostDataComboBox(CustomizeCorrelationPage home)
    {
       return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
       //         home.getPostData().getPreviousResponseCheckBox().setSelected(false);
            }
        };

    }
    public static ActionListener actionSetPreviousResponsePostDataCheckBox(CustomizeCorrelationPage home) {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
/*
                home.getUsePreviouseResponseModel().getUsePreviousResponseArray().get(home.getCurrent_request()).setUsed(
                        home.getPostData().getPreviousResponseCheckBox().isSelected()
                );

                if(home.getPostData().getPreviousResponseCheckBox().isSelected()) {
                    if(!PostDataAlreadyPresent(home)){

                        for (CheckableItem Citem : home.getCheckItemListsRequest().get(home.getCurrent_request()).get("postData")) {
                            Citem.setSelected(false);
                        }
                        updateTableModel(home);
                        home.getCheckItemListsRequest().get(home.getCurrent_request()).get("postData").add(
                                new CheckableItem(
                                        new Item(
                                                home.getPostData().getPreviousResponseComboBox().getSelectedItem().toString().substring(
                                                        home.getPostData().getPreviousResponseComboBox().getSelectedItem().toString().indexOf("]")+1,
                                                        home.getPostData().getPreviousResponseComboBox().getSelectedItem().toString().length()
                                                ),
                                                "All",
                                                "All",
                                                home.getPostData().getPreviousResponseComboBox().getSelectedIndex()
                                        ), null, true
                                )
                        );
                    }else {
                        for (CheckableItem Citem : home.getCheckItemListsRequest().get(home.getCurrent_request()).get("postData")) {
                            if(Citem.getItem().getValue().equals("All")){
                                Citem.getItem().setFrom(home.getPostData().getPreviousResponseComboBox().getSelectedItem().toString().substring(
                                        home.getPostData().getPreviousResponseComboBox().getSelectedItem().toString().indexOf("]")+1,
                                        home.getPostData().getPreviousResponseComboBox().getSelectedItem().toString().length()
                                ));
                                Citem.getItem().setNum_req(home.getPostData().getPreviousResponseComboBox().getSelectedIndex());
                            }
                        }
                    }
                    home.getUsePreviouseResponseModel().getUsePreviousResponseArray().get(home.getCurrent_request()).setNumberOfRequestUsed(
                            home.getPostData().getPreviousResponseComboBox().getSelectedIndex()
                    );
                    home.getReplacementTablePostData().fireTableDataChanged();
                }else {
                    home.getUsePreviouseResponseModel().getUsePreviousResponseArray().get(home.getCurrent_request()).setNumberOfRequestUsed(0);
                }
            }
  */
            }
        };

    }

}
