package com.wksc.counting.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wksc.counting.R;
import com.wksc.counting.model.CoreIndexListModel;
import com.wksc.counting.popwindows.TitleDescribewindow;
import com.wksc.framwork.util.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CoreIndexListAdapter extends BaseListAdapter<CoreIndexListModel>{
    public CoreIndexListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_core_index, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final  int pos = position;
        holder.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                ToastUtil.showShortMessage(mContext,mList.get(pos).titleDesc);
                TitleDescribewindow titleSelectPopupWindow = new TitleDescribewindow(mContext);
                titleSelectPopupWindow.showPopupwindow(v,mList.get(pos).titleDesc);
                return false;
            }
        });
        holder.name.setText(mList.get(position).title);
        holder.unit.setText("/"+mList.get(position).titleUnit);
        holder.name1.setText(mList.get(position).item1);
        holder.text1.setText(mList.get(position).itemValue1);
        holder.text2.setText(mList.get(position).itemValue2);
        holder.name2.setText(mList.get(position).item2);
        String[] color = mList.get(position).itemColor2.split(",");
        int red = Integer.valueOf(color[0]);
        int green = Integer.valueOf(color[1]);
        int blue = Integer.valueOf(color[2]);
        holder.text2.setTextColor(Color.rgb(red,green,blue));
//        holder.monthData.setText("月累计: "+mList.get(position).mounthData);
        holder.name3.setText(mList.get(position).item3);
        color = mList.get(position).itemColor3.split(",");
        holder.text3.setText(mList.get(position).itemValue3);
        holder.text3.setTextColor(Color.rgb(Integer.valueOf(color[0]),
                Integer.valueOf(color[1]),Integer.valueOf(color[2])));
        holder.text4.setText(mList.get(position).itemValue4);
        holder.name4.setText(mList.get(position).item4);
        if (!StringUtils.isBlank(mList.get(position).itemColor4)){

            color = mList.get(position).itemColor4.split(",");
            holder.text4.setTextColor(Color.rgb(Integer.valueOf(color[0]),
                    Integer.valueOf(color[1]),Integer.valueOf(color[2])));
        }
        holder.chart.setDescription("");
        holder.chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        holder.chart.setTouchEnabled(false);

        holder.chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        holder.chart.setDragEnabled(true);
        holder.chart.setScaleEnabled(true);
        holder.chart.setDrawGridBackground(false);
        holder.chart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        holder.chart.setPinchZoom(true);

        // set an alternative background color
        holder.chart.setBackgroundColor(Color.WHITE);

        // add data
        setData(6, mList.get(position).chartData,holder.chart,position);

//        holder.chart.animateX(2500);

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = holder.chart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tf);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setYOffset(11f);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaxValue(10f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(ColorTemplate.getHoloBlue());
        rightAxis.setAxisMaxValue(10f);
        rightAxis.setAxisMinValue(0f);
        rightAxis.setDrawGridLines(false);
        return convertView;
    }

    private void setData(int count, String range, Chart chart,int pos) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        String[] strArray = range.split(",");
        for (int i = 0; i < count; i++) {
            String s = strArray[i];
            float f = Float.valueOf(s);
            yVals1.add(new Entry(f, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, mList.get(pos).title);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        set1.setDrawFilled(true);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        chart.setData(data);
    }

    class ViewHolder{
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.unit)
        TextView unit;
        @Bind(R.id.item1)
        TextView text1;
        @Bind(R.id.item3)
        TextView text3;
        @Bind(R.id.item2)
        TextView text2;
        @Bind(R.id.item4)
        TextView text4;
        @Bind(R.id.name1)
        TextView name1;
        @Bind(R.id.name2)
        TextView name2;
        @Bind(R.id.name3)
        TextView name3;
        @Bind(R.id.name4)
        TextView name4;
        @Bind(R.id.chart)
        LineChart chart;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }


}
