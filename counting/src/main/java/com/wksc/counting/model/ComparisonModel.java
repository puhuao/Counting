package com.wksc.counting.model;

import com.wksc.framwork.baseui.fragment.CommonFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class ComparisonModel {
    public String area;
    public String oldStoreMonthData;
    public String oldStoreMonthCompareRelative;
    public String oldStoreMonthCompareEalair;
    public String newStoreMonthData;
    public String newStoreMonthCompareRelative;
    public String newStoreMonthCompareEalair;
    public String oldStoreData;
    public String newStoreData;

    public static List<ComparisonModel> getData(){
        List<ComparisonModel> list = new ArrayList<>();
        for (int i =0 ;i < 6;i ++){
            ComparisonModel model = new ComparisonModel();
            if (i ==1 ){
                model.area = "四川";
            }else if (i == 2){
                model.area = "江苏";
            }else if (i == 3){
                model.area = "云南";
            }else if (i == 4){
                model.area = "贵州";
        }else if (i == 5){
                model.area = "广东";
        }
            if(i != 0){
                model.oldStoreMonthData = "133.3";
                model.newStoreMonthData = "333.3";
                if(i==5){
                    model.oldStoreMonthData = "130";
                    model.newStoreMonthData = "300";
                }else if(i == 1){
                    model.oldStoreMonthData = "966";
                    model.newStoreMonthData = "900";
                }
                model.oldStoreMonthCompareRelative = "+12%";
                model.newStoreMonthCompareRelative = "+13%";
                model.oldStoreMonthCompareEalair = "-3%";
                model.newStoreMonthCompareEalair = "-3%";
                model.newStoreData = "30";
                model.oldStoreData = "35";
            }else{
                model.area = "区域/部门";
                model.oldStoreMonthData = "月累计(万元)";
                model.oldStoreMonthCompareRelative = "月同比";
                model.oldStoreMonthCompareEalair = "月环比";
                model.oldStoreData = "月均值";
                model.newStoreMonthData = "900";
                model.newStoreMonthCompareRelative = "+13%";
                model.newStoreMonthCompareEalair = "-3%";
                model.newStoreData = "30";
            }
            list.add(model);
        }

        return  list;
    }
}
