package com.wksc.counting.tools;

/**
 * Created by Administrator on 2016/7/5.
 */
public class Params2 {


    public  StringBuilder prams = new StringBuilder();
    public  StringBuilder channels = new StringBuilder();
    public  StringBuilder wchannel = new StringBuilder();
    public  StringBuilder years = new StringBuilder();
    public  StringBuilder month = new StringBuilder();
    public  StringBuilder day = new StringBuilder();
    public  StringBuilder city = new StringBuilder();
    public  StringBuilder county = new StringBuilder();
    public   StringBuilder mcu = new StringBuilder();
    public  StringBuilder goodsclass = new StringBuilder();
    public  StringBuilder goodssubclass = new StringBuilder();
    public  StringBuilder province = new StringBuilder();
    /////////////////////////////////////////////////////////////////
    public  StringBuilder areal = new StringBuilder();
    public  StringBuilder goods = new StringBuilder();
    public  StringBuilder time = new StringBuilder();
    public  StringBuilder channel = new StringBuilder();

    public  String extraParams;

    public  int y,m,d;
    public  int dateFlag = 3;

    public  String getParam(){
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

    public  void changeAreal(String string){
        if (areal.length()>0){
            areal.delete(0,areal.length());
        }
        areal.append(string);

    }
    public  void changeGoods(String string){
        if (goods.length()>0){
            goods.delete(0,goods.length());
        }
        goods.append(string);

    }
    public  void changeTime(String string){
        if (time.length()>0){
            time.delete(0,time.length());
        }
        time.append(string);

    }
    public  void changeChannel(String string){
        if (channel.length()>0){
            channel.delete(0,channel.length());
        }
        channel.append(string);

    }


}
