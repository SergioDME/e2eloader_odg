package Services.CustomizeCorrelationView;


import Entity.*;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.StructuredObject;
import View.AddManuallyCorrelationPage.AddManuallyCorrelationFrame;
import View.AddManuallyCorrelationPage.CustomCorrelationTableModel;
import View.CustomizeCorrelationPage.CustomizeCorrelationPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.*;

public class CustomCorrelationFrameService {

    public static ArrayList<String> getCookieName(MyNode node){
        ArrayList<String> list_name_add = new ArrayList<>();
        for(Cookie cookie : node.request.getCookies())
            list_name_add.add(cookie.getName());
        return list_name_add;
    }

    public static List<String> getNamePostData(MyNode node) throws ParseException {
        List<String> result = new ArrayList<>();
        String mimetype =  node.request.getPostData().getMimeType();
        if(mimetype.contains("application/x-www-form-urlencoded")) {
            for(Param param : node.request.getPostData().getParams())
                result.add(param.getName());
        } else if ("application/json".equals(mimetype)) {
            String json_response = node.getRequest().getPostData().getText();
            Object obj = null;
            if(json_response.startsWith("{")) {
                obj = new JsonParser().parse(json_response).getAsJsonObject();
            } else if(json_response.startsWith("[")) {
                obj = new JsonParser().parse(json_response).getAsJsonArray();
            }
            visitNodeDataRequestWS(result,obj,"$");
        }
        return result;
    }


    public static ArrayList<String> getHeadersNameFrom(MyNode node){
        ArrayList<String> list_name_add = new ArrayList<>();
        for(Header header : node.request.getHeaders())
            list_name_add.add(header.getName());
        return list_name_add;
    }

    public static List<String> getUrls(DependencyGraph dependencyGraph, Integer index_req) {
        ArrayList<String> list_url = new ArrayList<>();
        for(int i=0;i<index_req;i++){
            list_url.add(
                    dependencyGraph.nodes.get(i).request.getUrl()
            );
        }
        return list_url;
    }

    public static List<String> getUrlswithMethods (DependencyGraph dependencyGraph, Integer index_req){
        ArrayList<String> list_url = new ArrayList<>();
        for(int i=0;i<index_req;i++){
            String method  = dependencyGraph.nodes.get(i).request.getMethod();
            String url = dependencyGraph.nodes.get(i).request.getUrl();
            list_url.add(
                    "["+method+"]"+url
            );
        }
        return list_url;
    }

    public static ActionListener actionFromComboBox(AddManuallyCorrelationFrame customCorrelationFrame,CorrelatorHelperService correlatorHelperService) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String urlandmethod = (String) customCorrelationFrame.getFromComboBox().getSelectedItem();
                int start_pos = urlandmethod.indexOf("[");
                int end_pos = urlandmethod.indexOf("]");
                String method = urlandmethod.substring(start_pos+1,end_pos);
                String url = urlandmethod.substring(end_pos+1,urlandmethod.length());
                if(url.contains("_webSocketMessages")) {
                    customCorrelationFrame.getPanel2().setVisible(true);
                } else {
                    customCorrelationFrame.getPanel2().setVisible(false);
                }

                String inputNumbers = correlatorHelperService.getDependencyGraph().getNodeByUrlandMethod(url,method).indexs.toString();
                String numbersString= inputNumbers.substring(1,inputNumbers.length()-1);
                String [] numberArrays = numbersString.split(",");

                customCorrelationFrame.getCustomCorrelationTableModel().cleanItems();
                customCorrelationFrame.getCustomCorrelationTableModel().setItemFromResponseUnstructured(
                        correlatorHelperService.responseUnstructuredList.get(Integer.parseInt(numberArrays[0]))
                );
                customCorrelationFrame.getCustomCorrelationTableModel().fireTableDataChanged();
            }
        };
    }

    /*
    public static ArrayList<ResponseUnstructured> getValues(Integer index_req, Har har) throws Exception {
        HashMap<String, ArrayList<CheckableItem>> map_value = new HashMap<>();
        for (String url : urls) {
            map_value.put(url, new ArrayList<>());
        }
        JSONVariableCorrelator jvc = new JSONVariableCorrelator(FILENAME_HAR);
        jvc.buildCorrelationTree();
        for (Object key : jvc.getResponseVariablesByValue().keySet()) {
            for (ParsedResponseVariable prv : jvc.getResponseVariablesByValue().get(key)) {
                String urlWithIndex = "["+prv.num_resp+"]"+prv.fromResponseURL;
                if (map_value.containsKey(urlWithIndex)) {
                    map_value.get(urlWithIndex).add(
                            new CheckableItem(
                                    new Item(
                                            prv.fromResponseURL,
                                            prv.varName,
                                            key.toString(),
                                            prv.num_resp
                                    ), null, false
                            )
                    );
                }
            }
        }
        return map_value;
    }
*/

    public static ArrayList<String> getPathElementFrom(String url) {
        ArrayList<String> list_name_add = new ArrayList<>();
        URI uri = URI.create(url);
        String path = uri.getPath();
        String [] subPathOfRequestURL = path.split("/");
        for(int i=1; i< subPathOfRequestURL.length ;i++){
            list_name_add.add(subPathOfRequestURL[i]);
        }
        return list_name_add;
    }

    public static ArrayList<String> getQueryNameFrom(MyNode node) {
        ArrayList<String> list_name_add = new ArrayList<>();
        for(QueryParam queryParam : node.request.getQueryParams()){
            list_name_add.add(queryParam.getName());
        }
        return list_name_add;
    }

    public static ActionListener actionFilterButton(AddManuallyCorrelationFrame customCorrelationFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name_filter = customCorrelationFrame.getFilterJtext().getText();
                ArrayList<CustomCorrelationTableModel.ResponseParamCheckable> new_items = new ArrayList<>();
                ArrayList<CustomCorrelationTableModel.ResponseParamCheckable> items = (ArrayList<CustomCorrelationTableModel.ResponseParamCheckable>) customCorrelationFrame.getCustomCorrelationTableModel().getItems();
                for (CustomCorrelationTableModel.ResponseParamCheckable item : items) {
                    if (item.getResponse().getClass() == AtomicObject.class) {
                        AtomicObject atomicObject = (AtomicObject) item.getResponse();
                        if(atomicObject.getName().contains(name_filter))
                            new_items.add(item);
                    }else if(item.getResponse().getClass() == StructuredObject.class){
                        StructuredObject structuredObject = (StructuredObject) item.getResponse();
                        if(structuredObject.getName().contains(name_filter)){
                            new_items.add(item);
                        }
                    }
                }
                customCorrelationFrame.getCustomCorrelationTableModel().setItems(
                        new_items
                );
                customCorrelationFrame.getCustomCorrelationTableModel().fireTableDataChanged();
            }
        };
    }


    public static ActionListener actionOptionCheckBox(AddManuallyCorrelationFrame customCorrelationFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (customCorrelationFrame.getOptionCheckBox().isSelected()) {
                    customCorrelationFrame.getCustomCorrelationTableModel().cleanItems();
                    customCorrelationFrame.getScrollPaneValueTable().setVisible(false);
                } else {
                    customCorrelationFrame.getScrollPaneValueTable().setVisible(true);
                }
            }
        };
    }

    public static ActionListener actionNumReqCheckBox(AddManuallyCorrelationFrame customCorrelationFrame,ArrayList<String> datas,String url,ArrayList<String> from,String FILENAME_HAR) {
        return  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String num_req = (String) customCorrelationFrame.getNumReqComboBox().getSelectedItem();
                try {
                    ArrayList<String> data_name = CustomCorrelationFrameService.getDataNameRequestWS(datas.get(Integer.parseInt(num_req)));
                    ArrayList<String> new_from = CustomCorrelationFrameService.getFromByNumReq(from,url,Integer.parseInt(num_req),FILENAME_HAR);
                    customCorrelationFrame.getNameComboBox().setModel(
                            new DefaultComboBoxModel(
                                data_name.toArray()
                            )
                    );

                    customCorrelationFrame.getFromComboBox().setModel(
                            new DefaultComboBoxModel(
                                    new_from.toArray()
                            )
                    );

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private static CustomCorrelationTableModel.ResponseParamCheckable getCheckedItem(AddManuallyCorrelationFrame addManuallyCorrelationFrame){
        for(CustomCorrelationTableModel.ResponseParamCheckable responseParamCheckable : addManuallyCorrelationFrame.getCustomCorrelationTableModel().getItems()){
            if(responseParamCheckable.isIs_selected()){
                return  responseParamCheckable;
            }
        }
        return  null;
    }

    private static void addNewManuallyUrlDependency(boolean is_manually, List<CustomCorrelationTableModel.ResponseParamCheckable> items, CustomizeCorrelationPage customizeCorrelationPage, int current_request, String type, CustomCorrelationTableModel.ResponseParamCheckable checked_param, MyNode to, String name, AddManuallyCorrelationFrame addManuallyCorrelationFrame, MyNode from ){
        if(is_manually){
            if(items.isEmpty()){ // manually inserted without importing csv file
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeUrl(
                                        null,
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_inserted",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );

            }else{ // manually inserted with csv dependency
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeUrl(
                                        checked_param.getCsvNode(),
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_csv",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );
            }
        }else {
            customizeCorrelationPage.getCheckItemListsRequest().get(customizeCorrelationPage.getCurrent_request()).get(type).add(
                    new CheckableItem(
                            new EdgeUrl(
                                    from,
                                    to,
                                    name,
                                    (AtomicObject) checked_param.getResponse()
                            ),
                            true
                    )
            );
        }
    }

    private static void addNewManuallyHeaderlDependency(boolean is_manually, List<CustomCorrelationTableModel.ResponseParamCheckable> items, CustomizeCorrelationPage customizeCorrelationPage, int current_request, String type, CustomCorrelationTableModel.ResponseParamCheckable checked_param, MyNode to, String name, AddManuallyCorrelationFrame addManuallyCorrelationFrame, MyNode from){
        if(is_manually){
            if(items.isEmpty()){ // manually inserted without importing csv file
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeHeader(
                                        null,
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_inserted",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );

            }else{ // manually inserted with csv dependency
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeHeader(
                                        checked_param.getCsvNode(),
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_csv",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );
            }
        }else {
            customizeCorrelationPage.getCheckItemListsRequest().get(customizeCorrelationPage.getCurrent_request()).get(type).add(
                    new CheckableItem(
                            new EdgeHeader(
                                    from,
                                    to,
                                    name,
                                    (AtomicObject) checked_param.getResponse()
                            ),
                            true
                    )
            );
        }
    }


        public static ActionListener actionSaveButton(AddManuallyCorrelationFrame addManuallyCorrelationFrame, CustomizeCorrelationPage customizeCorrelationPage, String type, ArrayList<String> dataWS) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String name = (String) addManuallyCorrelationFrame.getNameComboBox().getSelectedItem();
                String fromComboBox = (String) addManuallyCorrelationFrame.getFromComboBox().getSelectedItem();
                boolean is_manually = addManuallyCorrelationFrame.getOptionCheckBox().isSelected();
                MyNode from = null;
                if(!is_manually)
                {
                    int start_pos = fromComboBox.indexOf("[");
                    int end_pos = fromComboBox.indexOf("]");
                    String from_method = fromComboBox.substring(start_pos+1,end_pos);
                    String from_url = fromComboBox.substring(end_pos+1,fromComboBox.length());
                    from = addManuallyCorrelationFrame.getCorrelatorHelperService().getDependencyGraph().getNodeByUrlandMethod(from_url,from_method);
                }
                List<CustomCorrelationTableModel.ResponseParamCheckable> items  = addManuallyCorrelationFrame.getCustomCorrelationTableModel().getItems();
                int current_request = customizeCorrelationPage.getCurrent_request();
                CustomCorrelationTableModel.ResponseParamCheckable checked_param = getCheckedItem(addManuallyCorrelationFrame);
                String url = addManuallyCorrelationFrame.getCorrelatorHelperService().dependencyGraph.nodes.get(current_request).getRequest().getUrl();
                String method =addManuallyCorrelationFrame.getCorrelatorHelperService().dependencyGraph.nodes.get(current_request).getRequest().getMethod();
                MyNode to = addManuallyCorrelationFrame.getCorrelatorHelperService().getDependencyGraph().getNodeByUrlandMethod(url,method);
                switch (type){
                    case "url": {
                        addNewManuallyUrlDependency(is_manually,items,customizeCorrelationPage,current_request,type,checked_param,to,name,addManuallyCorrelationFrame,from);
                        customizeCorrelationPage.getReplacementTableModelUrl().fireTableDataChanged();
                        break;
                    }
                    case "query":{
                        addNewManuallyQueryDependency(is_manually,items,customizeCorrelationPage,current_request,type,checked_param,to,name,addManuallyCorrelationFrame,from);
                        customizeCorrelationPage.getReplacementTableModelQuery().fireTableDataChanged();
                        break;
                    }
                    case "cookie":{
                        addNewManuallyCookieDependency(is_manually,items,customizeCorrelationPage,current_request,type,checked_param,to,name,addManuallyCorrelationFrame,from);
                        customizeCorrelationPage.getReplacementTableModelCookie().fireTableDataChanged();
                        break;
                    }
                    case "headers":{
                        addNewManuallyHeaderlDependency(is_manually,items,customizeCorrelationPage,current_request,type,checked_param,to,name,addManuallyCorrelationFrame,from);
                        customizeCorrelationPage.getReplacementTableModelHeaders().fireTableDataChanged();
                        break;
                    }
                    case "postData":{
                        addNewManuallyPostlDependency(is_manually,items,customizeCorrelationPage,current_request,type,checked_param,to,name,addManuallyCorrelationFrame,from);
                        customizeCorrelationPage.getReplacementTablePostData().fireTableDataChanged();
                        break;
                    }
                }

                int ok_pressed = JOptionPane.showConfirmDialog(null, "Operation successfully completed! \n Do you want to add other correlations?", "Correlation added", JOptionPane.INFORMATION_MESSAGE);

                if(!(ok_pressed == JOptionPane.OK_OPTION)){
                    addManuallyCorrelationFrame.dispose();
                }
                /*else if(type.equals("request")){

                        String num_req = (String) customCorrelationFrame.getNumReqComboBox().getSelectedItem();
                        home.getCheckItemListsRequest().get(home.getCurrent_request()).get(type).add(
                          new CheckableItem(
                                  new Item(
                                          from,
                                          name,
                                          value,
                                          index_from,
                                          regExpr
                                  ),null, Integer.parseInt(num_req),dataWS.get(Integer.parseInt(num_req)),false
                            )
                        );
                    }else{
                        /*home.getCheckItemListsRequest().get(home.getCurrent_request()).get(type).add(
                                new CheckableItem(
                                        new Item(
                                                from,
                                                name,
                                                value,
                                                index_from
                                        ), null, true
                                )
                        );
                    }


                    if(type.equals("headers")) {
                    } else if(type.equals("query")) {
                    }else if(type.equals("url")) {
                    }else if(type.equals("cookie")) {
                    } else if(type.equals("request")) {
                        customizeCorrelationPage.getReplacementTableModelRequest().fireTableDataChanged();
                    }

                }*/
            }
        };
    }

    private static void addNewManuallyPostlDependency(boolean is_manually, List<CustomCorrelationTableModel.ResponseParamCheckable> items, CustomizeCorrelationPage customizeCorrelationPage, int current_request, String type, CustomCorrelationTableModel.ResponseParamCheckable checked_param, MyNode to, String name, AddManuallyCorrelationFrame addManuallyCorrelationFrame, MyNode from) {
        if ("application/x-www-form-urlencoded".equals(to.getRequest().getPostData().getMimeType())) {
            if(is_manually){
                if(items.isEmpty()){ // manually inserted without importing csv file
                    customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                            new CheckableItem(
                                    new EdgeBodyUE(
                                            null,
                                            to,
                                            name,
                                            new AtomicObject(
                                                    "manually_inserted",
                                                    addManuallyCorrelationFrame.getOptionText().getText(),
                                                    ""
                                            )
                                    ),
                                    true
                            )
                    );

                }else{ // manually inserted with csv dependency
                    customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                            new CheckableItem(
                                    new EdgeBodyUE(
                                            checked_param.getCsvNode(),
                                            to,
                                            name,
                                            new AtomicObject(
                                                    "manually_csv",
                                                    addManuallyCorrelationFrame.getOptionText().getText(),
                                                    ""
                                            )
                                    ),
                                    true
                            )
                    );
                }
            }else {
                customizeCorrelationPage.getCheckItemListsRequest().get(customizeCorrelationPage.getCurrent_request()).get(type).add(
                        new CheckableItem(
                                new EdgeBodyUE(
                                        from,
                                        to,
                                        name,
                                        (AtomicObject) checked_param.getResponse()
                                ),
                                true
                        )
                );
            }
        } else if ("application/json".equals(to.getRequest().getPostData().getMimeType())) {
            addNewManuallyPostJSONDependency(is_manually,items,customizeCorrelationPage,current_request,type,checked_param,to,name,addManuallyCorrelationFrame,from);
        }
    }

    private static void addNewManuallyPostJSONDependency(boolean is_manually, List<CustomCorrelationTableModel.ResponseParamCheckable> items, CustomizeCorrelationPage customizeCorrelationPage, int current_request, String type, CustomCorrelationTableModel.ResponseParamCheckable checked_param, MyNode to, String name, AddManuallyCorrelationFrame addManuallyCorrelationFrame, MyNode from) {
        if(is_manually){
            if(items.isEmpty()){ // manually inserted without importing csv file
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeBodyJSON(
                                        true,
                                        name,
                                        null,
                                        to,
                                        new AtomicObject(
                                                "manually_inserted",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        ),
                                        null
                                ),
                                true
                        )
                );

            }else{ // manually inserted with csv dependency
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeBodyJSON(
                                        true,
                                        name,
                                        checked_param.getCsvNode(),
                                        to,
                                        new AtomicObject(
                                                "manually_csv",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        ),
                                        null
                                ),
                                true
                        )
                );
            }
        }else {
            boolean is_primitve = false;
            AtomicObject atomicObject= null;
            StructuredObject structuredObject = null;
            if(checked_param.getResponse().getClass() == AtomicObject.class){
                is_primitve = true;
                atomicObject = (AtomicObject) checked_param.getResponse();
            }else if(checked_param.getResponse().getClass() == StructuredObject.class){
                structuredObject = (StructuredObject) checked_param.getResponse();
            }
            customizeCorrelationPage.getCheckItemListsRequest().get(customizeCorrelationPage.getCurrent_request()).get(type).add(
                    new CheckableItem(
                            new EdgeBodyJSON(
                                    is_primitve,
                                    name,
                                    from,
                                    to,
                                    atomicObject,
                                    structuredObject
                            ),
                            true
                    )
            );
        }
    }

    private static void addNewManuallyCookieDependency(boolean is_manually, List<CustomCorrelationTableModel.ResponseParamCheckable> items, CustomizeCorrelationPage customizeCorrelationPage, int current_request, String type, CustomCorrelationTableModel.ResponseParamCheckable checked_param, MyNode to, String name, AddManuallyCorrelationFrame addManuallyCorrelationFrame, MyNode from) {
        if(is_manually){
            if(items.isEmpty()){ // manually inserted without importing csv file
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeCookie(
                                        null,
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_inserted",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );

            }else{ // manually inserted with csv dependency
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeCookie(
                                        checked_param.getCsvNode(),
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_csv",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );
            }
        }else {
            customizeCorrelationPage.getCheckItemListsRequest().get(customizeCorrelationPage.getCurrent_request()).get(type).add(
                    new CheckableItem(
                            new EdgeCookie(
                                    from,
                                    to,
                                    name,
                                    (AtomicObject) checked_param.getResponse()
                            ),
                            true
                    )
            );
        }
    }

    private static void addNewManuallyQueryDependency(boolean is_manually, List<CustomCorrelationTableModel.ResponseParamCheckable> items, CustomizeCorrelationPage customizeCorrelationPage, int current_request, String type, CustomCorrelationTableModel.ResponseParamCheckable checked_param, MyNode to, String name, AddManuallyCorrelationFrame addManuallyCorrelationFrame, MyNode from) {
        if(is_manually){
            if(items.isEmpty()){ // manually inserted without importing csv file
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeQueryParam(
                                        null,
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_inserted",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );

            }else{ // manually inserted with csv dependency
                customizeCorrelationPage.getCheckItemListsRequest().get(current_request).get(type).add(
                        new CheckableItem(
                                new EdgeQueryParam(
                                        checked_param.getCsvNode(),
                                        to,
                                        name,
                                        new AtomicObject(
                                                "manually_csv",
                                                addManuallyCorrelationFrame.getOptionText().getText(),
                                                ""
                                        )
                                ),
                                true
                        )
                );
            }
        }else {
            customizeCorrelationPage.getCheckItemListsRequest().get(customizeCorrelationPage.getCurrent_request()).get(type).add(
                    new CheckableItem(
                            new EdgeQueryParam(
                                    from,
                                    to,
                                    name,
                                    (AtomicObject) checked_param.getResponse()
                            ),
                            true
                    )
            );
        }
    }

    public static String returnNewValue(String type, String name, String url, String varName) {
        String value="";
        if (type.equals("headers")) {
            if ("Authorization".equals(name)) {
                value = "Bearer ${" + varName + "}";
            } else {
                value = "${" + varName + "}";
            }
        } else if (type.equals("query")) {
            value = "${" + varName + "}";
        } else if (type.equals("url")) {
            value = url.replace(name, "${" + varName + "}");
        } else if(type.equals("cookie")) {
                value="${"+varName+"}";
        } else if(type.equals("request")) {
            value="${"+varName+"}";
        } else if (type.equals("postData")){
            value="${"+varName+"}";
        }
        return value;
    }

    public static ArrayList<ArrayList<String>> getInfoReqWS(String url,int index_requestWS,String FILENAME_HAR) {
        ArrayList<ArrayList<String>> info = new ArrayList<>();
        /*ArrayList<String> num_req = new ArrayList<>();
        ArrayList<String> data_req = new ArrayList<>();
        info.add(num_req);
        info.add(data_req);
        int index=0;

        JSONParser parser = new JSONParser();

        try (FileReader in = new FileReader(FILENAME_HAR, StandardCharsets.UTF_8)) {
            Object obj = parser.parse(in);

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray entries = (JSONArray) ((JSONObject) jsonObject.get("log")).get("entries");
            Iterator<JSONObject> iterator = entries.iterator();
            int index_num_req=0;
            while (iterator.hasNext()) {
                JSONObject item = iterator.next();
                JSONObject request = (JSONObject) item.get("request");

                if (url.equals(request.get("url").toString()) && index_requestWS==index_num_req) {
                    JSONArray webSocketMessage = (JSONArray) item.get("_webSocketMessages");
                    for (int i = 0; i < webSocketMessage.size(); i++) {
                        JSONObject message = (JSONObject) webSocketMessage.get(i);
                        String type = message.get("type").toString();
                        String data = message.get("data").toString();
                        if ("request".equals(type)) {
                            info.get(0).add(String.valueOf(index));
                            info.get(1).add(data);
                            index++;
                        }
                    }
                }
                index_num_req++;
            }
        }catch(Exception e ){}
        */
        return info;

    }


    public static ArrayList<String> getDataNameRequestWS(String data) throws ParseException {
        ArrayList<String> result = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object json = parser.parse(data);
        visitNodeDataRequestWS(result,json,"$");
        return result;
    }

    public static void visitNodeDataRequestWS(List<String> result, Object object,String name) {

        if(object.getClass() == JsonObject.class)
        {
            JsonObject node = (JsonObject)object;
            if(node.isJsonNull()) return;
            for(String key : node.keySet()){
                JsonElement value = node.get(key);
                if(value.isJsonPrimitive() || value.isJsonNull()) {
                    // atomic node
                    result.add(key);
                } else{
                    visitNodeDataRequestWS(result,value,String.format("%s.%s", name,key));
                }
            }
        }else if(object.getClass() == JsonArray.class){
            JsonArray array  =(JsonArray) object;
            int id = 0;
            for(JsonElement element : array){
                if(element.isJsonPrimitive()){
                    result.add(name+"["+id+"]");
                }else {
                    visitNodeDataRequestWS(result, element, String.format("%s[%d]", name, id++));
                }
            }
        }
    }


    public static ArrayList<String> getFromByNumReq(ArrayList<String> list, String url, int num_req,String FILENAME_HAR) {
        ArrayList<String> new_from = new ArrayList<>(list);
        /*JSONParser parser = new JSONParser();
        if(num_req==0) {return list;}
        int actual_req=-1;
        try (FileReader in = new FileReader(FILENAME_HAR, StandardCharsets.UTF_8)) {
            Object obj = parser.parse(in);

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray entries = (JSONArray) ((JSONObject) jsonObject.get("log")).get("entries");

            Iterator<JSONObject> iterator = entries.iterator();
            int index_request =0;
            while (iterator.hasNext()) {
                JSONObject item = iterator.next();
                JSONObject request = (JSONObject) item.get("request");

                if (url.equals(request.get("url").toString())) {
                    JSONArray webSocketMessage = (JSONArray) item.get("_webSocketMessages");
                    for(int i=0;i<webSocketMessage.size();i++) {
                        JSONObject message = (JSONObject) webSocketMessage.get(i);
                        String type = message.get("type").toString();
                        if(actual_req< num_req) {
                            if ("request".equals(type)) {
                                actual_req++;
                            }
                            else if ("response".equals(type)) {
                                new_from.add("["+index_request+"]"+url + "_webSocketMessages[" + i + "]");
                            }
                        }
                        else {return new_from;}
                    }
                }
                index_request++;
            }
        } catch (Exception e) {
        }*/
        return new_from;
    }

    public static ArrayList<String> extendsFromListWS(ArrayList<String> list, String url,String FILENAME_HAR) {
        ArrayList<String> new_from = new ArrayList<>(list);
       /* JSONParser parser = new JSONParser();
        try (FileReader in = new FileReader(FILENAME_HAR, StandardCharsets.UTF_8)) {
            Object obj = parser.parse(in);

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray entries = (JSONArray) ((JSONObject) jsonObject.get("log")).get("entries");

            Iterator<JSONObject> iterator = entries.iterator();

            int index_request=0;
            while (iterator.hasNext()) {
                JSONObject item = iterator.next();
                JSONObject request = (JSONObject) item.get("request");

                if (url.equals(request.get("url").toString())) {
                    JSONArray webSocketMessage = (JSONArray) item.get("_webSocketMessages");
                    for(int i=0;i<webSocketMessage.size();i++) {
                        JSONObject message = (JSONObject) webSocketMessage.get(i);
                        String type = message.get("type").toString();
                        String data = message.get("data").toString();

                        if ("response".equals(type)) {
                            String urlnew = "["+index_request+"]"+url+"_webSocketMessages[" + i + "]";
                            new_from.add(urlnew);
                        }
                    }
                }
                index_request++;
            }
        } catch (Exception e) {
        }
        */
        return new_from;
    }
}
