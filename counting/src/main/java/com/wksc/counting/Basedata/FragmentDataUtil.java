package com.wksc.counting.Basedata;

import com.wksc.counting.model.CoreIndexListModel;
import com.wksc.counting.model.SaleAnaModel.SaleAnaModel;
import com.wksc.counting.model.coreDetail.CoreDetail;
import com.wksc.counting.model.goodsSaleAnaModle.GoodSaleModle;
import com.wksc.counting.model.platFormModel.PlatFormModel;
import com.wksc.counting.model.saleChannelModel.SaleChannelModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6.
 */
public class FragmentDataUtil {
    public static List<CoreIndexListModel> coreIndexListModels = new ArrayList<>();
    public static GoodSaleModle goodSaleModle;
    public static PlatFormModel platFormModel;
    public static SaleChannelModel saleChannelModel;
    public static SaleAnaModel saleAnaModel;
    public static GoodSaleModle saveModel;
    public static CoreDetail CoreDetail1;
    public static CoreDetail CoreDetail2;
    public static CoreDetail CoreDetail3;
    public static CoreDetail CoreDetail4;
    public static CoreDetail CoreDetail5;
    public static CoreDetail CoreDetail6;
    public static CoreDetail CoreDetail7;

    public static List<CoreDetail> coreDetails = new ArrayList<>();
    public static void inticores(){
        coreDetails.add(CoreDetail1);
    }

    public static HashMap<String,CoreDetail> map = new HashMap<>();
    public static HashMap<String,CoreDetail> map1 = new HashMap<>();
    public static void clearData(){
        goodSaleModle = null;
        platFormModel = null;
        saleChannelModel = null;
        saleAnaModel = null;
        saveModel = null;
         CoreDetail1 = null;
        coreIndexListModels.clear();
    }

    public static void clearCoreDetailData(HashMap<String,CoreDetail> map){
        for (Map.Entry<String, CoreDetail> entry : map.entrySet()) {
            entry.getKey();
            CoreDetail detail = entry.getValue();
            entry.setValue(null);
        }
    }

}
