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
import java.awt.geom.Ellipse2D;
import java.util.*;

import Entity.UltimateThreadGroup;


public class ChartWorkload{

    private static final Color[] FIXED_COLORS = {
            new Color(31, 119, 180), // Blu
            new Color(255, 127, 14), // Arancione
            new Color(44, 160, 44),  // Verde
            new Color(214, 39, 40),  // Rosso
            new Color(148, 103, 189),// Viola
            new Color(140, 86, 75),  // Marrone
            new Color(227, 119, 194),// Rosa
            new Color(127, 127, 127),// Grigio
            new Color(188, 189, 34), // Verde oliva
            new Color(23, 190, 207)  // Ciano
    };

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


        if (rows == null || rows.isEmpty()) {
            throw new IllegalArgumentException("No rows provided for merging.");
        }

        // Usa la prima riga come linea di base iniziale
        ArrayList<MyPoint> baseLine = new ArrayList<>(rows.get(0));

        // Itera sulle righe successive e fondile con la linea di base
        for (int i = 1; i < rows.size(); i++) {
            ArrayList<MyPoint> nextRow = rows.get(i);
            baseLine = mergeLines(baseLine, nextRow); // Fusione tra due righe
        }

        System.out.println("merged list");
        System.out.println(baseLine);


        // Crea la serie XY finale
        XYSeries series = new XYSeries(key);
        for (MyPoint point : baseLine) {
            series.add(point.getX(), point.getY());
        }

        return series;

    }



    // Metodo per calcolare l'intersezione tra due segmenti
    public static MyPoint findIntersection(MyPoint A1, MyPoint A2, MyPoint B1, MyPoint B2) {
        // Calcolare i determinanti
        float denom = (A1.getX() - A2.getX()) * (B1.getY() - B2.getY()) - (A1.getY() - A2.getY()) * (B1.getX() - B2.getX());

        // Se denom è zero, le linee sono parallele
        if (denom == 0) {
            return null; // Le linee sono parallele, non si intersecano
        }

        // Calcolare t e s
        float t = ((A1.getX() - B1.getX()) * (B1.getY() - B2.getY()) - (A1.getY() - B1.getY()) * (B1.getX() - B2.getX())) / denom;
        float s = ((A1.getX() - B1.getX()) * (A1.getY() - A2.getY()) - (A1.getY() - B1.getY()) * (A1.getX() - A2.getX())) / denom;

        // Verifica se l'intersezione è all'interno dei segmenti
        if (t >= 0 && t <= 1 && s >= 0 && s <= 1) {
            // Calcolare il punto di intersezione
            float intersectX = A1.getX() + t * (A2.getX() - A1.getX());
            float intersectY = A1.getY() + t * (A2.getY() - A1.getY());
            return new MyPoint(intersectX, intersectY);
        }

        // Se non è dentro i segmenti
        return null;
    }


    // Metodo per trovare tutti i punti di intersezione tra due polilinee
    public static ArrayList<MyPoint> findAllIntersections(ArrayList<MyPoint> polyline1, ArrayList<MyPoint> polyline2) {
        ArrayList<MyPoint> intersections = new ArrayList<>();

        // Itera su tutti i segmenti della polilinea 1
        for (int i = 0; i < polyline1.size() - 1; i++) {
            MyPoint A1 = polyline1.get(i);
            MyPoint A2 = polyline1.get(i + 1);

            // Itera su tutti i segmenti della polilinea 2
            for (int j = 0; j < polyline2.size() - 1; j++) {
                MyPoint B1 = polyline2.get(j);
                MyPoint B2 = polyline2.get(j + 1);

                // Trova l'intersezione tra il segmento A e il segmento B
                MyPoint intersection = findIntersection(A1, A2, B1, B2);
                if (intersection != null) {
                    intersections.add(intersection);
                }
            }
        }

        return intersections;
    }

    public static ArrayList<MyPoint> mergeLines(ArrayList<MyPoint> baseLine, ArrayList<MyPoint> newRow) {
        ArrayList<MyPoint> mergedList = new ArrayList<>();
        mergedList.addAll(baseLine);
        mergedList.addAll(newRow);
        mergedList.addAll(findAllIntersections(baseLine,newRow));
        Collections.sort(mergedList, Comparator
                .comparingDouble(MyPoint::getX)
                .thenComparingDouble(MyPoint::getY));
        System.out.println(mergedList);
        ArrayList<MyPoint> finalList = new ArrayList<>();

        //first point
        MyPoint p = mergedList.get(0);
        int previous_row = p.getRow_n();
        char previous_stat = p.getState();
        float previous_x = p.getX();
        float previous_y = p.getY();
        finalList.add(p);

        // Scorriamo la lista ordinata
        for (int i =1;i<mergedList.size();i++) {

            p = mergedList.get(i);
            int row = p.getRow_n();
            char state = p.getState();
            float x = p.getX();
            float y = p.getY();

            if (row != previous_row) {
                //ci stiamo intrecciando con l'altra riga o con un punto di intersezione
                if(state=='I'){ //intersezione
                    //caso in cui il precendete era un n ed è uguale y=0
                    MyPoint p_prev = finalList.get(finalList.size()-1);
                    MyPoint p_next = mergedList.get(i+1);
                    if (p_prev.getState()=='n'&& p_prev.getY()==0){

                        if (p_next.getY()>y){
                            finalList.set(finalList.size()-1,p_next);
                            finalList.add(p);
                            previous_row = p.getRow_n();
                            previous_stat = p.getState();
                            previous_x = p.getX();
                            previous_y = p.getY();
                            i++;

                        }else{
                            finalList.set(finalList.size()-1,p);
                            previous_row = p.getRow_n();
                            previous_stat = p.getState();
                            previous_x = p.getX();
                            previous_y = p.getY();
                        }
                        if(i+1==mergedList.size()){
                            finalList.add(p_prev);
                            previous_row = p_prev.getRow_n();
                            previous_stat = p_prev.getState();
                            previous_x = p_prev.getX();
                            previous_y = p_prev.getY();
                        }else{

                        }
                    }else{
                        finalList.add(p);
                        previous_row = p.getRow_n();
                        previous_stat = p.getState();
                        previous_x = p.getX();
                        previous_y = p.getY();
                    }
                }else {
                    if (previous_y == y) { //uguale

                        finalList.add(p);
                        previous_row = row;
                        previous_stat = state;
                        previous_x = x;
                        previous_y = y;

                    } else if (previous_y < y) { // sta sopra
                        finalList.add(p);
                        previous_row = row;
                        previous_stat = state;
                        previous_x = x;
                        previous_y = y;

                    } else { // sta sotto


                    }
                }
            } else {
                // stessa linea con y maggiore, inserisco
                if (state == 'r' || state == 'h') {
                    if (previous_y <= y) {
                        finalList.add(p);
                        previous_row = row;
                        previous_stat = state;
                        previous_x = x;
                        previous_y = y;
                    }
                } else if (state == 'n') {
                    if (previous_y >= y) {
                        finalList.add(p);
                        previous_row = row;
                        previous_stat = state;
                        previous_x = x;
                        previous_y = y;
                    }
                }
            }
        }
        return finalList;
    }


    private ArrayList<MyPoint> createSeriesByBehaviorData (int j, ArrayList<Integer>threadcount, ArrayList<Integer> initialdelay, ArrayList<Integer> startup, ArrayList<Integer> hold, ArrayList<Integer> down) {

        ArrayList<MyPoint> series = new ArrayList<>();
        //delay
        series.add( new MyPoint(initialdelay.get(j), 0,j,'d'));

        //startup
        if (startup.get(j) > 0) {
            for (int i = 1; i <= threadcount.get(j); i++) {
                float incr = (float) startup.get(j) / threadcount.get(j);
                series.add(new MyPoint((initialdelay.get(j) + (incr * i)), i,j,'r'));
            }
        }
        //hold load
        series.add(new MyPoint(initialdelay.get(j) + startup.get(j), threadcount.get(j),j,'h'));
        series.add(new MyPoint(initialdelay.get(j) + startup.get(j) + hold.get(j), threadcount.get(j),j,'h'));

        //shutdown
        int index_shutdown=1;
        if (down.get(j) > 0) {
            for (int i = threadcount.get(j)-1; i >= 0; i--) {
                float incr = (float) down.get(j) / threadcount.get(j);
                series.add(new MyPoint(initialdelay.get(j) + startup.get(j) + hold.get(j) + (incr*index_shutdown), i,j,'n'));
                index_shutdown+=1;
            }
        } else {
                series.add(new MyPoint(initialdelay.get(j) + startup.get(j) + hold.get(j), 0,j,'n'));
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
            Color color = FIXED_COLORS[i % FIXED_COLORS.length]; // Cicla attraverso i colori se ci sono più serie
            renderer.setSeriesPaint(i, color);
            renderer.setSeriesStroke(i, new BasicStroke(2.0f));
            //renderer.setSeriesShapesVisible(i, false); // Nascondi i punti
            renderer.setSeriesShapesVisible(i, true);            // Mostra i punti
            renderer.setSeriesShape(i, new Ellipse2D.Double(-2, -2, 4, 4)); // Piccolo cerchio
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
