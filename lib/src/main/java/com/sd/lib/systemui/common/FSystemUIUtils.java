package com.sd.lib.systemui.common;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.DisplayCutout;
import android.view.WindowInsets;

import java.lang.reflect.Method;

public class FSystemUIUtils
{
    public static int addFlag(int original, int flag)
    {
        return original | flag;
    }

    public static int clearFlag(int original, int flag)
    {
        return original & (~flag);
    }

    public static boolean hasFlag(int original, int flag)
    {
        return (original & flag) == flag;
    }

    //---------- 刘海屏 ----------

    /**
     * 是否有刘海屏
     *
     * @return
     */
    public static boolean hasNotch(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        {
            final WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();
            if (windowInsets != null)
            {
                final DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                return displayCutout != null;
            }
            return false;
        } else
        {
            final String manufacturer = Build.MANUFACTURER;
            if (TextUtils.isEmpty(manufacturer))
                return false;

            if ("oppo".equalsIgnoreCase(manufacturer))
            {
                return hasNotchOPPO(activity);
            } else if ("vivo".equalsIgnoreCase(manufacturer))
            {
                return hasNotchVIVO();
            } else if ("xiaomi".equalsIgnoreCase(manufacturer))
            {
                return hasNotchXiaoMi();
            } else if ("huawei".equalsIgnoreCase(manufacturer))
            {
                return hasNotchHuaWei();
            } else
            {
                return false;
            }
        }
    }

    /**
     * 判断oppo是否有刘海屏
     * https://open.oppomobile.com/wiki/doc#id=10159
     *
     * @param context
     * @return
     */
    private static boolean hasNotchOPPO(Context context)
    {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 判断vivo是否有刘海屏
     * https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf
     *
     * @return
     */
    private static boolean hasNotchVIVO()
    {
        try
        {
            final Class<?> clazz = Class.forName("android.util.FtFeature");
            final Method method = clazz.getMethod("isFeatureSupport", int.class);
            method.setAccessible(true);
            final Object result = method.invoke(clazz, 0x20);

            return (boolean) result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断xiaomi是否有刘海屏
     * https://dev.mi.com/console/doc/detail?pId=1293
     *
     * @return
     */
    private static boolean hasNotchXiaoMi()
    {
        try
        {
            final Class<?> clazz = Class.forName("android.os.SystemProperties");
            final Method method = clazz.getMethod("getInt", String.class, int.class);
            method.setAccessible(true);
            final Object result = method.invoke(clazz, "ro.miui.notch", 0);

            return ((int) result) == 1;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断华为是否有刘海屏
     * https://developer.huawei.com/consumer/cn/doc/50114
     *
     * @return
     */
    private static boolean hasNotchHuaWei()
    {
        try
        {
            final Class<?> clazz = Class.forName("com.huawei.android.util.HwNotchSizeUtil");
            final Method method = clazz.getMethod("hasNotchInScreen");
            method.setAccessible(true);
            final Object result = method.invoke(clazz);

            return (boolean) result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
