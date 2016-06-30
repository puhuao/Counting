package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.ComparisonModel;
import com.wksc.counting.model.coreDetail.TabelValueModel;
import com.wksc.counting.popwindows.StorePopupwindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesCompareListAdapter extends BaseListAdapter<ComparisonModel> {
    public SalesCompareListAdapter(Activity context) {
        super(context);
    }

    public void TransData(List<TabelValueModel> tableData){
        if (mList == null){
            mList = new ArrayList<>();
        }
        for (int i =0 ;i <tableData.size();i++){
            ComparisonModel model = new ComparisonModel();
            String[] oldValues = tableData.get(i).oldValue.split("\\|");
            String[] newValues = tableData.get(i).newValue.split("\\|");
            model.oldDatas = oldValues;
            model.newDatas = newValues;
            model.code = tableData.get(i).code;
            model.area = tableData.get(i).title;
            mList.add(model);
        }
        this.notifyDataSetChanged();

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
        holder.oldText3.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        holder.newText3.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        holder.oldText4.setTextColor(mContext.getResources().getColor(R.color.green));
        holder.newText4.setTextColor(mContext.getResources().getColor(R.color.green));
        holder.name.setText(mList.get(position).area);
        holder.oldText1.setText(mList.get(position).oldDatas[0]);
        holder.newText1.setText(mList.get(position).newDatas[0]);
        holder.oldText3.setText(mList.get(position).oldDatas[2]);
        holder.newText3.setText(mList.get(position).newDatas[2]);
        holder.oldText2.setText(mList.get(position).oldDatas[1]);
        holder.newText2.setText(mList.get(position).newDatas[1]);
        holder.oldText4.setText(mList.get(position).oldDatas[3]);
        holder.newText4.setText(mList.get(position).newDatas[3]);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorePopupwindow popupWindow = new StorePopupwindow(mContext);
                popupWindow.showPopupwindow(v);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.area)
        TextView name;
        @Bind(R.id.old_text1)
        TextView oldText1;
        @Bind(R.id.old_text3)
        TextView oldText3;
        @Bind(R.id.old_text4)
        TextView oldText4;
        @Bind(R.id.new_text1)
        TextView newText1;
        @Bind(R.id.new_text3)
        TextView newText3;
        @Bind(R.id.new_text4)
        TextView newText4;
        @Bind(R.id.old_text2)
        TextView oldText2;
        @Bind(R.id.new_text2)
        TextView newText2;
        @Bind(R.id.store)
        LinearLayout store;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }

    public void setDownToUp() {
        List<ComparisonModel> list = new ArrayList<>();
        for (int i = mList.size() - 1; i >= 0; i--) {
            list.add(mList.get(i));
        }
        this.setList(list);
        this.notifyDataSetChanged();

    }

}
