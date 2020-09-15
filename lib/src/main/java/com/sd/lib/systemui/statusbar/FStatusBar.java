package com.sd.lib.systemui.statusbar;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.sd.lib.systemui.common.FSystemUIUtils;

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
    private final Map<Config, LifecycleConfigHolder> mLifecycleConfigHolder = new HashMap<>();

    private boolean mCheckSystemUiVisibility = true;

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
     * 设置是否检查Window#getDecorView()#getSystemUiVisibility()配置
     *
     * @param check
     */
    public void setCheckSystemUiVisibility(boolean check)
    {
        if (mCheckSystemUiVisibility != check)
        {
            mCheckSystemUiVisibility = check;
            applyConfig();
        }
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

        if (config instanceof View)
        {
            final View view = (View) config;
            addConfig(config, view);
        } else if (config instanceof Dialog)
        {
            final View view = ((Dialog) config).getWindow().getDecorView();
            addConfig(config, view);
        } else
        {
            addConfig(config, null);
        }
    }

    /**
     * 添加配置，添加后该配置立即生效
     *
     * @param config
     * @param lifecycleView 生命周期view。不为null的话，则view被移除的时候，自动移除config
     */
    public void addConfig(Config config, View lifecycleView)
    {
        if (config == null)
            throw new NullPointerException("config is null");

        mConfigHolder.remove(config);
        mConfigHolder.add(config);
        applyConfig();

        if (lifecycleView != null)
        {
            LifecycleConfigHolder holder = mLifecycleConfigHolder.get(config);
            if (holder == null)
            {
                holder = new ViewConfigHolder(config, lifecycleView);
                mLifecycleConfigHolder.put(config, holder);
            } else
            {
                if (holder.mLifecycle != lifecycleView)
                {
                    // 如果生命周期view发生了变化，先移除销毁旧对象
                    removeLifecycleConfigIfNeed(config);

                    // 重新创建对象保存
                    holder = new ViewConfigHolder(config, lifecycleView);
                    mLifecycleConfigHolder.put(config, holder);
                }
            }
        } else
        {
            removeLifecycleConfigIfNeed(config);
        }
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
        {
            removeLifecycleConfigIfNeed(config);
            applyConfig();
        }
    }

    private void removeLifecycleConfigIfNeed(Config config)
    {
        final LifecycleConfigHolder holder = mLifecycleConfigHolder.remove(config);
        if (holder != null)
            holder.destroy();
    }

    /**
     * 应用配置
     */
    void applyConfig()
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
            if (mCheckSystemUiVisibility)
            {
                final int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
                final boolean hasFullScreen = FSystemUIUtils.hasFlag(systemUiVisibility, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                if (!hasFullScreen)
                    return;
            }

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

    public interface Config
    {
        /**
         * 返回状态栏亮度
         *
         * @return
         */
        Brightness getStatusBarBrightness();
    }

    private abstract class LifecycleConfigHolder<T>
    {
        protected final Config mConfig;
        protected final T mLifecycle;

        public LifecycleConfigHolder(Config config, T lifecycle)
        {
            if (config == null)
                throw new NullPointerException("config is null");

            if (lifecycle == null)
                throw new NullPointerException("lifecycle is null");

            mConfig = config;
            mLifecycle = lifecycle;
        }

        protected abstract void destroy();
    }

    private class ViewConfigHolder extends LifecycleConfigHolder<View> implements View.OnAttachStateChangeListener
    {
        public ViewConfigHolder(Config config, View lifecycle)
        {
            super(config, lifecycle);
            lifecycle.addOnAttachStateChangeListener(this);
        }

        @Override
        public void onViewAttachedToWindow(View v)
        {
        }

        @Override
        public void onViewDetachedFromWindow(View v)
        {
            removeConfig(mConfig);
        }

        @Override
        protected void destroy()
        {
            mLifecycle.removeOnAttachStateChangeListener(this);
        }
    }
}
