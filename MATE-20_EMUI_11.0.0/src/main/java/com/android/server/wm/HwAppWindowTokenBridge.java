package com.android.server.wm;

import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;
import android.os.IBinder;
import android.view.IApplicationToken;

public class HwAppWindowTokenBridge implements IHwAppWindowTokenEx {
    private HwAppWindowTokenBridgeEx mHwAppWindowTokenBridgeEx;

    public void setHwAppWindowTokenBridgeEx(HwAppWindowTokenBridgeEx bridgeEx) {
        this.mHwAppWindowTokenBridgeEx = bridgeEx;
    }

    public HwAppWindowTokenBridgeEx getHwAppWindowTokenBridgeEx() {
        return this.mHwAppWindowTokenBridgeEx;
    }

    public boolean isHwStartWindowEnabled(String pkg, CompatibilityInfo compatInfo) {
        return this.mHwAppWindowTokenBridgeEx.isHwStartWindowEnabled(pkg, compatInfo);
    }

    public int continueHwStartWindow(ApplicationInfo appInfo, IBinder transferFrom, IApplicationToken token, boolean[] windowArgs) {
        return this.mHwAppWindowTokenBridgeEx.continueHwStartWindow(appInfo, transferFrom, token, windowArgs);
    }

    public boolean isHwMwAnimationBelowStack(AppWindowToken appWindowToken) {
        return this.mHwAppWindowTokenBridgeEx.isHwMwAnimationBelowStack(new AppWindowTokenExt(appWindowToken));
    }

    public IBinder getTransferFrom(ApplicationInfo appInfo) {
        return this.mHwAppWindowTokenBridgeEx.getTransferFrom(appInfo);
    }

    public void cancelInputMethodRetractAnimation(WindowState inputMethodWindow) {
        this.mHwAppWindowTokenBridgeEx.cancelInputMethodRetractAnimation(new WindowStateEx(inputMethodWindow));
    }
}
