package com.wksc.counting.tools;

/**
 * Created by Administrator on 2016/7/5.
 */
public class Params {
    public static StringBuilder prams = new StringBuilder();
    public static StringBuilder channels = new StringBuilder();
    public static StringBuilder wchannel = new StringBuilder();
    public static StringBuilder years = new StringBuilder();
    public static StringBuilder month = new StringBuilder();
    public static StringBuilder day = new StringBuilder();
    public static StringBuilder city = new StringBuilder();
    public static StringBuilder county = new StringBuilder();
    public static  StringBuilder mcu = new StringBuilder();
    public static StringBuilder goodsclass = new StringBuilder();
    public static StringBuilder goodssubclass = new StringBuilder();
    public static StringBuilder province = new StringBuilder();
    public static String areal;
    public static String goods;
    public static String time;
    public static String channel;

    public static void getParam(){
        prams.append(province).append(channels).append(wchannel).append(years).append(month).append(day)
                .append(city).append(county).append(goodsclass).append(goodssubclass).append(mcu);
    }

}
