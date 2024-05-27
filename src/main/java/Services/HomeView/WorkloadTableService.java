package Services.HomeView;

import Entity.UltimateThreadGroup;
import Services.HomeView.WorkloadGraph.ChartWorkload;
import View.Home;
import org.jfree.chart.ChartPanel;

import javax.swing.*;

public class WorkloadTableService {

    public static void setActionWorkloadTable(JTable table, Home home ) {
        table.addMouseListener( new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                int row = table.rowAtPoint(event.getPoint());
                int col = table.columnAtPoint(event.getPoint());
                if(col==6){
                    String ScripNameChosen = ((WorkloadTableModel)table.getModel()).getThredGroups().get(row).getFilename();
                    ((WorkloadTableModel)home.getScriptChosenTable().getModel()).getThredGroups().add( new UltimateThreadGroup(
                            "","","","","",ScripNameChosen
                    ));
                    ((WorkloadTableModel) home.getScriptChosenTable().getModel()).fireTableDataChanged();
                } else if (col==7) {
                    ((WorkloadTableModel)home.getScriptChosenTable().getModel()).getThredGroups().remove(row);
                    ((WorkloadTableModel) home.getScriptChosenTable().getModel()).fireTableDataChanged();
                    updateChartWorkload(home);
                }else {
                    System.out.println("row:"+row+"col:"+col);
                    if(row!=-1) {
                        String key = ((WorkloadTableModel) table.getModel()).getThredGroups().get(row).getFilename();
                        String tn = ((WorkloadTableModel) table.getModel()).getThredGroups().get(row).getThreadsCount();
                        String id = ((WorkloadTableModel) table.getModel()).getThredGroups().get(row).getInitialDelay();
                        String r = ((WorkloadTableModel) table.getModel()).getThredGroups().get(row).getStartupTime();
                        String h = ((WorkloadTableModel) table.getModel()).getThredGroups().get(row).getHoldLoadFor();
                        String s = ((WorkloadTableModel) table.getModel()).getThredGroups().get(row).getShutDownTime();
                        if (!key.equals("") && !tn.equals("") && !id.equals("") && !r.equals("") && !h.equals("") && !s.equals("")) {
                            updateChartWorkload(home);
                        }
                    }

                }
            }
        });

    }

    public static void updateChartWorkload(Home home){
        home.getCHART().remove(0);
        ChartWorkload chartWorkload = new ChartWorkload();
        ChartPanel chartPanel = chartWorkload.ReturnChartPanel(((WorkloadTableModel) home.getScriptChosenTable().getModel()).getThredGroups());
        home.getCHART().add(chartPanel);
        home.getCHART().updateUI();
    }


}
