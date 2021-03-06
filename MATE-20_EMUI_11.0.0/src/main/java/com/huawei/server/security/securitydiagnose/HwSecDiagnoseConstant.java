package com.huawei.server.security.securitydiagnose;

public interface HwSecDiagnoseConstant {
    public static final int BD_PRIORITY = 27;
    public static final int BIT_ADBD = 64;
    public static final int BIT_KCODE = 1;
    public static final int BIT_RPROC = 1024;
    public static final int BIT_SETIDS = 512;
    public static final int BIT_SE_HOOKS = 4;
    public static final int BIT_SE_STATUS = 8;
    public static final int BIT_SU = 16;
    public static final int BIT_SYSMOUNT = 32;
    public static final int BIT_SYSTEM_CALL = 2;
    public static final int BIT_SYS_PROPS = 256;
    public static final int BIT_VERIFYBOOT = 128;
    public static final String DEVICE_RENEW_SN_CODE = "ISBN";
    public static final String DEVICE_RENEW_TIME = "ServerTime";
    public static final String MALAPP_APK_CERT = "apkSignature";
    public static final String MALAPP_APK_HASH = "apkHash";
    public static final String MALAPP_APK_MANIFEST_DIR = "META-INF/MANIFEST.MF";
    public static final String MALAPP_APK_NAME = "apkName";
    public static final String MALAPP_APK_PACKAGES = "packages";
    public static final String MALAPP_APK_SHA256 = "SHA256";
    public static final String MALAPP_PATCH_ID = "patchId";
    public static final String MALAPP_PATCH_ID_OLD = "patch_id";
    public static final String MALAPP_UID = "uid";
    public static final int OEMINFO_ID_DEVICE_RENEW = 170;
    public static final int OEMINFO_ID_ROOT_CHECK = 171;
    public static final String PROPERTY_ROOT_STATUS = "persist.sys.root.status";
    public static final int REPORT_ID_DEVICE_RENEW = 101;
    public static final int REPORT_ID_ROOT_CHECK = 102;
    public static final String ROOT_CHECK_ADBD = "CHECK_ADBD";
    public static final String ROOT_CHECK_CODE = "KCODE";
    public static final String ROOT_CHECK_PROP = "CHECK_PROP";
    public static final String ROOT_CHECK_SETIDS = "CHECK_SETIDS";
    public static final String ROOT_CHECK_SU = "CHECK_SU";
    public static final String ROOT_ERR_CODE = "ERR_CODE";
    public static final String ROOT_HID_PROC = "HID_PROC";
    public static final String ROOT_ROOT_PRO = "RPROCS";
    public static final String ROOT_SE_HOOKS = "SE_HOOKS";
    public static final String ROOT_SE_STATUS = "SE_STATUS";
    public static final String ROOT_STATUS = "ROOT_STATUS";
    public static final String ROOT_SYS_CALL = "SYS_CALL";
    public static final String ROOT_SYS_RW = "SYS_RW";
    public static final String ROOT_VB_STATUS = "VB_STATUS";
}
