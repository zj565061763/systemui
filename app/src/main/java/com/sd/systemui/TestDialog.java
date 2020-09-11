package com.sd.systemui;

import android.app.Activity;
import android.view.ViewGroup;

import com.sd.lib.dialoger.impl.FDialoger;
import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;

public class TestDialog extends FDialoger
{
    public TestDialog(Activity activity)
    {
        super(activity);
        setPadding(0, 0, 0, 0);
        setContentView(R.layout.dialog_test);
        setTransparent();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setTransparent();
    }

    public void setTransparent()
    {
        FStatusBarUtils.setTransparent(getWindow(), true);
        FNavigationBarUtils.setTransparent(getWindow(), true);
        getWindow().getAttributes().height = ViewGroup.LayoutParams.MATCH_PARENT;
    }
}
