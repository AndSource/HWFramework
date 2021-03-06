package android.app.usage;

import android.app.usage.NetworkStats.Bucket;
import android.content.Context;
import android.net.DataUsageRequest;
import android.net.INetworkStatsService;
import android.net.INetworkStatsService.Stub;
import android.net.NetworkIdentity;
import android.net.NetworkTemplate;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.Preconditions;

public class NetworkStatsManager {
    public static final int CALLBACK_LIMIT_REACHED = 0;
    public static final int CALLBACK_RELEASED = 1;
    private static final boolean DBG = false;
    private static final String TAG = "NetworkStatsManager";
    private final Context mContext;
    private final INetworkStatsService mService;

    private static class CallbackHandler extends Handler {
        private UsageCallback mCallback;
        private final int mNetworkType;
        private final String mSubscriberId;

        CallbackHandler(Looper looper, int networkType, String subscriberId, UsageCallback callback) {
            super(looper);
            this.mNetworkType = networkType;
            this.mSubscriberId = subscriberId;
            this.mCallback = callback;
        }

        public void handleMessage(Message message) {
            DataUsageRequest request = (DataUsageRequest) getObject(message, DataUsageRequest.PARCELABLE_KEY);
            switch (message.what) {
                case NetworkStatsManager.CALLBACK_LIMIT_REACHED /*0*/:
                    if (this.mCallback != null) {
                        this.mCallback.onThresholdReached(this.mNetworkType, this.mSubscriberId);
                    } else {
                        Log.e(NetworkStatsManager.TAG, "limit reached with released callback for " + request);
                    }
                case NetworkStatsManager.CALLBACK_RELEASED /*1*/:
                    this.mCallback = null;
                default:
            }
        }

        private static Object getObject(Message msg, String key) {
            return msg.getData().getParcelable(key);
        }
    }

    public static abstract class UsageCallback {
        private DataUsageRequest request;

        public abstract void onThresholdReached(int i, String str);
    }

    public NetworkStatsManager(Context context) {
        this.mContext = context;
        this.mService = Stub.asInterface(ServiceManager.getService(Context.NETWORK_STATS_SERVICE));
    }

    public Bucket querySummaryForDevice(int networkType, String subscriberId, long startTime, long endTime) throws SecurityException, RemoteException {
        try {
            NetworkStats stats = new NetworkStats(this.mContext, createTemplate(networkType, subscriberId), startTime, endTime);
            Bucket bucket = stats.getDeviceSummaryForNetwork();
            stats.close();
            return bucket;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Bucket querySummaryForUser(int networkType, String subscriberId, long startTime, long endTime) throws SecurityException, RemoteException {
        try {
            NetworkStats stats = new NetworkStats(this.mContext, createTemplate(networkType, subscriberId), startTime, endTime);
            stats.startSummaryEnumeration();
            stats.close();
            return stats.getSummaryAggregate();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public NetworkStats querySummary(int networkType, String subscriberId, long startTime, long endTime) throws SecurityException, RemoteException {
        try {
            NetworkStats result = new NetworkStats(this.mContext, createTemplate(networkType, subscriberId), startTime, endTime);
            result.startSummaryEnumeration();
            return result;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public NetworkStats queryDetailsForUid(int networkType, String subscriberId, long startTime, long endTime, int uid) throws SecurityException, RemoteException {
        return queryDetailsForUidTag(networkType, subscriberId, startTime, endTime, uid, CALLBACK_LIMIT_REACHED);
    }

    public NetworkStats queryDetailsForUidTag(int networkType, String subscriberId, long startTime, long endTime, int uid, int tag) throws SecurityException {
        try {
            NetworkStats result = new NetworkStats(this.mContext, createTemplate(networkType, subscriberId), startTime, endTime);
            result.startHistoryEnumeration(uid, tag);
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "Error while querying stats for uid=" + uid + " tag=" + tag, e);
            return null;
        }
    }

    public NetworkStats queryDetails(int networkType, String subscriberId, long startTime, long endTime) throws SecurityException, RemoteException {
        try {
            NetworkStats result = new NetworkStats(this.mContext, createTemplate(networkType, subscriberId), startTime, endTime);
            result.startUserUidEnumeration();
            return result;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void registerUsageCallback(int networkType, String subscriberId, long thresholdBytes, UsageCallback callback) {
        registerUsageCallback(networkType, subscriberId, thresholdBytes, callback, null);
    }

    public void registerUsageCallback(int networkType, String subscriberId, long thresholdBytes, UsageCallback callback, Handler handler) {
        Looper looper;
        Preconditions.checkNotNull(callback, "UsageCallback cannot be null");
        if (handler == null) {
            looper = Looper.myLooper();
        } else {
            looper = handler.getLooper();
        }
        DataUsageRequest request = new DataUsageRequest(CALLBACK_LIMIT_REACHED, createTemplate(networkType, subscriberId), thresholdBytes);
        try {
            callback.request = this.mService.registerUsageCallback(this.mContext.getOpPackageName(), request, new Messenger(new CallbackHandler(looper, networkType, subscriberId, callback)), new Binder());
            if (callback.request == null) {
                Log.e(TAG, "Request from callback is null; should not happen");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterUsageCallback(UsageCallback callback) {
        if (callback == null || callback.request == null || callback.request.requestId == 0) {
            throw new IllegalArgumentException("Invalid UsageCallback");
        }
        try {
            this.mService.unregisterUsageRequest(callback.request);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static NetworkTemplate createTemplate(int networkType, String subscriberId) {
        switch (networkType) {
            case CALLBACK_LIMIT_REACHED /*0*/:
                return NetworkTemplate.buildTemplateMobileAll(subscriberId);
            case CALLBACK_RELEASED /*1*/:
                return NetworkTemplate.buildTemplateWifiWildcard();
            default:
                throw new IllegalArgumentException("Cannot create template for network type " + networkType + ", subscriberId '" + NetworkIdentity.scrubSubscriberId(subscriberId) + "'.");
        }
    }
}
