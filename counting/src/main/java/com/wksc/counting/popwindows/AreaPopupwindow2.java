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
import com.wksc.counting.Basedata.BaseDataUtil2;
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
import com.wksc.counting.widegit.unionPickListView.PickListView2;
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
public class AreaPopupwindow2 extends BasePopupWindow {
    Activity mContext;
    PickListView2 regionListView, cityListView, countyListView;
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
    int flag =1;
    private IConfig config;
    List<BaseWithCheckBean> checkedRagions;
    List<BaseWithCheckBean> checkedCitys;
    List<BaseWithCheckBean> checkedCounty;
    StringBuilder store = new StringBuilder();

    public AreaPopupwindow2(Activity context) {
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_area2, null);
        regionListView = (PickListView2) view.findViewById(R.id.diriction_area);
        cityListView = (PickListView2) view.findViewById(R.id.diriction_province);
        countyListView = (PickListView2) view.findViewById(R.id.diriction_city);
        sure = (Button) view.findViewById(R.id.sure);
        radioGroup = (RadioGroup) view.findViewById(R.id.rg);
        search = (ImageView) view.findViewById(R.id.search);
        edit_query = (EditText) view.findViewById(R.id.edit_query);
        stores = (NestedListView) view.findViewById(R.id.stores);
        empty = (TextView) view.findViewById(R.id.empty);
        citysLayout = (LinearLayout) view.findViewById(R.id.citys);
        checkBox1 = (CheckBox) view.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkbox3);
        layout_countys = (LinearLayout) view.findViewById(R.id.countys);
        laytout_citys = (LinearLayout) view.findViewById(R.id.citys);
        search.setVisibility(View.GONE);
        this.setContentView(view);
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
        regionListAdapter = new CheckBoxListAdapter(context);
        regionListAdapter.isAll = true;
        regionListAdapter.setList(BaseDataUtil2.regions());
        regionListView.setAdapter(regionListAdapter);
        storsAdapter = new CheckBoxListAdapter(mContext);
        stores.setAdapter(storsAdapter);

        cityListAdapter = new CheckBoxListAdapter(context);
        countyListAdapter = new CheckBoxListAdapter(context);
        cityListView.setAdapter(cityListAdapter);
        countyListView.setAdapter(countyListAdapter);
        checkedRagions = BaseDataUtil2.checkedRagions();
        checkedCitys = BaseDataUtil2.checkedCitys();
        if (BaseDataUtil2.citySet!=null){
            cityListAdapter.setList(BaseDataUtil2.citySet);
        }else{
            cityListAdapter.setList(BaseDataUtil2.citys(0));
        }
        if (BaseDataUtil2.hideCity) {
            laytout_citys.setVisibility(View.INVISIBLE);
        }else{
            laytout_citys.setVisibility(View.VISIBLE);
        }
        cityListView.superPosition = BaseDataUtil2.superPosition;

        countyListAdapter = new CheckBoxListAdapter(context);
        if (BaseDataUtil2.countySet!=null){
            countyListAdapter.setList(BaseDataUtil2.countySet);
        }else{
            countyListAdapter.setList(BaseDataUtil2.countys(0, 0));
        }
        if (BaseDataUtil2.hideCounty){
            layout_countys.setVisibility(View.INVISIBLE);
        }else{
            layout_countys.setVisibility(View.VISIBLE);
        }
        countyListView.superPosition = BaseDataUtil.superPosition;
        countyListView.scendPosition=BaseDataUtil.scendPositon;

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

                StringBuilder sb = new StringBuilder();
                sb.append(regionListAdapter.sb).append(cityListAdapter.sb).append(countyListAdapter.sb);

                /*根据选中的数量来判断到底选中的是省还是市，区*/

                List<BaseWithCheckBean> checkBeenRagion = BaseDataUtil2.checkedRagions();
                List<BaseWithCheckBean> checkBeenCity = BaseDataUtil2.checkedCitys();
                List<BaseWithCheckBean> checkBeenCountys = BaseDataUtil2.checkedCountys();
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
                        mListener.conditionSelect(BaseDataUtil2.sbRegionCode.toString(), BaseDataUtil2.sbRegion.toString(), 0);
                } else if (flag == 2) {
                    dissmisPopupwindow();
//                    radioGroup.check(1);
                    if (mListener != null)
                        mListener.conditionSelect(BaseDataUtil2.sbCityCode.toString(), BaseDataUtil2.sbCity.toString(), 1);
                } else if (flag == 3) {
                    dissmisPopupwindow();
//                    radioGroup.check(2);
                    if (mListener != null)
                        mListener.conditionSelect(BaseDataUtil2.sbCountyCode.toString(), BaseDataUtil2.sbCounty.toString(), 2);
                } else if (flag == 4) {
                    dissmisPopupwindow();
//                    radioGroup.check(2);
                    if (mListener != null)
                        mListener.conditionSelect(store.toString(), storsAdapter.sb.toString(), 4);
                } else {
                    ToastUtil.showShortMessage(mContext, "筛选条件的格式不正确");
                }

            }
        });

        regionListView.setOnDataBaseChange(new PickListView2.OnDataBaseChange() {
            @Override
            public void onDataBaseChange() {
                changeCheckBoxs();
            }
        });
        cityListView.setOnDataBaseChange(new PickListView2.OnDataBaseChange() {
            @Override
            public void onDataBaseChange() {
                changeCheckBoxs();
            }
        });
        countyListView.setOnDataBaseChange(new PickListView2.OnDataBaseChange() {
            @Override
            public void onDataBaseChange() {
                changeCheckBoxs();
            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    regionListAdapter.setAllCheck();
                    checkBox1.setText("反选");
                }else{
                    regionListAdapter.setAllNormal();
                    checkBox1.setText("全选");
                }
                laytout_citys.setVisibility(View.INVISIBLE);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cityListAdapter.setAllCheck();
                    checkBox2.setText("反选");
                }else{
                    cityListAdapter.setAllNormal();
                    checkBox2.setText("全选");
                }
                layout_countys.setVisibility(View.INVISIBLE);
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    countyListAdapter.setAllCheck();
                    checkBox3.setText("反选");
                }else{
                    countyListAdapter.setAllNormal();
                    checkBox3.setText("全选");
                }
            }
        });
    }


    private void changeCheckBoxs() {
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
