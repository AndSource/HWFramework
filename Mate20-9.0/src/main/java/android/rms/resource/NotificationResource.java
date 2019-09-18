package android.rms.resource;

import android.rms.HwSysSpeedRes;
import android.rms.utils.Utils;
import android.util.Log;

public final class NotificationResource extends HwSysSpeedRes {
    private static final String TAG = "RMS.NotificationResource";
    private static NotificationResource mNotificationResource;

    private NotificationResource() {
        super(10, TAG);
    }

    public static synchronized NotificationResource getInstance() {
        synchronized (NotificationResource.class) {
            if (mNotificationResource == null) {
                mNotificationResource = new NotificationResource();
            }
            if (mNotificationResource.getConfig()) {
                if (Utils.DEBUG) {
                    Log.d(TAG, "getInstance create new resource");
                }
                NotificationResource notificationResource = mNotificationResource;
                return notificationResource;
            }
            if (Utils.DEBUG) {
                Log.d(TAG, "RMS not ready!");
            }
            return null;
        }
    }

    public int acquire(int callingUid, String pkg, int processTpye) {
        int strategy = 1;
        int typeID = super.getTypeId(callingUid, pkg, processTpye);
        if (this.mResourceConfig != null && isResourceSpeedOverload(callingUid, pkg, typeID) && !isInWhiteList(pkg, 0)) {
            strategy = getSpeedOverloadStrategy(typeID);
            if (Utils.DEBUG) {
                Log.d(TAG, "getOverloadStrategy = " + strategy);
            }
        }
        return strategy;
    }

    public void clear(int callingUid, String pkg, int processTpye) {
        this.mResourceFlowControl.removeResourceSpeedRecord(super.getResourceId(callingUid, pkg, processTpye));
        this.mResourceManger.clearResourceStatus(callingUid, 10);
    }
}
