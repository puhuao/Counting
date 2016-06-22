package com.wksc.counting.adapter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.PurchaseModel;

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
        SpannableString styledText = new SpannableString(mList.get(position).reachRate);
        if (position!=0)
            styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style1), 0, styledText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.monthEarlier.setText(styledText,TextView.BufferType.SPANNABLE);
        return convertView;
    }
    class ViewHolder{
        @Bind(R.id.area)
        TextView name;
        @Bind(R.id.old_text1)
        TextView data;
        @Bind(R.id.old_text2)
        TextView monthRelative;
        @Bind(R.id.old_text3)
        TextView monthEarlier;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }


}
