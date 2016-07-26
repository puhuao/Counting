package com.wksc.counting.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wksc.counting.R;
import com.wksc.counting.model.SaleAnaModel.TableModel;
import com.wksc.counting.model.SaleAnaModel.TableModel1;
import com.wksc.counting.widegit.MarqueeText;
import com.wksc.counting.widegit.TableTitleLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesFinishListAdapter extends BaseListAdapter<TableModel1>{

    public SalesFinishListAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_salegoale, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        String[] array = mList.get(position).newValue.split("\\|");
        String[] colors = mList.get(position).newColor.split("\\|");
        for (int i=0;i<itemCloums;i ++){
            MarqueeText textView = new MarqueeText(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(5,5,5,5);
            textView.setTextSize(12f);
//            textView.setSingleLine();
//            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            textView.setMarqueeRepeatLimit(-1);
//            if (i==0){
//                textView.setText(mList.get(position).title);
//            }else{
                textView.setText(array[i]);
                String[] color = colors[i].split(",");
                textView.setTextColor(Color.rgb(Integer.parseInt(color[0]),
                        Integer.parseInt(color[1]),
                        Integer.parseInt(color[2])));
//            }
            holder.layoutContainer.addView(textView);
        }

        return convertView;
    }
    class ViewHolder{
       @Bind(R.id.item_layout)
        LinearLayout layoutContainer;
        @Bind(R.id.table_titles)
        TableTitleLayout titleLayout;
        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }
    }


}
