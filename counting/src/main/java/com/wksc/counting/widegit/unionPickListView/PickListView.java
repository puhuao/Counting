package com.wksc.counting.widegit.unionPickListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.BaseInfo;
import com.wksc.counting.model.baseinfo.County;

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

    private PickListView superListView;
    private PickListView scendListView;
    private CheckBoxListAdapter superAdaper;
    private CheckBoxListAdapter scentAdapter;
    private List<County> superDataSet;
    private List<County> scendDataSet;
    private List<County> currentDataSet;
    private int superPosition;

    public void setScendPosition(int scendPosition) {
        this.scendPosition = scendPosition;
    }

    public void setSuperPosition(int superPosition) {
        this.superPosition = superPosition;
    }

    private int scendPosition;

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
            superListView = (PickListView) superView;
            superAdaper = (CheckBoxListAdapter) superListView.getAdapter();
            superDataSet = superAdaper.getList();
            hasSuperLevel = true;
        } else {
            hasSuperLevel = false;
        }

        if (scendView != null) {
            scendListView = (PickListView) scendView;
            scentAdapter = (CheckBoxListAdapter) scendListView.getAdapter();
            scendDataSet = scentAdapter.getList();
            hasScendLevel = true;
        } else {
            hasScendLevel = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!hasSuperLevel && hasScendLevel) {
            scendListView.superPosition = position;
            BaseDataUtil.updateDataStatus(position,-1,-1,
                    currentAdapter.moveToNextStatus(position));
            scendDataSet = BaseDataUtil.citys(position);
            scentAdapter.setList(scendDataSet);
            scendListView.update(position,0);
            //////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber();
            if (checkecNumber == 1&& currentDataSet.size()==1){
                scendListView.show();
//                scentAdapter.setAllCheck();
            }
            if (checkecNumber == currentDataSet.size()) {
                scendListView.hide();
//                scentAdapter.setAllNormal();
            } else if (checkecNumber == 0) {
//                scentAdapter.setAllNormal();
                scendListView.hide();
            }
            currentAdapter.notifyDataSetChanged();
            scentAdapter.notifyDataSetChanged();
        } else if (hasSuperLevel && hasScendLevel) {
            scendListView.scendPosition = position;
            scendListView.superPosition = superPosition;
            BaseDataUtil.updateDataStatus(superPosition,position,-1,
                    currentAdapter.moveToNextStatus(position));
            //下级列表重新加载当前position对应的列表
            //判断本级当前位置是否选中，选中将下级
            scendDataSet = BaseDataUtil.countys(superPosition,position);
            scentAdapter.setList(scendDataSet);
            //////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber();
                if (checkecNumber == currentDataSet.size()) {
                    //super设置为全选
//
//                    if (currentDataSet.size()==1){
//                        scentAdapter.setAllCheck();
//                    }
                } else if (checkecNumber == 0) {
//                    scentAdapter.setAllNormal();
                } else {
                    //super设置为半选
                    if (checkecNumber == 1) {//如果选中数为1清除其它下级菜单的所有选中项，
                        //并把当前位置的下级菜单全选
//                        scentAdapter.setAllCheck();
                    }else{
                        //当前位置的下级菜单不做任何处理，本级菜单重刷
                    }
                }
            currentAdapter.notifyDataSetChanged();
            scentAdapter.notifyDataSetChanged();
            superAdaper.notifyDataSetChanged();
        }else if (hasSuperLevel && !hasScendLevel){
            BaseDataUtil.updateDataStatus(superPosition,scendPosition,position,
                    currentAdapter.moveToNextStatus(position));
            /////////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber();
            if (checkecNumber == currentDataSet.size()) {
                //super设置为全选
                }
            currentAdapter.notifyDataSetChanged();
            superAdaper.notifyDataSetChanged();
        }

        /////////////////////////////////////////////////////////
        
    }

    public void update(int arg1,int arg2){
        this.invalidate();
        if (hasSuperLevel&&hasScendLevel){
            scendDataSet.clear();
            scendDataSet.addAll(BaseDataUtil.countys(arg1,arg2));
            scentAdapter.notifyDataSetChanged();
        }
    }

    public void hide(){
        this.setVisibility(INVISIBLE);
        if (hasScendLevel){
            scendListView.hide();
        }
    }

    public void show(){
        this.setVisibility(VISIBLE);

    }
}
