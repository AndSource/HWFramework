package com.android.server.rms.iaware.cpu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.rms.iaware.AwareLog;
import com.huawei.displayengine.IDisplayEngineService;
import com.huawei.pgmng.plug.PGSdk;
import java.util.concurrent.atomic.AtomicBoolean;

public class CPUAppRecogMngProxy {
    private static final int CONNECT_PG_DELAYED = 5000;
    private static final int CYCLE_MAX_NUM = 6;
    private static final int MSG_PG_CONNECT = 1;
    private static final String TAG = "CPUAppRecogMngProxy";
    private CPUAppRecogMngProxyHandler mCPUAppRecogMngProxyHandler;
    private Context mContext;
    private int mCycleNum = 0;
    private PGSdk mPGSdk = null;
    private AtomicBoolean mRegistered = new AtomicBoolean(false);
    private PGSdk.Sink mSink = null;

    private class CPUAppRecogMngProxyHandler extends Handler {
        public CPUAppRecogMngProxyHandler() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what != 1) {
                AwareLog.w(CPUAppRecogMngProxy.TAG, "msg.what = " + msg.what + "  is Invalid !");
                return;
            }
            CPUAppRecogMngProxy.this.getPGSdk();
        }
    }

    public CPUAppRecogMngProxy(Context context) {
        this.mContext = context;
        this.mCPUAppRecogMngProxyHandler = new CPUAppRecogMngProxyHandler();
        getPGSdk();
    }

    private void callPGRegisterListener() {
        if (!this.mRegistered.get()) {
            if (this.mPGSdk == null || this.mSink == null) {
                AwareLog.e(TAG, "callPGregisterListener null == mPGSdk");
                return;
            }
            try {
                this.mPGSdk.enableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_2DGAME_FRONT);
                this.mPGSdk.enableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_3DGAME_FRONT);
                this.mPGSdk.enableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_VIDEO_START);
                this.mPGSdk.enableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_VIDEO_END);
                this.mRegistered.set(true);
            } catch (RemoteException e) {
                this.mPGSdk = null;
                this.mCycleNum = 0;
                AwareLog.e(TAG, "mPGSdk registerSink && enableStateEvent happend RemoteException!");
            }
        }
    }

    private void callPGUnregisterListener() {
        if (this.mPGSdk != null && this.mRegistered.get()) {
            try {
                this.mPGSdk.disableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_2DGAME_FRONT);
                this.mPGSdk.disableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_3DGAME_FRONT);
                this.mPGSdk.disableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_VIDEO_START);
                this.mPGSdk.disableStateEvent(this.mSink, IDisplayEngineService.DE_ACTION_PG_VIDEO_END);
                this.mRegistered.set(false);
            } catch (RemoteException e) {
                AwareLog.e(TAG, "callPGUnregisterListener happend RemoteException!");
            }
        }
    }

    /* access modifiers changed from: private */
    public void getPGSdk() {
        if (this.mPGSdk == null) {
            this.mPGSdk = PGSdk.getInstance();
            if (this.mPGSdk != null || this.mCycleNum >= 6) {
                callPGRegisterListener();
                return;
            }
            this.mCycleNum++;
            this.mCPUAppRecogMngProxyHandler.removeMessages(1);
            this.mCPUAppRecogMngProxyHandler.sendEmptyMessageDelayed(1, 5000);
        }
    }

    private int queryStateType(String pkgName) {
        int pkgType = -1;
        if (this.mPGSdk == null) {
            return -1;
        }
        try {
            pkgType = this.mPGSdk.getPkgType(this.mContext, pkgName);
        } catch (RemoteException e) {
            AwareLog.w(TAG, "queryStateType catch RemoteException!");
        }
        return pkgType;
    }

    public boolean isGameType(String pkgName) {
        return isGameType(queryStateType(pkgName));
    }

    public boolean isGameType(int stateType) {
        if (stateType == 10011 || stateType == 10002) {
            return true;
        }
        return false;
    }

    public boolean isVideoType(int stateType) {
        if (stateType == 10015 || stateType == 10016) {
            return true;
        }
        return false;
    }

    public void register(PGSdk.Sink sink) {
        if (this.mSink == null && sink != null) {
            this.mSink = sink;
            callPGRegisterListener();
        }
    }

    public void unregister(PGSdk.Sink sink) {
        if (this.mSink == sink && sink != null) {
            callPGUnregisterListener();
            this.mSink = null;
        }
    }
}
