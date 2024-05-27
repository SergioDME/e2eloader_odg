package Services.HomeView;

import Entity.UltimateThreadGroup;
import Services.CorrelationsView.CorrelationFrameService;
import View.CorrelationsPage.CorrelationFrame;
import View.Home;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;


public class E2eTableService {    public static void setActionE2ETable(JTable table, Home home) {
    table.addMouseListener(new java.awt.event.MouseAdapter(){
        @Override
        public void mouseClicked(java.awt.event.MouseEvent event) {
            int row = table.rowAtPoint(event.getPoint());
            int col = table.columnAtPoint(event.getPoint());
            String filename = ((E2eTestTableModel) table.getModel()).getFilename().get(row);
            int dotIndex = filename.indexOf('.');
            filename = (dotIndex == -1) ? filename: filename.substring(0, dotIndex);

            String path = home.getPathJField().getText();
            String harpath = path+"/hars";
            String harfilename = filename+".har";
            if(col==2 && !(((E2eTestTableModel)home.getE2eTestCases().getModel()).getFilenameCheck().get(row))) { //not checked

                if(!HomeServices.checkFileExistByExt(filename,harpath,"har")){ // check if har file exist
                    JOptionPane.showMessageDialog(null, harfilename+" file not found! check whether "+filename+" was performed.",
                            "Error",JOptionPane.ERROR_MESSAGE
                    );
                }else {
                    CorrelationFrame correlationFrame = CorrelationFrameService.openCorrelationFrame(filename,harpath,harfilename,home);
                    correlationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    correlationFrame.setSize(1000,800);
                    correlationFrame.setLocationRelativeTo(null);
                    correlationFrame.setVisible(true);
                }
            }else if(col==2){ // already checked
                ((E2eTestTableModel)home.getE2eTestCases().getModel()).getFilenameCheck().set(row,false);
                ((E2eTestTableModel) home.getE2eTestCases().getModel()).fireTableDataChanged();
                Iterator<UltimateThreadGroup> ultimateThreadGroupIterator = ((WorkloadTableModel)home.getScriptChosenTable().getModel()).getThredGroups().iterator();
                while(ultimateThreadGroupIterator.hasNext()){
                    UltimateThreadGroup threadGroup = ultimateThreadGroupIterator.next();
                    if(threadGroup.getFilename().contains(filename)){
                        ultimateThreadGroupIterator.remove();
                        break;
                    }
                }
                ((WorkloadTableModel)home.getScriptChosenTable().getModel()).fireTableDataChanged();
            }
            /*else if(col==0){
                Path filePath = Path.of(descriptionFile);
                try {
                    String content = Files.readString(filePath);
                    JOptionPane.showMessageDialog(null, content,
                            "Info e2e test case",JOptionPane.DEFAULT_OPTION
                    );
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "No description found!",
                            "Info e2e test case",JOptionPane.ERROR_MESSAGE
                    );
                }
            }*/
        }
    });
}

}
