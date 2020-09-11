package com.sd.lib.systemui.navigationbar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;

public class FNavigationBarHeightLayout extends FrameLayout
{
    public FNavigationBarHeightLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private int getBarHeight()
    {
        return FNavigationBarUtils.getNavigationBarHeight(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int barHeight = getBarHeight();
        setMeasuredDimension(getMeasuredWidth(), barHeight);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom)
    {
        super.setPadding(left, 0, right, 0);
    }
}
