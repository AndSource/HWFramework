package com.android.server.security.deviceusage;

import android.util.Slog;
import java.lang.reflect.InvocationTargetException;

public class HwOEMInfoUtil {
    private static final String CHR_OEMINFO_CLASS = "com.huawei.android.os.HwOemInfoCustEx";
    private static final int OEMINFO_ACTIVATION_STATUS_ID = 136;
    private static final int OEMINFO_ACTIVATION_STATUS_SIZE = 1;
    private static final byte STATUS_ACTIVATED = -84;
    private static final String TAG = "HwCCOEM";

    public static boolean getActivationStatus() {
        boolean z = true;
        byte[] oEMData = getByteArrayFromOeminfo(136, 1);
        if (oEMData == null || oEMData.length < 1) {
            Slog.e(TAG, "get OEMINFO error");
            return false;
        }
        if (-84 != oEMData[0]) {
            z = false;
        }
        return z;
    }

    public static void setActivated() {
        writeByteArrayToOeminfo(136, 1, new byte[]{STATUS_ACTIVATED});
    }

    public static void resetActivation() {
        writeByteArrayToOeminfo(136, 1, new byte[]{-69});
    }

    private static byte[] getByteArrayFromOeminfo(int type, int sizeOf) {
        try {
            return (byte[]) Class.forName("com.huawei.android.os.HwOemInfoCustEx").getMethod("getByteArrayFromOeminfo", new Class[]{Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(type), Integer.valueOf(sizeOf)});
        } catch (ClassNotFoundException e) {
            Slog.w(TAG, "Unable to find class");
            return null;
        } catch (NoSuchMethodException e2) {
            Slog.w(TAG, "Method getByteArrayFromOeminfo not found");
            return null;
        } catch (IllegalAccessException e3) {
            Slog.w(TAG, "Method getByteArrayFromOeminfo access illegally");
            return null;
        } catch (InvocationTargetException e4) {
            Slog.w(TAG, "Method getByteArrayFromOeminfo exception:" + e4.getMessage());
            return null;
        }
    }

    private static int writeByteArrayToOeminfo(int type, int sizeOf, byte[] mByte) {
        try {
            return ((Integer) Class.forName("com.huawei.android.os.HwOemInfoCustEx").getMethod("writeByteArrayToOeminfo", new Class[]{Integer.TYPE, Integer.TYPE, byte[].class}).invoke(null, new Object[]{Integer.valueOf(type), Integer.valueOf(sizeOf), mByte})).intValue();
        } catch (ClassNotFoundException e) {
            Slog.w(TAG, "Unable to find class");
            return -1;
        } catch (NoSuchMethodException e2) {
            Slog.w(TAG, "Method writeByteArrayToOeminfo not found");
            return -1;
        } catch (IllegalAccessException e3) {
            Slog.w(TAG, "Method writeByteArrayToOeminfo access illegally");
            return -1;
        } catch (InvocationTargetException e4) {
            Slog.w(TAG, "Method writeByteArrayToOeminfo exception:" + e4.getMessage());
            return -1;
        }
    }
}
