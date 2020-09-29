package com.sd.systemui;

import android.app.Activity;

import com.sd.lib.dialoger.impl.FDialoger;
import com.sd.lib.systemui.statusbar.FStatusBar;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;

public class TestDialog extends FDialoger implements FStatusBar.Config
{
    private int mSystemUiVisibility;

    public TestDialog(Activity activity)
    {
        super(activity);
        setPadding(0, 0, 0, 0);
        setContentView(R.layout.dialog_test);

        FStatusBarUtils.setTransparent(dialog());
    }

    @Override
    public FStatusBar.Brightness getStatusBarBrightness()
    {
        return FStatusBar.Brightness.dark;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FStatusBar.of(getOwnerActivity()).applyConfig(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        FStatusBar.of(getOwnerActivity()).removeConfig(this);
    }
}
