package com.android.server.wm;

import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;
import android.os.IBinder;
import android.view.IApplicationToken;
import com.huawei.annotation.HwSystemApi;

public class HwAppWindowTokenBridgeEx {
    private HwAppWindowTokenBridge mHwAppWindowTokenBridge = new HwAppWindowTokenBridge();

    public HwAppWindowTokenBridgeEx() {
        this.mHwAppWindowTokenBridge.setHwAppWindowTokenBridgeEx(this);
    }

    public HwAppWindowTokenBridge getHwAppWindowTokenBridge() {
        return this.mHwAppWindowTokenBridge;
    }

    public boolean isHwStartWindowEnabled(String pkg, CompatibilityInfo compatInfo) {
        return false;
    }

    public int continueHwStartWindow(ApplicationInfo appInfo, IBinder transferFrom, IApplicationToken token, boolean[] windowArgs) {
        return 0;
    }

    public boolean isHwMwAnimationBelowStack(AppWindowTokenExt appWindowToken) {
        return false;
    }

    public IBinder getTransferFrom(ApplicationInfo appInfo) {
        return null;
    }

    @HwSystemApi
    public void cancelInputMethodRetractAnimation(WindowStateEx inputMethodWindow) {
    }
}
