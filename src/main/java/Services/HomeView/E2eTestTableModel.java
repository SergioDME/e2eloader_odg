package Services.HomeView;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class E2eTestTableModel extends AbstractTableModel {
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
    public E2eTestTableModel(ArrayList<String>list){
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
        return 3;
    }

    @Override
    public String getColumnName(int i) {
        switch (i){
            case 0 : {return "";}
            case 1 : { return "test case name";}
            case 2 : {return "";}
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        switch (i) {
            case 0 : {return String.class;}
            case 1 : {return String.class;}
            case 2 : {return Boolean.class;}
        }
        return null;
    }
    @Override
    public boolean isCellEditable(int i,int i1) {return  false;}

    @Override
    public Object getValueAt(int i, int i1) {
        switch (i1) {
            case 0 : {return "(i)";}
            case 1 : {return filename.get(i);}
            case 2 : {return filenameCheck.get(i);}
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int r, int c) {
        switch (c) {
            case 1: {
                filename.set(r, (String) value);
                break;
            }
            case 2: {
                filenameCheck.set(r, (Boolean) value);
                break;
            }
        }
        this.fireTableCellUpdated(r, c);
    }
}
