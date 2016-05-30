package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class PurchaseModel {
    public String name;
    public String goal;
    public String acture;
    public String reachRate;
    public static List<PurchaseModel> getData(){
        List<PurchaseModel> list = new ArrayList<>();
        for (int i =0 ;i < 5;i ++){
            PurchaseModel model = new PurchaseModel();
            if (i ==1 ){
                model.name = "白酒";
            }else if (i == 2){
                model.name = "红酒";
            }else if (i == 3){
                model.name = "米酒";
            }
            if(i != 0){
                model.goal = "133.3";
                model.acture = "66666";
                model.reachRate = "3%";
            }else{
                model.name = "名称";
                model.goal = "自行采购额";
                model.acture = "采购总额";
                model.reachRate = "采购自给率";
            }
            list.add(model);
        }

        return  list;
    }
}
