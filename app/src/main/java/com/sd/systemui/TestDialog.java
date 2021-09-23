package com.sd.systemui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.WindowManager;

import com.sd.lib.systemui.statusbar.FStatusBar;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;

public class TestDialog extends Dialog implements FStatusBar.Config {

    public TestDialog(Activity activity) {
        super(activity, R.style.AppDialog);
        setContentView(R.layout.dialog_test);

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        FStatusBarUtils.setBarColor(this, Color.TRANSPARENT);
        FStatusBarUtils.setContentExtension(this, true);
        FStatusBar.of(activity).applyConfig(this);
    }

    @Override
    public FStatusBar.Brightness getStatusBarBrightness() {
        return FStatusBar.Brightness.dark;
    }
}
