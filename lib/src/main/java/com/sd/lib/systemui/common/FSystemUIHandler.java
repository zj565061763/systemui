package com.sd.lib.systemui.common;

import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class FSystemUIHandler {
    private final Map<View, String> mViewHolder = new WeakHashMap<>();

    /**
     * 添加View
     */
    public void addView(View view) {
        if (view == null) {
            return;
        }
        if (mViewHolder.containsKey(view)) {
            return;
        }

        mViewHolder.put(view, "");
        view.addOnLayoutChangeListener(mOnLayoutChangeListener);
        checkView(view);
    }

    /**
     * 移除View
     */
    public void removeView(View view) {
        if (view == null) {
            return;
        }

        if (mViewHolder.remove(view) != null) {
            view.removeOnLayoutChangeListener(mOnLayoutChangeListener);
        }
    }

    private final View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            checkView(v);
        }
    };

    protected abstract void checkView(View view);
}
