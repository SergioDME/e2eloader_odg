package Services.HomeView.WorkloadGraph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import Entity.UltimateThreadGroup;


public class ChartWorkload{

    public ChartPanel ReturnChartPanel(ArrayList<UltimateThreadGroup> threadsgrup)
    {
        HashMap<String,ArrayList<UltimateThreadGroup>> threadGroupsMap = ConvertThreadGroupArrayToMap(threadsgrup);
        XYDataset dataset = createDataset(threadGroupsMap);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart){
            @Override
            public Dimension getPreferredSize() {
                // given some values of w & h
                return new Dimension(1000   ,400);
            }
        };
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
    }

    private XYDataset createDataset (HashMap<String,ArrayList<UltimateThreadGroup>> threadGroupsMap) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(String key : threadGroupsMap.keySet()) {
            dataset.addSeries(returnNewSeriesFromMapByKey(key,threadGroupsMap));
        }
            return dataset;
        }

    private XYSeries returnNewSeriesFromMapByKey(String key, HashMap<String, ArrayList<UltimateThreadGroup>> threadGroupsMap) {

        ArrayList<ArrayList<MyPoint>> rows = new ArrayList<>();
        ArrayList<UltimateThreadGroup> ultimateThreadGroups = threadGroupsMap.get(key);
        ArrayList<Integer> threadcount = new ArrayList<>();
        ArrayList<Integer> initialdelay = new ArrayList<>();
        ArrayList<Integer> startup =  new ArrayList<>();
        ArrayList<Integer> hold =  new ArrayList<>();
        ArrayList<Integer> down =  new ArrayList<>();
        int count =0;
        for(UltimateThreadGroup u : ultimateThreadGroups){
            String tn = u.getThreadsCount();
            String id = u.getInitialDelay();
            String r = u.getStartupTime();
            String h = u.getHoldLoadFor();
            String s = u.getShutDownTime();
            if (!key.isEmpty() && !tn.isEmpty() && !id.isEmpty() && !r.isEmpty() && !h.isEmpty() && !s.isEmpty()) {
                count++;
                threadcount.add(Integer.parseInt(u.getThreadsCount()));
                initialdelay.add(Integer.parseInt(u.getInitialDelay()));
                startup.add(Integer.parseInt(u.getStartupTime()));
                hold.add(Integer.parseInt(u.getHoldLoadFor()));
                down.add(Integer.parseInt(u.getShutDownTime()));
            }
        }
        for(int i = 0 ; i< count;i++){
            rows.add(createSeriesByBehaviorData(i,threadcount,initialdelay,startup,hold,down));
        }

        for(int i=0;i<rows.size();i++){
            for(MyPoint p : rows.get(i)){
                System.out.println(p.getX()+"-"+p.getY());
            }
        }
        System.out.println();

        XYSeries series = new XYSeries(key.split("_")[0]);
        series.add(0,0);
        for(int i =0 ; i < rows.size();i++)
        {
            ArrayList<MyPoint> currentRow = rows.get(i);
            for(int z=0 ; z< currentRow.size();z++)
            {
                MyPoint pI = currentRow.get(z);

                float x = pI.getX();
                float newy = pI.getY();
                boolean add = true;
                for(int j=0;j<rows.size();j++)
                {
                    if(i!=j)
                    {
                        ArrayList<MyPoint> RowJ = rows.get(j);
                        for (int k = 0; k < RowJ.size()-1; k++) {
                            if ( x>RowJ.get(k).getX() && x < RowJ.get(k+1).getX() ) {
                                newy+=RowJ.get(k).getY();
                            } else if(x == RowJ.get(k).getX())
                            {
                                if(newy < RowJ.get(k).getY())
                                {
                                    if(z<currentRow.size()-1)
                                    {
                                        if (!(newy > currentRow.get(z + 1).getY())) {
                                            add = false;
                                            break;
                                        }
                                    }else {
                                        add = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if(!add) {break;}
                    }
                }
                if(add) {
                    series.add(x, newy);
                }
            }
        }
        return  series;
    }

    private ArrayList<MyPoint> createSeriesByBehaviorData(int j, ArrayList<Integer>threadcount, ArrayList<Integer> initialdelay, ArrayList<Integer> startup, ArrayList<Integer> hold, ArrayList<Integer> down) {

        ArrayList<MyPoint> series = new ArrayList<>();
        //delay
        series.add( new MyPoint(initialdelay.get(j), 0));

        //startup
        if (startup.get(j) > 0) {
            for (int i = 1; i <= threadcount.get(j); i++) {
                series.add(new MyPoint(initialdelay.get(j) + ((startup.get(j) / threadcount.get(j) * i)), i));
            }
        }
        //hold load
        series.add(new MyPoint(initialdelay.get(j) + startup.get(j), threadcount.get(j)));
        series.add(new MyPoint(initialdelay.get(j) + startup.get(j) + hold.get(j), threadcount.get(j)));

        //shutdown
        if (down.get(j) > 0) {
            for (int i = threadcount.get(j); i >= 0; i--) {
                float incr = down.get(j) / threadcount.get(j);
                series.add(new MyPoint(initialdelay.get(j) + startup.get(j) + hold.get(j) + incr, i));
            }
        } else {
                series.add(new MyPoint(initialdelay.get(j) + startup.get(j) + hold.get(j), 0));
        }
        return series;
    }

    private JFreeChart  createChart (final XYDataset dataset){
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Workload",
                "Elapsed time",
                "Number of active threads",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        Random rand = new Random();
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for(int i=0 ;i<dataset.getSeriesCount();i++){
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            renderer.setSeriesPaint(i,new Color(r,g,b));
            renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        }
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);
        chart.setTitle(new TextTitle("Workload",new Font("Serif",Font.BOLD,18))
        );
        return chart;
    }


    public static HashMap<String,ArrayList<UltimateThreadGroup>> ConvertThreadGroupArrayToMap(ArrayList<UltimateThreadGroup> threadGroups) {
        HashMap<String,ArrayList<UltimateThreadGroup>> result = new HashMap<>();
        for(UltimateThreadGroup td : threadGroups) {
            if(result.containsKey(td.getFilename())) {
                result.get(td.getFilename()).add(td);
            }else {
                result.put(td.getFilename(),new ArrayList<UltimateThreadGroup>());
                result.get(td.getFilename()).add(td);
            }
        }
        return result;
    }

}
