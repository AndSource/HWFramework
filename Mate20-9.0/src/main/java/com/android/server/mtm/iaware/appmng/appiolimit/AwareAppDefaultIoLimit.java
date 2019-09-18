package com.android.server.mtm.iaware.appmng.appiolimit;

import android.app.mtm.iaware.appmng.AppMngConstant;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.rms.iaware.AwareLog;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.server.mtm.iaware.appmng.AwareAppMngSort;
import com.android.server.mtm.iaware.appmng.AwareProcessBlockInfo;
import com.android.server.mtm.iaware.appmng.AwareProcessInfo;
import com.android.server.mtm.iaware.appmng.DecisionMaker;
import com.android.server.mtm.iaware.appmng.appclean.CleanSource;
import com.android.server.mtm.iaware.appmng.appstart.datamgr.SystemUnremoveUidCache;
import com.android.server.mtm.taskstatus.ProcessCleaner;
import com.android.server.mtm.taskstatus.ProcessInfo;
import com.android.server.mtm.taskstatus.ProcessInfoCollector;
import com.android.server.rms.iaware.appmng.AwareAppAssociate;
import com.android.server.rms.iaware.appmng.AwareAppKeyBackgroup;
import com.android.server.rms.iaware.feature.IOLimitFeature;
import com.android.server.rms.iaware.feature.IOLimitGroup;
import com.android.server.security.tsmagent.logic.spi.tsm.laser.LaserTSMService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AwareAppDefaultIoLimit extends CleanSource {
    private static boolean DEBUG = false;
    private static int DEFAULT_CAPACITY = 10;
    private static final String IOLIMITED_BAD = "app_iolimit_bad";
    private static final String PACKAGE_CAMERA = "com.huawei.camera";
    private static final String TAG = "AwareAppDefaultIoLimit";
    private static AwareAppDefaultIoLimit mAwareAppDefaultIoLimit = null;
    private Map<Integer, Integer> mHeavyIOPids = new ArrayMap();
    private Map<Integer, Integer> mIOPids = new ArrayMap();
    private Set<Integer> mIolimitUids = new ArraySet();
    private PackageManager mPm = null;
    private Set<String> mSysUnremoveBadPkgs = new ArraySet();
    private SystemUnremoveUidCache mSystemUnremoveUidCache;

    public static synchronized AwareAppDefaultIoLimit getInstance() {
        AwareAppDefaultIoLimit awareAppDefaultIoLimit;
        synchronized (AwareAppDefaultIoLimit.class) {
            if (mAwareAppDefaultIoLimit == null) {
                mAwareAppDefaultIoLimit = new AwareAppDefaultIoLimit();
            }
            awareAppDefaultIoLimit = mAwareAppDefaultIoLimit;
        }
        return awareAppDefaultIoLimit;
    }

    private AwareAppDefaultIoLimit() {
    }

    public void init(Context ctx) {
        if (ctx != null) {
            this.mPm = ctx.getPackageManager();
            this.mSystemUnremoveUidCache = SystemUnremoveUidCache.getInstance(ctx);
            initSystemUnremoveBadList();
        }
    }

    public void deInitDefaultFree() {
        this.mPm = null;
    }

    private void initSystemUnremoveBadList() {
        ArrayList<String> badList = DecisionMaker.getInstance().getRawConfig(AppMngConstant.AppMngFeature.APP_IOLIMIT.getDesc(), IOLIMITED_BAD);
        if (badList != null) {
            this.mSysUnremoveBadPkgs.clear();
            this.mSysUnremoveBadPkgs.addAll(badList);
        }
    }

    private boolean isDownloadUpApp(int pid, int uid, List<String> packageName) {
        return AwareAppKeyBackgroup.getInstance().checkKeyBackgroupByState(5, pid, uid, packageName);
    }

    public void doLimitIO(String pkgName, AppMngConstant.AppIoLimitSource config) {
        if (pkgName == null) {
            AwareLog.w(TAG, "The input params is invalid");
            return;
        }
        List<AwareProcessBlockInfo> awareProcessBlockInfos = getAllIOLimitApp(config, "com.huawei.camera".equals(pkgName));
        if (awareProcessBlockInfos == null || awareProcessBlockInfos.isEmpty()) {
            AwareLog.w(TAG, "no pid need to limit");
            return;
        }
        if (DEBUG) {
            AwareLog.d(TAG, "currPkg:" + pkgName);
        }
        synchronized (this) {
            this.mIOPids.clear();
            this.mHeavyIOPids.clear();
            this.mIolimitUids.clear();
            ArrayList<Integer> assocWithFGPids = getAssocPidsByUid(getUidByPackageName(pkgName));
            for (AwareProcessBlockInfo processInfo : awareProcessBlockInfos) {
                if (processInfo.mCleanType == ProcessCleaner.CleanType.IOLIMIT) {
                    if (!pkgName.equals(processInfo.mPackageName) || UserHandle.getUserId(processInfo.mUid) != AwareAppAssociate.getInstance().getCurUserId()) {
                        List<AwareProcessInfo> infos = processInfo.mProcessList;
                        if (infos != null) {
                            if (infos.size() != 0) {
                                doIOGroup(infos, assocWithFGPids, processInfo.mPackageName);
                            }
                        }
                    }
                }
            }
            doLightIoLimit();
            doHeavyIoLimit();
        }
    }

    private void doIOGroup(List<AwareProcessInfo> infos, ArrayList<Integer> filterPids, String pkgName) {
        if (infos != null && !infos.isEmpty()) {
            for (AwareProcessInfo info : infos) {
                this.mIolimitUids.add(Integer.valueOf(info.mProcInfo.mUid));
                if (!filterPids.contains(Integer.valueOf(info.mPid))) {
                    if (isDownloadUpApp(info.mProcInfo.mPid, info.mProcInfo.mUid, info.mProcInfo.mPackageName)) {
                        this.mHeavyIOPids.put(Integer.valueOf(info.mPid), Integer.valueOf(info.mProcInfo.mUid));
                    } else {
                        this.mIOPids.put(Integer.valueOf(info.mPid), Integer.valueOf(info.mProcInfo.mUid));
                    }
                }
            }
            if (DEBUG) {
                StringBuffer sb = new StringBuffer();
                for (AwareProcessInfo info2 : infos) {
                    sb.append(info2.mPid);
                    sb.append(" ");
                }
                AwareLog.d(TAG, "IO Limit App:" + pkgName + ",pids:" + sb.toString());
            }
        }
    }

    private void doLightIoLimit() {
        if (!this.mIOPids.isEmpty()) {
            IOLimitFeature.getInstance().setIoLimitTaskList(IOLimitGroup.LIGHT, this.mIOPids, true);
            IOLimitFeature.getInstance().enable(IOLimitGroup.LIGHT);
            AwareLog.i(TAG, "do Limit light IO count:" + this.mIOPids.size() + ",pids:" + this.mIOPids.toString());
        }
    }

    private void doHeavyIoLimit() {
        if (!this.mHeavyIOPids.isEmpty()) {
            IOLimitFeature.getInstance().setIoLimitTaskList(IOLimitGroup.HEAVY, this.mHeavyIOPids, true);
            IOLimitFeature.getInstance().enable(IOLimitGroup.HEAVY);
            AwareLog.i(TAG, "do Limit heavy IO count:" + this.mHeavyIOPids.size() + ",pids:" + this.mHeavyIOPids.toString());
        }
    }

    private List<AwareProcessBlockInfo> getAllIOLimitApp(AppMngConstant.AppIoLimitSource config, boolean isCamera) {
        List<ProcessInfo> procList = ProcessInfoCollector.getInstance().getProcessInfoList();
        if (procList.size() == 0) {
            AwareLog.d(TAG, "no pid exit in mtm");
            return null;
        }
        List<AwareProcessInfo> awareProcList = new ArrayList<>(DEFAULT_CAPACITY);
        for (ProcessInfo procInfo : procList) {
            if ((isCamera && AwareAppIoLimitMng.isCameraEnhanced()) || !isUnRemoveApp(procInfo)) {
                AwareProcessInfo awareProcInfo = new AwareProcessInfo(procInfo.mPid, 0, 0, AwareAppMngSort.ClassRate.NORMAL.ordinal(), procInfo);
                awareProcList.add(awareProcInfo);
            }
        }
        if (awareProcList.size() == 0) {
            AwareLog.w(TAG, "no pid need to app filter");
            return null;
        }
        List<AwareProcessBlockInfo> awareProcessBlockInfos = DecisionMaker.getInstance().decideAll(awareProcList, getLevel(), AppMngConstant.AppMngFeature.APP_IOLIMIT, config);
        if (awareProcessBlockInfos != null && awareProcessBlockInfos.size() != 0) {
            return mergeBlock(awareProcessBlockInfos);
        }
        AwareLog.w(TAG, "no pid need to io limit");
        return null;
    }

    private boolean isUnRemoveApp(ProcessInfo procInfo) {
        int uid = procInfo.mAppUid % LaserTSMService.EXCUTE_OTA_RESULT_SUCCESS;
        if (isSystemUnRemoveBadPkg(procInfo)) {
            return false;
        }
        if (uid > 0 && uid <= 10000) {
            return true;
        }
        if (this.mSystemUnremoveUidCache == null || !this.mSystemUnremoveUidCache.checkUidExist(uid)) {
            return false;
        }
        return true;
    }

    private boolean isSystemUnRemoveBadPkg(ProcessInfo procInfo) {
        if (!this.mSysUnremoveBadPkgs.isEmpty() && procInfo.mPackageName != null && !procInfo.mPackageName.isEmpty()) {
            int size = procInfo.mPackageName.size();
            for (int i = 0; i < size; i++) {
                if (this.mSysUnremoveBadPkgs.contains((String) procInfo.mPackageName.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getUidByPackageName(String pkgName) {
        if (pkgName == null) {
            return 0;
        }
        try {
            if (pkgName.isEmpty() || this.mPm == null) {
                return 0;
            }
            return this.mPm.getApplicationInfoAsUser(pkgName, 1, AwareAppAssociate.getInstance().getCurUserId()).uid;
        } catch (PackageManager.NameNotFoundException e) {
            AwareLog.w(TAG, "get application info failed");
            return 0;
        }
    }

    private ArrayList<Integer> getAssocPidsByUid(int uid) {
        ArrayList<Integer> assocPids = new ArrayList<>();
        ArrayList<Integer> pids = ProcessInfoCollector.getInstance().getPidsFromUid(uid, UserHandle.getUserId(uid));
        if (pids.isEmpty()) {
            if (DEBUG) {
                AwareLog.w(TAG, "get assoc pids is empty");
            }
            return assocPids;
        }
        assocPids.addAll(pids);
        int size = pids.size();
        for (int i = 0; i < size; i++) {
            int sPid = pids.get(i).intValue();
            Set<Integer> strongPids = new ArraySet<>();
            AwareAppAssociate.getInstance().getAssocListForPid(sPid, strongPids);
            if (!strongPids.isEmpty()) {
                assocPids.addAll(strongPids);
            }
        }
        if (DEBUG != 0) {
            AwareLog.i(TAG, "get assoc pids by uid:" + uid + ", pids:" + pids + ", assocPids:" + assocPids);
        }
        return assocPids;
    }

    public static void enableDebug() {
        AwareLog.d(TAG, "enableDebug");
        DEBUG = true;
    }

    public static void disableDebug() {
        AwareLog.d(TAG, "disableDebug");
        DEBUG = false;
    }

    public void doUnLimitIO() {
        IOLimitFeature.getInstance().disable(IOLimitGroup.LIGHT);
        IOLimitFeature.getInstance().disable(IOLimitGroup.HEAVY);
        AwareLog.i(TAG, "do IO UnLimit ok");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0070, code lost:
        return;
     */
    public void doRemoveIoPids(int uid, int pid) {
        synchronized (this) {
            if (this.mIolimitUids.contains(Integer.valueOf(uid))) {
                if (pid <= 0 || this.mIOPids.containsKey(Integer.valueOf(pid)) || this.mHeavyIOPids.containsKey(Integer.valueOf(pid))) {
                    ArrayList<Integer> ioLimitPids = getAssocPidsByUid(uid);
                    if (!ioLimitPids.isEmpty()) {
                        IOLimitFeature.getInstance().removeIoLimitTaskList(ioLimitPids);
                        this.mIolimitUids.remove(Integer.valueOf(uid));
                        AwareLog.i(TAG, "doRemoveIoPids uid:" + uid + ",pid:" + pid + ", remove pids:" + ioLimitPids.toString());
                    }
                }
            }
        }
    }

    private int getLevel() {
        return 0;
    }
}
