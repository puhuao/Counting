package com.wksc.counting.widegit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wksc.counting.R;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.BasePopupWindow;
import com.wksc.counting.popwindows.ChannelPopupWindow;
import com.wksc.counting.popwindows.DateSelectPopupWindow;
import com.wksc.counting.popwindows.GoodsPopupWindow;
import com.wksc.counting.tools.Params;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import java.util.Calendar;

/**
 * Created by puhua on 2016/6/28.
 *
 * @
 */
public class ConditionLayout extends LinearLayout implements View.OnClickListener {
    LinearLayout layout_channel1;
    LinearLayout layout_goods1;
    Calendar calendar;
    //    MarqueeText channel1;
//    MarqueeText time1;
//    MarqueeText area1;
//    MarqueeText goods1;
    MarqueeText area;
    MarqueeText goods;
    MarqueeText time;
    MarqueeText channel;
    RelativeLayout layoutGoods;
    RelativeLayout layoutChannel;
    LinearLayout layout;
    AreaPopupwindow areaPopupWindow;
    GoodsPopupWindow goodsPopupWindow;
    DateSelectPopupWindow myPopupwindow;
    ChannelPopupWindow channelPopupWindow;
    LinearLayout dark_below;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6;

    public String getAllConditions() {
        return Params.getParam();
    }

    public String getAllAreas() {
        return Params.getParam();
    }

    public ConditionLayout(Context context) {
        super(context);
    }

    public ConditionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConditionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_condition, this);
        calendar = Calendar.getInstance();//初始化时间
//        area1 = (MarqueeText) findViewById(R.id.area1);
//        goods1 = (MarqueeText) findViewById(R.id.goods1);
//        time1 = (MarqueeText) findViewById(R.id.time1);
//        channel1 = (MarqueeText) findViewById(R.id.channel1);
        area = (MarqueeText) findViewById(R.id.area);
        goods = (MarqueeText) findViewById(R.id.goods);
        time = (MarqueeText) findViewById(R.id.time);
        channel = (MarqueeText) findViewById(R.id.channel);
        layoutGoods = (RelativeLayout) findViewById(R.id.layout_goods);
        layoutChannel = (RelativeLayout) findViewById(R.id.layout_channel);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout_goods1 = (LinearLayout) findViewById(R.id.layout_goods1);
        layout_channel1 = (LinearLayout) findViewById(R.id.layout_channel1);
        dark_below = (LinearLayout) findViewById(R.id.dark_below);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        channel.setOnClickListener(this);
//        index.setOnClickListener(this);
        goodsPopupWindow = new GoodsPopupWindow((Activity) getContext());
        channelPopupWindow = new ChannelPopupWindow((Activity) getContext());
        init();
    }


    public void init() {
        if (Params.y == 0)
            Params.y = calendar.get(Calendar.YEAR);
        if (Params.m == 0)
            Params.m = calendar.get(Calendar.MONTH);
        if (Params.d == 0)
            Params.d = calendar.get(Calendar.DAY_OF_MONTH);

        if (Params.years.length() == 0) {
            Params.years.append("&year=").append(Params.y);
        }
        if (Params.month.length() == 0) {
            if (Params.m < 10) {
                if (Params.month.length() == 0)
                    Params.month.append("&month=").append("0" + (Params.m + 1));
            } else if (Params.m == 12) {
                if (Params.month.length() == 0)
                    Params.month.append("&month=").append("01");
            } else {
                if (Params.month.length() == 0)
                    Params.month.append("&month=").append((Params.m + 1));
            }
        }
        if (Params.day.length() == 0) {
            if (Params.d < 10) {
                if (Params.day.length() == 0)
                    Params.day.append("&day=").append("0" + (Params.d - 1));
            } else if (Params.d == 1) {

            } else {
                if (Params.day.length() == 0)
                    Params.day.append("&day=").append(Params.d - 1);
            }
        }

        initViewBySelf();

    }


    /**
     * @param
     */
    public void initViewBySelf() {
        time.setText(Params.y + "-" + (Params.m + 1) + "-" + (Params.d - 1));
        area.setText("全部");
        channel.setText("全部");
        goods.setText("全部");
    }

    public void initViewByParam() {
        if (StringUtils.isBlank(Params.time.toString())) {
            time.setText(Params.y + "-" + (Params.m + 1) + "-" + (Params.d - 1));
        } else {
            time.setText(Params.time.toString());
        }
        if (StringUtils.isBlank(Params.areal.toString())) {
            area.setText("全部");
        } else
            area.setText(Params.areal.toString());
        if (StringUtils.isBlank(Params.channel.toString())) {
            channel.setText("全部");
        } else
            channel.setText(Params.channel.toString());
        if (StringUtils.isBlank(Params.goods.toString())) {
            goods.setText("全部");
        } else
            goods.setText(Params.goods.toString());
    }

    public void hideGoods(Boolean hide) {
        if (hide) {
            layoutGoods.setVisibility(GONE);
            layout_goods1.setVisibility(GONE);
        } else {
            layoutChannel.setVisibility(GONE);
            layout_channel1.setVisibility(GONE);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area:
                if (firbidSelectArea) {
                    ToastUtil.showShortMessage(getContext(), "禁止选择区域");
                    break;
                }
                if (areaPopupWindow == null) {
                    areaPopupWindow = new AreaPopupwindow((Activity) getContext());
                }

                areaPopupWindow.bindTextView(area);
                areaPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String checkBeenRagion, String name, int tag) {
                        if (Params.province.length() > 0) {
                            Params.province.delete(0, Params.province.length());
                        }
                        if (Params.city.length() > 0) {
                            Params.city.delete(0, Params.city.length());
                        }
                        if (Params.county.length() > 0) {
                            Params.county.delete(0, Params.county.length());
                        }
                        if (Params.mcu.length() > 0) {
                            Params.mcu.delete(0, Params.mcu.length());
                        }

                        if (tag == 0) {
                            Params.province.append("&province=").append(checkBeenRagion);
                            area.setText(name);
                        } else if (tag == 1) {
                            Params.city.append("&city=").append(checkBeenRagion);
                            area.setText(name);
                        } else if (tag == 2) {
                            Params.county.append("&county=").append(checkBeenRagion);
                            area.setText(name);
                        } else if (tag == 4) {
                            Params.mcu.append("&mcu=").append(checkBeenRagion);
                            area.setText(name);
                        } else {
                            area.setText("全国");
                        }
                        Params.changeAreal(name);
                        conditionSelect.postParams();
                    }
                });
//                areaPopupWindow.showPopupwindow(v);
                areaPopupWindow.setDarkStyle(-1);
                areaPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
                areaPopupWindow.resetDarkPosition();
                areaPopupWindow.darkBelow(dark_below);
                areaPopupWindow.showAsDropDown(v, v.getRight() / 2, 0);
                showShadow(0);
                break;
            case R.id.goods:

                goodsPopupWindow.bindTextView(goods);
                goodsPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String ragions, String name, int tag) {
                        if (Params.goodsclass.length() > 0) {
                            Params.goodsclass.delete(0, Params.goodsclass.length());
                        }
                        if (Params.goodssubclass.length() > 0) {
                            Params.goodssubclass.delete(0, Params.goodssubclass.length());
                        }
                        if (tag == 0) {
                            Params.goodsclass.append("&goodsclass=").append(ragions);
                        } else if (tag == 1) {
                            Params.goodssubclass.append("&goodssubclass=").append(ragions);
                        } else if (tag == -1) {
                            Params.goodsclass.delete(0, Params.goodsclass.length());
                            Params.goodssubclass.delete(0, Params.goodssubclass.length());
                        }
                        layout.setVisibility(VISIBLE);
                        goods.setText(name);
                        Params.changeGoods(name);

                        conditionSelect.postParams();
                    }
                });
//                goodsPopupWindow.showPopupwindow(v);
                goodsPopupWindow.setDarkStyle(-1);
                goodsPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
                goodsPopupWindow.resetDarkPosition();
                goodsPopupWindow.darkBelow(dark_below);
                goodsPopupWindow.showAsDropDown(v, v.getRight() / 2, 0);
                showShadow(3);
                break;
            case R.id.time:
                if (myPopupwindow == null) {
                    myPopupwindow = new DateSelectPopupWindow((Activity) getContext());
                }
                if (hideDay)
                    myPopupwindow.hideDay(hideDay);
//                myPopupwindow.showPopupwindow(v);
                myPopupwindow.setDarkStyle(-1);
                myPopupwindow.setDarkColor(Color.parseColor("#a0000000"));
                myPopupwindow.resetDarkPosition();
                myPopupwindow.darkBelow(dark_below);
                myPopupwindow.showAsDropDown(v, v.getRight() / 2, 0);
showShadow(1);
                myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow.OnDateSelectListener() {
                    @Override
                    public void onDateSelect(String y, String m, String date, int f) {
                        if (Params.years.length() > 0) {
                            Params.years.delete(0, Params.years.length());
                        }
                        if (Params.month.length() > 0) {
                            Params.month.delete(0, Params.month.length());
                        }
                        if (Params.day.length() > 0) {
                            Params.day.delete(0, Params.day.length());
                        }
                        if (f == 1) {
                            Params.years.append("&year=").append(y);

                        } else if (f == 2) {
                            Params.years.append("&year=").append(y);
                            Params.month.append("&month=").append(m);
                            (time).setText(y + "-" + m);
                            Params.changeTime(y + "-" + m);
                        } else {
                            Params.years.append("&year=").append(y);
                            Params.month.append("&month=").append(m);
                            Params.day.append("&day=").append(date);
                            (time).setText(y + "-" + m + "-" + date);
                            Params.changeTime(y + "-" + m + "-" + date);
                        }
                        conditionSelect.postParams();
                    }
                });
                break;
            case R.id.channel:

                channelPopupWindow.bindTextView(channel);
                channelPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String ragions, String name, int tag) {
                        if (Params.channels.length() > 0) {
                            Params.channels.delete(0, Params.channels.length());
                        }
                        if (Params.wchannel.length() > 0) {
                            Params.wchannel.delete(0, Params.wchannel.length());
                        }
                        if (tag == 0) {
                            Params.channels.append("&channel=").append(ragions);
                        } else if (tag == 1) {
                            Params.wchannel.append("&wchannel=").append(ragions);
                        }
                        layout.setVisibility(VISIBLE);
                        channel.setText(name);
                        Params.changeChannel(name);
                        conditionSelect.postParams();
                    }
                });
//                channelPopupWindow.showPopupwindow(v);
                channelPopupWindow.setDarkStyle(-1);
                channelPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
                channelPopupWindow.resetDarkPosition();
                channelPopupWindow.darkBelow(dark_below);
                channelPopupWindow.showAsDropDown(v, v.getRight() / 2, 0);
                showShadow(2);
                break;
//            case R.id.index:
//                IndexPopupWindow indexPopupwindow = new IndexPopupWindow((Activity) getContext());
//                indexPopupwindow.showPopupwindow(v);
//                break;

        }
    }

    public void setConditionSelect(OnConditionSelect conditionSelect) {
        this.conditionSelect = conditionSelect;
    }

    private OnConditionSelect conditionSelect;

    private Boolean hideDay = false;

    public void hideDay() {
        hideDay = true;
    }

    private Boolean firbidSelectArea = false;

    public void ferbidSelectArea() {
        firbidSelectArea = true;
    }

    public interface OnConditionSelect {
        public void postParams();
    }

    public void showShadow(int po){
        switch (po){
            case 0:
                iv1.setVisibility(GONE);
                iv2.setVisibility(VISIBLE);
                iv3.setVisibility(GONE);
                iv4.setVisibility(GONE);
                iv5.setVisibility(GONE);
                iv6.setVisibility(GONE);
                break;
            case 1:
                iv1.setVisibility(VISIBLE);
                iv2.setVisibility(GONE);
                iv3.setVisibility(GONE);
                iv4.setVisibility(VISIBLE);
                iv5.setVisibility(GONE);
                iv6.setVisibility(GONE);
                break;
            case 2:
                iv1.setVisibility(GONE);
                iv2.setVisibility(GONE);
                iv3.setVisibility(VISIBLE);
                iv4.setVisibility(GONE);
                iv5.setVisibility(GONE);
                iv6.setVisibility(VISIBLE);
                break;
            case 3:
                iv1.setVisibility(GONE);
                iv2.setVisibility(GONE);
                iv3.setVisibility(GONE);
                iv4.setVisibility(GONE);
                iv5.setVisibility(VISIBLE);
                iv6.setVisibility(GONE);
                break;
        }

    }


}
