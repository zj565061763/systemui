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

        FStatusBarUtils.setTransparent(getWindow());
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
        FStatusBar.get(getOwnerActivity()).addConfig(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        FStatusBar.get(getOwnerActivity()).removeConfig(this);
    }

    @Override
    public void show()
    {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}
