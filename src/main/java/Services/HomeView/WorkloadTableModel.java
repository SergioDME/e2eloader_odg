package Services.HomeView;

import Entity.UltimateThreadGroup;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class WorkloadTableModel extends AbstractTableModel {

    public ArrayList<UltimateThreadGroup> getThredGroups() {
        return thredGroups;
    }

    private ArrayList<UltimateThreadGroup> thredGroups;


    public WorkloadTableModel() {
        this.thredGroups = new ArrayList<UltimateThreadGroup>();
    }
    @Override
    public int getRowCount() {return thredGroups.size();}
    @Override
    public int getColumnCount( ){
        return 8;
    }

    @Override
    public String getColumnName(int i) {
        switch (i){
            case 0 : { return "Script Name";}
            case 1 : {return "Threads Count";}
            case 2 : {return "Initial Delay,sec";}
            case 3 : {return "Startup Time,sec";}
            case 4 : {return "Hold Load For,sec";}
            case 5 : {return "Shutdown Time";}
            case 6 : {return "";}
            case 7 : {return "";}
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i,int i1) {
        if(i1==0 || i1==6 || i1==7) return  false;
        return  true;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        UltimateThreadGroup ultimateThreadGroup = thredGroups.get(i);
        switch (i1) {
            case 0 : {return ultimateThreadGroup.getFilename();}
            case 1 : {return ultimateThreadGroup.getThreadsCount();}
            case 2 : {return ultimateThreadGroup.getInitialDelay();}
            case 3 : {return ultimateThreadGroup.getStartupTime();}
            case 4 : {return ultimateThreadGroup.getHoldLoadFor();}
            case 5 : {return ultimateThreadGroup.getShutDownTime();}
            case 6 : {return ultimateThreadGroup.getAdd();}
            case 7 : {return  ultimateThreadGroup.getDelete();}
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int r, int c) {
        UltimateThreadGroup ultimateThreadGroup = thredGroups.get(r);
        switch (c){
            case 0 : { ultimateThreadGroup.setFilename((String) value);break;}
            case 1 : { ultimateThreadGroup.setThreadsCount((String) value);break;}
            case 2 : { ultimateThreadGroup.setInitialDelay((String) value);break;}
            case 3 : { ultimateThreadGroup.setStartupTime((String) value);break;}
            case 4 : { ultimateThreadGroup.setHoldLoadFor((String) value);break;}
            case 5 : { ultimateThreadGroup.setShutDownTime((String) value);break;}
        }
        this.fireTableCellUpdated(r,c);
    }

}
