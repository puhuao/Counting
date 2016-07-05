package com.wksc.counting.widegit;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wksc.counting.R;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.BasePopupWindow;
import com.wksc.counting.popwindows.ChannelPopupWindow;
import com.wksc.counting.popwindows.DateSelectPopupWindow;
import com.wksc.counting.popwindows.GoodsPopupWindow;
import com.wksc.counting.tools.Params;
import com.wksc.framwork.util.StringUtils;

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
    public StringBuilder prams = new StringBuilder();
    public StringBuilder channels = new StringBuilder();
    public StringBuilder wchannel = new StringBuilder();
    public StringBuilder years = new StringBuilder();
    public StringBuilder month = new StringBuilder();
    public StringBuilder day = new StringBuilder();
    public StringBuilder city = new StringBuilder();
    public StringBuilder county = new StringBuilder();
    public StringBuilder mcu = new StringBuilder();
    public StringBuilder goodsclass = new StringBuilder();
    public StringBuilder goodssubclass = new StringBuilder();
    public StringBuilder province = new StringBuilder();
    private String data = "";
    public boolean paramsDeliver = true;


    public void getAllConditions(boolean isFromDelevir) {
        if (prams.length() > 0) {
            prams.delete(0, prams.length());
        }

            prams.append(province).append(channels).append(wchannel).append(years).append(month).append(day)
                    .append(city).append(county).append(goodsclass).append(goodssubclass).append(mcu);

//        if (paramsDeliver) {
//            if (Params.province.length()>0){
//                Params.province.delete(0,Params.province.length()-1);
//            }
//            Params.province.append(province);
//            if (Params.channels.length()>0){
//                Params.channels.delete(0,Params.channels.length()-1);
//            }
//            Params.channels.append(channels);
//            if (Params.wchannel.length()>0){
//                Params.wchannel.delete(0,Params.wchannel.length()-1);
//            }
//            Params.wchannel.append(wchannel);
//            if (Params.years.length()>0){
//                Params.years.delete(0,Params.years.length()-1);
//            }
//            Params.years.append(years);
//            if (Params.month.length()>0){
//                Params.month.delete(0,Params.month.length()-1);
//            }
//            Params.month.append(month);
//            if (Params.day.length()>0){
//                Params.day.delete(0,Params.day.length()-1);
//            }
//            Params.day.append(day);
//            if (Params.city.length()>0){
//                Params.city.delete(0,Params.city.length()-1);
//            }
//            Params.city.append(city);
//            if (Params.county.length()>0){
//                Params.county.delete(0,Params.county.length()-1);
//            }
//            Params.county.append(county);
//            if (Params.goodsclass.length()>0){
//                Params.goodsclass.delete(0,Params.goodsclass.length()-1);
//            }
//            Params.goodsclass.append(goodsclass);
//            if (Params.goodssubclass.length()>0){
//                Params.goodssubclass.delete(0,Params.goodssubclass.length()-1);
//            }
//            Params.goodssubclass.append(goodssubclass);
//            if (Params.mcu.length()>0){
//                Params.mcu.delete(0,Params.mcu.length()-1);
//            }
//            Params.mcu.append(mcu);
//        }/
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
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        channel.setOnClickListener(this);
//        index.setOnClickListener(this);

        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);
        if (y != 0)
            years.append("&year=").append(y);
        if (m < 10) {
            month.append("&month=").append("0" + (m + 1));
        } else if (m == 12) {
            month.append("&month=").append("01");
        } else {
            month.append("&month=").append((m + 1));
        }
//        calendar.set(y,m+1);
        if (d < 10) {
            day.append("&day=").append("0" + (d - 1));
        } else if (d == 1) {

        } else {
            day.append("&day=").append(d - 1);
        }

    }

    /**
     * @param b true为初始化false为从上一页传过来
     */
    public void setView(boolean b) {
        if (b) {
            time1.setText(y + "-" + (m + 1) + "-" + (d - 1));
            area1.setText("全国");
            channel1.setText("全部");
            goods1.setText("全部");
        } else {
            if (StringUtils.isBlank(Params.time)) {
                time1.setText(y + "-" + (m + 1) + "-" + (d - 1));
            } else {
                time1.setText(Params.time);
            }
            if (StringUtils.isBlank(Params.areal)) {
                area1.setText("全国");
            } else
                area1.setText(Params.areal);
            if (StringUtils.isBlank(Params.channel)) {
                area1.setText("全国");
            } else
                channel1.setText(Params.channel);
            if (StringUtils.isBlank(Params.goods)) {
                area1.setText("全国");
            } else
                goods1.setText(Params.goods);
        }
    }

    public void initParam() {

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
                AreaPopupwindow areaPopupWindow = new AreaPopupwindow((Activity) getContext());
                areaPopupWindow.bindTextView(area);

                areaPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String checkBeenRagion, String name, int tag) {
                        if (province.length() > 0) {
                            province.delete(0, province.length());
                        }
                        if (city.length() > 0) {
                            city.delete(0, city.length());
                        }
                        if (county.length() > 0) {
                            county.delete(0, county.length());
                        }

                        if (tag == 0) {
                            province.append("&province=").append(checkBeenRagion);
                        } else if (tag == 1) {
                            city.append("&city=").append(checkBeenRagion);
                        } else if (tag == 2) {
                            county.append("&county=").append(checkBeenRagion);
                        } else if (tag == 4) {
                            mcu.append("&mcu=").append(checkBeenRagion);
                        }
//                        getAllConditions(true);
//                        if (prams.length() > 0) {
//                            layout.setVisibility(VISIBLE);
                            area1.setText(name);
                            Params.areal = name;
//                        }
                        conditionSelect.postParams();
                    }
                });
                areaPopupWindow.showPopupwindow(v);
                break;
            case R.id.goods:
                GoodsPopupWindow goodsPopupWindow = new GoodsPopupWindow((Activity) getContext());
                goodsPopupWindow.bindTextView(goods);
                goodsPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String ragions, String name, int tag) {
                        if (goodsclass.length() > 0) {
                            goodsclass.delete(0, goodsclass.length());
                        }
                        if (goodssubclass.length() > 0) {
                            goodssubclass.delete(0, goodssubclass.length());
                        }
                        if (tag == 0) {
                            goodsclass.append("&goodsclass=").append(ragions);
                        } else if (tag == 1) {
                            goodssubclass.append("&goodssubclass=").append(ragions);
                        } else if (tag == -1) {
                            goodsclass.delete(0, goodsclass.length());
                            goodssubclass.delete(0, goodssubclass.length());
                        }
                        layout.setVisibility(VISIBLE);
                        goods1.setText(name);
                        Params.goods = name;

                        conditionSelect.postParams();
                    }
                });
                goodsPopupWindow.showPopupwindow(v);
                break;
            case R.id.time:
                DateSelectPopupWindow myPopupwindow = new DateSelectPopupWindow((Activity) getContext(), data);
               if (hideDay)
                myPopupwindow.hideDay(hideDay);
                myPopupwindow.showPopupwindow(v);
                myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow.OnDateSelectListener() {
                    @Override
                    public void onDateSelect(String y, String m, String date, int f) {
                        if (years.length() > 0) {
                            years.delete(0, years.length());
                        }
                        if (month.length() > 0) {
                            month.delete(0, month.length());
                        }
                        if (day.length() > 0) {
                            day.delete(0, day.length());
                        }
                        if (f == 1) {
                            years.append("&year=").append(y);

                        } else if (f == 2) {
                            month.append("&month=").append(m);
                            (time1).setText(y + "-" + m);
                        } else {
                            month.append("&month=").append(m);
                            day.append("&day=").append(date);
                            (time1).setText(y + "-" + m + "-" + date);
                            Params.time = y + "-" + m + "-" + date;
                        }

//                            time.setText(data);
                        conditionSelect.postParams();
//                         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//                        if (year==0&&monthOfYear==0&&dayOfMonth==0){
//                            if (data.equals("")) {
//                                data = DateTool.getChinaDate();
//                            }
//                        }else{
//                            data=DateTool.getChinaDateFromCalendar(year,monthOfYear,dayOfMonth);
//                        }
                        layout.setVisibility(VISIBLE);
                    }
                });
                break;
            case R.id.channel:
                ChannelPopupWindow channelPopupWindow = new ChannelPopupWindow((Activity) getContext());
                channelPopupWindow.bindTextView(channel);
                channelPopupWindow.setOnConditionSelectListener(new BasePopupWindow.OnConditionSelectListener() {
                    @Override
                    public void conditionSelect(String ragions, String name, int tag) {
                        if (channels.length() > 0) {
                            channels.delete(0, channels.length());
                        }
                        if (wchannel.length() > 0) {
                            wchannel.delete(0, wchannel.length());
                        }
                        if (tag == 0) {
                            channels.append("&channel=").append(ragions);
                        } else if (tag == 1) {
                            wchannel.append("&wchannel=").append(ragions);
                        } else if (tag == -1) {
                            channels.delete(0, channels.length());
                            wchannel.delete(0, wchannel.length());
                        }
                        layout.setVisibility(VISIBLE);
                        channel1.setText(name);
                        Params.channel = name;
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

    public interface OnConditionSelect {
        public void postParams();
    }


}
