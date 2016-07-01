package com.wksc.counting.widegit;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wksc.counting.R;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.DateSelectPopupWindow;
import com.wksc.counting.popwindows.GoodsPopupWindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.counting.tools.DateTool;

/**
 * Created by puhua on 2016/6/28.
 *
 * @
 */
public class ConditionLayout extends LinearLayout implements View.OnClickListener {
    MarqueeText area;
    MarqueeText goods;
    MarqueeText time;
    MarqueeText channel;
    LinearLayout layoutGoods;
    LinearLayout layoutChannel;

    private String data = "";
    public ConditionLayout(Context context) {
        super(context);
    }

    public ConditionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConditionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_condition, this);
//        ButterKnife.bind(this);
        area = (MarqueeText) findViewById(R.id.area);
        goods = (MarqueeText) findViewById(R.id.goods);
        time = (MarqueeText) findViewById(R.id.time);
        channel = (MarqueeText) findViewById(R.id.channel);
        layoutGoods = (LinearLayout) findViewById(R.id.layout_goods);
        layoutChannel = (LinearLayout) findViewById(R.id.layout_channel);
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        channel.setOnClickListener(this);
//        index.setOnClickListener(this);
    }

    public void hideGoods(Boolean hide){
        if (hide){
            layoutGoods.setVisibility(GONE);
        }else{
            layoutChannel.setVisibility(GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area:
                AreaPopupwindow areaPopupwindow = new AreaPopupwindow((Activity) getContext());
                areaPopupwindow.bindTextView(area);
                areaPopupwindow.showPopupwindow(v);
                break;
            case R.id.goods:
                GoodsPopupWindow goodsPopupWindow = new GoodsPopupWindow((Activity) getContext());
                goodsPopupWindow.bindTextView(goods);
                goodsPopupWindow.showPopupwindow(v);
                break;
            case R.id.time:
//                TimePopupwindow timePopupwindow = new TimePopupwindow((Activity) getContext());
//                timePopupwindow.bindTextView(time);
//                timePopupwindow.showPopupwindow(v);
                DateSelectPopupWindow myPopupwindow=new DateSelectPopupWindow(getContext(),data);
                myPopupwindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
                myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow.OnDateSelectListener() {
                    @Override
                    public void onDateSelect(int year, int monthOfYear, int dayOfMonth) {
                        // SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                        if (year==0&&monthOfYear==0&&dayOfMonth==0){
                            if (data.equals("")) {
                                data = DateTool.getChinaDate();
                            }
                        }else{
                            data=DateTool.getChinaDateFromCalendar(year,monthOfYear,dayOfMonth);
                        }
                        (time).setText(data);
                    }
                });
                break;
            case R.id.channel:
                SupplyChianPopupwindow supplyChianPopupwindow = new SupplyChianPopupwindow((Activity) getContext());
                supplyChianPopupwindow.bindTextView(channel);
                supplyChianPopupwindow.showPopupwindow(v);
                break;
//            case R.id.index:
//                IndexPopupwindow indexPopupwindow = new IndexPopupwindow((Activity) getContext());
//                indexPopupwindow.showPopupwindow(v);
//                break;

        }
    }
}
