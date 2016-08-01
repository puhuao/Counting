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

import com.wksc.counting.Contorner.Condition;
import com.wksc.counting.R;
import com.wksc.counting.popwindows.AreaPopupwindow3;
import com.wksc.counting.popwindows.BasePopupWindow;
import com.wksc.counting.popwindows.ChannelPopupWindow2;
import com.wksc.counting.popwindows.DateSelectPopupWindow2;
import com.wksc.counting.popwindows.GoodsPopupWindow2;
import com.wksc.counting.tools.Params2;
import com.wksc.framwork.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by puhua on 2016/6/28.
 *
 * @
 */
public class ConditionLayout3 extends LinearLayout implements View.OnClickListener {
    public static List<Params2> params2List = new ArrayList<>();

    static {
        for (int i = 0; i < 5; i++) {
            Params2 params21 = new Params2();
            params2List.add(params21);
        }
    }

    LinearLayout layout_channel1;
    LinearLayout layout_goods1;
    RelativeLayout area_layout;
    LinearLayout area1_layout;
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
    AreaPopupwindow3 areaPopupWindow;
    GoodsPopupWindow2 goodsPopupWindow;
    DateSelectPopupWindow2 myPopupwindow;
    ChannelPopupWindow2 channelPopupWindow;
    public Params2 params2;
    LinearLayout dark_below;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6;

    public String getAllConditions() {
        return params2.getParam();
    }

    public ConditionLayout3(Context context) {
        super(context);
    }

    public ConditionLayout3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConditionLayout3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_condition, this);
//        ButterKnife.bind(this);
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
        area_layout = (RelativeLayout) findViewById(R.id.area_layout);
        area1_layout = (LinearLayout) findViewById(R.id.area1_layout);
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

        channelPopupWindow = new ChannelPopupWindow2((Activity) getContext());

    }

    public void init(int posion) {
        params2 = params2List.get(posion);

        if (params2.y==0)
            params2.y = calendar.get(Calendar.YEAR);
        if (params2.m==0)
            params2.m = calendar.get(Calendar.MONTH);
        if (params2.d == 0)
            params2.d = calendar.get(Calendar.DAY_OF_MONTH);
        if (params2.years.length() == 0) {
            params2.years.append("&year=").append(params2.y);
        }
        if (params2.month.length() == 0) {
            if (params2.m < 10) {
                if (params2.month.length() == 0)
                    params2.month.append("&month=").append("0" + (params2.m + 1));
            } else if (params2.m == 12) {
                if (params2.month.length() == 0)
                    params2.month.append("&month=").append("01");
            } else {
                if (params2.month.length() == 0)
                    params2.month.append("&month=").append((params2.m + 1));
            }
        }
        if (params2.day.length() == 0) {
            if (params2.d < 10&&params2.d>1) {
                if (params2.day.length() == 0)
                    params2.day.append("&day=").append("0" + (params2.d - 1));
            } else if (params2.d == 1) {
                calendar.roll(Calendar.DATE, -1);

//                Params.m = calendar.get(Calendar.MONTH);
                params2.m = calendar.get(Calendar.MONTH)-1;
                params2.month.delete(0,params2.month.length());
                if (calendar.get(Calendar.MONTH)<10)
                    params2.month.append("&month=").append("0"+calendar.get(Calendar.MONTH));
                else{
                    params2.month.append("&month=").append(calendar.get(Calendar.MONTH));
                }
                if (params2.day.length() == 0)
                    params2.d = calendar.get(Calendar.DAY_OF_MONTH)+1;
                params2.day.append("&day=").append(calendar.get(Calendar.DAY_OF_MONTH));
            } else {
                if (params2.day.length() == 0)
                    params2.day.append("&day=").append(params2.d - 1);
            }
        }
        initViewBySelf();

    }


    private Boolean hideCity = false;
    private Boolean hideCounty = false;

    public void hideCity() {
        hideCity = true;
    }

    public void hideCounty() {
        hideCounty = true;
    }


    public void initParams() {
        if (params2.day.length() > 0)
            params2.day.delete(0, params2.day.length());
    }

    /**
     * @param
     */
    public void initViewBySelf() {
//        time.setText(params2.y + "-" + (params2.m + 1) + "-" + (params2.d - 1));
//        area.setText("全部");
//        channel.setText("全部");
//        goods.setText("全部");
    }

    public void initViewByParam() {

        if (StringUtils.isBlank(params2.time.toString())) {
            if (hideDay) {
                time.setText(params2.y + "-" + (params2.m + 1));
            } else {
                time.setText(params2.y + "-" + (params2.m + 1) + "-" + (params2.d - 1));
            }
        } else {
            time.setText(params2.time.toString());
        }
        if (StringUtils.isBlank(params2.areal.toString())) {
            area.setText("区域");
        } else
            area.setText(params2.areal.toString());
        if (StringUtils.isBlank(params2.channel.toString())) {
            channel.setText("渠道");
        } else
            channel.setText(params2.channel.toString());
        if (StringUtils.isBlank(params2.goods.toString())) {
            goods.setText("商品");
        } else
            goods.setText(params2.goods.toString());
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

    public void hideBothGoodsAndChannel(Boolean hide) {
        if (hide) {
            layoutGoods.setVisibility(GONE);
            layout_goods1.setVisibility(GONE);
            layoutChannel.setVisibility(GONE);
            layout_channel1.setVisibility(GONE);
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area:
                if (areaPopupWindow == null) {
                    areaPopupWindow = new AreaPopupwindow3((Activity) getContext(),mCondition);
                }
                if (hideCity) {
                    areaPopupWindow.hideCity();
                }
                if (hideCounty) {
                    areaPopupWindow.hideCounty();
                }

                if (hideStores) {
                    areaPopupWindow.hideStores();
                }

                if (params2.areal.length()==0){
                    areaPopupWindow.setFirstSelect();
                }
                areaPopupWindow.bindTextView(area);

                areaPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String checkBeenRagion, String name, int tag) {
                        if (params2.province.length() > 0) {
                            params2.province.delete(0, params2.province.length());
                        }
                        if (params2.city.length() > 0) {
                            params2.city.delete(0, params2.city.length());
                        }
                        if (params2.county.length() > 0) {
                            params2.county.delete(0, params2.county.length());
                        }

                        if (tag == 0) {
                            params2.province.append("&province=").append(checkBeenRagion);
                            area.setText(name);
                        } else if (tag == 1) {
                            params2.city.append("&city=").append(checkBeenRagion);
                            area.setText(name);
                        } else if (tag == 2) {
                            params2.county.append("&county=").append(checkBeenRagion);
                            area.setText(name);
                        } else if (tag == 4) {
                            params2.mcu.append("&mcu=").append(checkBeenRagion);
                            area.setText(name);
                        } else {
                            area.setText("全部");
                        }
                        params2.changeAreal(name);
                        conditionSelect.postParams();
                        hideShadow();
                    }
                });
                areaPopupWindow.setDarkStyle(-1);
                areaPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
                areaPopupWindow.resetDarkPosition();
                areaPopupWindow.darkBelow(dark_below);
                areaPopupWindow.showAsDropDown(v, v.getRight() / 2, 0);
                showShadow(0);
                break;
            case R.id.goods:

                if (goodsPopupWindow == null) {
                    goodsPopupWindow = new GoodsPopupWindow2((Activity) getContext(),mCondition);
                }
                if (params2.goods.length()==0){
                    goodsPopupWindow.setFirstSelect();
                }
                goodsPopupWindow.setCondition(mCondition);
                goodsPopupWindow.bindTextView(goods);
                goodsPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String ragions, String name, int tag) {
                        if (params2.goodsclass.length() > 0) {
                            params2.goodsclass.delete(0, params2.goodsclass.length());
                        }
                        if (params2.goodssubclass.length() > 0) {
                            params2.goodssubclass.delete(0, params2.goodssubclass.length());
                        }
                        if (tag == 0) {
                            params2.goodsclass.append("&goodsclass=").append(ragions);
                        } else if (tag == 1) {
                            params2.goodssubclass.append("&goodssubclass=").append(ragions);
                        } else if (tag == -1) {
                            params2.goodsclass.delete(0, params2.goodsclass.length());
                            params2.goodssubclass.delete(0, params2.goodssubclass.length());
                        }
                        layout.setVisibility(VISIBLE);
                        goods.setText(name);
                        params2.changeGoods(name);

                        conditionSelect.postParams();
                        hideShadow();
                    }
                });
                goodsPopupWindow.setDarkStyle(-1);
                goodsPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
                goodsPopupWindow.resetDarkPosition();
                goodsPopupWindow.darkBelow(dark_below);
                goodsPopupWindow.showAsDropDown(v, v.getRight() / 2, 0);
                showShadow(3);
                break;
            case R.id.time:

                if(myPopupwindow==null){
                    myPopupwindow = new DateSelectPopupWindow2((Activity) getContext(),params2);
                }
                myPopupwindow.initView();
                if (hideDay) {
                    myPopupwindow.hideDay(hideDay);
                    myPopupwindow.hideMonthCheck();
                }

                if (hideDayAndMonthCheck) {
                    myPopupwindow.hideDayAndMonthCheck();
                }

                myPopupwindow.setDarkStyle(-1);
                myPopupwindow.setDarkColor(Color.parseColor("#a0000000"));
                myPopupwindow.resetDarkPosition();
                myPopupwindow.darkBelow(dark_below);
                myPopupwindow.showAsDropDown(v, v.getRight() / 2, 0);
                showShadow(1);
                myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow2.OnDateSelectListener() {
                    @Override
                    public void onDateSelect(String y, String m, String date, int f) {
                        if (params2.years.length() > 0) {
                            params2.years.delete(0, params2.years.length());
                        }
                        if (params2.month.length() > 0) {
                            params2.month.delete(0, params2.month.length());
                        }
                        if (params2.day.length() > 0) {
                            params2.day.delete(0, params2.day.length());
                        }
                        if (f == 1) {
                            params2.years.append("&year=").append(y);

                        } else if (f == 2) {
                            params2.years.append("&year=").append(y);
                            params2.month.append("&month=").append(m);
                            (time).setText(y + "-" + m);
                            params2.changeTime(y + "-" + m);
                        } else {
                            params2.years.append("&year=").append(y);
                            params2.month.append("&month=").append(m);
                            params2.day.append("&day=").append(date);
                            (time).setText(y + "-" + m + "-" + date);
                            params2.changeTime(y + "-" + m + "-" + date);
                        }
                        conditionSelect.postParams();
                        hideShadow();
                    }
                });
                break;
            case R.id.channel:

                channelPopupWindow.bindTextView(channel);
                channelPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String ragions, String name, int tag) {
                        if (params2.channels.length() > 0) {
                            params2.channels.delete(0, params2.channels.length());
                        }
                        if (params2.wchannel.length() > 0) {
                            params2.wchannel.delete(0, params2.wchannel.length());
                        }
                        if (tag == 0) {
                            params2.channels.append("&channel=").append(ragions);
                        } else if (tag == 1) {
                            params2.wchannel.append("&wchannel=").append(ragions);
                        } else if (tag == -1) {
                            params2.channels.delete(0, params2.channels.length());
                            params2.wchannel.delete(0, params2.wchannel.length());
                        }
                        layout.setVisibility(VISIBLE);
                        channel.setText(name);
                        params2.changeChannel(name);
                        conditionSelect.postParams();
                        hideShadow();
                    }
                });
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

    public Boolean hideDayAndMonthCheck = false;

    public void hideDayAndMonthCheck() {
        hideDayAndMonthCheck = true;
    }

    public void hidArea(boolean b) {
        if (b) {
            area_layout.setVisibility(GONE);
            area1_layout.setVisibility(GONE);
        }
    }

    private Boolean hideStores = false;

    public void hideStores() {
        hideStores = true;
    }
private Condition mCondition;
    public void setcondition(Condition condition) {
        mCondition = condition;
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

    public void hideShadow(){
        iv1.setVisibility(GONE);
        iv2.setVisibility(GONE);
        iv3.setVisibility(GONE);
        iv4.setVisibility(GONE);
        iv5.setVisibility(GONE);
        iv6.setVisibility(GONE);
    }

}
