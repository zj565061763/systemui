package com.sd.lib.systemui.statusbar;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class FStatusBar {
    private static final Map<Activity, FStatusBar> MAP_STATUS_BAR = new HashMap<>();
    private final WeakReference<Activity> mActivity;

    private Config mDefaultConfig;
    private Config mLastConfig;
    private final Collection<Config> mConfigHolder = new LinkedHashSet<>();
    private final Map<Config, LifecycleConfigHolder> mLifecycleConfigHolder = new HashMap<>();

    private boolean mCheckContentExtension = true;

    private FStatusBar(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is null");
        }

        mActivity = new WeakReference<>(activity);
        if (!activity.isFinishing()) {
            activity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
    }

    /**
     * 返回activity的状态栏
     */
    public static synchronized FStatusBar of(Activity activity) {
        if (activity == null) {
            return null;
        }

        FStatusBar statusBar = MAP_STATUS_BAR.get(activity);
        if (statusBar == null) {
            statusBar = new FStatusBar(activity);
            if (!activity.isFinishing()) {
                MAP_STATUS_BAR.put(activity, statusBar);
            }
        }
        return statusBar;
    }

    private static synchronized void remove(Activity activity) {
        if (activity == null) {
            return;
        }
        MAP_STATUS_BAR.remove(activity);
    }

    private Activity getActivity() {
        return mActivity.get();
    }

    private Config getActiveConfig() {
        if (mConfigHolder.isEmpty()) {
            mLastConfig = null;
            return mDefaultConfig;
        }

        final List<Config> list = new ArrayList<>(mConfigHolder);
        final Config lastConfig = list.get(list.size() - 1);

        mLastConfig = lastConfig;
        return lastConfig;
    }

    /**
     * 返回最后设置的配置
     */
    public Config getLastConfig() {
        return mLastConfig;
    }

    /**
     * 设置是否检查内容延展到状态栏底部，默认true检查
     */
    public void setCheckContentExtension(boolean check) {
        if (mCheckContentExtension != check) {
            mCheckContentExtension = check;
            applyActiveConfig();
        }
    }

    /**
     * 设置默认配置
     */
    public void setDefaultConfig(Config config) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }
        if (mDefaultConfig != config) {
            mDefaultConfig = config;
            applyActiveConfig();
        }
    }

    /**
     * 应用默认配置
     */
    public void applyDefaultConfig() {
        applyConfigInternal(mDefaultConfig);
    }

    /**
     * 应用可用的配置
     */
    public void applyActiveConfig() {
        final Config config = getActiveConfig();
        applyConfigInternal(config);
    }

    /**
     * 应用配置
     */
    public void applyConfig(Config config) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }

        if (config instanceof View) {
            final View view = (View) config;
            applyConfig(config, view);
        } else if (config instanceof Dialog) {
            final View view = ((Dialog) config).getWindow().getDecorView();
            applyConfig(config, view);
        } else {
            applyConfig(config, null);
        }
    }

    /**
     * 应用配置
     *
     * @param lifecycleView 生命周期view。不为null的话，则view被移除的时候，自动移除config
     */
    public void applyConfig(Config config, View lifecycleView) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }
        if (config == mDefaultConfig) {
            throw new IllegalArgumentException("can not apply default config here");
        }

        if (lifecycleView == null) {
            destroyLifecycleConfig(config);
            addConfigInternal(config);
            return;
        }

        final LifecycleConfigHolder holder = mLifecycleConfigHolder.get(config);
        if (holder != null) {
            if (holder.mLifecycle == lifecycleView) {
                // 生命周期View未发生变化，直接返回不处理
                return;
            } else {
                // 生命周期view发生变化，先移除销毁
                destroyLifecycleConfig(config);
            }
        }

        final LifecycleConfigHolder newHolder = new ViewConfigHolder(config, lifecycleView);
        newHolder.init();
        mLifecycleConfigHolder.put(config, newHolder);
    }

    /**
     * 移除配置，移除后上一个配置立即生效
     */
    public void removeConfig(Config config) {
        removeConfigInternal(config);
        destroyLifecycleConfig(config);
    }

    private void destroyLifecycleConfig(Config config) {
        if (config == null) {
            return;
        }
        final LifecycleConfigHolder holder = mLifecycleConfigHolder.remove(config);
        if (holder != null) {
            holder.destroy();
        }
    }

    /**
     * 添加配置
     */
    private void addConfigInternal(Config config) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }
        if (config == mDefaultConfig) {
            throw new IllegalArgumentException("can not apply default config here");
        }

        if (mLastConfig != config) {
            mConfigHolder.remove(config);
            mConfigHolder.add(config);
        }
        applyActiveConfig();
    }

    /**
     * 移除配置
     */
    private void removeConfigInternal(Config config) {
        if (config == null) {
            return;
        }
        if (mConfigHolder.remove(config)) {
            applyActiveConfig();
        }
    }

    /**
     * 应用配置
     */
    private void applyConfigInternal(Config config) {
        if (config == null) {
            return;
        }

        final Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        final Brightness brightness = config.getStatusBarBrightness();
        if (brightness != null) {
            if (mCheckContentExtension) {
                final boolean isContentExtension = FStatusBarUtils.isContentExtension(activity.getWindow());
                if (!isContentExtension) {
                    return;
                }
            }

            if (brightness == Brightness.dark) {
                FStatusBarUtils.setBrightness(activity.getWindow(), true);
            } else {
                FStatusBarUtils.setBrightness(activity.getWindow(), false);
            }
        }
    }

    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity == getActivity()) {
                activity.getApplication().unregisterActivityLifecycleCallbacks(this);
                remove(activity);
            }
        }
    };

    public enum Brightness {
        /** 暗色 */
        dark,
        /** 亮色 */
        light
    }

    public interface Config {
        /**
         * 返回状态栏亮度
         */
        Brightness getStatusBarBrightness();
    }

    private abstract class LifecycleConfigHolder<T> {
        protected final Config mConfig;
        protected final T mLifecycle;

        public LifecycleConfigHolder(Config config, T lifecycle) {
            if (config == null) {
                throw new NullPointerException("config is null");
            }
            if (lifecycle == null) {
                throw new NullPointerException("lifecycle is null");
            }

            mConfig = config;
            mLifecycle = lifecycle;
        }

        protected abstract void init();

        protected abstract void destroy();
    }

    private class ViewConfigHolder extends LifecycleConfigHolder<View> implements View.OnAttachStateChangeListener {
        public ViewConfigHolder(Config config, View lifecycle) {
            super(config, lifecycle);
        }

        @Override
        protected void init() {
            mLifecycle.addOnAttachStateChangeListener(this);
        }

        @Override
        public void onViewAttachedToWindow(View v) {
            addConfigInternal(mConfig);
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            removeConfigInternal(mConfig);
        }

        @Override
        protected void destroy() {
            mLifecycle.removeOnAttachStateChangeListener(this);
        }
    }
}
