package com.wksc.counting.widegit;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wksc.counting.model.SaleAnaModel.PeiModel;

import java.util.ArrayList;

/**
 * Created by puhua on 2016/6/17.
 *
 * @
 */
public class PieChartTool {

    public PieChartTool(PieChart pieChart){
        this.pieChart1 = pieChart;
//        setPiechart();
    }
    private PieChart pieChart1;
    private PeiModel peiModel;

    public void setPiechart() {
        pieChart1.setUsePercentValues(true);
        pieChart1.setDescription("");
        pieChart1.setExtraOffsets(5, 10, 5, 5);
        pieChart1.setDragDecelerationFrictionCoef(0.95f);
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setHoleColor(Color.WHITE);
        pieChart1.setTransparentCircleColor(Color.WHITE);
        pieChart1.setTransparentCircleAlpha(110);
        pieChart1.setHoleRadius(58f);
        pieChart1.setTransparentCircleRadius(61f);

        pieChart1.setDrawCenterText(true);

        pieChart1.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart1.setRotationEnabled(true);
        pieChart1.setHighlightPerTapEnabled(true);
        Legend l = pieChart1.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(0f);
        l.setYOffset(5f);
        l.setXOffset(5f);
        setData();
    }

    private void setData() {

        ArrayList<Entry> yVals1 = new ArrayList<>();
        String[] values = peiModel.chartValue1.split("\\|");
        String[] des = peiModel.chartPoint1.split("\\|");
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        if (!values[0].equals("")&&!des[0].equals("")){
            for (int i = 0; i < values.length; i++) {
                yVals1.add(new Entry(Float.valueOf(values[i]),i));
            }
            ArrayList<String> xVals = new ArrayList<>();

            for (int i = 0; i < des.length; i++)
                xVals.add(des[i]);

            PieDataSet dataSet = new PieDataSet(yVals1, peiModel.chartTitle1);
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            //dataSet.setSelectionShift(0f);

            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.BLACK);
            pieChart1.setData(data);
            pieChart1.setDrawSliceText(true);
            // undo all highlights
            pieChart1.highlightValues(null);

            pieChart1.invalidate();
        }

    }

    public void setData(PeiModel chartData) {
        this.peiModel = chartData;
    }
}
