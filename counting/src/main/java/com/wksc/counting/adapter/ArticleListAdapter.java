package com.wksc.counting.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.model.AreaModel;
import com.wksc.counting.model.Notice;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class ArticleListAdapter extends BaseListAdapter<Notice> {

    public ArticleListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_article, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.name.setText(mList.get(position).title);
//        holder.content.setText(mList.get(position).content);
//        holder.time.setText(mList.get(position).title);
        return convertView;
    }

    class ViewHolder{
        @Bind(R.id.title)
        TextView name;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.time)
        TextView time;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }

}
