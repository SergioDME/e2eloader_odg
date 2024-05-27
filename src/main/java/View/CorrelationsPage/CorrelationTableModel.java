package View.CorrelationsPage;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CorrelationTableModel extends AbstractTableModel {

    public ArrayList<String> getFilename() {
        return filename;
    }

    public void setFilename(ArrayList<String> filename) {
        this.filename = filename;
    }

    public ArrayList<Boolean> getFilenameCheck() {
        return filenameCheck;
    }

    public void setFilenameCheck(ArrayList<Boolean> filenameCheck) {
        this.filenameCheck = filenameCheck;
    }

    private ArrayList<String> filename;
    private ArrayList<Boolean> filenameCheck;

    public CorrelationTableModel(ArrayList<String>list){
        this.filename=list;
        this.filenameCheck= new ArrayList<>();
        for(int i=0; i< list.size();i++) {
            filenameCheck.add(false);
        }
    }

    @Override
    public int getRowCount() {
        return  filename.size();
    }
    @Override
    public int getColumnCount(){
        return 2;
    }

    @Override
    public String getColumnName(int i) {
        switch (i){
            case 0 : { return "Correlation file name";}
            case 1 : {return "";}
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        switch (i) {
            case 0 : {return String.class;}
            case 1 : {return Boolean.class;}
        }
        return null;
    }
    @Override
    public boolean isCellEditable(int i,int i1) {
        if(i1==1) return true;
        return  false;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        switch (i1) {
            case 0 : {return filename.get(i);}
            case 1 : {return filenameCheck.get(i);}
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int r, int c) {
        switch (c){
            case 0 :{   filename.set(r,(String)value); break;}
            case 1 : { filenameCheck.set(r,(Boolean) value);break;}
        }
        this.fireTableCellUpdated(r,c);
    }

}
