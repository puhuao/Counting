package com.wksc.counting.model.coreDetail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by puhua on 2016/6/22.
 *
 * @
 */
public class CoreDetail implements Serializable {
    public List<TabelValueModel> tableData;
    public BarChartModel CoreChart1;
    public BarChartModel CoreChart2;
    public String tableTitle;
    public String tableTitleDesc;
}
