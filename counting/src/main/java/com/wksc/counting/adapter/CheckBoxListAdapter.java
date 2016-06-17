package com.wksc.counting.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.AreaCheckModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CheckBoxListAdapter extends BaseListAdapter<AreaCheckModel> {
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
        for (AreaCheckModel m : mList) {
            m.isCheck = ALL;
        }
    }
    public void setAllNormal() {
        if (sb.length()>0)
            sb.delete(0,sb.length()-1);
        for (AreaCheckModel m : mList) {
            m.isCheck = NORMAL;
        }
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
//        holder.name.setChecked(mList.get(position).isCheck);
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

    public int getCheckedNumber(){
        if (sb.length()>0){
            sb.delete(0,sb.length());
        }
        int i = 0;
        for (AreaCheckModel area:
             mList) {
            if (area.isCheck==ALL){
                i++;
                sb.append(area.name).append(".");
            }
        }
        if (sb.length()>0)
        sb.deleteCharAt(sb.length()-1);
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setSelect(int status,TextView view){
        Drawable drawable = null;
        switch (status){
            case NORMAL:
                drawable = mContext.getDrawable(R.drawable.ic_check_normal);
                break;
            case ALL:
                drawable = mContext.getDrawable(R.drawable.ic_check);
                break;
            case HALF:
                drawable = mContext.getDrawable(R.drawable.icon_blue_selected);
                break;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable,null,null,null);
    }

    public void moveToNextStatus(int position){
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
    }

}
