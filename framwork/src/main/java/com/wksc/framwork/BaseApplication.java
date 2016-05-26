package com.wksc.framwork;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.platform.config.PreferenceConfig;
import com.wksc.framwork.platform.config.PropertiesConfig;
import com.wksc.framwork.util.AppUtil;

/**
 * Created by puhua on 2016/5/26.
 *
 * @
 */
public class BaseApplication extends Application {
    private static final String LOGTAG = BaseApplication.class.getSimpleName();
    private static BaseApplication sInstance;
    private static Context sContext = null;
    private static SharedPreferences sPrefs = null;

    /**
     * 配置器为preference
     */
    public final static int PREFERENCECONFIG = 0;

    /**
     * 配置器为Properties属性文件
     */
    public final static int PROPERTIESCONFIG = 1;

    /**
     * 配置器
     */
    private IConfig mCurrentConfig;


    private boolean isUserLogged;

    private SQLiteDatabase db;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOGTAG, "Application created");

        sInstance = this;
        sContext = getApplicationContext();
        sPrefs = PreferenceManager.getDefaultSharedPreferences(sContext);

        AppUtil.onCreate(this);

        // Map API must be initialized before inflating map view
        // GeoMapFactory.initGeoMap("Baidu", sContext);

        // start ControlCenterService
//        startService(new Intent(sContext, ControlCenterService.class));

//        RequestManager.init(this);

        //init DBManger
        this.initDB();
    }


    private void initDB() {
//        DBManager.getInstance(this).init();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //MultiDex.install(this);
    }

    public static Context getContext() {
        return sContext;
    }

    public static SharedPreferences getPreferences() {
        return sPrefs;
    }

    public IConfig getConfig(int confingType) {
        if (confingType == PREFERENCECONFIG) {
            mCurrentConfig = PreferenceConfig.getPreferenceConfig(this);

        } else if (confingType == PROPERTIESCONFIG) {
            mCurrentConfig = PropertiesConfig.getPropertiesConfig(this);
        } else {
            mCurrentConfig = PropertiesConfig.getPropertiesConfig(this);
        }
        if (!mCurrentConfig.isLoadConfig()) {
            mCurrentConfig.loadConfig();
        }
        return mCurrentConfig;
    }


    /**
     * 获得Preference配置
     *
     * @return
     */
    public IConfig getPreferenceConfig() {
        return getConfig(PREFERENCECONFIG);
    }

    /**
     * 获得属性文件Properties配置
     *
     * @return
     */
    public IConfig getPropertiesConfig() {
        return getConfig(PROPERTIESCONFIG);
    }

    /**
     * 获得当前配置：默认为PrefrenceConfig
     *
     * @return
     */
    public IConfig getCurrentConfig() {
        if (mCurrentConfig == null) {
            getPreferenceConfig();
        }
        return mCurrentConfig;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }


    public boolean isUserLogged() {
        return isUserLogged;
    }

    public void setUserLogged(boolean isUserLogged) {
        this.isUserLogged = isUserLogged;
    }

}
