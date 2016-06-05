package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.wksc.counting.R;
import com.wksc.counting.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class IndexListAdapter extends BaseListAdapter<AreaModel> {
    public IndexListAdapter(Activity context) {
        super(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_area_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.name.setText(mList.get(position).name);
        holder.name.setChecked(mList.get(position).isCheck);
        return convertView;
    }

    class ViewHolder{
        @Bind(R.id.item)
        CheckBox name;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }
}
