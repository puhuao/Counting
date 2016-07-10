package com.wksc.counting.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CheckBoxListAdapter extends BaseListAdapter<BaseWithCheckBean> {
    public static final int NORMAL = 0;
    public static final int HALF = 1;
    public static final int ALL = 2;
    public Boolean isAll=false;
    public StringBuilder sb = new StringBuilder();

    public CheckBoxListAdapter(Activity context) {
        super(context);
    }

    public void setAllCheck() {
        if (sb.length()>0)
            sb.delete(0,sb.length()-1);
        for (BaseWithCheckBean m : mList) {
            m.isCheck = ALL;
        }
        this.notifyDataSetChanged();
    }
    public void setAllNormal() {
        if (sb.length()>0)
            sb.delete(0,sb.length()-1);
        for (BaseWithCheckBean m : mList) {
            m.isCheck = NORMAL;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_check, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.name.setText(mList.get(position).name);
        setSelect(mList.get(position).isCheck,holder.name);
        return convertView;
    }

    class ViewHolder{
        @Bind(R.id.item)
        TextView name;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }

    public int oneCheckPosition = 0;

    public int getCheckedNumber(){
        if (sb.length()>0){
            sb.delete(0,sb.length());
        }
        int i = 0;
        for (BaseWithCheckBean area:
             mList) {
            if (area.isCheck==ALL){
                i++;
                oneCheckPosition = mList.indexOf(area);
                sb.append(area.name).append(".");
            }
        }
        if (sb.length()>0)
        sb.deleteCharAt(sb.length()-1);
        return i;
    }

    private void setSelect(int status,TextView view){
        Drawable drawable = null;
        switch (status){
            case NORMAL:
                    drawable = mContext.getResources().getDrawable(R.drawable.ic_check_normal);
                break;
            case ALL:
                drawable = mContext.getResources().getDrawable(R.drawable.ic_check);
                break;
            case HALF:
                drawable = mContext.getResources().getDrawable(R.drawable.icon_blue_selected);
                break;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable,null,null,null);
    }

    public int moveToNextStatus(int position){
        switch (mList.get(position).isCheck){
            case ALL:
                mList.get(position).isCheck = NORMAL;
                break;
            case NORMAL:
                mList.get(position).isCheck = ALL;
                break;
            case HALF:
                mList.get(position).isCheck = ALL;
                break;
        }
        return  mList.get(position).isCheck;
    }

}
