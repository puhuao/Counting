package com.wksc.counting.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wksc.counting.R;
import com.wksc.counting.model.saleChannelModel.TableModel;
import com.wksc.counting.widegit.MarqueeText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesSupplyListAdapter extends BaseListAdapter<TableModel> {
    public SalesSupplyListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        String[] array = mList.get(position).newValue.split("\\|");
        String[] colors = mList.get(position).newColor.split("\\|");
        for (int i = 0; i < itemCloums; i++) {
            MarqueeText textView = new MarqueeText(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(5,5,5,5);
            textView.setTextSize(12f);
            textView.setText(array[i]);
//            textView.setSingleLine();
//            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            textView.setMarqueeRepeatLimit(-1);
            String[] color = colors[i].split(",");
            textView.setTextColor(Color.rgb(Integer.parseInt(color[0]),
                    Integer.parseInt(color[1]),
                    Integer.parseInt(color[2])));
            holder.layoutContainer.addView(textView);
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.item_layout)
        LinearLayout layoutContainer;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }


}
