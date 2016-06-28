package com.wksc.counting.widegit;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.GoodsPopupwindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.counting.popwindows.TimePopupwindow;

import butterknife.Bind;

/**
 * Created by puhua on 2016/6/28.
 *
 * @
 */
public class ConditionLayout extends LinearLayout implements View.OnClickListener {
    MarqueeText area;
    TextView goods;
    @Bind(R.id.time)
    MarqueeText time;
    @Bind(R.id.channel)
    MarqueeText channel;
    @Bind(R.id.layout_goods)
    LinearLayout layoutGoods;
    @Bind(R.id.layout_channel)
    LinearLayout layoutChannel;
//    @Bind(R.id.index)
//    TextView index;
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
                GoodsPopupwindow goodsPopupwindow = new GoodsPopupwindow((Activity) getContext());
                goodsPopupwindow.showPopupwindow(v);
                break;
            case R.id.time:
                TimePopupwindow timePopupwindow = new TimePopupwindow((Activity) getContext());
                timePopupwindow.bindTextView(time);
                timePopupwindow.showPopupwindow(v);
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
