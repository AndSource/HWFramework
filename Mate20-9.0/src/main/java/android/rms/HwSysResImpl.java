package android.rms;

import android.annotation.SuppressLint;
import android.cover.CoverManager;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.rms.IUpdateWhiteListCallback;
import android.rms.config.ResourceConfig;
import android.rms.resource.ActivityResource;
import android.rms.resource.AlarmResource;
import android.rms.resource.AppOpsResource;
import android.rms.resource.AppResource;
import android.rms.resource.AppServiceResource;
import android.rms.resource.BroadcastResource;
import android.rms.resource.ContentObserverResource;
import android.rms.resource.CursorResource;
import android.rms.resource.NotificationResource;
import android.rms.resource.OrderedBroadcastObserveResource;
import android.rms.resource.PidsResource;
import android.rms.resource.ReceiverResource;
import android.rms.utils.Utils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HwSysResImpl implements HwSysResource {
    private static final String TAG = "RMS.HwSysResImpl";
    private static boolean enableIaware = SystemProperties.getBoolean("persist.sys.enable_iaware", false);
    private static boolean enableRms = SystemProperties.getBoolean("ro.config.enable_rms", false);
    protected ResourceConfig[] mResourceConfig;
    protected HwSysResManager mResourceManger;
    protected int mResourceType = -1;
    /* access modifiers changed from: private */
    public String mTag = TAG;
    private IUpdateWhiteListCallback mUpdateWhiteListCallback = new IUpdateWhiteListCallback.Stub() {
        public void update() throws RemoteException {
            if (Utils.DEBUG) {
                Log.d(HwSysResImpl.this.mTag, "IUpdateWhiteListCallback was called");
            }
            HwSysResImpl.this.initWhiteLists();
            HwSysResImpl.this.onWhiteListUpdate();
        }
    };
    private Map<Integer, ArrayList<String>> mWhiteListMap = new HashMap();
    private int[] mWhiteListTypes;

    public static HwSysResource getResource(int resourceType) {
        if (enableRms) {
            if (resourceType == 10) {
                return NotificationResource.getInstance();
            }
            switch (resourceType) {
                case 12:
                    return ReceiverResource.getInstance();
                case 13:
                    return AlarmResource.getInstance();
                case 14:
                    return AppOpsResource.getInstance();
                case 15:
                    return PidsResource.getInstance();
                case 16:
                    return CursorResource.getInstance();
                case 17:
                    return AppServiceResource.getInstance();
                case 18:
                    return AppResource.getInstance();
                default:
                    switch (resourceType) {
                        case 29:
                            return ContentObserverResource.getInstance();
                        case 30:
                            return ActivityResource.getInstance();
                    }
            }
        }
        if (enableIaware) {
            if (resourceType == 11) {
                return BroadcastResource.getInstance();
            }
            if (resourceType == 31) {
                return OrderedBroadcastObserveResource.getInstance();
            }
        }
        return null;
    }

    public int acquire(int callingUid, String pkg, int processTpye) {
        return 1;
    }

    public int acquire(int callingUid, String pkg, int processTpye, int count) {
        return 1;
    }

    public int acquire(Uri uri, IContentObserver observer, Bundle args) {
        return 1;
    }

    public int queryPkgPolicy(int type, int value, String key) {
        return 0;
    }

    public void release(int callingUid, String pkg, int processTpye) {
    }

    public void clear(int callingUid, String pkg, int processTpye) {
    }

    public void dump(FileDescriptor fd, PrintWriter pw) {
    }

    public Bundle query() {
        return null;
    }

    private static boolean isUidSystem(int uid, String pkg) {
        int appid = UserHandle.getAppId(uid);
        return appid == 1000 || appid == 1001 || uid == 0 || CoverManager.HALL_STATE_RECEIVER_DEFINE.equals(pkg);
    }

    private int isHuaweiApp(String pkg) {
        return pkg.contains("huawei") ? 1 : 0;
    }

    public int getTypeId(int callingUid, String pkg, int processTpye) {
        int typeID = processTpye;
        if (-1 == processTpye) {
            if (isUidSystem(callingUid, pkg)) {
                typeID = 2;
            } else if (pkg != null) {
                typeID = isHuaweiApp(pkg);
            } else {
                typeID = 0;
            }
        }
        if (Utils.DEBUG) {
            Log.d(TAG, "getTypeId typeID/" + typeID);
        }
        return typeID;
    }

    public long getResourceId(int callingUid, String pkg, int processTpye) {
        int typeID = getTypeId(callingUid, pkg, processTpye);
        int uid = 3 == processTpye ? -1 : callingUid;
        long id = (((long) typeID) << 32) + ((long) uid);
        if (Utils.DEBUG) {
            Log.d(TAG, "getResourceId  typeID/" + typeID + " uid/" + uid + " id/" + id);
        }
        return id;
    }

    /* access modifiers changed from: protected */
    public boolean registerResourceCallback(IUpdateWhiteListCallback updateCallback) {
        return HwSysResManager.getInstance().registerResourceCallback(updateCallback);
    }

    /* access modifiers changed from: protected */
    public ArrayList<String> getResWhiteList(int type) {
        if (this.mWhiteListMap.get(Integer.valueOf(type)) == null) {
            this.mWhiteListMap.put(Integer.valueOf(type), new ArrayList());
        }
        return this.mWhiteListMap.get(Integer.valueOf(type));
    }

    private ArrayList<String> initResWhiteList(int type) {
        String[] whiteList = null;
        ArrayList<String> mList = new ArrayList<>();
        String configWhiteList = HwSysResManager.getInstance().getWhiteList(this.mResourceType, type);
        if (Utils.DEBUG) {
            String str = this.mTag;
            Log.d(str, "getResWhiteList put" + this.mResourceType + type + configWhiteList);
        }
        if (configWhiteList != null) {
            whiteList = configWhiteList.split(";");
        }
        if (whiteList != null) {
            for (int i = 0; i < whiteList.length; i++) {
                if (!mList.contains(whiteList[i]) && !whiteList[i].isEmpty()) {
                    mList.add(whiteList[i]);
                    if (Utils.DEBUG) {
                        Log.d(TAG, "getResWhiteList put the name into the list  type:" + this.mResourceType + ", name:" + whiteList[i] + " , num:" + i);
                    }
                }
            }
        }
        return mList;
    }

    @SuppressLint({"PreferForInArrayList"})
    private boolean isInWhiteList(String pkg, ArrayList<String> whiteList) {
        if (pkg == null || whiteList == null) {
            return false;
        }
        Iterator<String> it = whiteList.iterator();
        while (it.hasNext()) {
            String proc = it.next();
            if (pkg.equals(proc)) {
                if (Utils.DEBUG) {
                    String str = this.mTag;
                    Log.i(str, "HwSysResImpl.isWhiteList pkg = " + pkg + " proc = " + proc);
                }
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isInWhiteList(String pkg, int whiteListType) {
        return isInWhiteList(pkg, this.mWhiteListMap.get(Integer.valueOf(whiteListType)));
    }

    public HwSysResImpl(int resourceType, String tag, int[] whiteListTypes) {
        this.mTag = tag;
        this.mWhiteListTypes = copyWhiteListTypes(whiteListTypes);
        this.mResourceType = resourceType;
        this.mResourceManger = HwSysResManager.getInstance();
    }

    private int[] copyWhiteListTypes(int[] whiteListTypes) {
        if (whiteListTypes == null) {
            return null;
        }
        return Arrays.copyOf(whiteListTypes, whiteListTypes.length);
    }

    /* access modifiers changed from: protected */
    public boolean getConfig() {
        if (this.mResourceConfig != null) {
            return true;
        }
        this.mResourceConfig = this.mResourceManger.getResourceConfig(this.mResourceType);
        if (this.mResourceConfig == null && this.mResourceType == 11) {
            getWhiteListConfig();
            return true;
        }
        int i = 0;
        if (this.mResourceConfig == null) {
            return false;
        }
        if (Utils.DEBUG) {
            while (true) {
                int i2 = i;
                if (i2 >= this.mResourceConfig.length) {
                    break;
                }
                if (Utils.DEBUG) {
                    String str = this.mTag;
                    Log.d(str, "getConfig threshold" + this.mResourceConfig[i2].getResourceThreshold());
                }
                i = i2 + 1;
            }
        }
        getWhiteListConfig();
        return true;
    }

    private void getWhiteListConfig() {
        if (this.mWhiteListTypes != null && this.mWhiteListTypes.length > 0) {
            if (needUpdateWhiteList() && !registerResourceCallback(this.mUpdateWhiteListCallback) && Utils.DEBUG) {
                String str = this.mTag;
                Log.e(str, "Resource[type=" + this.mResourceManger + "] register callback failed");
            }
            initWhiteLists();
        }
    }

    /* access modifiers changed from: protected */
    public void onWhiteListUpdate() {
    }

    /* access modifiers changed from: private */
    public void initWhiteLists() {
        if (this.mWhiteListTypes != null && this.mWhiteListTypes.length > 0) {
            for (int type : this.mWhiteListTypes) {
                ArrayList<String> whitelist = initResWhiteList(type);
                if (whitelist.size() > 0) {
                    if (Utils.DEBUG) {
                        Log.d(this.mTag, "initWhiteLists was called" + whitelist);
                    }
                    this.mWhiteListMap.put(Integer.valueOf(type), whitelist);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean needUpdateWhiteList() {
        return true;
    }
}
