package com.wksc.counting.widegit.unionPickListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wksc.counting.Basedata.BaseDataUtil2;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.BaseInfo;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.widegit.NestedListView;

import java.util.List;

/**
 * Created by puhua on 2016/6/20.
 *
 * @
 */
public class PickListView2 extends NestedListView implements AdapterView.OnItemClickListener {
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

    private PickListView2 superListView;
    private PickListView2 scendListView;
    private CheckBoxListAdapter superAdaper;
    private CheckBoxListAdapter scentAdapter;
    private List<BaseWithCheckBean> superDataSet;
    private List<BaseWithCheckBean> scendDataSet;
    private List<BaseWithCheckBean> currentDataSet;
    private int superPosition;

    public void setScendPosition(int scendPosition) {
        this.scendPosition = scendPosition;
    }

    public void setSuperPosition(int superPosition) {
        this.superPosition = superPosition;
    }

    private int scendPosition;

    public int lastPos;

    public PickListView2(Context con) {
        super(con);
    }

    public PickListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PickListView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private void getCurrentData(){
        currentAdapter = (CheckBoxListAdapter) this.getAdapter();
        currentDataSet = currentAdapter.getList();
    }

    public void initView(ListView superView, ListView scendView) {
        this.setOnItemClickListener(this);
        currentAdapter = (CheckBoxListAdapter) this.getAdapter();
        currentDataSet = currentAdapter.getList();
        if (superView != null) {
            superListView = (PickListView2) superView;
            superAdaper = (CheckBoxListAdapter) superListView.getAdapter();
            superDataSet = superAdaper.getList();
            hasSuperLevel = true;
        } else {
            hasSuperLevel = false;
        }

        if (scendView != null) {
            scendListView = (PickListView2) scendView;
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
            BaseDataUtil2.lastAnaRagionPos = position;
            getCurrentData();
            BaseDataUtil2.updateDataStatus(position,-1,-1,
                    currentAdapter.moveToNextStatus(position));

            //////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber();

            if (checkecNumber == 1){
                scendListView.superPosition = currentAdapter.oneCheckPosition;
                scendDataSet = BaseDataUtil2.citys(currentAdapter.oneCheckPosition);
                scentAdapter.setList(scendDataSet);
                scendListView.update(currentAdapter.oneCheckPosition,0);
            }else{
                scendListView.superPosition = position;
                scendDataSet = BaseDataUtil2.citys(position);
                scentAdapter.setList(scendDataSet);
                scendListView.update(position,0);
            }

            ////////////////////////////////////////////////////////////
            if (checkecNumber == currentDataSet.size()) {
                if (checkecNumber == 1&& currentDataSet.size()==1){
                    scendListView.show();
                }else{
                    scendListView.hide();
                }
            } else if (checkecNumber == 0) {
//                scentAdapter.setAllNormal();
//                scendListView.hide();
            }else if(checkecNumber == 1){
                scendListView.show();
            }else if (checkecNumber>1){
                scendListView.hide();
                BaseDataUtil2.citys();
                BaseDataUtil2.countys();
            }
            currentAdapter.notifyDataSetChanged();
            scentAdapter.notifyDataSetChanged();
        } else if (hasSuperLevel && hasScendLevel) {

            getCurrentData();
            scendListView.superPosition = superPosition;
            BaseDataUtil2.updateDataStatus(superPosition,position,-1,
                    currentAdapter.moveToNextStatus(position));
            //下级列表重新加载当前position对应的列表
            //判断本级当前位置是否选中，选中将下级

            //////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber();

            if (checkecNumber == 1){
                BaseDataUtil2.lastAnaCityPos = position;
                scendListView.scendPosition = currentAdapter.oneCheckPosition;
                scendDataSet = BaseDataUtil2.countys(superPosition,currentAdapter.oneCheckPosition);
                scentAdapter.setList(scendDataSet);
//                scendListView.update(currentAdapter.oneCheckPosition,0);
            }else{
                BaseDataUtil2.lastAnaCityPos = 0;
                scendListView.scendPosition = position;
                scendDataSet = BaseDataUtil2.countys(superPosition,position);
                scentAdapter.setList(scendDataSet);
//                scendListView.update(superPosition,0);
            }
            ///////////////////////////////////////////////////////////////
            if (checkecNumber == currentDataSet.size()) {
                if (checkecNumber == 1&& currentDataSet.size()==1){
                    scendListView.show();
                }else{
                    scendListView.hide();
                }
            } else if (checkecNumber == 0) {
//                scentAdapter.setAllNormal();
//                scendListView.hide();
            }else if(checkecNumber == 1){
                scendListView.show();
            }else if (checkecNumber>1){
                scendListView.hide();
                BaseDataUtil2.countys();
            }

            if (checkecNumber<currentDataSet.size()&&checkecNumber!=0){
                superListView.changeSupperStatus(superPosition,CheckBoxListAdapter.HALF);
            }else if (checkecNumber==currentDataSet.size()){
                superListView.changeSupperStatus(superPosition,CheckBoxListAdapter.ALL);
            }else if(checkecNumber==0){
                superListView.changeSupperStatus(scendPosition,CheckBoxListAdapter.NORMAL);
            }
            currentAdapter.notifyDataSetChanged();
            scentAdapter.notifyDataSetChanged();
            superAdaper.notifyDataSetChanged();
        }else if (hasSuperLevel && !hasScendLevel){
            BaseDataUtil2.lastAnaCountyPos = position;
            getCurrentData();
            BaseDataUtil2.updateDataStatus(superPosition,scendPosition,position,
                    currentAdapter.moveToNextStatus(position));
            /////////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber();
            if (checkecNumber == currentDataSet.size()) {
                //super设置为全选

                }
            if (checkecNumber<currentDataSet.size()&&checkecNumber!=0){
                superListView.changeSupperStatus(scendPosition,CheckBoxListAdapter.HALF);
            }else if (checkecNumber==currentDataSet.size()){
                superListView.changeSupperStatus(scendPosition,CheckBoxListAdapter.ALL);
            }else if(checkecNumber==0){
                superListView.changeSupperStatus(scendPosition,CheckBoxListAdapter.NORMAL);
            }
            currentAdapter.notifyDataSetChanged();
            superAdaper.notifyDataSetChanged();
        }

        /////////////////////////////////////////////////////////

    }

    private void changeSupperStatus(int positon,int status){
        this.currentDataSet.get(positon).isCheck = status;
    }

    public void update(int arg1,int arg2){
        this.invalidate();
        if (hasSuperLevel&&hasScendLevel){
            scendDataSet.clear();
            scendDataSet.addAll(BaseDataUtil2.countys(arg1,arg2));
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
        if (hasScendLevel){
            if (currentAdapter.getCheckedNumber()==1||currentAdapter.getCheckedNumber()==0){
                scendListView.show();
            }
        }

    }
}
