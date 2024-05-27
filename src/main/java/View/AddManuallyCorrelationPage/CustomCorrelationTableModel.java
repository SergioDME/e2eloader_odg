package View.AddManuallyCorrelationPage;
import Entity.CSVNode;
import Entity.CheckableItem;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.ResponseUnstructured;
import Services.ResponseAnalyzer.StructuredObject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomCorrelationTableModel extends AbstractTableModel {


    public static class  ResponseParamCheckable{

        public Object getResponse() {
            return response;
        }

        public void setResponse(Object response) {
            this.response = response;
        }

        public boolean isIs_selected() {
            return is_selected;
        }

        public void setIs_selected(boolean is_selected) {
            this.is_selected = is_selected;
        }

        Object response;
        boolean is_selected;
        boolean imported_from_csv=false;

        public boolean isImported_from_csv() {
            return imported_from_csv;
        }

        public void setImported_from_csv(boolean imported_from_csv) {
            this.imported_from_csv = imported_from_csv;
        }

        public CSVNode getCsvNode() {
            return csvNode;
        }

        public void setCsvNode(CSVNode csvNode) {
            this.csvNode = csvNode;
        }

        CSVNode csvNode;

        public  ResponseParamCheckable(Object response){
            this.response=response;
            this.is_selected= false;
        }

        public ResponseParamCheckable(CSVNode csvNode){
            this.is_selected=true;
            this.csvNode= csvNode;
            this.imported_from_csv=true;
        }

    }

    public List<ResponseParamCheckable> getItems() {
        return items;
    }
    public void setItems(List<ResponseParamCheckable> list){this.items=list;}
    public void cleanItems(){
        this.items.clear();
    }
    public void setItemFromResponseUnstructured(Object object) {
        if(object.getClass() == ResponseUnstructured.class){
           ResponseUnstructured responseUnstructured = (ResponseUnstructured) object;
            for(Object resp_obj : responseUnstructured.getObjects()){
                if(resp_obj.getClass() == AtomicObject.class){
                    items.add(new ResponseParamCheckable((AtomicObject)resp_obj));
                }else if(resp_obj.getClass() == StructuredObject.class){
                    setItemFromResponseUnstructured(resp_obj);
                }
            }
        }else if(object.getClass() == StructuredObject.class){
            StructuredObject structuredObject = (StructuredObject) object;
            items.add(new ResponseParamCheckable(object));
            for(Object resp_obj : structuredObject.getObjects()){
                if(resp_obj.getClass() == AtomicObject.class){
                    items.add(new ResponseParamCheckable((AtomicObject)resp_obj));
                }else if(resp_obj.getClass() == StructuredObject.class){
                    setItemFromResponseUnstructured(resp_obj);
                }
            }
        }
    }

    private List<ResponseParamCheckable> items ;
    public CustomCorrelationTableModel() {
        this.items = new ArrayList<>();
    }
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0: {
                return "Name";
            }
            case 1: {
                return "Value";
            }

            case 2: {
                return "";
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        if (i == 2) {
                return Boolean.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        if(i1==2) return true;
        return false;
    }

    @Override
    public Object getValueAt(int i, int i1) {

        ResponseParamCheckable item = this.items.get(i);
        switch (i1) {
            case 0 : {
                if(item.response.getClass() == AtomicObject.class){
                    AtomicObject atomicObject = (AtomicObject) item.response;
                    return  atomicObject.getName();
                }else if(item.response.getClass() == StructuredObject.class){
                    StructuredObject structuredObject =(StructuredObject) item.response;
                    return structuredObject.getName();
                }
            }
            case 1 : {
                if(item.response.getClass() == AtomicObject.class){
                    AtomicObject atomicObject = (AtomicObject) item.response;
                    return  atomicObject.getValue();
                }else if(item.response.getClass() == StructuredObject.class){
                    StructuredObject structuredObject =(StructuredObject) item.response;
                    return structuredObject.value;
                }
            }
            case 2 : {return item.is_selected;}
        }
        return null;
    }

    @Override
    public void setValueAt (Object value, int r, int c) {
        ResponseParamCheckable item = this.items.get(r);
        switch (c) {
            case 0 : {
                if(item.response.getClass() == AtomicObject.class){
                    AtomicObject atomicObject = (AtomicObject) item.response;
                    atomicObject.setName((String) value);
                }else if(item.response.getClass() == StructuredObject.class){
                    StructuredObject structuredObject =(StructuredObject) item.response;
                    structuredObject.setName((String) value);
                }
            }
            case 1 : {
                if(item.response.getClass() == AtomicObject.class){
                    AtomicObject atomicObject = (AtomicObject) item.response;
                    atomicObject.setValue((String) value);
                }else if(item.response.getClass() == StructuredObject.class){
                    StructuredObject structuredObject =(StructuredObject) item.response;
                    structuredObject.setValue((String) value);
                }
            }
            case 2 : {item.setIs_selected((boolean) value);}
        }
        this.fireTableCellUpdated(r,c);
    }
}
