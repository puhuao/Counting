package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CoreIndexModel {
    public String name;
    public String data;
    public String currentDada;
    public String mounthRealativ;//同比
    public String mounthData;//累计
    public String mounthEaliear;//环比

    public static List<CoreIndexModel> getData(){
        List<CoreIndexModel> list = new ArrayList<>();
        for (int i = 0 ; i <4;i++){
            CoreIndexModel model = new CoreIndexModel();
            if (i == 0){
                model.name = "销售额(万元)：";
                model.data = "105555.00";
            }else if (i == 1){
                model.name = "毛利额(万元)：";
                model.data = "555.00";
            }
            else if (i == 2){
                model.name = "毛利率：";
                model.data = "58%";
            }
            else if (i == 3){
                model.name = "客单数(个)";
                model.data = "105";
            }
            model.mounthData = "256";
            model.mounthRealativ = "+89%";
            model.currentDada = "2555";
            model.mounthEaliear="-52%";
            list.add(model);
        }
        return list;
    }
}
