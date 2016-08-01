package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.callBack.BaseInfo;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.ToastUtil;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/29.
 */
public class IndexPopupwindow extends PopupWindow {
    private  IConfig config;
    ListView list ;
    Button sure;
    CheckBoxListAdapter areaListAdapter;
    Activity mContext;
    String[] noderule;
    CheckBox checkbox1;
    private boolean isFromList =false;

    public IndexPopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_index,null);
        list = (ListView) view.findViewById(R.id.diriction_area);
        sure = (Button) view.findViewById(R.id.sure);
        checkbox1 = (CheckBox) view.findViewById(R.id.checkbox1);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setAnimationStyle(R.style.selectDataViewAnimation);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                dismiss();
            }
        });
        config = BaseApplication.getInstance().getCurrentConfig();
        noderule = config.getString("noderule","").split(",");
        int k =0;
        for (int i = 0;i<noderule.length;i++){
            for (int j = k ; j < BaseDataUtil.coreItems.size();j++){
                if (noderule[i].equals(BaseDataUtil.coreItems.get(j).code)){
                    k=j;
                    BaseDataUtil.coreItems.get(j).isCheck = CheckBoxListAdapter.ALL;
                    break;
                }
            }
        }
//        isFromList = true;
        if (noderule.length==BaseDataUtil.coreItems.size()){
            checkbox1.setChecked(true);
        }else{
            checkbox1.setChecked(false);
        }
        areaListAdapter = new CheckBoxListAdapter(context);
        areaListAdapter. setList(BaseDataUtil.coreItems());
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
                setNodeRule();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseDataUtil.updateCoreItemsStatus(position,areaListAdapter.moveToNextStatus(position));
                areaListAdapter.notifyDataSetChanged();

                isFromList = true;
                if (areaListAdapter.getCheckedNumber(position)==BaseDataUtil.coreItems.size()){
                    checkbox1.setChecked(true);
                }else{
                    checkbox1.setChecked(false);
                }
                isFromList = false;
            }
        });
        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isFromList)
                    if (isChecked){
                        areaListAdapter.setAllCheck();
                    }else{
                        areaListAdapter.setAllNormal();
                    }
                isFromList = false;
            }
        });
    }

    private void setNodeRule() {
        StringBuilder sb = new StringBuilder();
        for (int i =0 ;i <BaseDataUtil.coreItems.size();i++){
            BaseWithCheckBean bean = BaseDataUtil.coreItems.get(i);
            if (bean.isCheck == CheckBoxListAdapter.ALL){
                sb.append(bean.code).append(",");
            }
        }
        if (sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        post(sb.toString());
    }

    private void post(String s) {
        StringBuilder sb = new StringBuilder(Urls.SET_NODE_RULE);
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "code", s);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<Object>(mContext, Object.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {


                    }
                });
    }

    public void showPopupwindow(View view){
        backgroundAlpha(0.5f);
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
        mContext.overridePendingTransition(com.wksc.framwork.R.anim.push_left_in,
                com.wksc.framwork.R.anim.push_left_out);
    }
    public void dissmisPopupwindow(){
        this.dismiss();
        mContext.overridePendingTransition(com.wksc.framwork.R.anim.push_left_in,
                com.wksc.framwork.R.anim.push_left_out);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0

        mContext.getWindow().setAttributes(lp);
    }

}
