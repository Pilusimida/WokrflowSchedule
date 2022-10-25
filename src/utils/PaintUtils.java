package utils;

import entity.Chromosome;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class PaintUtils {
//    public static void main(String[] args) throws IOException {
//        //设置散点图数据集
//        //设置第一个
//        XYSeries firefox = new XYSeries("Firefox");
//        firefox.add(1.0, 1.0);
//        firefox.add(2.0, 4.0);
//        firefox.add(3.0, 3.0);
//
//        //设置第二个
//        XYSeries chrome = new XYSeries("Chrome");
//        chrome.add(1.0, 4.0);
//        chrome.add(2.0, 5.0);
//        chrome.add(3.0, 6.0);
//
//        //设置第三个
//        XYSeries ie = new XYSeries("IE");
//        ie.add(3.0, 4.0);
//        ie.add(4.0, 5.0);
//        ie.add(5.0, 4.0);
//
//        //添加到数据集
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(firefox);
//        dataset.addSeries(chrome);
//        dataset.addSeries(ie);
//
//        //实现简单的散点图，设置基本的数据
//        JFreeChart freeChart = ChartFactory.createScatterPlot(
//                "数据散点图",// 图表标题
//                "Category",//y轴方向数据标签
//                "Score",//x轴方向数据标签
//                dataset,//数据集，即要显示在图表上的数据
//                PlotOrientation.VERTICAL,//设置方向
//                true,//是否显示图例
//                true,//是否显示提示
//                false//是否生成URL连接
//        );
//
//        //以面板显示
//        ChartPanel chartPanel = new ChartPanel(freeChart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(560, 400));
//
//        //创建一个主窗口来显示面板
//        JFrame frame = new JFrame("饼图");
//        frame.setLocation(500, 400);
//        frame.setSize(600, 500);
//
//        //将主窗口的内容面板设置为图表面板
//        frame.setContentPane(chartPanel);
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }

    public static void paintDiagram(List<List<Chromosome>> data){

        XYSeriesCollection collection=new XYSeriesCollection();
        int i=0;
        for(List<Chromosome> list:data){
            i++;
            XYSeries series=new XYSeries("rank "+i);
            for (Chromosome chromosome:list){
                series.add(chromosome.makespan,chromosome.cost);
            }
            collection.addSeries(series);
        }

        JFreeChart freeChart = ChartFactory.createScatterPlot(
                "Pareto Set Diagram",// 图表标题
                "cost",//y轴方向数据标签
                "makespan",//x轴方向数据标签
                collection,//数据集，即要显示在图表上的数据
                PlotOrientation.VERTICAL,//设置方向
                true,//是否显示图例
                true,//是否显示提示
                false//是否生成URL连接
        );

        ChartPanel chartPanel = new ChartPanel(freeChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 400));

        //创建一个主窗口来显示面板
        JFrame frame = new JFrame("帕累托散点图");
        frame.setLocation(500, 400);
        frame.setSize(600, 500);

        //将主窗口的内容面板设置为图表面板
        frame.setContentPane(chartPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}
