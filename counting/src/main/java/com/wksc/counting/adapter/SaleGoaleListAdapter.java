package com.wksc.counting.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.wksc.counting.R;
import com.wksc.counting.event.ClickTojumpEvent;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.SaleAnaModel.TableModel;
import com.wksc.counting.widegit.MarqueeText;
import com.wksc.counting.widegit.TableTitleLayout;
import com.wksc.counting.widegit.VirtualBarTool;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/6/29.
 *
 * @
 */
public class SaleGoaleListAdapter extends BaseListAdapter<TableModel> {
    public SaleGoaleListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_salegoale, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        TableModel model = mList.get(position);
        holder.name.setText(model.title);
        PeiModel peiModel = new PeiModel();
        peiModel.chartPoint1 = model.chartDesc;
        peiModel.chartValue1 = model.chartData;
        peiModel.chartTitle1 = model.chartTitle;
        VirtualBarTool chartTool = new VirtualBarTool(holder.pieChart,mContext);
        chartTool.setData(peiModel);
        String[] titles = model.tableTitle.split("\\|");
        String[] desc = model.tableTitleDesc.split("\\|");
        holder.tableTitle.initView(titles, desc);
        SaleItemAdapter itemAdapter = new SaleItemAdapter(mContext);
//        List<TableModel> list = new ArrayList<>();
//        list.add(model);
//        itemAdapter.setList(list);
//        itemAdapter.setItemCloums(titles.length);
//        holder.lv.setAdapter(itemAdapter);
        String[] array = model.tableData.split("\\|");
        String[] colors = model.tableColor.split("\\|");
        for (int i=0;i<titles.length;i ++){
            MarqueeText textView = new MarqueeText(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(5,5,5,5);
            textView.setTextSize(12f);
//            textView.setSingleLine();
//            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            textView.setMarqueeRepeatLimit(-1);
//            if (i==0){
//                textView.setText(mList.get(position).title);
//            }else{
            textView.setText(array[i]);
            String[] color = colors[i].split(",");
            textView.setTextColor(Color.rgb(Integer.parseInt(color[0]),
                    Integer.parseInt(color[1]),
                    Integer.parseInt(color[2])));
//            }
            holder.lv.addView(textView);
        }
        holder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickTojumpEvent event = new ClickTojumpEvent();
                event.pos = position;
                EventBus.getDefault().post(event);
            }
        });

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.title)
        TextView name;
        @Bind(R.id.pie)
        BarChart pieChart;
        @Bind(R.id.table_titles)
        TableTitleLayout tableTitle;
        @Bind(R.id.item_layout)
        LinearLayout lv;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}