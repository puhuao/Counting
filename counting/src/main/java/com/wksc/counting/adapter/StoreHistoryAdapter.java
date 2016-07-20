package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.AreaModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class StoreHistoryAdapter extends BaseListAdapter<String> {

    public StoreHistoryAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_history_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.name.setText(mList.get(position));
        return convertView;
    }

    class ViewHolder{
        @Bind(R.id.item)
        TextView name;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }
}
