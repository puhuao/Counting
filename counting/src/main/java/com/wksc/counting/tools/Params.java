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
    public static StringBuilder city ;
    public static StringBuilder county;
    public static  StringBuilder mcu = new StringBuilder();
    public static StringBuilder goodsclass = new StringBuilder();
    public static StringBuilder goodssubclass = new StringBuilder();
    public static StringBuilder province;
    /////////////////////////////////////////////////////////////////
    public static StringBuilder areal;
    public static StringBuilder goods = new StringBuilder();
    public static StringBuilder time = new StringBuilder();
    public static StringBuilder channel = new StringBuilder();
    public static StringBuilder arealCore = new StringBuilder();
    public static StringBuilder arealMain = new StringBuilder();
    public static StringBuilder arealMain1 = new StringBuilder();
    public static int y,m,d;
    public static int dateFlag = 3;

    public static StringBuilder cityCore = new StringBuilder();
    public static StringBuilder countyCore = new StringBuilder();
    public static StringBuilder provinceCore = new StringBuilder();
    public static StringBuilder cityMain = new StringBuilder();
    public static StringBuilder countyMain = new StringBuilder();
    public static StringBuilder provinceMain = new StringBuilder();
    public static StringBuilder cityMain1 = new StringBuilder();
    public static StringBuilder countyMain1 = new StringBuilder();
    public static StringBuilder provinceMain1 = new StringBuilder();

    public static void getAreaByFlag(int flag){
        if (flag == 0){
            province = provinceCore;
            city = cityCore;
            county = countyCore;
            areal =arealCore;

        }else if(flag == 1){
            province = provinceMain;
            city = cityMain;
            county = countyMain;
            areal = arealMain;
        }else if(flag == 2){
            province = provinceMain1;
            city = cityMain1;
            county = countyMain1;
            areal = arealMain1;
        }
    }

    public static String getAreas(){
        StringBuilder sb = new StringBuilder();
        sb.append(province).append(city).append(county);
        return sb.toString();
    }

    public static String getParam(){
        if (prams.length() > 0) {
            prams.delete(0, prams.length());
        }
        prams.append(province).append(channels).append(wchannel)
                .append(city).append(county).append(goodsclass).append(goodssubclass).append(mcu);
        if (dateFlag==3){
            prams.append(day).append(years).append(month);
        }else if(dateFlag == 2){
            prams.append(years).append(month);
        }else if(dateFlag == 1){
            prams.append(years);
        }
        return prams.toString();
    }

    public static void changeAreal(String string){
        if (areal.length()>0){
            areal.delete(0,areal.length());
        }
        areal.append(string);

    }
    public static void changeGoods(String string){
        if (goods.length()>0){
            goods.delete(0,goods.length());
        }
        goods.append(string);

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

    public static void clearArea(){
        if (areal.length()>0)
            areal.delete(0,areal.length());
        if (city.length()>0)
            city.delete(0,city.length());
        if (county.length()>0)
            county.delete(0,county.length());
        if (mcu.length()>0)
            mcu.delete(0,mcu.length());
        if (province.length()>0)
            province.delete(0,province.length());
    }

    public static void clearData(){
        if (prams.length()>0)
        prams.delete(0,prams.length());
        if (time.length()>0)
            time.delete(0,time.length());
        if (goods.length()>0)
            goods.delete(0,goods.length());
        if (areal.length()>0)
            areal.delete(0,areal.length());
        if (channels.length()>0)
            channels.delete(0,channels.length());
        if (wchannel.length()>0)
            wchannel.delete(0,wchannel.length());
        if (years.length()>0)
            years.delete(0,years.length());
        if (month.length()>0)
            month.delete(0,month.length());
        if (day.length()>0)
            day.delete(0,day.length());
        if (city.length()>0)
            city.delete(0,city.length());
        if (county.length()>0)
            county.delete(0,county.length());
        if (mcu.length()>0)
            mcu.delete(0,mcu.length());
        if (goodsclass.length()>0)
            goodsclass.delete(0,goodsclass.length());
        if (goodssubclass.length()>0)
            goodssubclass.delete(0,goodssubclass.length());
        if (province.length()>0)
            province.delete(0,province.length());
        y =0;
        m = 0;
        d = 0;
        dateFlag = 3;
    }

}
