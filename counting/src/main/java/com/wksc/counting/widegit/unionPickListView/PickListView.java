package com.wksc.counting.widegit.unionPickListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.BaseInfo;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;

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
//                scentAdapter.setList(BaseDataUtil.citys(currentAdapter.oneCheckPosition));

                scendListView.show();
            }else if (checkecNumber>1){
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

    private void changeSupperStatus(){
        this.superDataSet.get(scendPosition).isCheck = CheckBoxListAdapter.HALF;
        if (hasSuperLevel){
            if (this.currentAdapter.getCheckedNumber() < currentDataSet.size()&&this.currentAdapter.getCheckedNumber()>0){

            }
        }
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
        if (hasScendLevel){
            if (currentAdapter.getCheckedNumber()==1||currentAdapter.getCheckedNumber()==0){
                scendListView.show();
            }
        }

    }
}
