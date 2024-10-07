package View.CustomizeCorrelationPage;

import Entity.*;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.StructuredObject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ReplacementTableModel extends AbstractTableModel {

    public static enum TableView {
        HEADER, QUERY, COOKIE, URL, REQUEST,POSTDATA
    }

    public void setItems(List<CheckableItem> items) {
        this.items = items;
    }

    private List<CheckableItem> items;

    private TableView viewType;

    public ReplacementTableModel(TableView viewType, List<CheckableItem> items) {
        this.viewType = viewType;
        this.items = items;
    }
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        if(viewType == TableView.REQUEST) {
            return 8;
        }
        return 6;
    }

    @Override
    public String getColumnName(int i) {

        if (viewType == TableView.URL) {
            switch (i) {
                case 0 : {return "";}
                case 1 : { return "SubPath";}
                case 2 : {return "numReq";}
                case 3 : {return "From";}
                case 4 : {return "Name";}
                case 5 : {return "";}
            }
        }
         else if (viewType == TableView.REQUEST) {
            switch (i) {
                case 0 : {return "";}
                case 1 : { return "ReqWS n°";}
                case 2 : {return "Name";}
                case 3 : { return "numReq";}
                case 4 : {return "From";}
                case 5 : {return "Value";}
                case 6 : {return "RegEx";}
                case 7 : {return "";}
            }
        } else {
            switch (i) {
                case 0 : {return "";}
                case 1: {return "Name";}
                case 2: {return "numReq";}
                case 3: {return "From";}
                case 4: {return "Value";}
                case 5: {return "";}
            }
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        if(viewType == TableView.REQUEST) {
            if(i==7) {
                return Boolean.class;
            }
        }
        else {
            if (i == 5) {
                return Boolean.class;
            }
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }


    private static String getIndexByNode(Object from){
        if(from == null){
            return "-";
        }else if(from.getClass() == MyNode.class){
            return ((MyNode)from).indexs.toString();
        }
        return "-";
    }

    private static String getFromByNode(Object from, AtomicObject dependency){
        if(from == null){
                return dependency.name;
        } else if(from.getClass() == MyNode.class){
            return ((MyNode)from).request.getUrl();
        } else if(from.getClass() == CSVNode.class){
            return ((CSVNode)from).getFilename();
        }
        return "-";
    }

    private  static String getValueByNode(Object from, AtomicObject dependency,StructuredObject dep_s, boolean is_primitive){
        if(from == null) {
            return dependency.value;

        }else if(from.getClass() == MyNode.class){
            if(is_primitive)
                return dependency.name;
            else
                return dep_s.name;
        }
        return dependency.value;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        if (viewType == TableView.URL) {
            CheckableItem itemUrl = this.items.get(i);
            EdgeUrl edgeUrl = (EdgeUrl)itemUrl.getEdge();
            switch (i1) {
                case 0 : {return "(i)";}
                case 1 : { return edgeUrl.subPath;}
                case 2 : {
                    return  getIndexByNode(edgeUrl.from);
                }
                case 3 : {
                    return  getFromByNode(edgeUrl.from,edgeUrl.dependency);
                }
                case 4 : {
                    return  getValueByNode(edgeUrl.from,edgeUrl.dependency,null,true);
                }
                case 5 : {return itemUrl.isSelected();}
            }
        } else if (viewType == TableView.REQUEST){
            /*CheckableItem item = this.items.get(i);
            switch (i1) {
                case 0 : {return "(i)";}
                case 1 : {return item.getNumReqWS();}
                case 2 : { return item.getItem().getName(); }
                case 3 : { return item.getItem().getNum_req();}
                case 4 : {return item.getItem().getFrom();}
                case 5 : {return item.getItem().getValue();}
                case 6 : {return item.getItem().getRegEx();}
                case 7 : {return item.isSelected();}
            }*/
        }else {
            CheckableItem item = this.items.get(i);
            String name="";
            String num_req="";
            String from ="";
            String value ="";
            if(item.getEdge().getClass() == EdgeHeader.class) {
                EdgeHeader edgeHeader = (EdgeHeader) item.getEdge();
                name=edgeHeader.header_name;
                num_req=getIndexByNode(edgeHeader.from);
                from = getFromByNode(edgeHeader.from,edgeHeader.dependency);
                value = getValueByNode(edgeHeader.from,edgeHeader.dependency,null,true);
            }else if(item.getEdge().getClass() == EdgeQueryParam.class){
                EdgeQueryParam edgeQueryParam = (EdgeQueryParam) item.getEdge();
                name=edgeQueryParam.query_param_name;
                num_req= getIndexByNode(edgeQueryParam.from);
                from = getFromByNode(edgeQueryParam.from, edgeQueryParam.dependency);
                value = getValueByNode(edgeQueryParam.from,edgeQueryParam.dependency,null,true);
            }else if(item.getEdge().getClass() == EdgeCookie.class){
                EdgeCookie edgeCookie = (EdgeCookie) item.getEdge();
                name=edgeCookie.name;
                num_req=getIndexByNode(edgeCookie.from);
                from = getFromByNode(edgeCookie.from,edgeCookie.dependency);
                value = getValueByNode(edgeCookie.from,edgeCookie.dependency,null,true);
            }else if(item.getEdge().getClass() == EdgeBodyJSON.class){
                EdgeBodyJSON edgeBodyJSON = (EdgeBodyJSON) item.getEdge();
                name=edgeBodyJSON.name;
                num_req=getIndexByNode(edgeBodyJSON.from);
                from = getFromByNode(edgeBodyJSON.from,edgeBodyJSON.dependency);
                value = getValueByNode(edgeBodyJSON.from, edgeBodyJSON.dependency,edgeBodyJSON.structuredObject,edgeBodyJSON.isPrimitive());
            }else if(item.getEdge().getClass() == EdgeBodyUE.class){
                EdgeBodyUE edgeBodyUE = (EdgeBodyUE) item.getEdge();
                name=edgeBodyUE.name;
                num_req=getIndexByNode(edgeBodyUE.from);
                from = getFromByNode(edgeBodyUE.from,edgeBodyUE.dependency);
                value =getValueByNode(edgeBodyUE.from, edgeBodyUE.dependency,null,true);
            }
            switch (i1) {
                    case 0 : {return "(i)";}
                    case 1 : { return name; }
                    case 2:  {return num_req;}
                    case 3 : {return from;}
                    case 4 : {return value;}
                    case 5 : {return item.isSelected();}
                }

            }
        return null;
    }

    private  static void setFromByNode(Object from, Object value){
        if(from.getClass() == MyNode.class)
            ((MyNode)from).request.setUrl((String)value);
        else if(from.getClass() == CSVNode.class){
            ((CSVNode)from).setFilename((String) value);
        }
    }

    private static void setValueByNode(Object from, AtomicObject atomicObject, StructuredObject structuredObject, boolean is_primitive,Object value){
        if(from.getClass() == MyNode.class)
            if(is_primitive)
                atomicObject.setName((String)value);
            else
                structuredObject.setName((String) value);
        else
                atomicObject.setValue((String) value);
    }

    @Override
    public void setValueAt (Object value, int r, int c) {

        if (viewType == TableView.URL) {

            CheckableItem itemUrl = this.items.get(r);
            EdgeUrl edgeUrl = (EdgeUrl) itemUrl.getEdge();
            switch (c) {
                case 1 : {edgeUrl.setSubPath((String)value); break; }
                case 2 : {
                    if(edgeUrl.from.getClass() == MyNode.class){
                        ArrayList<Integer> arrayList = (ArrayList<Integer>) getIndex(value);
                        ((MyNode)edgeUrl.from).setIndexs(arrayList);
                    }
                    break;
                }
                case 3 : {
                    setFromByNode(edgeUrl.from,value);
                    break;
                }
                case 4 : {
                    setValueByNode(edgeUrl.from,edgeUrl.dependency,null,true,value);
                    break;
                }
                case 5 : {itemUrl.setSelected((boolean)value );}

            }
        } else if ( viewType == TableView.REQUEST) {
           /* CheckableItem item = this.items.get(r);
            switch (c) {
                case 1 : {item.setNumReqWS((int)value); break;}
                case 2 : {item.getItem().setName((String)value); break;}
                case 3 : {item.getItem().setNum_req((int)value); break;}
                case 4 : {item.getItem().setFrom((String)value); break;}
                case 5 : {item.getItem().setValue((String) value); break;}
                case 6 : {item.getItem().setRegEx((String) value);break;}
                case 7 : {item.setSelected((boolean) value);}
            }*/

        } else {
            CheckableItem item = this.items.get(r);

            if(item.getEdge().getClass() == EdgeHeader.class){
                EdgeHeader edgeHeader = (EdgeHeader) item.getEdge();

                switch (c) {
                    case 1 : {edgeHeader.setHeader_name(((String)value)); break;}
                    case 2 : {
                        if(edgeHeader.from.getClass() == MyNode.class){
                            ArrayList<Integer> arrayList = (ArrayList<Integer>) getIndex(value);
                            ((MyNode)edgeHeader.from).setIndexs(arrayList);
                        }
                        break;
                    }
                    case 3 : {setFromByNode(edgeHeader.from,value); break;}
                    case 4 : {setValueByNode(edgeHeader.from,edgeHeader.dependency,null,true,value);break;}
                    case 5 : {item.setSelected((boolean) value);}

                }

            }else if(item.getEdge().getClass() == EdgeQueryParam.class){
                EdgeQueryParam edgeQueryParam = (EdgeQueryParam) item.getEdge();

                switch (c) {
                    case 1 : {edgeQueryParam.setQuery_param_name((String)value); break;}
                    case 2 : {
                        if(edgeQueryParam.from.getClass() == MyNode.class){
                            ArrayList<Integer> arrayList = (ArrayList<Integer>) getIndex(value);
                            ((MyNode)edgeQueryParam.from).setIndexs(arrayList);
                        }
                        break;
                    }
                    case 3 : {setFromByNode(edgeQueryParam.from,value); break;}
                    case 4 : {setValueByNode(edgeQueryParam.from,edgeQueryParam.dependency,null,true,value);break;}
                    case 5 : {item.setSelected((boolean) value);}

                }

            }else if(item.getEdge().getClass() == EdgeCookie.class){
                EdgeCookie edgeCookie = (EdgeCookie) item.getEdge();

                switch (c) {
                    case 1 : {edgeCookie.setName((String)value); break;}
                    case 2 : {
                        if(edgeCookie.from.getClass() == MyNode.class){
                            ArrayList<Integer> arrayList = (ArrayList<Integer>) getIndex(value);
                            ((MyNode)edgeCookie.from).setIndexs(arrayList);
                        }
                        break;
                    }
                    case 3 : {setFromByNode(edgeCookie.from,value); break;}
                    case 4 : {setValueByNode(edgeCookie.from,edgeCookie.dependency,null,true,value);break;}
                    case 5 : {item.setSelected((boolean) value);}

                }

            }else if(item.getEdge().getClass() == EdgeBodyJSON.class){
                EdgeBodyJSON edgeBodyJSON = (EdgeBodyJSON) item.getEdge();

                switch (c) {
                    case 1 : {edgeBodyJSON.setName((String)value); break;}
                    case 2 : {
                        if(edgeBodyJSON.from.getClass() == MyNode.class){
                            ArrayList<Integer> arrayList = (ArrayList<Integer>) getIndex(value);
                            ((MyNode)edgeBodyJSON.from).setIndexs(arrayList);
                        }
                        break;}
                    case 3 : {setFromByNode(edgeBodyJSON.from,value); break;}
                    case 4 : {
                        setValueByNode(edgeBodyJSON.from,edgeBodyJSON.dependency,edgeBodyJSON.structuredObject,edgeBodyJSON.isPrimitive(),value);
                        break;
                    }
                    case 5 : {item.setSelected((boolean) value);}

                }

            }else if(item.getEdge().getClass() == EdgeBodyUE.class){
                EdgeBodyUE edgeBodyUE = (EdgeBodyUE) item.getEdge();

                switch (c) {

                    case 1 : {edgeBodyUE.setName((String)value); break;}
                    case 2 : {
                        if(edgeBodyUE.from.getClass() == MyNode.class){
                            ArrayList<Integer> arrayList = (ArrayList<Integer>) getIndex(value);
                            ((MyNode)edgeBodyUE.from).setIndexs(arrayList);
                        }
                        break;}
                    case 3 : {setFromByNode(edgeBodyUE.from,value); break;}
                    case 4 : {setValueByNode(edgeBodyUE.from,edgeBodyUE.dependency,null,true,value);break;}
                    case 5 : {item.setSelected((boolean) value);}

                }

            }
        }
        this.fireTableCellUpdated(r,c);
    }


    public List<Integer> getIndex(Object value){
        String inputNumbers = (String) value;
        String numbersString= inputNumbers.substring(1,inputNumbers.length()-1);
        String [] numberArrays = numbersString.split(",");
        List<Integer> arrayList = new ArrayList<>();
        for(String number : numberArrays){
            arrayList.add(Integer.parseInt(number.trim()));
        }
        return arrayList;
    }
}

