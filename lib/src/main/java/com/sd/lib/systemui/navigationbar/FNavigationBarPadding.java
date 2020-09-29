package com.sd.lib.systemui.navigationbar;

import android.view.View;
import android.view.Window;

import com.sd.lib.systemui.common.FSystemUIHandler;

public class FNavigationBarPadding extends FSystemUIHandler
{
    private final Window mWindow;

    public FNavigationBarPadding(Window window)
    {
        if (window == null)
            throw new NullPointerException("window is null");
        mWindow = window;
    }

    @Override
    protected void checkView(View view)
    {
        final Window window = mWindow;
        final boolean isNavigationBarVisible = FNavigationBarUtils.isNavigationBarVisible(view.getContext());
        final boolean isContentExtension = FNavigationBarUtils.isContentExtension(window);
        if (isNavigationBarVisible && isContentExtension)
        {
            final int barHeight = FNavigationBarUtils.getNavigationBarHeight(view.getContext());
            if (barHeight != view.getPaddingBottom())
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), barHeight);
        }
    }
}
