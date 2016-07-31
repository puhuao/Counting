package com.wksc.counting.Basedata;

import com.wksc.counting.activity.SearchActivity;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.model.baseinfo.Channel;
import com.wksc.counting.model.baseinfo.City;
import com.wksc.counting.model.baseinfo.CoreItem;
import com.wksc.counting.model.baseinfo.GoodsClassFirst;
import com.wksc.counting.model.baseinfo.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/6/21.
 *
 * @
 */
public class BaseDataUtil {

//    public static int lastCoreRagionPos=0;
//    public static int lastCoreCityPos=0;
//    public static int lastCoreCountyPos=0;
    public static List<BaseWithCheckBean> stores;
    public static List<BaseWithCheckBean> citySet;//用以记录地区选择消失后的位置
    public static List<BaseWithCheckBean> countySet;
    public static List<Region> region;
    public static List<Region> mainRegion = new ArrayList<>();
    public static List<Region> main1Region = new ArrayList<>();
    public static List<Region> coreRegion = new ArrayList<>();
    public static StringBuilder sbRegion;
    public static StringBuilder sbCity= new StringBuilder();
    public static StringBuilder sbCounty= new StringBuilder();
    public static StringBuilder sbRegionCode= new StringBuilder();
    public static StringBuilder sbCityCode= new StringBuilder();
    public static StringBuilder sbCountyCode= new StringBuilder();
    public static StringBuilder sbRegionCore= new StringBuilder();
    public static StringBuilder sbCityCore= new StringBuilder();
    public static StringBuilder sbCountyCore= new StringBuilder();
    public static StringBuilder sbRegionCodeCore= new StringBuilder();
    public static StringBuilder sbCityCodeCore= new StringBuilder();
    public static StringBuilder sbCountyCodeCore= new StringBuilder();
    public static StringBuilder sbRegionMain= new StringBuilder();
    public static StringBuilder sbCityMain= new StringBuilder();
    public static StringBuilder sbCountyMain= new StringBuilder();
    public static StringBuilder sbRegionCodeMain= new StringBuilder();
    public static StringBuilder sbCityCodeMain= new StringBuilder();
    public static StringBuilder sbCountyCodeMain= new StringBuilder();
    public static StringBuilder sbRegionMain1= new StringBuilder();
    public static StringBuilder sbCityMain1= new StringBuilder();
    public static StringBuilder sbCountyMain1= new StringBuilder();
    public static StringBuilder sbRegionCodeMain1= new StringBuilder();
    public static StringBuilder sbCityCodeMain1= new StringBuilder();
    public static StringBuilder sbCountyCodeMain1= new StringBuilder();
    public static List<GoodsClassFirst> goodsClassFirst = new ArrayList<>();

    public static List<CoreItem> coreItems = new ArrayList<>();

    public static List<Channel> channels = new ArrayList<>();
    public static boolean hideCounty = false;
    public static boolean hideCity = false;
    public static int scendPositon;
    public static int superPosition;

    public static int coreScendPosition;
    public static int coreSuperPostion;
    public static int mainScendPosition;
    public static int mainSuperPosition;
    public static int main1ScendPosition;
    public static int main1SuperPosition;
    public static void restorePosition(int flag){
        if (flag ==0){
            coreScendPosition = scendPositon;
            coreSuperPostion = superPosition;
        }else if(flag == 1){
            mainScendPosition = scendPositon;
            mainSuperPosition = superPosition;
        }else if(flag == 2){
            main1ScendPosition = scendPositon;
            main1SuperPosition = superPosition;
        }
    }

    public static void setListPosition(int flag){
        if (flag ==0){
             scendPositon =coreScendPosition;
             superPosition =coreSuperPostion;
        }else if(flag == 1){
             scendPositon =mainScendPosition;
            superPosition = mainSuperPosition ;
        }else if(flag == 2){
            scendPositon =main1ScendPosition;
            superPosition = main1SuperPosition ;
        }
    }


    public static List<BaseWithCheckBean> coreItems(){
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : coreItems) {
            regions.add(reg);
        }
        return regions;
    }

    public static void setFlag(int flag){
        if (flag ==0){
            region = coreRegion;
            sbRegion = sbRegionCore;
            sbCity = sbCityCore;
            sbCounty = sbCountyCore;
            sbRegionCode = sbRegionCodeCore;
            sbCityCode = sbCityCodeCore;
            sbCountyCode = sbCountyCodeCore;
        }else if(flag == 1){
            region = mainRegion;
            sbRegion = sbRegionMain;
            sbCity = sbCityMain;
            sbCounty = sbCountyMain;
            sbRegionCode = sbRegionCodeMain;
            sbCityCode = sbCityCodeMain;
            sbCountyCode = sbCountyCodeMain;
        }else if(flag == 2){
            region = main1Region;
            sbRegion = sbRegionMain1;
            sbCity = sbCityMain1;
            sbCounty = sbCountyMain1;
            sbRegionCode = sbRegionCodeMain1;
            sbCityCode = sbCityCodeMain1;
            sbCountyCode = sbCountyCodeMain1;
        }
    }


    public static void clearData(){
        coreRegion.clear();
        mainRegion.clear();
        main1Region.clear();
        goodsClassFirst.clear();
        coreItems.clear();
        channels.clear();
        SearchActivity.historys.clear();
        superPosition = 0;
        scendPositon = 0;
    }
    public static StringBuilder sbGoodsClassFirst= new StringBuilder();
    public static StringBuilder sbGoodsClassFirstCode = new StringBuilder();

    public static List<BaseWithCheckBean> goodsClassFirst() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : goodsClassFirst) {
            regions.add(reg);
        }
        return regions;
    }
    public static List<BaseWithCheckBean> checkGoodsClassFirst() {
        if (sbGoodsClassFirst.length()>0){
            sbGoodsClassFirst.delete(0,sbGoodsClassFirst.length());
        }
        if (sbGoodsClassFirstCode.length()>0){
            sbGoodsClassFirstCode.delete(0,sbGoodsClassFirstCode.length());
        }
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : goodsClassFirst) {
            if (reg.isCheck == CheckBoxListAdapter.ALL){
                regions.add(reg);
                sbGoodsClassFirst.append(reg.name).append(",");
                sbGoodsClassFirstCode.append(reg.code).append(",");
            }
        }
        if (sbGoodsClassFirst.length()>0){
            sbGoodsClassFirst.deleteCharAt(sbGoodsClassFirst.length()-1);
        }
        if (sbGoodsClassFirstCode.length()>0){
            sbGoodsClassFirstCode.deleteCharAt(sbGoodsClassFirstCode.length()-1);
        }
        return regions;
    }

    public static List<BaseWithCheckBean> goodsClassScend(int position) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (goodsClassFirst.size() > 0)
            for (BaseWithCheckBean reg : goodsClassFirst.get(position).classX) {
                regions.add(reg);
            }
        return regions;
    }
public static StringBuilder sbGoodsClassScend = new StringBuilder();
    public static StringBuilder sbGoodsClassScendCode = new StringBuilder();

    public static List<BaseWithCheckBean> checkGoodsClassScend() {
        if (sbGoodsClassScend.length()>0){
            sbGoodsClassScend.delete(0,sbGoodsClassScend.length());
        }
        if (sbGoodsClassScendCode.length()>0){
            sbGoodsClassScendCode.delete(0,sbGoodsClassScendCode.length());
        }
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (goodsClassFirst.size() > 0)
            for (GoodsClassFirst reg : goodsClassFirst) {
                for (BaseWithCheckBean bean:
                     reg.classX) {
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        regions.add(bean);
                        sbGoodsClassScend.append(bean.name).append(",");
                        sbGoodsClassScendCode.append(bean.code).append(",");
                    }
                }

            }

        if (sbGoodsClassScend.length()>0){
            sbGoodsClassScend.deleteCharAt(sbGoodsClassScend.length()-1);
        }
        if (sbGoodsClassScendCode.length()>0){
            sbGoodsClassScendCode.deleteCharAt(sbGoodsClassScendCode.length()-1);
        }
        return regions;
    }


    public static List<BaseWithCheckBean> regions() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : region) {
            regions.add(reg);
        }
        return regions;
    }

    public static List<BaseWithCheckBean> regionsNormal() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : region) {
            reg.isCheck = CheckBoxListAdapter.NORMAL;
        }
        return regions;
    }

    public static List<BaseWithCheckBean> checkedRagions(){
        sbRegion.delete(0,sbRegion.length());
        sbRegionCode.delete(0,sbRegionCode.length());
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : region) {
            if (reg.isCheck == CheckBoxListAdapter.ALL){
                regions.add(reg);
                sbRegion.append(reg.name).append(",");
                sbRegionCode.append(reg.code).append(",");
            }
        }
        if (sbRegion.length()>0){
            sbRegion.deleteCharAt(sbRegion.length()-1);
        }
        if (sbRegionCode.length()>0){
            sbRegionCode.deleteCharAt(sbRegionCode.length()-1);
        }
        return regions;
    }

    public static List<BaseWithCheckBean> citys(int regPosition) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size() > 0)
            for (BaseWithCheckBean reg : region.get(regPosition).city) {
                regions.add(reg);
            }
        return regions;
    }

    public static List<BaseWithCheckBean> citys() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size() > 0)
            for (Region bean :
                    region) {
                for (BaseWithCheckBean reg : bean.city) {
                    reg.isCheck = CheckBoxListAdapter.NORMAL;
                    regions.add(reg);
                }
            }

        return regions;
    }

    public static List<BaseWithCheckBean> checkedCitys() {
        sbCity.delete(0,sbCity.length());
        sbCityCode.delete(0,sbCityCode.length());
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size() > 0)
            for (Region bean :
                    region) {
                for (BaseWithCheckBean reg : bean.city) {
                    if (reg.isCheck == CheckBoxListAdapter.ALL) {
                        regions.add(reg);
                        sbCity.append(reg.name).append(",");
                        sbCityCode.append(reg.code).append(",");
                    }
                }

            }
        if (sbCity.length() > 0) {
            sbCity.deleteCharAt(sbCity.length()-1);
        }
        if (sbCityCode.length()>0){
            sbCityCode.deleteCharAt(sbCityCode.length()-1);
        }
                return regions;
            }

    public static List<BaseWithCheckBean> countys(int regPosition, int cityPosition) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size() > 0 && region.get(regPosition).city.size() > 0)
            for (BaseWithCheckBean reg : region.get(regPosition).city.get(cityPosition).county) {
                regions.add(reg);
            }
        return regions;
    }

    public static List<BaseWithCheckBean> countys() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size() > 0)
            for (Region bean :
                    region) {
                if (bean.city.size() > 0)
                    for (City city : bean.city) {
                        if (city.county.size() > 0)
                            for (BaseWithCheckBean reg : city.county){
                                reg.isCheck = CheckBoxListAdapter.NORMAL;
                                regions.add(reg);
                            }
                    }
            }
        return regions;
    }

    public static List<BaseWithCheckBean> checkedCountys() {
        sbCounty.delete(0,sbCounty.length());
        sbCountyCode.delete(0,sbCounty.length());
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size() > 0)
            for (Region bean :
                    region) {
                if (bean.city.size() > 0)
                    for (City city : bean.city) {
                        if (city.county.size() > 0)
                            for (BaseWithCheckBean reg : city.county){
                                if (reg.isCheck == CheckBoxListAdapter.ALL){
                                    regions.add(reg);
                                    sbCounty.append(reg.name).append(",");
                                    sbCountyCode.append(reg.code).append(",");
                                }

                            }
                    }

            }
        if (sbCounty.length() > 0) {
            sbCounty.deleteCharAt(sbCounty.length()-1);
        }
        if (sbCountyCode.length() > 0) {
            sbCountyCode.deleteCharAt(sbCountyCode.length()-1);
        }
        return regions;
    }


    public static void updateDataStatus(int arg1, int arg2, int arg3, int isCheck) {
        if (arg1 != -1 && arg2 == -1 && arg3 == -1) {
            region.get(arg1).isCheck = isCheck;
            return;
        }
        if (arg1 != -1 && arg2 != -1 && arg3 == -1) {
            region.get(arg1).city.get(arg2).isCheck = isCheck;
            return;
        }

        if (arg1 != -1 && arg2 != -1 && arg3 != -1) {
            region.get(arg1).city.get(arg2).county.get(arg3).isCheck = isCheck;
            return;
        }

    }

    public static void updateGoodsStatus(int arg1, int arg2, int isCheck) {
        if (arg1 != -1 && arg2 == -1) {
            goodsClassFirst.get(arg1).isCheck = isCheck;
            return;
        }
        if (arg1 != -1 && arg2 != -1) {
            goodsClassFirst.get(arg1).classX.get(arg2).isCheck = isCheck;
            return;
        }

    }

    public static void updateCoreItemsStatus(int arg1, int isCheck) {
        if (arg1 != -1) {
            coreItems.get(arg1).isCheck = isCheck;
            return;
        }
    }


    public static List<BaseWithCheckBean> channels() {
        List<BaseWithCheckBean> list = new ArrayList<>();
        for (BaseWithCheckBean channel :
                channels) {
            list.add(channel);
        }
        return list;
    }

    public static List<BaseWithCheckBean> platforms(int pos) {
        List<BaseWithCheckBean> list = new ArrayList<>();
        if (channels.size() > 0)
            if (channels.get(pos).MCU != null)
                for (BaseWithCheckBean channel :
                        channels.get(pos).MCU) {
                    list.add(channel);
                }
        return list;
    }

    public static List<BaseWithCheckBean> resetPlatforms(int pos) {
        List<BaseWithCheckBean> list = new ArrayList<>();
        if (channels.size() > 0)
            if (channels.get(pos).MCU != null)
                for (BaseWithCheckBean channel :
                        channels.get(pos).MCU) {
                    list.add(channel);
                    channel.isCheck = CheckBoxListAdapter.NORMAL;
                }
        return list;
    }

}
