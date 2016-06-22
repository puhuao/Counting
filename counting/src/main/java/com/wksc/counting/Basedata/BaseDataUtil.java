package com.wksc.counting.Basedata;

import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.model.baseinfo.Channel;
import com.wksc.counting.model.baseinfo.CoreItem;
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

    public static List<BaseWithCheckBean> regions() {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        for (BaseWithCheckBean reg : region) {
            BaseWithCheckBean county = new County();
            county.name = reg.name;
            county.code = reg.code;
            county.isCheck = reg.isCheck;
            regions.add(county);
        }
        return regions;
    }

    public static List<BaseWithCheckBean> citys(int regPosition) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size()>0)
        for (BaseWithCheckBean reg : region.get(regPosition).city) {
            BaseWithCheckBean county = new County();
            county.name = reg.name;
            county.code = reg.code;
            county.isCheck = reg.isCheck;
            regions.add(county);
        }
        return regions;
    }

    public static List<BaseWithCheckBean> countys(int regPosition, int cityPosition) {
        List<BaseWithCheckBean> regions = new ArrayList<>();
        if (region.size()>0&&region.get(regPosition).city.size()>0)
        for (BaseWithCheckBean reg : region.get(regPosition).city.get(cityPosition).county) {
            regions.add(reg);
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

    public static List<CoreItem> coreItems = new ArrayList<>();

    public static List<Channel> channels = new ArrayList<>();

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
        if (channels.size()>0)
        if (channels.get(pos).MCU != null)
            for (BaseWithCheckBean channel :
                    channels.get(pos).MCU) {
                list.add(channel);
            }
        return list;
    }

}
