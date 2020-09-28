package com.huawei.bd;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.huawei.android.os.SystemPropertiesEx;
import com.huawei.bd.IBDService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONObject;

public final class Reporter {
    public static final int ACTIVITY_CREATE = 1;
    public static final int ACTIVITY_DESTROY = 4;
    public static final int ACTIVITY_PAUSE = 3;
    public static final int ACTIVITY_RESUME = 2;
    public static final int MAX_CONTENT_SIZE = 1024;
    private static final int NO_DATA = -1;
    public static final int PRI_HIGH = 25;
    public static final int PRI_LOW = 5;
    public static final int PRI_NORMAL = 15;
    private static final String TAG = "BD.Reporter";
    private static BDServiceDeathRecipient deathRecipient = new BDServiceDeathRecipient();
    private static int notAvailCount = 0;
    private static int sBetaState = -1;
    private static Method sGetServiceMethod;
    private static IBDService sService = null;
    private static Class<?> sServiceManagerClazz;

    static {
        sServiceManagerClazz = null;
        sGetServiceMethod = null;
        try {
            sServiceManagerClazz = Class.forName("android.os.ServiceManager");
            sGetServiceMethod = sServiceManagerClazz.getMethod("getService", String.class);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ServiceManager ClassNotFoundException");
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "ServiceManager NoSuchMethodException");
        } catch (IllegalArgumentException e3) {
            Log.e(TAG, "ServiceManager IllegalArgumentException");
        } catch (Exception e4) {
            Log.e(TAG, "ServiceManager Exception");
        }
    }

    private Reporter() {
    }

    public static boolean e(Context context, int eventID, String eventMsg) {
        return handleEvent(context, null, null, eventID, eventMsg, 15, -1);
    }

    public static boolean e(Context context, int eventID, String eventMsg, int priority) {
        return handleEvent(context, null, null, eventID, eventMsg, priority, -1);
    }

    public static boolean j(Context context, int eventID, JSONObject eventMsg) {
        return handleEvent(context, null, null, eventID, eventMsg.toString(), 15, -1);
    }

    public static boolean j(Context context, int eventID, JSONObject eventMsg, int priority) {
        return handleEvent(context, null, null, eventID, eventMsg.toString(), priority, -1);
    }

    public static boolean c(Context context, int eventID) {
        return c(context, eventID, 1);
    }

    public static boolean c(Context context, int eventID, int count) {
        if (context == null || eventID > 65535 || count < 1) {
            Log.e(TAG, "null == context || eventID > 65535 || count < 1");
            return false;
        }
        IBDService service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.sendAccumulativeData(context.getPackageName(), restrictID(eventID), count);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "sendAccumulativeData RemoteException");
            return false;
        }
    }

    public static boolean beta(Context context, int eventID, String eventMsg) {
        if (sBetaState == -1) {
            sBetaState = SystemPropertiesEx.getInt("ro.logsystem.usertype", 0);
        }
        if (sBetaState == 3) {
            return e(context, eventID, eventMsg, 5);
        }
        return false;
    }

    private static int restrictID(int eventID) {
        return (eventID & 65535) | 65536;
    }

    private static boolean ifServiceNotAvailable() {
        return notAvailCount > 5;
    }

    private static boolean handleEvent(Context context, String pkgName, String className, int eventID, String message, int priority, int taskId) {
        if (context == null || eventID > 65535) {
            Log.e(TAG, "null == context || eventID > 65535");
            return false;
        }
        IBDService service = getService();
        if (service == null) {
            return false;
        }
        if (pkgName == null) {
            pkgName = context.getPackageName();
        }
        try {
            if (message.length() > 1024) {
                message = message.substring(0, MAX_CONTENT_SIZE);
            }
            service.sendAppActionData(pkgName, restrictID(eventID), message, priority);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "sendAppActionData RemoteException");
            return false;
        }
    }

    private static IBDService getService() {
        Method method;
        if (ifServiceNotAvailable()) {
            return null;
        }
        IBDService iBDService = sService;
        if (iBDService != null) {
            return iBDService;
        }
        if (sServiceManagerClazz == null || (method = sGetServiceMethod) == null) {
            Log.e(TAG, "Can't support using ServiceManager");
            return null;
        }
        try {
            IBinder b = (IBinder) method.invoke(null, "com.huawei.bd.BDService");
            if (b == null) {
                notAvailCount++;
                Log.e(TAG, "Can't getService HwBDService");
                return null;
            } else if (!b.pingBinder()) {
                Log.e(TAG, "HwBDService is not running");
                return null;
            } else {
                b.linkToDeath(deathRecipient, 0);
                sService = IBDService.Stub.asInterface(b);
                Log.i(TAG, sService.toString());
                return sService;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "getService ClassNotFoundException");
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "getService IllegalArgumentException");
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "getService IllegalAccessException");
        } catch (InvocationTargetException e4) {
            Log.e(TAG, "getService InvocationTargetException");
        } catch (RuntimeException e5) {
            Log.e(TAG, "getService RuntimeException");
        } catch (RemoteException e6) {
            Log.e(TAG, "RemoteException:" + e6.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public static final class BDServiceDeathRecipient implements IBinder.DeathRecipient {
        private BDServiceDeathRecipient() {
        }

        public void binderDied() {
            IBDService unused = Reporter.sService = null;
        }
    }
}
