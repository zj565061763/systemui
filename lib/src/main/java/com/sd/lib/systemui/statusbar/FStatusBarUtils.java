package com.sd.lib.systemui.statusbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sd.lib.systemui.common.FFlagUtils;

public class FStatusBarUtils extends FFlagUtils {
    /**
     * 设置状态栏背景色
     */
    public static void setBarColor(Activity activity, int color) {
        if (activity != null) {
            setBarColor(activity.getWindow(), color);
        }
    }

    /**
     * 设置状态栏背景色
     */
    public static void setBarColor(Dialog dialog, int color) {
        if (dialog != null) {
            setBarColor(dialog.getWindow(), color);
        }
    }

    /**
     * 设置状态栏背景亮度
     *
     * @param dark true-暗色；false-亮色
     */
    public static void setBrightness(Activity activity, boolean dark) {
        if (activity != null) {
            setBrightness(activity.getWindow(), dark);
        }
    }

    /**
     * 设置状态栏背景亮度
     *
     * @param dark true-暗色；false-亮色
     */
    public static void setBrightness(Dialog dialog, boolean dark) {
        if (dialog != null) {
            setBrightness(dialog.getWindow(), dark);
        }
    }

    /**
     * 设置内容是否延展到状态栏底层
     *
     * @param extension true-延展；false-不延展
     */
    public static void setContentExtension(Activity activity, boolean extension) {
        if (activity != null) {
            setContentExtension(activity.getWindow(), extension);
            FStatusBar.of(activity).applyActiveConfig();
        }
    }

    /**
     * 设置内容是否延展到状态栏底层
     *
     * @param extension true-延展；false-不延展
     */
    public static void setContentExtension(Dialog dialog, boolean extension) {
        if (dialog != null) {
            setContentExtension(dialog.getWindow(), extension);
        }
    }

    /**
     * 设置状态栏背景色
     */
    static void setBarColor(Window window, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    /**
     * 设置状态栏背景亮度
     *
     * @param dark true-暗色；false-亮色
     */
    static void setBrightness(Window window, boolean dark) {
        if (Build.VERSION.SDK_INT >= 23) {
            int flag = window.getDecorView().getSystemUiVisibility();
            if (dark) {
                flag = clearFlag(flag, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                flag = addFlag(flag, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.getDecorView().setSystemUiVisibility(flag);
        }
    }

    /**
     * 设置Window内容是否延展到状态栏底层
     *
     * @param extension true-延展；false-不延展
     */
    static void setContentExtension(Window window, boolean extension) {
        if (Build.VERSION.SDK_INT >= 21) {
            int flag = window.getDecorView().getSystemUiVisibility();
            if (extension) {
                flag = addFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                flag = clearFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            window.getDecorView().setSystemUiVisibility(flag);
        } else if (Build.VERSION.SDK_INT >= 19) {
            if (extension) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * Window内容是否延展到状态栏底层
     */
    public static boolean isContentExtension(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            final int flags = window.getDecorView().getSystemUiVisibility();
            return hasFlag(flags, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= 19) {
            final int flags = window.getAttributes().flags;
            return hasFlag(flags, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            return false;
        }
    }

    /**
     * Window的状态栏是否可见
     */
    public static boolean isBarVisible(Window window) {
        final int flags = window.getAttributes().flags;
        return !hasFlag(flags, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 状态栏高度
     */
    public static int getBarHeight(Context context) {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resourceId == 0 ? 0 : resources.getDimensionPixelSize(resourceId);
    }

    @Deprecated
    public static int getStatusBarHeight(Context context) {
        return getBarHeight(context);
    }

    @Deprecated
    public static void setTransparent(Activity activity) {
        if (activity != null) {
            setBarColor(activity, Color.TRANSPARENT);
            setContentExtension(activity, true);
        }
    }

    @Deprecated
    public static void setTransparent(Dialog dialog) {
        if (dialog != null) {
            setBarColor(dialog, Color.TRANSPARENT);
            setContentExtension(dialog, true);
        }
    }
}
