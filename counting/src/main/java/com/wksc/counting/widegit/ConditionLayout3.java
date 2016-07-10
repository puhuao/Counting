package com.wksc.counting.widegit;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wksc.counting.Contorner.Condition;
import com.wksc.counting.R;
import com.wksc.counting.popwindows.AreaPopupwindow2;
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
    LinearLayout area_layout;
    LinearLayout area1_layout;
    Calendar calendar;
    MarqueeText channel1;
    MarqueeText time1;
    MarqueeText area1;
    MarqueeText goods1;
    MarqueeText area;
    MarqueeText goods;
    MarqueeText time;
    MarqueeText channel;
    LinearLayout layoutGoods;
    LinearLayout layoutChannel;
    LinearLayout layout;
    int y, m, d;
    private String data = "";
    AreaPopupwindow3 areaPopupWindow;
    GoodsPopupWindow2 goodsPopupWindow;
    DateSelectPopupWindow2 myPopupwindow;
    ChannelPopupWindow2 channelPopupWindow;
    public Params2 params2;

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
        area1 = (MarqueeText) findViewById(R.id.area1);
        goods1 = (MarqueeText) findViewById(R.id.goods1);
        time1 = (MarqueeText) findViewById(R.id.time1);
        channel1 = (MarqueeText) findViewById(R.id.channel1);
        area = (MarqueeText) findViewById(R.id.area);
        goods = (MarqueeText) findViewById(R.id.goods);
        time = (MarqueeText) findViewById(R.id.time);
        channel = (MarqueeText) findViewById(R.id.channel);
        layoutGoods = (LinearLayout) findViewById(R.id.layout_goods);
        layoutChannel = (LinearLayout) findViewById(R.id.layout_channel);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout_goods1 = (LinearLayout) findViewById(R.id.layout_goods1);
        layout_channel1 = (LinearLayout) findViewById(R.id.layout_channel1);
        area_layout = (LinearLayout) findViewById(R.id.area_layout);
        area1_layout = (LinearLayout) findViewById(R.id.area1_layout);
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        channel.setOnClickListener(this);

        myPopupwindow = new DateSelectPopupWindow2((Activity) getContext(), data);
        channelPopupWindow = new ChannelPopupWindow2((Activity) getContext());

    }

    public void init(int posion) {
        params2 = params2List.get(posion);
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);
        if (params2.years.length() == 0) {
            params2.years.append("&year=").append(y);
        }
        if (params2.month.length() == 0) {
            if (m < 10) {
                if (params2.month.length() == 0)
                    params2.month.append("&month=").append("0" + (m + 1));
            } else if (m == 12) {
                if (params2.month.length() == 0)
                    params2.month.append("&month=").append("01");
            } else {
                if (params2.month.length() == 0)
                    params2.month.append("&month=").append((m + 1));
            }
        }
        if (params2.day.length() == 0) {
            if (d < 10) {
                if (params2.day.length() == 0)
                    params2.day.append("&day=").append("0" + (d - 1));
            } else if (d == 1) {

            } else {
                if (params2.day.length() == 0)
                    params2.day.append("&day=").append(d - 1);
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
        time1.setText(y + "-" + (m + 1) + "-" + (d - 1));
        area1.setText("全国");
        channel1.setText("全部");
        goods1.setText("全部");
    }

    public void initViewByParam() {

        if (StringUtils.isBlank(params2.time.toString())) {
            if (hideDay) {
                time1.setText(y + "-" + (m + 1));
            } else {
                time1.setText(y + "-" + (m + 1) + "-" + (d - 1));
            }
        } else {
            time1.setText(params2.time.toString());
        }
        if (StringUtils.isBlank(params2.areal.toString())) {
            area1.setText("全国");
        } else
            area1.setText(params2.areal.toString());
        if (StringUtils.isBlank(params2.channel.toString())) {
            channel1.setText("全部");
        } else
            channel1.setText(params2.channel.toString());
        if (StringUtils.isBlank(params2.goods.toString())) {
            goods1.setText("全部");
        } else
            goods1.setText(params2.goods.toString());
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
                            area1.setText(name);
                        } else if (tag == 1) {
                            params2.city.append("&city=").append(checkBeenRagion);
                            area1.setText(name);
                        } else if (tag == 2) {
                            params2.county.append("&county=").append(checkBeenRagion);
                            area1.setText(name);
                        } else if (tag == 4) {
                            params2.mcu.append("&mcu=").append(checkBeenRagion);
                            area1.setText(name);
                        } else {
                            area1.setText("全国");
                        }
                        params2.changeAreal(name);
                        conditionSelect.postParams();
                    }
                });
                areaPopupWindow.showPopupwindow(v);
                break;
            case R.id.goods:

                if (goodsPopupWindow == null) {
                    goodsPopupWindow = new GoodsPopupWindow2((Activity) getContext(),mCondition);
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
                        goods1.setText(name);
                        params2.changeGoods(name);

                        conditionSelect.postParams();
                    }
                });
                goodsPopupWindow.showPopupwindow(v);
                break;
            case R.id.time:

                if (hideDay) {
                    myPopupwindow.hideDay(hideDay);
                    myPopupwindow.hideMonthCheck();
                }

                if (hideDayAndMonthCheck) {
                    myPopupwindow.hideDayAndMonthCheck();
                }

                myPopupwindow.showPopupwindow(v);
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
                            (time1).setText(y + "-" + m);
                            params2.changeTime(y + "-" + m);
                        } else {
                            params2.years.append("&year=").append(y);
                            params2.month.append("&month=").append(m);
                            params2.day.append("&day=").append(date);
                            (time1).setText(y + "-" + m + "-" + date);
                            params2.changeTime(y + "-" + m + "-" + date);
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
                        channel1.setText(name);
                        params2.changeChannel(name);
                        conditionSelect.postParams();
                    }
                });
                channelPopupWindow.showPopupwindow(v);
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


}
