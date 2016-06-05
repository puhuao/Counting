package com.wksc.counting.adapter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.ComparisonModel;
import com.wksc.counting.model.SalesFinishModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesFinishListAdapter extends BaseListAdapter<SalesFinishModel>{
    public SalesFinishListAdapter(Activity context) {
        super(context);
        setList(SalesFinishModel.getData());
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
        SpannableString styledText = new SpannableString(mList.get(position).acture);
        if (position!=0)
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style1), 0, styledText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.monthRelative.setText(styledText,TextView.BufferType.SPANNABLE);
        styledText = new SpannableString(mList.get(position).reachRate);
        if (position!=0)
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style3), 0, styledText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.monthEarlier.setText(styledText,TextView.BufferType.SPANNABLE);
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
