package com.sd.lib.systemui.navigationbar;

import android.content.Context;

public class FNavigationBarUtils
{
    /**
     * 底部导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context)
    {
        final int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }
}
