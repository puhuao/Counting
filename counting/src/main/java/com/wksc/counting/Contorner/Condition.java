package com.wksc.counting.Contorner;

import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.model.baseinfo.City;
import com.wksc.counting.model.baseinfo.GoodsClassFirst;
import com.wksc.counting.model.baseinfo.Region;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Condition implements Serializable{
/*用以记录选择的条件，方便条件空间初始化*/
    public Condition(List<Region> list){
        area = list;
    }
    public List<Region> area;
    public List<BaseWithCheckBean> regions;
    public List<BaseWithCheckBean> citys;
    public List<BaseWithCheckBean> countys;
    public Boolean hideCity = false;
    public Boolean hideCounty = false;
    public  int scendPositon;
    public  int superPosition;
    public  StringBuilder sbRegion= new StringBuilder();
    public  StringBuilder sbCity= new StringBuilder();
    public  StringBuilder sbCounty= new StringBuilder();
    public  StringBuilder sbRegionCode= new StringBuilder();
    public  StringBuilder sbCityCode= new StringBuilder();
    public  StringBuilder sbCountyCode= new StringBuilder();
    public  StringBuilder sbGoodsClassFirst= new StringBuilder();
    public  StringBuilder sbGoodsClassFirstCode = new StringBuilder();
    public  List<GoodsClassFirst> goodsClassFirst = new ArrayList<>();
    public  StringBuilder sbGoodsClassScend = new StringBuilder();
    public  StringBuilder sbGoodsClassScendCode = new StringBuilder();

    public void init(){
        if (regions == null){
            regions = regions();
        }

        if (citys == null){
            citys = citys(0);
        }

        if (countys == null){
            countys = countys(0,0);
        }
    }

    public List<BaseWithCheckBean> regions() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : area) {
            regions.add(reg);
        }
        return regions;
    }

    public  List<BaseWithCheckBean> citys(int regPosition) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (area.size() > 0)
            for (BaseWithCheckBean reg : area.get(regPosition).city) {
                regions.add(reg);
            }
        return regions;
    }

    public  List<BaseWithCheckBean> countys(int regPosition, int cityPosition) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (area.size() > 0 && area.get(regPosition).city.size() > 0)
            for (BaseWithCheckBean reg : area.get(regPosition).city.get(cityPosition).county) {
                regions.add(reg);
            }
        return regions;
    }

    public  List<BaseWithCheckBean> checkedRagions(){
        sbRegion.delete(0,sbRegion.length());
        sbRegionCode.delete(0,sbRegionCode.length());
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : area) {
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

    public  List<BaseWithCheckBean> checkedCitys() {
        sbCity.delete(0,sbCity.length());
        sbCityCode.delete(0,sbCityCode.length());
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (area.size() > 0)
            for (Region bean :
                    area) {
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

    public  List<BaseWithCheckBean> checkedCountys() {
        sbCounty.delete(0,sbCounty.length());
        sbCountyCode.delete(0,sbCounty.length());
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (area.size() > 0)
            for (Region bean :
                    area) {
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

    public  void updateDataStatus(int arg1, int arg2, int arg3, int isCheck) {
        if (arg1 != -1 && arg2 == -1 && arg3 == -1) {
            area.get(arg1).isCheck = isCheck;
            return;
        }
        if (arg1 != -1 && arg2 != -1 && arg3 == -1) {
            area.get(arg1).city.get(arg2).isCheck = isCheck;
            return;
        }

        if (arg1 != -1 && arg2 != -1 && arg3 != -1) {
            area.get(arg1).city.get(arg2).county.get(arg3).isCheck = isCheck;
            return;
        }

    }

    public  List<BaseWithCheckBean> citys() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (area.size() > 0)
            for (Region bean :
                    area) {
                for (BaseWithCheckBean reg : bean.city) {
                    reg.isCheck = CheckBoxListAdapter.NORMAL;
                    regions.add(reg);
                }
            }

        return regions;
    }

    public  List<BaseWithCheckBean> countys() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (area.size() > 0)
            for (Region bean :
                    area) {
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

    public  List<BaseWithCheckBean> goodsClassFirst() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : goodsClassFirst) {
            regions.add(reg);
        }
        return regions;
    }
    public  List<BaseWithCheckBean> checkGoodsClassFirst() {
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

    public  List<BaseWithCheckBean> goodsClassScend(int position) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (goodsClassFirst.size() > 0)
            for (BaseWithCheckBean reg : goodsClassFirst.get(position).classX) {
                regions.add(reg);
            }
        return regions;
    }

    public  List<BaseWithCheckBean> checkGoodsClassScend() {
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

    public  void updateGoodsStatus(int arg1, int arg2, int isCheck) {
        if (arg1 != -1 && arg2 == -1) {
            goodsClassFirst.get(arg1).isCheck = isCheck;
            return;
        }
        if (arg1 != -1 && arg2 != -1) {
            goodsClassFirst.get(arg1).classX.get(arg2).isCheck = isCheck;
            return;
        }

    }

    public void regionsNormal() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : area) {
            reg.isCheck = CheckBoxListAdapter.NORMAL;
        }

    }
}
