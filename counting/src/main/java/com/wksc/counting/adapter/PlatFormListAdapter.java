package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.wksc.counting.R;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.platFormModel.TableModel;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.PieChartTool;
import com.wksc.counting.widegit.TableTitleLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/6/29.
 *
 * @
 */
public class PlatFormListAdapter extends BaseListAdapter<TableModel> {
    public PlatFormListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_platform, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        TableModel model = mList.get(position);
        holder.name.setText(model.title);
        PeiModel peiModel = new PeiModel();
        peiModel.chartPoint1 = model.chartDesc;
        peiModel.chartValue1 = model.chartData;
        peiModel.chartTitle1 = model.chartTitle;
        PieChartTool chartTool = new PieChartTool(holder.pieChart);
        chartTool.setData(peiModel);
        chartTool.setPiechart();
        String[] titles = model.tableTitle.split("\\|");
        String[] desc = model.tableTitleDesc.split("\\|");
        holder.tableTitle.initView(titles, desc);
        PlatFormItemAdapter itemAdapter = new PlatFormItemAdapter(mContext);
        List<TableModel> list = new ArrayList<>();
        list.add(model);
        itemAdapter.setList(list);
        itemAdapter.setItemCloums(titles.length);
        holder.lv.setAdapter(itemAdapter);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.title)
        TextView name;
        @Bind(R.id.pie)
        PieChart pieChart;
        @Bind(R.id.table_titles)
        TableTitleLayout tableTitle;
        @Bind(R.id.lv)
        NestedListView lv;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}