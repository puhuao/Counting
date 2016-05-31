package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class GoodsAnalysisModel {
    public String name;
    public String saleData;
    public String profitData;
    public String profitRate;
    public String saleRalive;
    public String saleProportion;
    public static List<GoodsAnalysisModel> getData(){
        List<GoodsAnalysisModel> list = new ArrayList<>();
        for (int i =0 ;i < 5;i ++){
            GoodsAnalysisModel model = new GoodsAnalysisModel();
            if (i ==1 ){
                model.name = "白酒";
            }else if (i == 2){
                model.name = "葡萄酒";
            }else if (i == 3){
                model.name = "洋酒";
            }else if (i == 4){
                model.name = "啤酒";
            }
            if(i != 0){
                model.saleData = "133.3";
                model.profitData = "155.5";
                model.profitRate = "54%";
                model.saleRalive = "+12%";
                model.saleProportion = "-3%";
            }else{
                model.name = "名称";
                model.saleData = "销售额";
                model.profitData = "毛利额";
                model.profitRate = "毛利率";
                model.saleRalive = "销售环比";
                model.saleProportion = "销售占比";
            }
            list.add(model);
        }

        return  list;
    }
}
