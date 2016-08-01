package com.wksc.counting.widegit.unionPickListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wksc.counting.Basedata.BaseDataUtil;
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
public class PickListView extends NestedListView implements AdapterView.OnItemClickListener {
    CheckBoxListAdapter currentAdapter;
    private Boolean hasSuperLevel = false;
    private Boolean hasScendLevel = false;
    private LinearLayout nextLayout;

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
    private List<BaseWithCheckBean> superDataSet;
    private List<BaseWithCheckBean> scendDataSet;
    private List<BaseWithCheckBean> currentDataSet;
//    public  int superPosition;
//
//    public  int scendPosition;

    public int lastPos;

    public PickListView(Context con) {
        super(con);
    }

    public PickListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PickListView(Context context, AttributeSet attrs, int defStyle) {
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
            getCurrentData();
            BaseDataUtil.updateDataStatus(position,-1,-1,
                    currentAdapter.moveToNextStatus(position));

            //////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber(position);

            if (checkecNumber == 1){
//                scendListView.superPosition = currentAdapter.oneCheckPosition;
                BaseDataUtil.superPosition = currentAdapter.oneCheckPosition;
                scendDataSet = BaseDataUtil.citys(currentAdapter.oneCheckPosition);
                scentAdapter.setList(scendDataSet);
                scendListView.update(currentAdapter.oneCheckPosition,0);
            }else{
//                scendListView.superPosition = position;
                BaseDataUtil.superPosition = position;
                scendDataSet = BaseDataUtil.citys(position);
                scentAdapter.setList(scendDataSet);
                scendListView.update(position,0);
            }

            ////////////////////////////////////////////////////////////
            if (checkecNumber == currentAdapter.getList().size()) {
                if (checkecNumber == 1&& currentAdapter.getList().size()==1){
                    scendListView.show();
                }else{
                    BaseDataUtil.hideCity = true;
                    scendListView.hide();
                }
            } else{
                if (checkecNumber == 0) {
                }else if(checkecNumber == 1){
                    scendListView.show();
                }else if (checkecNumber>1){
                    BaseDataUtil.hideCity = true;
                    scendListView.hide();
                    BaseDataUtil.citys();
                    BaseDataUtil.countys();
                }
            }
            currentAdapter.notifyDataSetChanged();
            scentAdapter.notifyDataSetChanged();
        } else if (hasSuperLevel && hasScendLevel) {

            getCurrentData();
//            scendListView.superPosition = superPosition;
            BaseDataUtil.updateDataStatus(BaseDataUtil.superPosition,position,-1,
                    currentAdapter.moveToNextStatus(position));
            //下级列表重新加载当前position对应的列表
            //判断本级当前位置是否选中，选中将下级

            //////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber(position);

            if (checkecNumber == 1){
//                scendListView.scendPosition = currentAdapter.oneCheckPosition;
                BaseDataUtil.scendPositon = currentAdapter.oneCheckPosition;
                scendDataSet = BaseDataUtil.countys(BaseDataUtil.superPosition,currentAdapter.oneCheckPosition);
                scentAdapter.setList(scendDataSet);
//                scendListView.update(currentAdapter.oneCheckPosition,0);
            }else{
//                scendListView.scendPosition = position;
                BaseDataUtil.scendPositon =position;
                scendDataSet = BaseDataUtil.countys(BaseDataUtil.superPosition,position);
                scentAdapter.setList(scendDataSet);
//                scendListView.update(superPosition,0);
            }
            ///////////////////////////////////////////////////////////////
            if (checkecNumber == currentAdapter.getList().size()) {
                if (checkecNumber == 1&& currentAdapter.getList().size()==1){
                    scendListView.show();
                }else{
                    BaseDataUtil.hideCounty = true;
                    scendListView.hide();
                }
            } else{
                if (checkecNumber == 0) {
//                scentAdapter.setAllNormal();
//                scendListView.hide();
                }else if(checkecNumber == 1){
                    scendListView.show();
                }else if (checkecNumber>1){
                    BaseDataUtil.hideCounty = true;
                    scendListView.hide();
                    BaseDataUtil.countys();
                }
            }

            if (checkecNumber<currentAdapter.getList().size()&&checkecNumber!=0){
                superListView.changeSupperStatus(BaseDataUtil.superPosition,CheckBoxListAdapter.HALF);
            }else if (checkecNumber==currentAdapter.getList().size()){
                superListView.changeSupperStatus(BaseDataUtil.superPosition,CheckBoxListAdapter.ALL);
            }else if(checkecNumber==0){
                superListView.changeSupperStatus(BaseDataUtil.superPosition,CheckBoxListAdapter.NORMAL);
            }
            currentAdapter.notifyDataSetChanged();
            scentAdapter.notifyDataSetChanged();
            superAdaper.notifyDataSetChanged();
        }else if (hasSuperLevel && !hasScendLevel){
            getCurrentData();
            BaseDataUtil.updateDataStatus(BaseDataUtil.superPosition,BaseDataUtil.scendPositon,position,
                    currentAdapter.moveToNextStatus(position));
            /////////////////////////////////////////////////////////////
            int checkecNumber = currentAdapter.getCheckedNumber(position);
            if (checkecNumber == currentDataSet.size()) {
                //super设置为全选

                }
            if (checkecNumber<currentAdapter.getList().size()&&checkecNumber!=0){
                superListView.changeSupperStatus(BaseDataUtil.scendPositon,CheckBoxListAdapter.HALF);
            }else if (checkecNumber==currentAdapter.getList().size()){
                superListView.changeSupperStatus(BaseDataUtil.scendPositon,CheckBoxListAdapter.ALL);
            }else if(checkecNumber==0){
                superListView.changeSupperStatus(BaseDataUtil.scendPositon,CheckBoxListAdapter.NORMAL);
            }
            currentAdapter.notifyDataSetChanged();
            superAdaper.notifyDataSetChanged();
        }

        /////////////////////////////////////////////////////////
        onDataBaseChange.onDataBaseChange(true);
    }

    private void changeSupperStatus(int positon,int status){
        this.currentAdapter.getList().get(positon).isCheck = status;
//        this.currentDataSet.get(positon).isCheck = status;
        if (hasSuperLevel){
            if(this.currentAdapter.getCheckedNumber(-1)==this.currentAdapter.getList().size()){
                superListView.currentAdapter.getList().
                        get(BaseDataUtil.superPosition).isCheck = CheckBoxListAdapter.ALL;
                superListView.currentAdapter.notifyDataSetChanged();
            }else if (this.currentAdapter.getCheckedNumber(-1)<this.currentAdapter.getList().size()&&this.currentAdapter.getCheckedNumber(-1)>=0
                    &&scendListView.currentAdapter.getCheckedNumber(-1)>0){
                superListView.currentAdapter.getList().
                        get(BaseDataUtil.superPosition).isCheck = CheckBoxListAdapter.HALF;
                superListView.currentAdapter.notifyDataSetChanged();
            }else {
                superListView.currentAdapter.getList().
                        get(BaseDataUtil.superPosition).isCheck = CheckBoxListAdapter.NORMAL;
                superListView.currentAdapter.notifyDataSetChanged();
            }

        }
    }

    public void update(int arg1,int arg2){
        this.invalidate();
        if (hasSuperLevel&&hasScendLevel){
            if (scendDataSet!=null){
                scendDataSet.clear();
                scendDataSet.addAll(BaseDataUtil.countys(arg1,arg2));
                scentAdapter.notifyDataSetChanged();
            }

        }
    }

    public void hide(){
        this.setVisibility(GONE);
        this.nextLayout.setVisibility(GONE);
        if (hasScendLevel){
            scendListView.hide();
        }
    }

    public void show(){
        this.setVisibility(VISIBLE);
        if (!hideNext)
        this.nextLayout.setVisibility(VISIBLE);
        if (hasScendLevel){
            if (currentAdapter.getCheckedNumber(-1)==1||currentAdapter.getCheckedNumber(-1)==0){
                scendListView.show();
            }
        }
    }

    public void setOnDataBaseChange(OnDataBaseChange onDataBaseChange) {
        this.onDataBaseChange = onDataBaseChange;
    }

    private OnDataBaseChange onDataBaseChange;

    public void setNextLayout(LinearLayout laytout_citys) {
        this.nextLayout = laytout_citys;
    }

    private Boolean hideNext = false;
    public void hideNext(Boolean hideCity) {
        hideNext = hideCity;
    }

    private int areaFlag = 0;
    public void setFlag(int areaFlag) {
        this.areaFlag = areaFlag;
    }


    public interface OnDataBaseChange{
        public void onDataBaseChange(Boolean isFromList);
    }

}
