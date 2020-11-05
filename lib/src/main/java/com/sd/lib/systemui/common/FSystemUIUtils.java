package com.sd.lib.systemui.common;

import android.util.DisplayMetrics;
import android.view.Window;

public class FSystemUIUtils
{
    public static DisplayMetrics getRealMetrics(Window window)
    {
        if (window == null)
            return null;

        final DisplayMetrics metrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        return metrics;
    }

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
}
