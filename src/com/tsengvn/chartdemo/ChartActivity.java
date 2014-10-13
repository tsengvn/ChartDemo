package com.tsengvn.chartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import com.shinobicontrols.charts.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Copyright (c) 2014, Saritasa LLC (www.saritasa.com). All rights reserved.
 *
 * @author Hien Ngo
 */
public class ChartActivity extends Activity{
    private ShinobiChart mChart;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shinobi);

        if (savedInstanceState == null) {
            bindViews();
            bindData();
        }
    }

    private void bindViews(){
        ChartFragment chartFragment =
                (ChartFragment) getFragmentManager().findFragmentById(R.id.chart);
        mChart = chartFragment.getShinobiChart();
        mChart.setTitle("Donated Money");
        mChart.setLicenseKey(getString(R.string.shinobi_license));

    }

    private void bindData(){
        //setup X-axis
        DateTimeAxis xAxis = new DateTimeAxis();
        xAxis.enableGesturePanning(true);
        xAxis.enableGestureZooming(true);
        xAxis.enableMomentumPanning(true);
        xAxis.enableMomentumZooming(true);
        mChart.addXAxis(xAxis);

        //setup Y-axis
        DateRange xDefaultRange = new DateRange(
                new GregorianCalendar(2010, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2010, Calendar.DECEMBER, 31).getTime());
        xAxis.setDefaultRange(xDefaultRange);
        NumberAxis yAxis = new NumberAxis();
        mChart.addYAxis(yAxis);

        //create sample data
        SimpleDataAdapter<Date, Integer> dataAdapter = new SimpleDataAdapter<Date, Integer>();

        GregorianCalendar date = new GregorianCalendar(2010, Calendar.JANUARY, 1);
        Random random = new Random();
        int start = 100;
        for (int i=0 ; i<24 ; i++){
            int jump = random.nextInt(50) + start;
            dataAdapter.add(new DataPoint<Date, Integer>(date.getTime(), jump));
            date.add(GregorianCalendar.MONTH, 1);
            start = jump;
        }

        LineSeries lineSeries = new LineSeries();
        lineSeries.setDataAdapter(dataAdapter);
        lineSeries.setEntryAnimation(SeriesAnimation.createGrowVerticalAnimation());
        lineSeries.enableAnimation(true);
        lineSeries.setSelectionMode(Series.SelectionMode.POINT_SINGLE);
        lineSeries.setCrosshairEnabled(true);
        styleLineChart(lineSeries);
        mChart.addSeries(lineSeries);

    }


    private void styleLineChart(LineSeries aLineSeries){
        int mainColor = getResources().getColor(R.color.line_color);
        aLineSeries.getStyle().setLineColor(mainColor);
        aLineSeries.getStyle().setLineWidth(1.5f);

        PointStyle pointStyle = new PointStyle();
        pointStyle.setPointsShown(true);
        pointStyle.setColor(Color.GRAY);
        pointStyle.setInnerColor(mainColor);
        pointStyle.setRadius(4);
        aLineSeries.getStyle().setPointStyle(pointStyle);
        aLineSeries.getStyle().setSelectedPointStyle(pointStyle);
    }
}
