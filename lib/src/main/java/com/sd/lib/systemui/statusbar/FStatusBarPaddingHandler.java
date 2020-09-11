package com.sd.lib.systemui.statusbar;

import android.view.View;
import android.view.Window;

import com.sd.lib.systemui.common.FSystemUIHandler;

public class FStatusBarPaddingHandler extends FSystemUIHandler
{
    private final Window mWindow;

    public FStatusBarPaddingHandler(Window window)
    {
        if (window == null)
            throw new NullPointerException("window is null");
        mWindow = window;
    }

    @Override
    protected void checkView(View view)
    {
        final int statusBarHeight = FStatusBarUtils.getStatusBarHeight(mWindow, view.getContext());
        if (view.getPaddingTop() != statusBarHeight)
            view.setPadding(view.getPaddingLeft(), statusBarHeight, view.getPaddingRight(), view.getPaddingBottom());
    }
}
