package com.android.server;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.rms.HwSysResManager;
import android.rms.iaware.AwareConstant;
import android.rms.iaware.CollectData;
import android.util.Slog;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputMethodClient;
import com.android.internal.view.IInputMethodManager;
import com.android.internal.view.InputBindResult;
import com.android.server.inputmethod.HwInputMethodUtils;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wifipro.WifiProCHRManager;
import com.android.server.wifipro.WifiProCommonUtils;
import huawei.android.view.inputmethod.HwSecImmHelper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;

public class HwInputMethodManagerService extends InputMethodManagerService {
    public static final String ACTION_SECURE_IME = "com.huawei.secime.SoftKeyboard";
    private static final Locale CHINA_LOCALE = new Locale("zh");
    private static final String DESCRIPTOR = "android.view.inputmethod.InputMethodManager";
    private static final Locale ENGLISH_LOCALE = new Locale("en");
    public static final int FLAG_SHOW_INPUT = 65536;
    private static final String INPUT_METHOD_ENABLED_FILE = "bflag";
    private static final boolean IS_CHINA_AREA = "CN".equalsIgnoreCase(SystemProperties.get(WifiProCommonUtils.KEY_PROP_LOCALE, ""));
    public static final int SECURE_IME_NO_HIDE_FLAG = 4096;
    public static final String SECURE_IME_PACKAGENAME = "com.huawei.secime";
    public static final String SECURITY_INPUT_METHOD_ID = "com.huawei.secime/.SoftKeyboard";
    public static final String SECURITY_INPUT_SERVICE_NAME = "input_method_secure";
    static final String TAG = "HwInputMethodManagerService";
    private static final int TRANSACTION_isUseSecureIME = 1001;
    private static final int UNBIND_SECIME_IF_SHOULD = 10000;
    private static final boolean isSupportedSecIme = IS_CHINA_AREA;
    private boolean isSecMethodUsing = false;
    /* access modifiers changed from: private */
    public boolean isSecureIMEEnabled = false;
    private boolean isSecureIMEExist = false;
    private SecureSettingsObserver mSecureSettingsObserver;
    private IInputMethodManager mSecurityInputMethodService;

    private class SecureSettingsObserver extends ContentObserver {
        boolean mRegistered = false;
        int mUserId;

        public SecureSettingsObserver() {
            super(new Handler());
        }

        public void registerContentObserverInner(int userId) {
            Slog.d(HwInputMethodManagerService.TAG, "SecureSettingsObserver mRegistered=" + this.mRegistered + " new user=" + userId + " current user=" + this.mUserId);
            if (!this.mRegistered || this.mUserId != userId) {
                ContentResolver resolver = HwInputMethodManagerService.this.mContext.getContentResolver();
                if (this.mRegistered) {
                    resolver.unregisterContentObserver(this);
                    this.mRegistered = false;
                }
                this.mUserId = userId;
                resolver.registerContentObserver(Settings.Secure.getUriFor(HwInputMethodUtils.SETTINGS_SECURE_KEYBOARD_CONTROL), false, this, userId);
                this.mRegistered = true;
            }
        }

        public void onChange(boolean selfChange, Uri uri) {
            if (uri != null) {
                Slog.i(HwInputMethodManagerService.TAG, "SecureSettingsObserver onChange, uri = " + uri.toString());
                if (Settings.Secure.getUriFor(HwInputMethodUtils.SETTINGS_SECURE_KEYBOARD_CONTROL).equals(uri)) {
                    boolean unused = HwInputMethodManagerService.this.isSecureIMEEnabled = HwInputMethodUtils.isSecureIMEEnable(HwInputMethodManagerService.this.mContext, HwInputMethodManagerService.this.mSettings.getCurrentUserId());
                    Slog.i(HwInputMethodManagerService.TAG, "isSecureIMEEnabled = " + HwInputMethodManagerService.this.isSecureIMEEnabled);
                }
            }
        }
    }

    public HwInputMethodManagerService(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void createFlagIfNecessary(int newUserId) {
        File flagFile = new File(Environment.getUserSystemDirectory(newUserId), INPUT_METHOD_ENABLED_FILE);
        boolean existFlag = false;
        try {
            if (flagFile.exists()) {
                existFlag = true;
            } else {
                flagFile.createNewFile();
            }
            synchronized (this.mEnabledFileMap) {
                this.mEnabledFileMap.put(String.valueOf(newUserId), Boolean.valueOf(existFlag));
            }
        } catch (IOException e) {
            Slog.e(TAG, "Unable to create flag file!");
        }
    }

    /* access modifiers changed from: protected */
    public boolean isFlagExists(int userId) {
        boolean value;
        synchronized (this.mEnabledFileMap) {
            value = ((Boolean) this.mEnabledFileMap.get(String.valueOf(userId))).booleanValue();
        }
        Slog.i(TAG, "isFlagExists  value = " + value + ", userId = " + userId);
        return value;
    }

    /* access modifiers changed from: protected */
    public void ensureEnableSystemIME(String id, InputMethodInfo p, Context context, int userId) {
        if (isFlagExists(userId)) {
            return;
        }
        if (isValidSystemDefaultIme(p, context) || isSystemImeThatHasEnglishSubtype(p) || isSystemImeThatHasChinaSubtype(p)) {
            Slog.i(TAG, "ensureEnableSystemIME will setInputMethodEnabledLocked");
            setInputMethodEnabledLocked(id, true);
        }
    }

    private static boolean isSystemIme(InputMethodInfo inputMethod) {
        return (inputMethod.getServiceInfo().applicationInfo.flags & 1) != 0;
    }

    private boolean isValidSystemDefaultIme(InputMethodInfo imi, Context context) {
        if (!this.mSystemReady || !isSystemIme(imi)) {
            return false;
        }
        if (imi.getIsDefaultResourceId() != 0) {
            try {
                if (context.createPackageContext(imi.getPackageName(), 0).getResources().getBoolean(imi.getIsDefaultResourceId()) && containsSubtypeOf(imi, context.getResources().getConfiguration().locale.getLanguage())) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            }
        }
        if (imi.getSubtypeCount() == 0) {
            Slog.w(TAG, "Found no subtypes in a system IME: " + imi.getPackageName());
        }
        return false;
    }

    private static boolean isSystemImeThatHasEnglishSubtype(InputMethodInfo imi) {
        if (!isSystemIme(imi)) {
            return false;
        }
        return containsSubtypeOf(imi, ENGLISH_LOCALE.getLanguage());
    }

    private static boolean isSystemImeThatHasChinaSubtype(InputMethodInfo imi) {
        if (!isSystemIme(imi)) {
            return false;
        }
        return containsSubtypeOf(imi, CHINA_LOCALE.getLanguage());
    }

    private static boolean containsSubtypeOf(InputMethodInfo imi, String language) {
        int N = imi.getSubtypeCount();
        for (int i = 0; i < N; i++) {
            if (imi.getSubtypeAt(i).getLocale().startsWith(language)) {
                return true;
            }
        }
        return false;
    }

    public void systemRunning(StatusBarManagerService statusBar) {
        if (isSupportedSecIme) {
            this.mSecureSettingsObserver = new SecureSettingsObserver();
            this.mSecureSettingsObserver.registerContentObserverInner(this.mSettings.getCurrentUserId());
            this.isSecureIMEEnabled = HwInputMethodUtils.isSecureIMEEnable(this.mContext, this.mSettings.getCurrentUserId());
            this.isSecureIMEExist = existSecureIME();
            Slog.i(TAG, "systemRunning isSecureIMEEnabled = " + this.isSecureIMEEnabled + ", isSecureIMEExist = " + this.isSecureIMEExist);
        }
        HwInputMethodManagerService.super.systemRunning(statusBar);
    }

    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code != 1001) {
            return HwInputMethodManagerService.super.onTransact(code, data, reply, flags);
        }
        data.enforceInterface(DESCRIPTOR);
        boolean result = isUseSecureIME();
        reply.writeNoException();
        reply.writeInt(result);
        return true;
    }

    private IInputMethodManager getSecurityInputMethodService() {
        if (this.mSecurityInputMethodService == null) {
            this.mSecurityInputMethodService = IInputMethodManager.Stub.asInterface(ServiceManager.getService(SECURITY_INPUT_SERVICE_NAME));
        }
        return this.mSecurityInputMethodService;
    }

    private boolean isUseSecureIME() {
        return this.isSecureIMEExist && (this.isSecureIMEEnabled || HwInputMethodUtils.isNeedSecIMEInSpecialScenes(this.mContext)) && isSupportedSecIme && getSecurityInputMethodService() != null;
    }

    public void addClient(IInputMethodClient client, IInputContext inputContext, int uid, int pid) {
        HwInputMethodManagerService.super.addClient(client, inputContext, uid, pid);
        if (getSecurityInputMethodService() != null) {
            try {
                this.mSecurityInputMethodService.addClient(client, inputContext, uid, pid);
            } catch (RemoteException e) {
            }
        }
    }

    public void removeClient(IInputMethodClient client) {
        HwInputMethodManagerService.super.removeClient(client);
        if (getSecurityInputMethodService() != null) {
            try {
                this.mSecurityInputMethodService.removeClient(client);
            } catch (RemoteException e) {
            }
        }
    }

    public void setImeWindowStatus(IBinder token, IBinder startInputToken, int vis, int backDisposition) {
        if ((isUseSecureIME() || this.isSecMethodUsing) && (token == null || this.mCurToken != token)) {
            try {
                this.mSecurityInputMethodService.setImeWindowStatus(token, startInputToken, vis, backDisposition);
            } catch (RemoteException e) {
                Slog.e(TAG, "setImeWindowStatus, remote exception");
            }
        } else {
            HwInputMethodManagerService.super.setImeWindowStatus(token, startInputToken, vis, backDisposition);
        }
    }

    public InputBindResult startInputOrWindowGainedFocus(int startInputReason, IInputMethodClient client, IBinder windowToken, int controlFlags, int softInputMode, int windowFlags, EditorInfo attribute, IInputContext inputContext, int missingMethods, int unverifiedTargetSdkVersion) {
        int controlFlags2;
        int controlFlags3;
        boolean isHide;
        IInputMethodClient iInputMethodClient = client;
        EditorInfo editorInfo = attribute;
        if (windowToken == null) {
            Slog.d(TAG, "------------startInput--------------");
            if (isUseSecureIME() || this.isSecMethodUsing) {
                if (isPasswordType(editorInfo)) {
                    Slog.d(TAG, "isPasswordType(attribute) == true, using SecurityIMMS");
                    if (this.mInputShown) {
                        Slog.d(TAG, "hideNormalSoftInput = " + isHide);
                        if (isHide) {
                            controlFlags2 = controlFlags | 65536;
                            this.isSecMethodUsing = true;
                            return this.mSecurityInputMethodService.startInputOrWindowGainedFocus(startInputReason, iInputMethodClient, windowToken, controlFlags2, softInputMode, windowFlags, editorInfo, inputContext, missingMethods, unverifiedTargetSdkVersion);
                        }
                    }
                    controlFlags2 = controlFlags;
                    this.isSecMethodUsing = true;
                    try {
                        return this.mSecurityInputMethodService.startInputOrWindowGainedFocus(startInputReason, iInputMethodClient, windowToken, controlFlags2, softInputMode, windowFlags, editorInfo, inputContext, missingMethods, unverifiedTargetSdkVersion);
                    } catch (RemoteException e) {
                    }
                } else {
                    boolean isHide2 = false;
                    try {
                        isHide2 = this.mSecurityInputMethodService.hideSoftInput(iInputMethodClient, 0, null);
                    } catch (RemoteException e2) {
                    }
                    Slog.d(TAG, "hideSecSoftInput = " + isHide2);
                    if (isHide2) {
                        controlFlags3 = controlFlags | 65536;
                    } else {
                        controlFlags3 = controlFlags;
                    }
                    this.isSecMethodUsing = false;
                    return HwInputMethodManagerService.super.startInputOrWindowGainedFocus(startInputReason, iInputMethodClient, windowToken, controlFlags2, softInputMode, windowFlags, editorInfo, inputContext, missingMethods, unverifiedTargetSdkVersion);
                }
            }
        } else if (isUseSecureIME() || this.isSecMethodUsing) {
            if (isPasswordType(editorInfo)) {
                Slog.d(TAG, "windowGainedFocus  isPasswordType(attribute) = true");
                this.isSecMethodUsing = true;
                HwInputMethodManagerService.super.startInputOrWindowGainedFocus(startInputReason, iInputMethodClient, windowToken, controlFlags, 1, windowFlags, editorInfo, inputContext, missingMethods, unverifiedTargetSdkVersion);
                HwInputMethodManagerService.super.hideSoftInput(iInputMethodClient, 0, null);
                try {
                    return this.mSecurityInputMethodService.startInputOrWindowGainedFocus(startInputReason, iInputMethodClient, windowToken, controlFlags, softInputMode, windowFlags, editorInfo, inputContext, missingMethods, unverifiedTargetSdkVersion);
                } catch (RemoteException e3) {
                }
            } else {
                this.isSecMethodUsing = false;
                try {
                    this.mSecurityInputMethodService.hideSoftInput(iInputMethodClient, 0, null);
                    InputBindResult inputBindRes = HwInputMethodManagerService.super.startInputOrWindowGainedFocus(startInputReason, client, windowToken, controlFlags, softInputMode, windowFlags, attribute, inputContext, missingMethods, unverifiedTargetSdkVersion);
                    this.mSecurityInputMethodService.startInputOrWindowGainedFocus(10000, iInputMethodClient, windowToken, controlFlags, softInputMode, windowFlags, editorInfo, inputContext, missingMethods, unverifiedTargetSdkVersion);
                    return inputBindRes;
                } catch (RemoteException e4) {
                }
            }
        }
        controlFlags2 = controlFlags;
        return HwInputMethodManagerService.super.startInputOrWindowGainedFocus(startInputReason, iInputMethodClient, windowToken, controlFlags2, softInputMode, windowFlags, editorInfo, inputContext, missingMethods, unverifiedTargetSdkVersion);
    }

    public void setInputMethod(IBinder token, String id) {
        if (!SECURITY_INPUT_METHOD_ID.equals(id)) {
            HwInputMethodManagerService.super.setInputMethod(token, id);
        }
    }

    public void setInputMethodAndSubtype(IBinder token, String id, InputMethodSubtype subtype) {
        if (!isSupportedSecIme || !SECURITY_INPUT_METHOD_ID.equals(id)) {
            HwInputMethodManagerService.super.setInputMethodAndSubtype(token, id, subtype);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean setInputMethodEnabledLocked(String id, boolean enabled) {
        if (!isSupportedSecIme || !SECURITY_INPUT_METHOD_ID.equals(id)) {
            return HwInputMethodManagerService.super.setInputMethodEnabledLocked(id, enabled);
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setInputMethodLocked(String id, int subtypeId) {
        if (!isSupportedSecIme || !SECURITY_INPUT_METHOD_ID.equals(id)) {
            HwInputMethodManagerService.super.setInputMethodLocked(id, subtypeId);
        }
    }

    public void showInputMethodPickerFromClient(IInputMethodClient client, int auxiliarySubtypeMode) {
        if (this.isSecMethodUsing) {
            Slog.w(TAG, "SecIME is using, don't show the input method chooser dialog!");
        } else {
            HwInputMethodManagerService.super.showInputMethodPickerFromClient(client, auxiliarySubtypeMode);
        }
    }

    public void hideMySoftInput(IBinder token, int flags) {
        if ((isUseSecureIME() || this.isSecMethodUsing) && (token == null || this.mCurToken != token)) {
            try {
                this.mSecurityInputMethodService.hideMySoftInput(token, flags);
            } catch (RemoteException e) {
            }
        } else {
            HwInputMethodManagerService.super.hideMySoftInput(token, flags);
        }
    }

    private void reportToAware(int event) {
        HwSysResManager resManager = HwSysResManager.getInstance();
        if (resManager == null) {
            Slog.w(TAG, "iAware is not started");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("eventid", event);
        int resid = AwareConstant.ResourceType.getReousrceId(AwareConstant.ResourceType.RESOURCE_SHOW_INPUTMETHOD);
        if (resManager.isResourceNeeded(resid)) {
            CollectData data = new CollectData(resid, System.currentTimeMillis(), bundle);
            long id = Binder.clearCallingIdentity();
            resManager.reportData(data);
            Binder.restoreCallingIdentity(id);
        }
    }

    public boolean showSoftInput(IInputMethodClient client, int flags, ResultReceiver resultReceiver) {
        reportToAware(34);
        if (isUseSecureIME() && this.isSecMethodUsing) {
            try {
                hideSoftInput(client, 4096, resultReceiver);
                return this.mSecurityInputMethodService.showSoftInput(client, flags, resultReceiver);
            } catch (RemoteException e) {
            }
        }
        return HwInputMethodManagerService.super.showSoftInput(client, flags, resultReceiver);
    }

    public void showMySoftInput(IBinder token, int flags) {
        if ((isUseSecureIME() || this.isSecMethodUsing) && (token == null || this.mCurToken != token)) {
            try {
                this.mSecurityInputMethodService.showMySoftInput(token, flags);
            } catch (RemoteException e) {
            }
        } else {
            HwInputMethodManagerService.super.showMySoftInput(token, flags);
        }
    }

    private boolean isPasswordType(EditorInfo attribute) {
        if (attribute != null) {
            return HwSecImmHelper.isPasswordInputType(attribute.inputType, true);
        }
        if (this.isSecMethodUsing) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isSecureIME(String packageName) {
        if (SECURE_IME_PACKAGENAME.equals(packageName)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean shouldBuildInputMethodList(String packageName) {
        if (!isSupportedSecIme || !SECURE_IME_PACKAGENAME.equals(packageName)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void updateSecureIMEStatus() {
        this.isSecureIMEExist = existSecureIME();
        Slog.i(TAG, "isSecureIMEExist = " + this.isSecureIMEExist);
    }

    private boolean existSecureIME() {
        List<ResolveInfo> packages = this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent(ACTION_SECURE_IME), 0, this.mSettings.getCurrentUserId());
        if (packages == null || packages.size() <= 0) {
            return false;
        }
        return true;
    }

    public boolean hideSoftInput(IInputMethodClient client, int flags, ResultReceiver resultReceiver) {
        if ((isUseSecureIME() || this.isSecMethodUsing) && this.isSecMethodUsing) {
            if (4096 != flags) {
                try {
                    this.mSecurityInputMethodService.hideSoftInput(client, flags, resultReceiver);
                } catch (RemoteException e) {
                }
            } else {
                flags = 0;
            }
        }
        return HwInputMethodManagerService.super.hideSoftInput(client, flags, resultReceiver);
    }

    /* access modifiers changed from: protected */
    public void switchUserExtra(int userId) {
        if (isSupportedSecIme) {
            if (this.mSecureSettingsObserver != null) {
                this.mSecureSettingsObserver.registerContentObserverInner(userId);
            }
            updateSecureIMEStatus();
            this.isSecureIMEEnabled = HwInputMethodUtils.isSecureIMEEnable(this.mContext, this.mSettings.getCurrentUserId());
            Slog.i(TAG, "isSecureIMEEnabled = " + this.isSecureIMEEnabled);
            if (isUseSecureIME() || this.isSecMethodUsing) {
                HwSecureInputMethodManagerInternal mLocalServices = (HwSecureInputMethodManagerInternal) LocalServices.getService(HwSecureInputMethodManagerInternal.class);
                if (mLocalServices != null) {
                    mLocalServices.setClientActiveFlag();
                } else {
                    Slog.w(TAG, "HwSecureInputMethodManagerInternal is not exist !");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getNaviBarEnabledDefValue() {
        int defValue;
        boolean FRONT_FINGERPRINT_NAVIGATION = SystemProperties.getBoolean("ro.config.hw_front_fp_navi", false);
        int FRONT_FINGERPRINT_NAVIGATION_TRIKEY = SystemProperties.getInt("ro.config.hw_front_fp_trikey", 0);
        if (!FRONT_FINGERPRINT_NAVIGATION) {
            defValue = 1;
        } else {
            boolean isTrikeyExist = isTrikeyExist();
            if (FRONT_FINGERPRINT_NAVIGATION_TRIKEY == 1 && isTrikeyExist) {
                defValue = 0;
            } else if (SystemProperties.get("ro.config.hw_optb", "0").equals("156")) {
                defValue = 0;
            } else {
                defValue = 1;
            }
        }
        Slog.i(TAG, "NaviBar defValue = " + defValue);
        return defValue;
    }

    private boolean isTrikeyExist() {
        try {
            Class clazz = Class.forName("huawei.android.os.HwGeneralManager");
            return ((Boolean) clazz.getDeclaredMethod("isSupportTrikey", new Class[0]).invoke(clazz.getDeclaredMethod(WifiProCHRManager.LOG_GET_INSTANCE_API_NAME, new Class[0]).invoke(clazz, new Object[0]), new Object[0])).booleanValue();
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | NullPointerException | InvocationTargetException e) {
            Slog.e(TAG, "isTrikeyExist, reflect method handle, and has exception: " + e);
            return false;
        } catch (Exception ex) {
            Slog.e(TAG, "isTrikeyExist, other exception: " + ex);
            return false;
        }
    }

    public void reportFullscreenMode(IBinder token, boolean fullscreen) {
        if ((isUseSecureIME() || this.isSecMethodUsing) && (token == null || this.mCurToken != token)) {
            try {
                this.mSecurityInputMethodService.reportFullscreenMode(token, fullscreen);
            } catch (RemoteException e) {
                Slog.e(TAG, "reportFullscreenMode, remote exception");
            }
            return;
        }
        HwInputMethodManagerService.super.reportFullscreenMode(token, fullscreen);
    }
}
