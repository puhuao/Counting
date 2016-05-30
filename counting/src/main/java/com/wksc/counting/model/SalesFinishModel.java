package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class SalesFinishModel {
    public String name;
    public String goal;
    public String acture;
    public String reachRate;
    public static List<SalesFinishModel> getData(){
        List<SalesFinishModel> list = new ArrayList<>();
        for (int i =0 ;i < 5;i ++){
            SalesFinishModel model = new SalesFinishModel();
            if (i ==1 ){
                model.name = "销售额";
            }else if (i == 2){
                model.name = "开店数量";
            }else if (i == 3){
                model.name = "会员数量";
            }
            if(i != 0){
                model.goal = "133.3";
                model.acture = "+12%";
                model.reachRate = "-3%";
            }else{
                model.name = "指标名";
                model.goal = "目标值";
                model.acture = "实际值";
                model.reachRate = "达成率";
            }
            list.add(model);
        }

        return  list;
    }
}
