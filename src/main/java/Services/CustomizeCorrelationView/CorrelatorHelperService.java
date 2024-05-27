package Services.CustomizeCorrelationView;

import Entity.*;
import Services.ResponseAnalyzer.ResponseUnstructured;
import View.CorrelationsPage.CorrelationFrame;
import View.CustomizeCorrelationPage.CustomizeCorrelationPage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import View.AddManuallyCorrelationPage.UsePreviouseResponseModel;
public class CorrelatorHelperService {

    public DependencyGraph getDependencyGraph() {
        return dependencyGraph;
    }

    public Har getHar() {
        return har;
    }

    public String getE2eName() {
        return e2eName;
    }

    public CorrelationFrame getCorrelationFrame() {
        return correlationFrame;
    }

    DependencyGraph dependencyGraph;
    Har har;
    String e2eName;

    List<ResponseUnstructured> responseUnstructuredList;
    CorrelationFrame correlationFrame;

    public CorrelatorHelperService(List<ResponseUnstructured>list,DependencyGraph dependencyGraph, Har har, String e2eName, CorrelationFrame correlationFrame) {
        this.dependencyGraph = dependencyGraph;
        this.har = har;
        this.e2eName = e2eName;
        this.correlationFrame = correlationFrame;
        this.responseUnstructuredList=list;
    }

    public  void runCustomizeCorrelationFrame() {
        List<Map<String, List<CheckableItem>>> requestsTableModelCheckItem = createRequestsTableModelCheckItem();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomizeCorrelationPage customizeCorrelationPage = null;
                try {
                    customizeCorrelationPage = new CustomizeCorrelationPage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                customizeCorrelationPage.setTitle(CorrelatorHelperService.this.e2eName+"- Select the correlations between requests");
                customizeCorrelationPage.setCheckItemListsRequest(requestsTableModelCheckItem);
                customizeCorrelationPage.setCorrelatorHelperApp(CorrelatorHelperService.this);
                //customizeCorrelationPage.setMapResponse(correlatorHelperApp.createMapResponse(file_har));
                customizeCorrelationPage.getNameE2E().setText(CorrelatorHelperService.this.e2eName+"-");
                customizeCorrelationPage.setUrl();
                customizeCorrelationPage.getInfoRequestsLabel().setText(CorrelatorHelperService.this.e2eName+" consists of "+CorrelatorHelperService.this.har.getLog().getEntries().length+" requests");
                customizeCorrelationPage.enableTabByUrl();
                customizeCorrelationPage.setReplacementTableModel();
                try {
                    customizeCorrelationPage.setActionTableComponent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageIcon info_icon = new ImageIcon("src/data/img/info-25.png");
                customizeCorrelationPage.getInfoRequestsButton().setIcon(info_icon);
                ImageIcon sx_icon = new ImageIcon("src/data/img/back-25.png");
                ImageIcon dx_icon = new ImageIcon("src/data/img/forward-25.png");
                customizeCorrelationPage.getSx().setIcon(sx_icon);
                customizeCorrelationPage.getDx().setIcon(dx_icon);
                customizeCorrelationPage.getInfoRequestsButton().addActionListener(ButtonsAction.actionInfoRequestButton(customizeCorrelationPage));
                customizeCorrelationPage.getSx().setEnabled(false);
                customizeCorrelationPage.setActionSx(ButtonsAction.actionSxButton(customizeCorrelationPage));
                customizeCorrelationPage.setActionDx(ButtonsAction.actionDxButton(customizeCorrelationPage));
                customizeCorrelationPage.setActionSave(ButtonsAction.actionSaveButton(CorrelatorHelperService.this));
                customizeCorrelationPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                       //ButtonsAction.updateCorrelationFrameTable(correlationFrame,CorrelatorHelperService.this.e2eName+"-");
                        super.windowClosing(e);
                    }
                });

                for (int i=0;i<CorrelatorHelperService.this.dependencyGraph.nodes.size();i++){
                    customizeCorrelationPage.getUsePreviouseResponseModel().getUsePreviousResponseArray().add(
                            new UsePreviouseResponseModel.SingleItem(
                                    false, 0,""
                            )
                    );
                }
                customizeCorrelationPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                customizeCorrelationPage.setSize(1000, 800);
                customizeCorrelationPage.setLocationRelativeTo(null);
                customizeCorrelationPage.setVisible(true);
            }
        });

    }

    private List<Map<String, List<CheckableItem>>> createRequestsTableModelCheckItem() {
        List<Map<String, List<CheckableItem>>> result = new ArrayList<>();
        for (Node node : this.dependencyGraph.nodes) {
            HashMap<String, List<CheckableItem>> map = new HashMap<>();
            List<CheckableItem> list_headers = new ArrayList<>();
            List<CheckableItem> list_queryparams = new ArrayList<>();
            List<CheckableItem> list_urls = new ArrayList<>();
            List<CheckableItem> list_cookies = new ArrayList<>();
            List<CheckableItem> list_bodyparam = new ArrayList<>();
            for (Edge edge : this.dependencyGraph.getDependenciesByNode(node)) {
                switch (edge.type) {
                    case "bodyjson": {
                        EdgeBodyJSON edgeBodyJSON = (EdgeBodyJSON) edge;
                        CheckableItem checkableItem = new CheckableItem(
                                edgeBodyJSON,
                                true
                        );
                        list_bodyparam.add(checkableItem);
                        break;
                    }
                    case "bodyue": {
                        EdgeBodyUE edgeBodyUE = (EdgeBodyUE) edge;
                        CheckableItem checkableItem = new CheckableItem(
                          edgeBodyUE,true
                        );
                        list_bodyparam.add(checkableItem);
                        break;
                    }
                    case "cookie": {
                        EdgeCookie edgeCookie = (EdgeCookie) edge;
                        CheckableItem checkableItem = new CheckableItem(
                                edgeCookie,true
                        );
                        list_cookies.add(checkableItem);
                        break;
                    }
                    case "header": {
                        EdgeHeader edgeHeader = (EdgeHeader) edge;
                        CheckableItem checkableItem = new CheckableItem(
                                edgeHeader,true
                        );
                        list_headers.add(checkableItem);
                        break;
                    }
                    case "queryparam": {
                        EdgeQueryParam edgeQueryParam = (EdgeQueryParam) edge;
                        CheckableItem checkableItem = new CheckableItem(
                                edgeQueryParam,
                                true
                        );
                        list_queryparams.add(checkableItem);
                        break;
                    }
                    case "url": {
                        EdgeUrl edgeUrl = (EdgeUrl) edge;
                        CheckableItem checkableItem = new CheckableItem(
                                edgeUrl,true
                        );
                        list_urls.add(checkableItem);
                        break;
                    }
                }
            }
            map.put("headers",list_headers);
            map.put("query",list_queryparams);
            map.put("postData",list_bodyparam);
            map.put("url",list_urls);
            map.put("cookie",list_cookies);
            result.add(map);
        }
        return result;
    }
}
