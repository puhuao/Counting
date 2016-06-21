package com.wksc.counting.Basedata;

import com.wksc.counting.model.baseinfo.City;
import com.wksc.counting.model.baseinfo.County;
import com.wksc.counting.model.baseinfo.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puhua on 2016/6/21.
 *
 * @
 */
public class BaseDataUtil {
    public static List<Region> region = new ArrayList<>();

    public static List<County> counties = new ArrayList<>();

    public static List<County> citys = new ArrayList<>();

    public static List<County> regions = new ArrayList<>();

    public static List<County> regions(){
        List<County> regions = new ArrayList<>();
        for (Region reg:region){
            County county = new County();
            county.name = reg.name;
            county.code = reg.code;
            county.isCheck = reg.isCheck;
            regions.add(county);
        }
        return  regions;
    }

    public static List<County> citys(int regPosition){
        List<County> regions = new ArrayList<>();
        for (City reg:region.get(regPosition).city){
            County county = new County();
            county.name = reg.name;
            county.code = reg.code;
            county.isCheck = reg.isCheck;
            regions.add(county);
        }
        return  regions;
    }

    public static List<County> countys(int regPosition,int cityPosition){
        List<County> regions = new ArrayList<>();
        for (County reg:region.get(regPosition).city.get(cityPosition).county){
            regions.add(reg);
        }
        return  regions;
    }


    public static void updateDataStatus(int arg1,int arg2,int arg3,int isCheck){
        if (arg1!=-1&&arg2==-1&&arg3==-1){
            region.get(arg1).isCheck = isCheck;
            return;
        }
        if(arg1!=-1&&arg2!=-1&&arg3==-1){
            region.get(arg1).city.get(arg2).isCheck = isCheck;
            return;
        }

        if(arg1!=-1&&arg2!=-1&&arg3!=-1){
            region.get(arg1).city.get(arg2).county.get(arg3).isCheck = isCheck;
            return;
        }

    }

}
