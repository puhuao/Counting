package com.wksc.counting.callBack;

import android.util.Log;

import com.wksc.counting.model.baseinfo.BaseInfo;
import com.wksc.framwork.util.GsonUtil;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by puhua on 2016/6/20.
 *
 * @
 */
public abstract class BaseInfoCallBack extends Callback<BaseInfo> {
    @Override
    public BaseInfo parseNetworkResponse(Response response) throws Exception {
        Log.i("info",response.body().string());
        BaseInfo info = GsonUtil.fromJson(String.valueOf(response.body().string()), BaseInfo.class);

        return info;
    }
}
