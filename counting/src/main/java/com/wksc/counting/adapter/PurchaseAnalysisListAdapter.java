package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.PurchaseModel;
import com.wksc.counting.model.SalesFinishModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class PurchaseAnalysisListAdapter extends BaseListAdapter<PurchaseModel>{
    public PurchaseAnalysisListAdapter(Activity context) {
        super(context);
        setList(PurchaseModel.getData());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_sales_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.name.setText(mList.get(position).name);
        holder.data.setText(mList.get(position).goal);
        holder.monthRelative.setText(mList.get(position).acture);
        holder.monthEarlier.setText(mList.get(position).reachRate);
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
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }


}
