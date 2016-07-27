package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Contorner.Condition;
import com.wksc.counting.R;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.model.AreaCheckModel;
import com.wksc.counting.model.MCU;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.MarqueeText;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.unionPickListView.PickListView3;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/29.
 */
public class AreaPopupwindow3 extends BasePopupWindow {
    Activity mContext;
    PickListView3 regionListView, cityListView, countyListView;
    Button sure;
    CheckBoxListAdapter regionListAdapter;
    CheckBoxListAdapter cityListAdapter;
    CheckBoxListAdapter countyListAdapter;
    CheckBoxListAdapter storsAdapter;
    RadioGroup radioGroup;
    ImageView search;
    EditText edit_query;
    NestedListView stores;
    LinearLayout citysLayout;
    TextView empty;
    CheckBox checkBox1,checkBox2,checkBox3;
    LinearLayout layout_countys,laytout_citys;
    List<AreaCheckModel> areas = new ArrayList<>();
    //    private MarqueeText area;
    Button reset;
    int flag =1;
    private IConfig config;
    List<BaseWithCheckBean> checkedRagions;
    List<BaseWithCheckBean> checkedCitys;
    List<BaseWithCheckBean> checkedCounty;
    StringBuilder store = new StringBuilder();
    private Condition condition;
    public Boolean mFromList = false;
    public AreaPopupwindow3(Activity context, Condition mCondition) {
        super();
        mContext = context;
        condition = mCondition;
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_layout_area3, null);
        regionListView = (PickListView3) contentView.findViewById(R.id.diriction_area);
        cityListView = (PickListView3) contentView.findViewById(R.id.diriction_province);
        countyListView = (PickListView3) contentView.findViewById(R.id.diriction_city);
        sure = (Button) contentView.findViewById(R.id.sure);
        radioGroup = (RadioGroup) contentView.findViewById(R.id.rg);
        search = (ImageView) contentView.findViewById(R.id.search);
        edit_query = (EditText) contentView.findViewById(R.id.edit_query);
        stores = (NestedListView) contentView.findViewById(R.id.stores);
        empty = (TextView) contentView.findViewById(R.id.empty);
        citysLayout = (LinearLayout) contentView.findViewById(R.id.citys);
        checkBox1 = (CheckBox) contentView.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) contentView.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) contentView.findViewById(R.id.checkbox3);
        layout_countys = (LinearLayout) contentView.findViewById(R.id.countys);
        laytout_citys = (LinearLayout) contentView.findViewById(R.id.citys);
        reset = (Button) contentView.findViewById(R.id.reset);
        search.setVisibility(View.GONE);
        regionListView.setCondition(mCondition);
        cityListView.setCondition(mCondition);
        countyListView.setCondition(mCondition);
        this.setContentView(contentView);
        this.setOutsideTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setAnimationStyle(R.style.areaAnimation);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                dismiss();
            }
        });
        init();
        regionListAdapter = new CheckBoxListAdapter(context);
        regionListAdapter.isAll = true;
        regionListAdapter.setList(condition.regions);
        regionListView.setAdapter(regionListAdapter);
        storsAdapter = new CheckBoxListAdapter(mContext);
        stores.setAdapter(storsAdapter);

        cityListAdapter = new CheckBoxListAdapter(context);
        countyListAdapter = new CheckBoxListAdapter(context);
//        cityListAdapter.setList(BaseDataUtil2.citys(BaseDataUtil2.lastAnaRagionPos));
        cityListView.setAdapter(cityListAdapter);
        countyListView.setAdapter(countyListAdapter);
        checkedRagions = condition.checkedRagions();
        checkedCitys = condition.checkedCitys();
        if (condition.citys!=null){
            cityListAdapter.setList(condition.citys);
        }else{
            cityListAdapter.setList(condition.citys(0));
        }
        if (condition.hideCity) {
            laytout_citys.setVisibility(View.GONE);
        }else{
            laytout_citys.setVisibility(View.VISIBLE);
        }
//        cityListView.superPosition = condition.superPosition;

//        countyListAdapter = new CheckBoxListAdapter(context);
        if (condition.countys!=null){
            countyListAdapter.setList(condition.countys);
        }else{
            countyListAdapter.setList(condition.countys(0, 0));
        }
        if (condition.hideCounty){
            layout_countys.setVisibility(View.GONE);
        }else{
            layout_countys.setVisibility(View.VISIBLE);
        }
//        countyListView.superPosition = condition.superPosition;
//        countyListView.scendPosition=condition.scendPositon;

        regionListView.initView(null, cityListView);
        cityListView.initView(regionListView, countyListView);
        countyListView.initView(cityListView, null);
        cityListView.setNextLayout(laytout_citys);
        countyListView.setNextLayout(layout_countys);
        radioGroup.setVisibility(View.GONE);
        edit_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getData();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        stores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                storsAdapter.moveToNextStatus(position);
                storsAdapter.notifyDataSetChanged();
                if (store.length() > 0) {
                    store.delete(0, store.length());
                }
                if (storsAdapter.getCheckedNumber(position) > 0)
                    for (int i = 0; i < storsAdapter.getList().size(); i++) {
                        BaseWithCheckBean bean = storsAdapter.getList().get(i);
                        if (bean.isCheck == CheckBoxListAdapter.ALL)
                            store.append(bean.code).append(",");
                    }
                if (store.length() > 0)
                    store.deleteCharAt(store.length() - 1);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder sb = new StringBuilder();
                sb.append(regionListAdapter.sb).append(cityListAdapter.sb).append(countyListAdapter.sb);

                /*根据选中的数量来判断到底选中的是省还是市，区*/

                List<BaseWithCheckBean> checkBeenRagion = condition.checkedRagions();
                List<BaseWithCheckBean> checkBeenCity = condition.checkedCitys();
                List<BaseWithCheckBean> checkBeenCountys = condition.checkedCountys();

//                if (checkBeenRagion.size()==0&&checkBeenCity.size()==0&&checkBeenCountys.size()==0){
//                    ToastUtil.showShortMessage(mContext,"请选择条件");
//                    return;
//                }

                if (store.length()>0){
                    flag = 4;
                }else if(checkBeenCountys.size()>0&&store.length()==0){
                    flag =3;
                }else if(checkBeenCity.size()>0&&checkBeenCountys.size()==0&&store.length()==0){
                    flag=2;
                }else if (checkBeenRagion.size()>0&&checkBeenCity.size()==0&checkBeenCountys.size()==0&&store.length()==0){
                    flag = 1;
                }

                if (flag == 1) {
                    dissmisPopupwindow();
                    if (mListener != null)
                        mListener.conditionSelect(condition.sbRegionCode.toString(), condition.sbRegion.toString(), 0);
                } else if (flag == 2) {
                    dissmisPopupwindow();
//                    radioGroup.check(1);
                    if (mListener != null)
                        mListener.conditionSelect(condition.sbCityCode.toString(), condition.sbCity.toString(), 1);
                } else if (flag == 3) {
                    dissmisPopupwindow();
//                    radioGroup.check(2);
                    if (mListener != null)
                        mListener.conditionSelect(condition.sbCountyCode.toString(), condition.sbCounty.toString(), 2);
                } else if (flag == 4) {
                    dissmisPopupwindow();
//                    radioGroup.check(2);
                    if (mListener != null)
                        mListener.conditionSelect(store.toString(), storsAdapter.sb.toString(), 4);
                }

            }
        });

        regionListView.setOnDataBaseChange(new PickListView3.OnDataBaseChange() {
            @Override
            public void onDataBaseChange(Boolean isFromList) {
                changeCheckBoxs(isFromList);
            }
        });
        cityListView.setOnDataBaseChange(new PickListView3.OnDataBaseChange() {
            @Override
            public void onDataBaseChange(Boolean isFromList) {
                changeCheckBoxs(isFromList);
            }
        });
        countyListView.setOnDataBaseChange(new PickListView3.OnDataBaseChange() {
            @Override
            public void onDataBaseChange(Boolean isFromList) {
                changeCheckBoxs(isFromList);
            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mFromList)
                if (isChecked){
                    regionListAdapter.setAllCheck();
                }else{
                    regionListAdapter.setAllNormal();
                }
                mFromList = false;
                laytout_citys.setVisibility(View.GONE);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mFromList)
                if (isChecked){
                    cityListAdapter.setAllCheck();
                }else{
                    cityListAdapter.setAllNormal();
                }
                mFromList = false;
                layout_countys.setVisibility(View.GONE);
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mFromList)
                if (isChecked){
                    countyListAdapter.setAllCheck();
                }else{
                    countyListAdapter.setAllNormal();
                }
                mFromList = false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condition.regionsNormal();
                condition.citys();
                condition.countys();
                cityListAdapter.setList(condition.citys(0));
                countyListAdapter.setList(condition.countys(0, 0));
                condition.superPosition = 0;
                condition.scendPositon = 0;
                regionListAdapter.notifyDataSetChanged();
                cityListAdapter.notifyDataSetChanged();
                countyListAdapter.notifyDataSetChanged();
            }
        });
    }


    private void changeCheckBoxs(Boolean isFromList) {
        mFromList = isFromList;
        if (condition.checkedRagions().size()<condition.regions().size()){
            checkBox1.setChecked(false);
        }else{
            checkBox1.setChecked(true);
        }
        int cCity =0;
        if (cityListAdapter.getList()!=null){
            for (int i =0 ;i <cityListAdapter.getList().size();i++){
                if (cityListAdapter.getList().get(i).isCheck == CheckBoxListAdapter.ALL){
                    cCity++;
                }
            }
            if (cCity<cityListAdapter.getList().size()){
                checkBox2.setChecked(false);
            }else{
                checkBox2.setChecked(true);
            }
        }

        int cCounty = 0;
        if (countyListAdapter.getList()!=null){//在初始化的时候，是根据上一次选中的状态来的，可能为空
            for (int i =0 ;i <countyListAdapter.getList().size();i++){
                if (countyListAdapter.getList().get(i).isCheck == CheckBoxListAdapter.ALL){
                    cCounty++;
                }
            }
            if (cCounty<countyListAdapter.getList().size()){
                checkBox3.setChecked(false);
            }else{
                checkBox3.setChecked(true);
            }
        }
        mFromList = false;
    }
    private void getData() {
        String param = edit_query.getText().toString();

        if (StringUtils.isBlank(param)) {
            ToastUtil.showShortMessage(mContext, "请输入要查询门店所在的地区名");
            return;
        }
        config = BaseApplication.getInstance().getCurrentConfig();
        StringBuilder sb = new StringBuilder(Urls.STORS);
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "name", param);
        DialogCallback<MCU> callback = new DialogCallback<MCU>(mContext,MCU.class) {
            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
            }

            @Override
            public void onResponse(boolean isFromCache, MCU c, Request request, @Nullable Response response) {
                if (c.MCU.size() > 0) {
                    storsAdapter.setList(c.MCU);
                    empty.setVisibility(View.GONE);
                    stores.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                    stores.setVisibility(View.GONE);
                }
            }
        };
        callback.setDialogHide();
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(callback);
    }

    public void showPopupwindow(View view) {
        backgroundAlpha(0.5f);
        this.showAsDropDown(view);
    }

    public void dissmisPopupwindow() {
        this.dismiss();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0

        mContext.getWindow().setAttributes(lp);
    }

    public void bindTextView(MarqueeText area) {
//        this.area = area;
    }

    public void hideCity() {
        cityListView.setVisibility(View.GONE);
        citysLayout.setVisibility(View.GONE);
        regionListView.ShowCityVisiable = false;
    }

    public void hideCounty() {
        countyListView.setVisibility(View.GONE);
        citysLayout.setVisibility(View.GONE);
    }

    public void hideStores() {
        edit_query.setVisibility(View.GONE);
    }
}
