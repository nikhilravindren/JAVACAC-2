package com.example;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Visualization {
    // creating the datacleanser object for loading the data.
    datacleanser data = new datacleanser("new_datas.csv");
    // creating the bar chart which showing the debt of companies based on the market type.
    public void chart1() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(293.157851, "large_cap ", "Series 1");
        dataset.addValue(341.294437, "mid_cap ", "Series 1");
        dataset.addValue(362.226040, "small_cap ", "Series 1");

        JFreeChart barChart = ChartFactory.createBarChart(
            "company valuation wise debt",
            "market capital",
            " debt",
            dataset,
            PlotOrientation.VERTICAL,
            true, 
            true, 
            false);

            ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        JFrame frame = new JFrame("Bar Chart");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);

    }

    // function which showing the scatter plot of different columns based on the another columns.
    public void chart2(){
        for(String column1:data.columns()){
            for(String column2:data.columns()){
        XYSeries series = new XYSeries(column1+" vs "+column2);
        Double[] col1 = data.column(column1);
        Double[] col2 = data.column(column2);
        for(int i=0;i<col1.length;i++){
            series.add(col1[i], col2[i]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        String title = column1+" vs "+column2;
        String xAxisLabel = column1;
        String yAxisLabel = column2;
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean showLegend = true;
        boolean tooltips = false;
        boolean urls = false;
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, dataset, orientation, showLegend, tooltips, urls);
    
        ChartPanel chartPanel = new ChartPanel(scatterPlot);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        JFrame frame = new JFrame("Scatter Plot");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
}
    // function which plotting the count of other columns based on the category column.
    public void chart3(){
        Double[][] datas = data.getdataframe();
        String[] columnNames = data.columns();
    for(int j=1;j<datas[0].length;j++){
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < datas.length; i++) {
        String category = datas[i][0].toString(); 
        Double count = datas[i][j]; 
        dataset.addValue(count, "Count", category);

    }

    
    String title = "Category Counts";
    String xAxisLabel = columnNames[0];
    String yAxisLabel = columnNames[j]; 
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    boolean showLegend = true;
    boolean tooltips = false;
    boolean urls = false;
    JFreeChart barChart = ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, dataset, orientation, showLegend, tooltips, urls);

    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    JFrame frame = new JFrame("count plot");
    frame.setContentPane(chartPanel);
    // frame.getContentPane().add(chartPanel, BorderLayout.WEST);
    

    frame.pack();
    frame.setVisible(true);


}
    }
}