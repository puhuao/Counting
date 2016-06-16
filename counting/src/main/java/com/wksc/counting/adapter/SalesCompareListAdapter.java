package com.wksc.counting.adapter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
if (position == 0){
    holder.store.setVisibility(View.INVISIBLE);
    holder.storeDataNew.setVisibility(View.GONE);
    holder.monthEarlierNew.setVisibility(View.GONE);
    holder.monthRelativeNew.setVisibility(View.GONE);
    holder.dataNew.setVisibility(View.GONE);
    holder.monthRelative.setTextColor(mContext.getResources().getColor(R.color.text_bold));
    holder.monthRelativeNew.setTextColor(mContext.getResources().getColor(R.color.text_bold));
    holder.monthEarlier.setTextColor(mContext.getResources().getColor(R.color.text_bold));
    holder.monthEarlierNew.setTextColor(mContext.getResources().getColor(R.color.text_bold));
}else{
    holder.store.setVisibility(View.VISIBLE);
    holder.storeDataNew.setVisibility(View.VISIBLE);
    holder.monthEarlierNew.setVisibility(View.VISIBLE);
    holder.monthRelativeNew.setVisibility(View.VISIBLE);
    holder.dataNew.setVisibility(View.VISIBLE);
    holder.monthRelative.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
    holder.monthRelativeNew.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
    holder.monthEarlier.setTextColor(mContext.getResources().getColor(R.color.green));
    holder.monthEarlierNew.setTextColor(mContext.getResources().getColor(R.color.green));
}
        holder.name.setText(mList.get(position).area);
        holder.data.setText(mList.get(position).oldStoreMonthData);
        holder.dataNew.setText(mList.get(position).newStoreData);
        holder.monthRelative.setText(mList.get(position).oldStoreMonthCompareRelative);
        holder.monthRelativeNew.setText(mList.get(position).newStoreMonthCompareRelative);
        holder.storeData.setText(mList.get(position).oldStoreData);
        holder.storeDataNew.setText(mList.get(position).newStoreData);
        holder.monthEarlier.setText(mList.get(position).oldStoreMonthCompareEalair);
        holder.monthEarlierNew.setText(mList.get(position).newStoreMonthCompareEalair);
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
        @Bind(R.id.moth_data_new)
        TextView dataNew;
        @Bind(R.id.month_comparison_relative_new)
        TextView monthRelativeNew;
        @Bind(R.id.month_comparison_eala_new)
        TextView monthEarlierNew;
        @Bind(R.id.store_month_data)
        TextView storeData;
        @Bind(R.id.store_month_data_new)
        TextView storeDataNew;
        @Bind(R.id.store)
        LinearLayout store;

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
