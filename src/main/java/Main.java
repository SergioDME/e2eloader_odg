import Services.HomeView.E2eTableService;
import Services.HomeView.HomeServices;
import Services.HomeView.WorkloadTableModel;
import Services.HomeView.WorkloadTableService;
import View.Home;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import Services.HomeView.WorkloadGraph.ChartWorkload;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;
import org.jfree.chart.ChartPanel;

public class Main {
    public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

       /* for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }*/
        try{
            IntelliJTheme.setup( Main.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        }catch (Exception e){
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Color botton_color = new Color(245,121,0);
                Home home = null;
                try {
                    home = new Home();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                home.setTitle("E2E-LOADER");
                ImageIcon folderIcon = new ImageIcon("src/data/img/folder-25.png");
                home.getPathJLabel().setText("");
                home.getPathJLabel().setIcon(folderIcon);
                home.getPathJButton().setText("");
                home.getPathJButton().setIcon(new ImageIcon("src/data/img/search-25.png"));
                home.getPathJButton().setBackground(botton_color);
                home.getPathJButton().addActionListener(HomeServices.actionPathSearch(home));
                home.getSaveButton().addActionListener(HomeServices.actionSaveButton(home));
                home.getScriptChosenTable().setModel(new WorkloadTableModel());
                WorkloadTableService.setActionWorkloadTable(home.getScriptChosenTable(),home);
                E2eTableService.setActionE2ETable(home.getE2eTestCases(),home);
                home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                home.setSize(1000, 800);
                home.setLocationRelativeTo(null);
                ChartWorkload chartWorkload = new ChartWorkload();
                ChartPanel chartPanel = chartWorkload.ReturnChartPanel(((WorkloadTableModel)home.getScriptChosenTable().getModel()).getThredGroups());
                home.getCHART().add(chartPanel);
                home.setVisible(true);
                ImageIcon img = new ImageIcon("src/data/img/logo.png");
                home.setIconImage(img.getImage());
            }
        });
    }
}
