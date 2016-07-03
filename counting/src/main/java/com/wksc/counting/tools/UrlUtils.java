package com.wksc.counting.tools;

import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.StringUtils;

/**
 * Created by Administrator on 2016/7/2.
 */
public class UrlUtils {


    private static UrlUtils mInstance;

    public static UrlUtils getInstance() {
        if (mInstance == null) {
            synchronized (UrlUtils.class) {
                if (mInstance == null) {
                    mInstance = new UrlUtils();
                }
            }
        }
        return mInstance;
    }

    public UrlUtils praseToUrl(StringBuilder base, String key, String value) {
        if (!StringUtils.isBlank(value))
            base.append("&").append(key).append("=").append(value);
        return this;
    }

    public UrlUtils addSession(StringBuilder base, IConfig config) {
        base.append("&").append("sessionId").append("=").append(config.getString("sessionId", ""));
        return this;
    }
}
