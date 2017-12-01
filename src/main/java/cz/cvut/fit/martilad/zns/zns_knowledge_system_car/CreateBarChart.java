/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.martilad.zns.zns_knowledge_system_car;

import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice.ConclusionFuzzy;
import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import at.downdrown.vaadinaddons.highchartsapi.HighChartFactory;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.data.HighChartsData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.DoubleData;
import at.downdrown.vaadinaddons.highchartsapi.model.plotoptions.ColumnChartPlotOptions;
import at.downdrown.vaadinaddons.highchartsapi.model.series.ColumnChartSeries;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lamxi
 */
public class CreateBarChart {
    public static HighChart put_chart_to_layout(VerticalLayout layout, ConclusionFuzzy to_chart){
        ChartConfiguration columnConfiguration = new ChartConfiguration();
        columnConfiguration.setTitle("Chart of possibly conclusions and jurisdiction.");
        columnConfiguration.setChartType(ChartType.COLUMN);

        ColumnChartPlotOptions columnPlotOptions = new ColumnChartPlotOptions();
        columnConfiguration.setPlotOptions(columnPlotOptions);

        for (Map.Entry<String, Double> entry : to_chart.getConlusion_and_meter_of_is().entrySet()) {
            List<HighChartsData> bananaColumnValues = new ArrayList<>();
            
            bananaColumnValues.add(new DoubleData(entry.getValue()));
            ColumnChartSeries bananaColumn = new ColumnChartSeries(entry.getKey(), bananaColumnValues);
            columnConfiguration.getSeriesList().add(bananaColumn);
           
        }
        
        HighChart columnChart = null;
        try {
            columnChart = HighChartFactory.renderChart(columnConfiguration);
            columnChart.setHeight(60, Sizeable.Unit.PERCENTAGE);
            columnChart.setWidth(90, Sizeable.Unit.PERCENTAGE);
            layout.addComponent(columnChart);
            layout.setComponentAlignment(columnChart, Alignment.MIDDLE_CENTER);
            layout.setExpandRatio(columnChart, 1.0f);

        } catch (HighChartsException e) {
            e.printStackTrace();
        }
        
        
        return columnChart;
    }
}
