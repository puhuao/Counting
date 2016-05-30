package com.wksc.counting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class SaleSupplyModel {
    public String name;
    public String saleData;
    public String profitData;
    public String profitRate;
    public String saleRalive;
    public String saleProportion;
    public static List<SaleSupplyModel> getData(){
        List<SaleSupplyModel> list = new ArrayList<>();
        for (int i =0 ;i < 5;i ++){
            SaleSupplyModel model = new SaleSupplyModel();
            if (i ==1 ){
                model.name = "天猫";
            }else if (i == 2){
                model.name = "淘宝";
            }else if (i == 3){
                model.name = "京东";
            }
            if(i != 0){
                model.saleData = "133.3";
                model.profitData = "155.5";
                model.profitRate = "54%";
                model.saleRalive = "+12%";
                model.saleProportion = "-3%";
            }else{
                model.name = "渠道名";
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
