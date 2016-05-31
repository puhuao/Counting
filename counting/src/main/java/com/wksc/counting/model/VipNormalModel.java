package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class VipNormalModel {
    public String name;
    public String goal;
    public String acture;
    public String reachRate;
    public static List<VipNormalModel> getData(){
        List<VipNormalModel> list = new ArrayList<>();
        for (int i =0 ;i < 2;i ++){
            VipNormalModel model = new VipNormalModel();
            if (i ==1 ){
                model.name = "100";
                model.goal = "133";
                model.acture = "133";
                model.reachRate = "57%";
            }else{
                model.name = "购买一次的会员数";
                model.goal = "购买两次的会员数";
                model.acture = "购买三次以上的会员数";
                model.reachRate = "复购率";
            }
            list.add(model);
        }

        return  list;
    }
}
