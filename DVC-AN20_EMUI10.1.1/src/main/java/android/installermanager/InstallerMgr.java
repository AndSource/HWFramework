package android.installermanager;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.rms.iaware.AwareLog;
import android.rms.iaware.Events;

public class InstallerMgr {
    public static final int EVENT_FORK_NOTIFY = 2;
    public static final int EVENT_INSTALLED = 1;
    public static final int EVENT_INSTALLING = 0;
    public static final String KEY_BUNDLE_DEXOPT_PID = "dexopt_pid";
    public static final String KEY_BUNDLE_EVENT_ID = "eventId";
    public static final String KEY_BUNDLE_INSTALLER_NAME = "installer_name";
    public static final String KEY_BUNDLE_INSTALLER_UID = "installer_uid";
    public static final String KEY_BUNDLE_PACKAGE_NAME = "package_name";
    private static final String TAG = "InstallerMgr";
    private static InstallerMgr sInstallerMgr;
    private static Object sObject = new Object();
    private IBinder mAwareService;
    private InstallerInfo mInstallerInfo;
    private Object mLock = new Object();

    private InstallerMgr() {
    }

    public static InstallerMgr getInstance() {
        InstallerMgr installerMgr;
        synchronized (sObject) {
            if (sInstallerMgr == null) {
                sInstallerMgr = new InstallerMgr();
            }
            installerMgr = sInstallerMgr;
        }
        return installerMgr;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003d, code lost:
        if (r5 != null) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0040, code lost:
        r4 = new android.os.Bundle();
        r4.putInt(android.installermanager.InstallerMgr.KEY_BUNDLE_EVENT_ID, r9);
        r4.putString(android.installermanager.InstallerMgr.KEY_BUNDLE_INSTALLER_NAME, r5);
        r4.putString("package_name", r5);
        r4.putInt(android.installermanager.InstallerMgr.KEY_BUNDLE_INSTALLER_UID, r5);
        notifiyEvent(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005d, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003b, code lost:
        if (r5 == null) goto L_?;
     */
    private void notifiyInstallEvent(int eventId) {
        synchronized (this.mLock) {
            if (this.mInstallerInfo != null) {
                String installerPkgName = this.mInstallerInfo.installerPkgName;
                String pkgName = this.mInstallerInfo.pkgName;
                int installerUid = this.mInstallerInfo.installerUid;
                AwareLog.d(TAG, "notifiyInstallEvent eventId = " + eventId + " pkgName = " + pkgName);
            }
        }
    }

    private IBinder getAwareService() {
        return ServiceManager.getService("hwsysresmanager");
    }

    private void notifiyEvent(Bundle bundle) {
        if (this.mAwareService == null) {
            this.mAwareService = getAwareService();
            if (this.mAwareService == null) {
                return;
            }
        }
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken("android.rms.IHwSysResManager");
            data.writeBundle(bundle);
            this.mAwareService.transact(Events.AppEvents.EVENT_APP_INSTALLER_MANAGER, data, reply, 0);
        } catch (RemoteException e) {
            AwareLog.e(TAG, "mAwareService ontransact " + e.getMessage());
        } catch (Throwable th) {
            data.recycle();
            reply.recycle();
            throw th;
        }
        data.recycle();
        reply.recycle();
    }

    public void installPackage(int eventId, String installerPkgName, String pkgName) {
        AwareLog.d(TAG, "installPackage eventId = " + eventId + " installerPkgName = " + installerPkgName + " pkgName = " + pkgName);
        synchronized (this.mLock) {
            if (eventId == 0) {
                try {
                    this.mInstallerInfo = new InstallerInfo(installerPkgName, pkgName, 0);
                    notifiyInstallEvent(eventId);
                } catch (Throwable th) {
                    throw th;
                }
            } else if (eventId == 1) {
                notifiyInstallEvent(eventId);
                this.mInstallerInfo = null;
            } else {
                AwareLog.w(TAG, "installPackage unknown eventId = " + eventId);
            }
        }
    }

    public static class InstallerInfo {
        public String installerPkgName;
        public int installerUid;
        public String pkgName;

        public InstallerInfo(String installerPkgName2, String pkgName2, int installerUid2) {
            this.installerPkgName = installerPkgName2;
            this.pkgName = pkgName2;
            this.installerUid = installerUid2;
        }
    }
}
