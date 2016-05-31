package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.SaleSupplyModel;
import com.wksc.counting.model.SalesFinishModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesSupplyListAdapter extends BaseListAdapter<SaleSupplyModel>{
    public SalesSupplyListAdapter(Activity context) {
        super(context);
        setList(SaleSupplyModel.getData());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_sales_supply_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.name.setText(mList.get(position).name);
        holder.data.setText(mList.get(position).saleData);
        holder.monthRelative.setText(mList.get(position).saleRalive);
        holder.monthEarlier.setText(mList.get(position).saleProportion);
        holder.profitData.setText(mList.get(position).profitData);
        holder.profitRate.setText(mList.get(position).profitRate);
        return convertView;
    }
    class ViewHolder{
        @Bind(R.id.area)
        TextView name;
        @Bind(R.id.moth_data)
        TextView data;
        @Bind(R.id.month_comparison_relative)
        TextView monthRelative;
        @Bind(R.id.month_comparison_eala)
        TextView monthEarlier;
        @Bind(R.id.profit_data)
        TextView profitData;
        @Bind(R.id.profit_rate)
        TextView profitRate;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }


}