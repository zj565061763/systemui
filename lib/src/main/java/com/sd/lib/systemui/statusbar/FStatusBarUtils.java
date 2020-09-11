package com.sd.lib.systemui.statusbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sd.lib.systemui.common.FSystemUIUtils;

public class FStatusBarUtils extends FSystemUIUtils
{
    /**
     * 全透明
     */
    public static void setTransparent(Window window, boolean dark)
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            int flag = window.getDecorView().getSystemUiVisibility();
            flag = addFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            if (dark)
            {
                flag = addFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                flag = clearFlag(flag, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else
            {
                flag = addFlag(flag, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                flag = clearFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.getDecorView().setSystemUiVisibility(flag);

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (window.getStatusBarColor() != Color.TRANSPARENT)
                window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context)
    {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resourceId == 0 ? 0 : resources.getDimensionPixelSize(resourceId);
    }

    /**
     * Window的状态栏高度
     *
     * @param window
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Window window, Context context)
    {
        return isStatusBarVisible(window) ? getStatusBarHeight(context) : 0;
    }

    /**
     * 返回Activity的状态栏高度
     *
     * @param context
     * @return
     */
    public static int getActivityStatusBarHeight(Context context)
    {
        if (context instanceof Activity)
            return getStatusBarHeight(((Activity) context).getWindow(), context);
        else
            return getStatusBarHeight(context);
    }

    /**
     * Window的状态栏是否可见
     *
     * @param window
     * @return
     */
    public static boolean isStatusBarVisible(Window window)
    {
        return ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0);
    }
}
