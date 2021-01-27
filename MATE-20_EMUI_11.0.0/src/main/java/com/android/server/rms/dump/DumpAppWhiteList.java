package com.android.server.rms.dump;

import android.content.Context;
import com.android.server.rms.iaware.appmng.AppMngConfig;
import com.android.server.rms.iaware.appmng.AwareDefaultConfigList;
import java.io.PrintWriter;

public final class DumpAppWhiteList {
    public static void dumpAppWhiteList(PrintWriter pw, Context context, String[] args) {
        if (args != null) {
            for (String arg : args) {
                if ("enable".equals(arg)) {
                    AwareDefaultConfigList.enable(context);
                    return;
                } else if ("disable".equals(arg)) {
                    AwareDefaultConfigList.disable();
                    return;
                } else if ("enable_log".equals(arg)) {
                    AwareDefaultConfigList.enableDebug();
                    return;
                } else if ("disable_log".equals(arg)) {
                    AwareDefaultConfigList.disableDebug();
                    return;
                } else if ("enable_restart".equals(arg)) {
                    AppMngConfig.setRestartFlag(true);
                    return;
                } else if ("disable_restart".equals(arg)) {
                    AppMngConfig.setRestartFlag(false);
                    return;
                }
            }
        }
        AwareDefaultConfigList.getInstance().dump(null, pw);
    }
}