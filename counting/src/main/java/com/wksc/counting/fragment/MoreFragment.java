package com.wksc.counting.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.activity.LocusPassActivity;
import com.wksc.counting.activity.LoginActivity;
import com.wksc.counting.popwindows.IndexPopupWindow;
import com.wksc.counting.widegit.CustomDialog;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/27.
 *
 * @
 */
public class MoreFragment extends CommonFragment implements View.OnClickListener {
    @Bind(R.id.loc)
    TextView loc;
    @Bind(R.id.clear_catch)
    TextView clearCache;
    @Bind(R.id.index_choice)
    TextView indexChoice;
    @Bind(R.id.logout)
    TextView logout;
    private IConfig config;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, null);
        hideLeftButton();
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        loc.setOnClickListener(this);
        clearCache.setOnClickListener(this);
        indexChoice.setOnClickListener(this);
        logout.setOnClickListener(this);
        return v;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loc:
                startActivity(LocusPassActivity.class);
//                getContext().pushFragmentToBackStack(LocusPassFragment.class,false);
                break;
            case R.id.clear_catch:
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setTitle("清除缓存");
                builder.setMessage("清楚缓存会清空应用存储的所有记录\n你是否要清除缓存？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 清除缓存
//                        RequestManager.clearCache();

                        ToastUtil.showLongMessage(getActivity(), "清除缓存完成!");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.index_choice:
                IndexPopupWindow indexPopupwindow = new IndexPopupWindow( getContext());
                indexPopupwindow.showPopupwindow(v);
                break;
            case R.id.logout:
                /*在退出登录时将存在本地的手势密码清空，将设置手势密码的标志位设为未设置*/
                config = BaseApplication.getInstance().getCurrentConfig();
                config.setBoolean("setLocusPassword", false);
                config.setString("locusPassword","");
                startActivity(LoginActivity.class,null);
                getActivity().finish();
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }
}
