package com.sd.lib.systemui.statusbar;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class FStatusBar
{
    private static final Map<Activity, FStatusBar> MAP_STATUS_BAR = new HashMap<>();

    private final WeakReference<Activity> mActivity;
    private Config mDefaultConfig;

    private final Collection<Config> mConfigHolder = new LinkedHashSet<>();

    private FStatusBar(Activity activity)
    {
        if (activity == null)
            throw new NullPointerException("activity is null");

        if (activity.isFinishing())
            throw new IllegalArgumentException("activity is finishing");

        mActivity = new WeakReference<>(activity);
        activity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    /**
     * 返回activity的状态栏
     *
     * @param activity
     * @return
     */
    public static synchronized FStatusBar get(Activity activity)
    {
        if (activity == null)
            return null;

        FStatusBar statusBar = MAP_STATUS_BAR.get(activity);
        if (statusBar == null)
        {
            statusBar = new FStatusBar(activity);
            if (!activity.isFinishing())
                MAP_STATUS_BAR.put(activity, statusBar);
        }
        return statusBar;
    }

    private static synchronized void remove(Activity activity)
    {
        if (activity == null)
            return;

        MAP_STATUS_BAR.remove(activity);
    }

    private Activity getActivity()
    {
        return mActivity.get();
    }

    private Config getActiveConfig()
    {
        if (mConfigHolder.isEmpty())
            return mDefaultConfig;

        final List<Config> list = new ArrayList<>(mConfigHolder);
        return list.get(list.size() - 1);
    }

    /**
     * 设置默认配置
     *
     * @param config
     */
    public void setDefaultConfig(Config config)
    {
        if (config == null)
            throw new NullPointerException("config is null");

        if (mDefaultConfig != config)
        {
            mDefaultConfig = config;
            applyConfig();
        }
    }

    /**
     * 添加配置，添加后该配置立即生效
     *
     * @param config
     */
    public void addConfig(Config config)
    {
        if (config == null)
            throw new NullPointerException("config is null");

        if (mConfigHolder.add(config))
            applyConfig();
    }

    /**
     * 移除配置，移除后上一个配置立即生效
     *
     * @param config
     */
    public void removeConfig(Config config)
    {
        if (config == null)
            return;

        if (mConfigHolder.remove(config))
            applyConfig();
    }

    /**
     * 应用配置
     */
    private void applyConfig()
    {
        final Config config = getActiveConfig();
        if (config == null)
            return;

        final Activity activity = getActivity();
        if (activity == null || activity.isFinishing())
            return;

        final Brightness brightness = config.getStatusBarBrightness();
        if (brightness != null)
        {
            if (brightness == Brightness.dark)
                FStatusBarUtils.setBrightness(activity.getWindow(), true);
            else
                FStatusBarUtils.setBrightness(activity.getWindow(), false);
        }
    }

    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks()
    {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState)
        {
        }

        @Override
        public void onActivityStarted(Activity activity)
        {
        }

        @Override
        public void onActivityResumed(Activity activity)
        {
        }

        @Override
        public void onActivityPaused(Activity activity)
        {
        }

        @Override
        public void onActivityStopped(Activity activity)
        {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState)
        {
        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            if (activity == getActivity())
                remove(activity);
        }
    };

    public interface Config
    {
        /**
         * 返回状态栏亮度
         *
         * @return
         */
        Brightness getStatusBarBrightness();
    }

    public enum Brightness
    {
        /**
         * 暗色主题
         */
        dark,
        /**
         * 亮色主题
         */
        light
    }
}
