package com.wksc.counting.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.wksc.counting.R;

import java.util.HashMap;

/**
 * Created by puhua on 2016/7/20.
 *
 * @
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private String[] beans;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<>();

    class ViewHolder {

        RadioButton rb_state;
    }

    public ListViewAdapter(Context context, String[] beans) {
        // TODO Auto-generated constructor stub
        this.beans = beans;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return beans.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return beans[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 页面
        ViewHolder holder;
        String bean = beans[position];
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.item_compare_pop, null);
            holder = new ViewHolder();
//			holder.rb_state = (RadioButton) convertView
//					.findViewById(R.id.rb_light);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RadioButton radio = (RadioButton) convertView.findViewById(R.id.rb_light);
        holder.rb_state = radio;
        holder.rb_state.setText(bean);
        holder.rb_state.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);

                }
                states.put(String.valueOf(position), radio.isChecked());
                ListViewAdapter.this.notifyDataSetChanged();
            }
        });

        boolean res = false;
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;

        holder.rb_state.setChecked(res);
        return convertView;
    }
}