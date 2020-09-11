package com.sd.lib.systemui.navigationbar;

import android.view.View;

import com.sd.lib.systemui.common.FSystemUIHandler;

public class FNavigationBarPadding extends FSystemUIHandler
{
    @Override
    protected void checkView(View view)
    {
        final int barHeight = FNavigationBarUtils.getNavigationBarHeight(view.getContext());
        if (barHeight != view.getPaddingBottom())
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), barHeight);
    }
}
