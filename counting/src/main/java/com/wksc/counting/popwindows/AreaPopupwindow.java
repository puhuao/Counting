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
import com.wksc.counting.Basedata.BaseDataUtil;
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
import com.wksc.counting.widegit.unionPickListView.PickListView;
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
public class AreaPopupwindow extends BasePopupWindow {
    private List<BaseWithCheckBean> checkedCitys;
    private  List<BaseWithCheckBean> checkedRagions;
    Activity mContext;
    PickListView regionListView, cityListView, countyListView;
    Button sure;
    CheckBoxListAdapter regionListAdapter;
    CheckBoxListAdapter cityListAdapter;
    CheckBoxListAdapter countyListAdapter;
    CheckBoxListAdapter storsAdapter;
    RadioGroup radioGroup;
    ImageView search;
    EditText edit_query;
    NestedListView stores;
    TextView empty;
    CheckBox checkBox1,checkBox2,checkBox3;
    LinearLayout layout_countys,laytout_citys;
    List<AreaCheckModel> areas = new ArrayList<>();
    //    private MarqueeText area;
    Button reset;
    int flag =1;
    private IConfig config;
    StringBuilder store = new StringBuilder();
    private int currentShowCityPosition = 0;
    private int cuttentShowCountPosition = 0;
//    List<BaseWithCheckBean> checkedRagions;
//    List<BaseWithCheckBean> checkedCitys;
    List<BaseWithCheckBean> checkedCounty;
    public Boolean mFromList = false;

    public AreaPopupwindow(final Activity context) {
        super();
        mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_layout_area, null);
        regionListView = (PickListView) contentView.findViewById(R.id.diriction_area);
        cityListView = (PickListView) contentView.findViewById(R.id.diriction_province);
        countyListView = (PickListView) contentView.findViewById(R.id.diriction_city);
        sure = (Button) contentView.findViewById(R.id.sure);
        radioGroup = (RadioGroup) contentView.findViewById(R.id.rg);
        search = (ImageView) contentView.findViewById(R.id.search);
        edit_query = (EditText) contentView.findViewById(R.id.edit_query);
        stores = (NestedListView) contentView.findViewById(R.id.stores);
        empty = (TextView) contentView.findViewById(R.id.empty);
        checkBox1 = (CheckBox) contentView.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) contentView.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) contentView.findViewById(R.id.checkbox3);
        layout_countys = (LinearLayout) contentView.findViewById(R.id.countys);
        laytout_citys = (LinearLayout) contentView.findViewById(R.id.citys);
        reset = (Button) contentView.findViewById(R.id.reset);
        search.setVisibility(View.GONE);
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
        regionListAdapter.setList(BaseDataUtil.regions());
        regionListView.setAdapter(regionListAdapter);
        storsAdapter = new CheckBoxListAdapter(mContext);
        stores.setAdapter(storsAdapter);
        radioGroup.setVisibility(View.GONE);
        cityListAdapter = new CheckBoxListAdapter(context);
        cityListView.setAdapter(cityListAdapter);
        checkedRagions = BaseDataUtil.checkedRagions();
        checkedCitys = BaseDataUtil.checkedCitys();
        if (BaseDataUtil.citySet!=null){
            cityListAdapter.setList(BaseDataUtil.citySet);
        }else{
            cityListAdapter.setList(BaseDataUtil.citys(0));
        }

        if (BaseDataUtil.hideCity) {
            laytout_citys.setVisibility(View.GONE);
        }else{
            laytout_citys.setVisibility(View.VISIBLE);
        }
        cityListView.superPosition = BaseDataUtil.superPosition;
//        if (checkedRagions.size()==0){
//
//        }else if(checkedRagions.size()==1){
//
//        }

        countyListAdapter = new CheckBoxListAdapter(context);
//       checkedCitys = BaseDataUtil.checkedCitys();
//        int cityId = 0;
        if (BaseDataUtil.countySet!=null){
            countyListAdapter.setList(BaseDataUtil.countySet);
        }else{
            countyListAdapter.setList(BaseDataUtil.countys(0, 0));
        }
        if (BaseDataUtil.hideCounty){
            layout_countys.setVisibility(View.GONE);
        }else{
            layout_countys.setVisibility(View.VISIBLE);
        }
        countyListView.superPosition = BaseDataUtil.superPosition;
        countyListView.scendPosition=BaseDataUtil.scendPositon;
//        if (checkedRagions.size()==0&&checkedCitys.size()==0){
//        }else if (checkedCitys.size() == 1){
//
//        }else if (checkedCitys.size()>1){
//        }

        countyListView.setAdapter(countyListAdapter);
        regionListView.initView(null, cityListView);
        cityListView.initView(regionListView, countyListView);
        countyListView.initView(cityListView, null);
        cityListView.setNextLayout(laytout_citys);
        countyListView.setNextLayout(layout_countys);
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
        stores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                storsAdapter.moveToNextStatus(position);
                storsAdapter.notifyDataSetChanged();
                if (store.length() > 0) {
                    store.delete(0, store.length());
                }
                if (storsAdapter.getCheckedNumber() > 0)
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
                List<BaseWithCheckBean> checkBeenRagion = BaseDataUtil.checkedRagions();
                List<BaseWithCheckBean> checkBeenCity = BaseDataUtil.checkedCitys();
                List<BaseWithCheckBean> checkBeenCountys = BaseDataUtil.checkedCountys();

//                if (checkBeenRagion.size()==0&&checkBeenCity.size()==0&&checkBeenCountys.size()==0){
//                    ToastUtil.showShortMessage(context,"请选择条件");
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
                        mListener.conditionSelect(BaseDataUtil.sbRegionCode.toString(), BaseDataUtil.sbRegion.toString(), 0);
                } else if (flag == 2) {
                    dissmisPopupwindow();
                    if (mListener != null)
                        mListener.conditionSelect(BaseDataUtil.sbCityCode.toString(), BaseDataUtil.sbCity.toString(), 1);
                } else if (flag == 3) {
                    dissmisPopupwindow();
                    if (mListener != null)
                        mListener.conditionSelect(BaseDataUtil.sbCountyCode.toString(), BaseDataUtil.sbCounty.toString(), 2);
                } else if (flag == 4) {
                    dissmisPopupwindow();
                    if (mListener != null)
                        mListener.conditionSelect(store.toString(), storsAdapter.sb.toString(), 4);
                }
                restoreLastStatus();

            }
        });
        regionListView.setOnDataBaseChange(new PickListView.OnDataBaseChange() {
            @Override
            public void onDataBaseChange(Boolean isFromList) {
               changeCheckBoxs(isFromList);
            }
        });
        cityListView.setOnDataBaseChange(new PickListView.OnDataBaseChange() {
            @Override
            public void onDataBaseChange(Boolean isFromList) {
                changeCheckBoxs(isFromList);
            }
        });
        countyListView.setOnDataBaseChange(new PickListView.OnDataBaseChange() {
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
                BaseDataUtil.regionsNormal();
                BaseDataUtil.citys();
                BaseDataUtil.countys();
                regionListAdapter.notifyDataSetChanged();
                cityListAdapter.notifyDataSetChanged();
                countyListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void restoreLastStatus() {
        BaseDataUtil.citySet = cityListAdapter.getList();
        BaseDataUtil.countySet = countyListAdapter.getList();
    }

    private void changeCheckBoxs(Boolean isFromList) {
        mFromList = isFromList;
        if (BaseDataUtil.checkedRagions().size()<BaseDataUtil.regions().size()){
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
        if (countyListAdapter.getList()!=null){
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
                if(c!=null)
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


}
