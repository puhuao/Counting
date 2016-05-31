package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class ChainVipModel {
    public String name;
    public String goal;
    public String acture;
    public static List<ChainVipModel> getData(){
        List<ChainVipModel> list = new ArrayList<>();
        for (int i =0 ;i < 4;i ++){
            ChainVipModel model = new ChainVipModel();
            if (i ==1 ){
                model.name = "天猫";
            }else if (i == 2){
                model.name = "京东";
            }else if (i == 3){
                model.name = "官网";
            }
            if(i != 0){
                model.goal = "133.3";
                model.acture = "133";
            }else{
                model.name = "渠道";
                model.goal = "销售数";
                model.acture = "销售额";
            }
            list.add(model);
        }

        return list;
    }
}
