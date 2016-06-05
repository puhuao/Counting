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
import com.wksc.counting.model.CoreIndexModel;
import com.wksc.counting.popwindows.StorePopupwindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesCompareListAdapter extends BaseListAdapter<ComparisonModel>{
    public SalesCompareListAdapter(Activity context) {
        super(context);
        setList(ComparisonModel.getData());
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

        holder.name.setText(mList.get(position).area);
        holder.data.setText(mList.get(position).monthData);
        SpannableString styledText = new SpannableString(mList.get(position).monthCompareRelative);
        if (position!=0)
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style1), 0, styledText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.monthRelative.setText(styledText,TextView.BufferType.SPANNABLE);
        styledText = new SpannableString(mList.get(position).monthCompareEalair);
        if (position!=0)
            styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style3), 0, styledText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.monthEarlier.setText(styledText,TextView.BufferType.SPANNABLE);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorePopupwindow popupWindow = new StorePopupwindow(mContext);
                popupWindow.showPopupwindow(v);
            }
        });
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

    public void setDownToUp(){
        List<ComparisonModel> list = new ArrayList<>();
        for (int i  =mList.size()-1;i>=0;i--) {
            list.add(mList.get(i));
        }
        this.setList(list);
        this.notifyDataSetChanged();

    }

}
