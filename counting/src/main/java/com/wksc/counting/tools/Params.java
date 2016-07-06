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
    /////////////////////////////////////////////////////////////////
    public static StringBuilder areal = new StringBuilder();
    public static StringBuilder goods = new StringBuilder();
    public static StringBuilder time = new StringBuilder();
    public static StringBuilder channel = new StringBuilder();

    public static String getParam(){
        if (prams.length() > 0) {
            prams.delete(0, prams.length());
        }
        prams.append(province).append(channels).append(wchannel).append(years).append(month).append(day)
                .append(city).append(county).append(goodsclass).append(goodssubclass).append(mcu);
        return prams.toString();
    }

    public static void changeAreal(String string){
        if (areal.length()>0){
            areal.delete(0,areal.length());
        }
        areal.append(string);

    }
    public static void changeGoods(String string){
        if (areal.length()>0){
            areal.delete(0,areal.length());
        }
        areal.append(string);

    }
    public static void changeTime(String string){
        if (time.length()>0){
            time.delete(0,time.length());
        }
        time.append(string);

    }
    public static void changeChannel(String string){
        if (channel.length()>0){
            channel.delete(0,channel.length());
        }
        channel.append(string);

    }

}
