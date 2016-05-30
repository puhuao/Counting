package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class SaveModel {
    public String name;
    public String goal;
    public String acture;
    public String reachRate;
    public static List<SaveModel> getData(){
        List<SaveModel> list = new ArrayList<>();
        for (int i =0 ;i < 5;i ++){
            SaveModel model = new SaveModel();
            if (i ==1 ){
                model.name = "白酒";
            }else if (i == 2){
                model.name = "葡萄酒";
            }else if (i == 3){
                model.name = "洋酒";
            }
            if(i != 0){
                model.goal = "133.3";
                model.acture = "133";
                model.reachRate = "56666";
            }else{
                model.name = "名称";
                model.goal = "商品库存数";
                model.acture = "商品采购数";
                model.reachRate = "商品库存金额";
            }
            list.add(model);
        }

        return  list;
    }
}
