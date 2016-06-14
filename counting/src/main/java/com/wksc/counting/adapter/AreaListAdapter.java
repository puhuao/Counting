package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.wksc.counting.R;
import com.wksc.counting.model.AreaModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class AreaListAdapter extends BaseListAdapter<AreaModel> {
    public Boolean isAll=false;
    public StringBuilder sb = new StringBuilder();

    public AreaListAdapter(Activity context) {
        super(context);
    }

    public void setAllCheck(Boolean check) {
        if (sb.length()>0)
        sb.delete(0,sb.length()-1);
        for (AreaModel m : mList) {
            m.isCheck = check;
        }
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

    public int getCheckedNumber(){
        if (sb.length()>0){
            sb.delete(0,sb.length());
        }
        int i = 0;
        for (AreaModel area:
             mList) {
            if (area.isCheck){
                i++;
                sb.append(area.name).append(".");
            }
        }
        if (sb.length()>0)
        sb.deleteCharAt(sb.length()-1);
        return i;
    }

}
