package com.huawei.nb.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import com.huawei.nb.utils.logger.DSLog;

public class RemoteServiceConnection {
    /* access modifiers changed from: private */
    public IBinder binder;
    /* access modifiers changed from: private */
    public OnConnectListener connectListener;
    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            IBinder unused = RemoteServiceConnection.this.binder = iBinder;
            if (RemoteServiceConnection.this.connectListener != null) {
                RemoteServiceConnection.this.connectListener.onConnect(RemoteServiceConnection.this.binder);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (RemoteServiceConnection.this.binder != null) {
                IBinder unused = RemoteServiceConnection.this.binder = null;
                if (RemoteServiceConnection.this.connectListener != null) {
                    RemoteServiceConnection.this.connectListener.onDisconnect();
                }
                DSLog.e("Connection to data service is disconnected unexpectedly.", new Object[0]);
            }
        }
    };
    private final Context context;
    private final String serviceAction;
    private final String serviceClassName;
    private final String servicePackageName;

    public interface OnConnectListener {
        void onConnect(IBinder iBinder);

        void onDisconnect();
    }

    public RemoteServiceConnection(Context base, String servicePackageName2, String serviceClassName2) {
        this.context = base;
        this.serviceAction = null;
        this.servicePackageName = servicePackageName2;
        this.serviceClassName = serviceClassName2;
        this.binder = null;
    }

    public RemoteServiceConnection(Context base, String serviceAction2) {
        this.context = base;
        this.serviceAction = serviceAction2;
        this.servicePackageName = null;
        this.serviceClassName = null;
        this.binder = null;
    }

    public boolean open(OnConnectListener listener) {
        Intent remoteServiceIntent;
        this.connectListener = listener;
        if (this.servicePackageName == null || this.serviceClassName == null) {
            remoteServiceIntent = createImplicitIntent(this.serviceAction);
        } else {
            remoteServiceIntent = createExplicitIntent(this.servicePackageName, this.serviceClassName);
        }
        if (remoteServiceIntent == null) {
            DSLog.e("Failed to find the target service.", new Object[0]);
            return false;
        }
        try {
            if (this.context.bindService(remoteServiceIntent, this.connection, 1)) {
                return true;
            }
            DSLog.e("Failed to bind to the target service.", new Object[0]);
            return false;
        } catch (SecurityException e) {
            DSLog.e("Failed to bind service, error: %s.", e.getMessage());
            return false;
        }
    }

    public boolean close() {
        this.context.unbindService(this.connection);
        this.binder = null;
        this.connectListener = null;
        return true;
    }

    private Intent createImplicitIntent(String action) {
        PackageManager pm = this.context.getPackageManager();
        if (pm == null) {
            return null;
        }
        Intent intent = new Intent(action);
        ResolveInfo info = pm.resolveService(intent, 131072);
        if (info == null) {
            return null;
        }
        intent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
        return intent;
    }

    private Intent createExplicitIntent(String packageName, String className) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, className));
        return intent;
    }
}
