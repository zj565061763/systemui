package com.sd.lib.systemui.navigationbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.sd.lib.systemui.common.FSystemUIUtils;

import java.lang.reflect.Method;

public class FNavigationBarUtils extends FSystemUIUtils
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
            flag = addFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            if (dark)
            {
                flag = addFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                flag = clearFlag(flag, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else
            {
                flag = addFlag(flag, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                flag = clearFlag(flag, View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.getDecorView().setSystemUiVisibility(flag);

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (window.getNavigationBarColor() != Color.TRANSPARENT)
                window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 底部导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context)
    {
        if (isNavigationBarVisible(context))
        {
            final Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            return resourceId == 0 ? 0 : resources.getDimensionPixelSize(resourceId);
        } else
        {
            return 0;
        }
    }

    /**
     * 底部导航栏是否可见
     *
     * @param context
     * @return
     */
    public static boolean isNavigationBarVisible(Context context)
    {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId == 0)
            return !ViewConfiguration.get(context).hasPermanentMenuKey();

        boolean isNavigationBarVisible = resources.getBoolean(resourceId);

        final String navigationBarOverride = getNavigationBarOverride();
        if ("1".equals(navigationBarOverride))
        {
            isNavigationBarVisible = false;
        }

        return isNavigationBarVisible;
    }

    private static String getNavigationBarOverride()
    {
        String result = null;
        if (Build.VERSION.SDK_INT >= 19)
        {
            try
            {
                final Class clazz = Class.forName("android.os.SystemProperties");
                final Method method = clazz.getDeclaredMethod("get", String.class);
                method.setAccessible(true);
                result = (String) method.invoke(null, "qemu.hw.mainkeys");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }
}
