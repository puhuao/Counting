package com.wksc.counting.callBack;

import android.util.Log;

import com.wksc.counting.model.baseinfo.CoreInfo;
import com.wksc.framwork.util.GsonUtil;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by puhua on 2016/6/20.
 *
 * @
 */
public abstract class CoreIndexCallBack extends Callback<CoreInfo> {
    @Override
    public CoreInfo parseNetworkResponse(Response response) throws Exception {
        Log.i("info",response.body().string());
        CoreInfo info = GsonUtil.fromJson(String.valueOf(response.body().string()), CoreInfo.class);

        return info;
    }
}
