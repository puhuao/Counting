package com.wksc.counting.widegit.unionPickListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.AreaCheckModel;
import com.wksc.counting.model.BaseInfo;

import java.util.List;

/**
 * Created by puhua on 2016/6/20.
 *
 * @
 */
public class PickListView extends ListView implements AdapterView.OnItemClickListener {
    CheckBoxListAdapter currentAdapter;
    private Boolean hasSuperLevel = false;
    private Boolean hasScendLevel = false;

    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    private BaseInfo baseInfo;

    public void setScendListView(ListView scendListView) {
        this.scendListView = scendListView;
    }

    public void setSuperListView(ListView superListView) {
        this.superListView = superListView;
    }

    public void setHasScendLevel(Boolean hasScendLevel) {
        this.hasScendLevel = hasScendLevel;
    }

    public void setHasSuperLevel(Boolean hasSuperLevel) {
        this.hasSuperLevel = hasSuperLevel;
    }

    private ListView superListView;
    private ListView scendListView;
    private CheckBoxListAdapter superAdaper;
    private CheckBoxListAdapter scentAdapter;
    private List<AreaCheckModel> superDataSet;
    private List<AreaCheckModel> scendDataSet;
    private List<AreaCheckModel> currentDataSet;

    public PickListView(Context con) {
        super(con);
    }

    public PickListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PickListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void initView(ListView superView, ListView scendView) {
        this.setOnItemClickListener(this);
        currentAdapter = (CheckBoxListAdapter) this.getAdapter();
        currentDataSet = currentAdapter.getList();
        if (superView != null) {
            superListView = superView;
            superAdaper = (CheckBoxListAdapter) superListView.getAdapter();
            superDataSet = superAdaper.getList();
            hasSuperLevel = true;
        } else {
            hasSuperLevel = false;
        }

        if (scendView != null) {
            scendListView = scendView;
            scentAdapter = (CheckBoxListAdapter) scendListView.getAdapter();
            scendDataSet = scentAdapter.getList();
            hasScendLevel = true;
        } else {
            hasScendLevel = false;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!hasSuperLevel && hasScendLevel) {//第一级菜单，只有全国一个选项
            currentAdapter.moveToNextStatus(position);
            int checkecNumber = currentAdapter.getCheckedNumber();
            if (checkecNumber == currentDataSet.size()) {
                scentAdapter.setAllCheck();
            } else if (checkecNumber == 0) {
                scentAdapter.setAllNormal();
            }
            currentAdapter.notifyDataSetChanged();
            scentAdapter.notifyDataSetChanged();
        } else if (hasSuperLevel && hasScendLevel) {
            currentAdapter.moveToNextStatus(position);
//            baseInfo.getRetObj().getRegions().get(position).
//            scentAdapter.setList();
            if (currentDataSet.get(position).isCheck == CheckBoxListAdapter.ALL){
                //下级列表重新加载当前position对应的列表
            }
            int checkecNumber = currentAdapter.getCheckedNumber();
                if (checkecNumber == currentDataSet.size()) {
                    if (currentDataSet.size()==1){
                        scentAdapter.setAllCheck();
                    }
                } else if (checkecNumber == 0) {
                    scentAdapter.setAllNormal();
                } else {
                    //super设置为半选
                    if (checkecNumber == 1) {
                        scentAdapter.setAllCheck();
                    }else{
                        //scend隐藏显示，并将选中列表
                    }
                }
        }

    }

}
