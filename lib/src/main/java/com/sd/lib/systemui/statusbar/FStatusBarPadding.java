package com.sd.lib.systemui.statusbar;

import android.view.View;
import android.view.Window;

import com.sd.lib.systemui.common.FSystemUIHandler;

public class FStatusBarPadding extends FSystemUIHandler
{
    private final Window mWindow;

    public FStatusBarPadding(Window window)
    {
        if (window == null)
            throw new NullPointerException("window is null");
        mWindow = window;
    }

    @Override
    protected void checkView(View view)
    {
        final Window window = mWindow;
        final boolean isStatusBarVisible = FStatusBarUtils.isStatusBarVisible(window);
        final boolean isTransparent = FStatusBarUtils.isTransparent(window);
        if (isStatusBarVisible && isTransparent)
        {
            final int barHeight = FStatusBarUtils.getStatusBarHeight(view.getContext());
            if (barHeight != view.getPaddingTop())
                view.setPadding(view.getPaddingLeft(), barHeight, view.getPaddingRight(), view.getPaddingBottom());
        }
    }
}
