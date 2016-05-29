package com.wksc.counting.model;

import com.wksc.framwork.baseui.fragment.CommonFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class ComparisonModel {
    public String area;
    public String monthData;
    public String monthCompareRelative;
    public String monthCompareEalair;

    public static List<ComparisonModel> getData(){
        List<ComparisonModel> list = new ArrayList<>();
        for (int i =0 ;i < 5;i ++){
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
                model.monthData = "133.3";
                model.monthCompareRelative = "+12%";
                model.monthCompareEalair = "-3%";
            }else{
                model.area = "区域/部门";
                model.monthData = "月累计(万元)";
                model.monthCompareRelative = "月同比";
                model.monthCompareEalair = "月环比";
            }
            list.add(model);
        }

        return  list;
    }
}
