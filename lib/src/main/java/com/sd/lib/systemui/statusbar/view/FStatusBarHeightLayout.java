package com.sd.lib.systemui.statusbar.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sd.lib.systemui.statusbar.FStatusBarUtils;

public class FStatusBarHeightLayout extends FrameLayout
{
    public FStatusBarHeightLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private int getStatusBarHeight()
    {
        final Context context = getContext();
        if (context instanceof Activity)
            return FStatusBarUtils.getStatusBarHeight(((Activity) context).getWindow(), context);
        else
            return FStatusBarUtils.getStatusBarHeight(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int statusBarHeight = getStatusBarHeight();
        setMeasuredDimension(getMeasuredWidth(), statusBarHeight);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom)
    {
        super.setPadding(left, 0, right, 0);
    }
}
