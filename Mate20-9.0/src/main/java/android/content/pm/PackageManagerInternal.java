package android.content.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageParser;
import android.os.Bundle;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public abstract class PackageManagerInternal {
    public static final int PACKAGE_BROWSER = 4;
    public static final int PACKAGE_INSTALLER = 2;
    public static final int PACKAGE_SETUP_WIZARD = 1;
    public static final int PACKAGE_SYSTEM = 0;
    public static final int PACKAGE_SYSTEM_TEXT_CLASSIFIER = 5;
    public static final int PACKAGE_VERIFIER = 3;

    public interface ExternalSourcesPolicy {
        public static final int USER_BLOCKED = 1;
        public static final int USER_DEFAULT = 2;
        public static final int USER_TRUSTED = 0;

        int getPackageTrustedToInstallApps(String str, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface KnownPackage {
    }

    public interface PackageListObserver {
        void onPackageAdded(String str);

        void onPackageRemoved(String str);
    }

    public interface PackagesProvider {
        String[] getPackages(int i);
    }

    public interface SyncAdapterPackagesProvider {
        String[] getPackages(String str, int i);
    }

    public abstract void addIsolatedUid(int i, int i2);

    public abstract boolean canAccessComponent(int i, ComponentName componentName, int i2);

    public abstract boolean canAccessInstantApps(int i, int i2);

    public abstract void checkPackageStartable(String str, int i);

    public abstract boolean filterAppAccess(PackageParser.Package packageR, int i, int i2);

    public abstract ActivityInfo getActivityInfo(ComponentName componentName, int i, int i2, int i3);

    public abstract ApplicationInfo getApplicationInfo(String str, int i, int i2, int i3);

    public abstract ComponentName getDefaultHomeActivity(int i);

    public abstract PackageParser.Package getDisabledPackage(String str);

    public abstract ComponentName getHomeActivitiesAsUser(List<ResolveInfo> list, int i);

    public abstract boolean getHwCertPermission(boolean z, PackageParser.Package packageR, String str);

    public abstract String getInstantAppPackageName(int i);

    public abstract String getKnownPackageName(int i, int i2);

    public abstract String getNameForUid(int i);

    public abstract List<PackageInfo> getOverlayPackages(int i);

    public abstract PackageParser.Package getPackage(String str);

    public abstract PackageInfo getPackageInfo(String str, int i, int i2, int i3);

    public abstract PackageList getPackageList(PackageListObserver packageListObserver);

    public abstract int getPackageTargetSdkVersion(String str);

    public abstract int getPackageUid(String str, int i, int i2);

    public abstract int getPermissionFlagsTEMP(String str, String str2, int i);

    public abstract String getSetupWizardPackageName();

    public abstract String getSuspendedDialogMessage(String str, int i);

    public abstract Bundle getSuspendedPackageLauncherExtras(String str, int i);

    public abstract String getSuspendingPackage(String str, int i);

    public abstract List<String> getTargetPackageNames(int i);

    public abstract int getUidTargetSdkVersion(int i);

    public abstract float getUserAspectRatio(String str, String str2);

    public abstract float getUserMaxAspectRatio(String str);

    public abstract void grantDefaultPermissionsToDefaultDialerApp(String str, int i);

    public abstract void grantDefaultPermissionsToDefaultSimCallManager(String str, int i);

    public abstract void grantDefaultPermissionsToDefaultSmsApp(String str, int i);

    public abstract void grantDefaultPermissionsToDefaultUseOpenWifiApp(String str, int i);

    public abstract void grantEphemeralAccess(int i, Intent intent, int i2, int i3);

    public abstract void grantRuntimePermission(String str, String str2, int i, boolean z);

    public abstract boolean hasInstantApplicationMetadata(String str, int i);

    public abstract boolean hasSignatureCapability(int i, int i2, @PackageParser.SigningDetails.CertCapabilities int i3);

    public abstract void installPackageAsUser(String str, IPackageInstallObserver2 iPackageInstallObserver2, int i, String str2, int i2);

    public abstract boolean isDataRestoreSafe(Signature signature, String str);

    public abstract boolean isDataRestoreSafe(byte[] bArr, String str);

    public abstract boolean isInMWPortraitWhiteList(String str);

    public abstract boolean isInstantApp(String str, int i);

    public abstract boolean isInstantAppInstallerComponent(ComponentName componentName);

    public abstract boolean isLegacySystemApp(PackageParser.Package packageR);

    public abstract boolean isPackageDataProtected(int i, String str);

    public abstract boolean isPackageEphemeral(int i, String str);

    public abstract boolean isPackagePersistent(String str);

    public abstract boolean isPackageStateProtected(String str, int i);

    public abstract boolean isPackageSuspended(String str, int i);

    public abstract boolean isPermissionsReviewRequired(String str, int i);

    public abstract boolean isResolveActivityComponent(ComponentInfo componentInfo);

    public abstract void notifyPackageUse(String str, int i);

    public abstract void pruneInstantApps();

    public abstract List<ResolveInfo> queryIntentActivities(Intent intent, int i, int i2, int i3);

    public abstract List<ResolveInfo> queryIntentServices(Intent intent, int i, int i2, int i3);

    public abstract void removeIsolatedUid(int i);

    public abstract void removePackageListObserver(PackageListObserver packageListObserver);

    public abstract void requestInstantAppResolutionPhaseTwo(AuxiliaryResolveInfo auxiliaryResolveInfo, Intent intent, String str, String str2, Bundle bundle, int i);

    public abstract ProviderInfo resolveContentProvider(String str, int i, int i2);

    public abstract ResolveInfo resolveIntent(Intent intent, String str, int i, int i2, boolean z, int i3);

    public abstract ResolveInfo resolveService(Intent intent, String str, int i, int i2, int i3);

    public abstract void revokeRuntimePermission(String str, String str2, int i, boolean z);

    public abstract void setDeviceAndProfileOwnerPackages(int i, String str, SparseArray<String> sparseArray);

    public abstract void setDialerAppPackagesProvider(PackagesProvider packagesProvider);

    public abstract boolean setEnabledOverlayPackages(int i, String str, List<String> list);

    public abstract void setExternalSourcesPolicy(ExternalSourcesPolicy externalSourcesPolicy);

    public abstract void setKeepUninstalledPackages(List<String> list);

    public abstract void setLocationPackagesProvider(PackagesProvider packagesProvider);

    public abstract void setSimCallManagerPackagesProvider(PackagesProvider packagesProvider);

    public abstract void setSmsAppPackagesProvider(PackagesProvider packagesProvider);

    public abstract void setSyncAdapterPackagesprovider(SyncAdapterPackagesProvider syncAdapterPackagesProvider);

    public abstract void setUseOpenWifiAppPackagesProvider(PackagesProvider packagesProvider);

    public abstract void setVoiceInteractionPackagesProvider(PackagesProvider packagesProvider);

    public abstract void updatePermissionFlagsTEMP(String str, String str2, int i, int i2, int i3);

    public abstract boolean wasPackageEverLaunched(String str, int i);

    public PackageList getPackageList() {
        return getPackageList(null);
    }
}
