package com.perfectljy.ersanshi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;


import com.perfectljy.ersanshi.Utils.LogUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * 全局Application
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015-06-12  09:56
 */
public class AppContext extends Application {

    public final static boolean DEBUG = BuildConfig.DEBUG;
    private final static String TAG = "AppContext";
    private static AppContext mInstance;
    private static int mVersionCode;
    private static String mVersionName;
    private static String mPackageName;
    private static PackageInfo mInfo;
    private static HashMap<String, WeakReference<Activity>> mContexts = new HashMap<String, WeakReference<Activity>>();
    private static Typeface mRobotoSlabBold = null;
    private static Typeface mRobotoSlabLight = null;
    private static Typeface mRobotoSlabRegular = null;
    private static Typeface mRobotoSlabThin = null;
    private static List<Activity> mActivityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
//        init();
//        initAppInfo();
//        deviceInfo();
//        initAppTypeface();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    private void init() {
        this.mInstance = this;

    }

    private void initAppInfo() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pageInfo = pm.getPackageInfo(getPackageName(), 0);
            mPackageName = pageInfo.packageName;
            mVersionName = pageInfo.versionName;
            mVersionCode = pageInfo.versionCode;
            mInfo = pageInfo;
            LogUtil.d(TAG, "initAppInfo: versionName:" + mVersionName + " VersionCode:" + mVersionCode + " PackageName:" + mPackageName);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deviceInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        LogUtil.d(TAG, "heap size：" + activityManager.getMemoryClass() + " LargeMemory:" + activityManager.getLargeMemoryClass());
    }


    public static synchronized void setActiveContext(Activity context) {
        WeakReference<Activity> reference = new WeakReference<Activity>(context);
        mContexts.put(context.getClass().getSimpleName(), reference);
    }

    public static synchronized Activity getActiveContext(String className) {
        WeakReference<Activity> reference = mContexts.get(className);
        if (reference == null) {
            return null;
        }

        final Activity context = reference.get();

        if (context == null) {
            mContexts.remove(className);
        }
        return context;
    }

    public static HashMap<String, WeakReference<Activity>> getmContexts() {
        return mContexts;
    }

    public static AppContext getInstance() {
        return mInstance;
    }

    /**
     * 预处理字体，否则设置字体会很慢
     */
    private void initAppTypeface() {
        AssetManager localAssetManager = getAssets();
        mRobotoSlabRegular = Typeface.createFromAsset(localAssetManager, "fonts/RobotoSlab/RobotoSlab-Regular.ttf");
        mRobotoSlabBold = Typeface.createFromAsset(localAssetManager, "fonts/RobotoSlab/RobotoSlab-Bold.ttf");
        mRobotoSlabLight = Typeface.createFromAsset(localAssetManager, "fonts/RobotoSlab/RobotoSlab-Light.ttf");
        mRobotoSlabThin = Typeface.createFromAsset(localAssetManager, "fonts/RobotoSlab/RobotoSlab-Thin.ttf");
    }

    public static Typeface getRobotoSlabBold() {
        return mRobotoSlabBold;
    }

    public static Typeface getRobotoSlabLight() {
        return mRobotoSlabLight;
    }

    public static Typeface getRobotoSlabRegular() {
        return mRobotoSlabRegular;
    }

    public static Typeface getRobotoSlabThin() {
        return mRobotoSlabThin;
    }
}
