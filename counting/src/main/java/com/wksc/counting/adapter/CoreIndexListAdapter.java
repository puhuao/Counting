package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.CoreIndexModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CoreIndexListAdapter extends BaseListAdapter<CoreIndexModel>{
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

        holder.name.setText(mList.get(position).name);
        holder.data.setText(mList.get(position).data);
        holder.current.setText("当月值:"+mList.get(position).currentDada);
        holder.monthRelative.setText("月同比:"+mList.get(position).mounthRealativ);
        holder.monthData.setText("当累计:"+mList.get(position).mounthData);
        holder.monthEarlier.setText("当环比:"+mList.get(position).mounthEaliear);
        return convertView;
    }
    class ViewHolder{
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.data)
        TextView data;
        @Bind(R.id.current)
        TextView current;
        @Bind(R.id.month_data)
        TextView monthData;
        @Bind(R.id.month_relative)
        TextView monthRelative;
        @Bind(R.id.month_earlier)
        TextView monthEarlier;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }


}
