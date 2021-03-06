package com.huawei.ace.runtime;

public final class LibraryLoader {
    private static final String ANDROID_LIB_NAME = "ace_engine_android.z";
    private static final String ANDROID_V8_LIB_NAME = "ace_engine_v8_android.z";
    private static final String ANDROID_WATCH_LIB_NAME = "ace_watch_android.z";
    private static final Object JNI_LOAD_LOCK = new Object();
    private static final String LIB_NAME = "ace_engine.z";
    private static final String LIB_NAME_DEBUG = "ace_engine_debug.z";
    private static final String LOG_TAG = "LibraryLoader";
    private static final String V8_LIB_NAME = "ace_engine_v8.z";
    private static final String WATCH_LIB_NAME = "ace_watch.z";
    private static final String WATCH_LIB_NAME_DEBUG = "ace_watch_debug.z";
    private static final String WATCH_PRELOAD_LIB_NAME = "ace_watch_preload.z";
    private static volatile boolean jniLoaded = false;
    private static volatile boolean jniPreLoaded = false;
    private static volatile boolean loadDebugSo = false;
    private static volatile boolean useAndroidLib = false;
    private static volatile boolean useV8Lib = false;
    private static volatile boolean useWatchLib = false;

    private LibraryLoader() {
    }

    public static void setUseV8Lib() {
        synchronized (JNI_LOAD_LOCK) {
            useV8Lib = true;
        }
    }

    public static void setUseWatchLib() {
        synchronized (JNI_LOAD_LOCK) {
            useWatchLib = true;
        }
    }

    public static void setUseAndroidLib() {
        synchronized (JNI_LOAD_LOCK) {
            useAndroidLib = true;
        }
    }

    public static void setLoadDebugSo() {
        synchronized (JNI_LOAD_LOCK) {
            loadDebugSo = true;
        }
    }

    static boolean loadJniLibrary() {
        if (jniLoaded) {
            ALog.i(LOG_TAG, "Has loaded ace lib");
            return true;
        }
        synchronized (JNI_LOAD_LOCK) {
            if (jniLoaded) {
                ALog.i(LOG_TAG, "Has loaded ace lib");
                return true;
            }
            try {
                ALog.i(LOG_TAG, "Load ace lib");
                if (useAndroidLib) {
                    if (useWatchLib) {
                        System.loadLibrary(ANDROID_WATCH_LIB_NAME);
                    } else if (useV8Lib) {
                        System.loadLibrary(ANDROID_V8_LIB_NAME);
                    } else {
                        System.loadLibrary(ANDROID_LIB_NAME);
                    }
                } else if (useWatchLib) {
                    if (!loadDebugSo) {
                        System.loadLibrary(WATCH_LIB_NAME);
                    } else {
                        System.loadLibrary(WATCH_LIB_NAME_DEBUG);
                    }
                } else if (useV8Lib) {
                    System.loadLibrary(V8_LIB_NAME);
                } else if (!loadDebugSo) {
                    System.loadLibrary(LIB_NAME);
                } else {
                    System.loadLibrary(LIB_NAME_DEBUG);
                }
                jniLoaded = true;
            } catch (UnsatisfiedLinkError e) {
                ALog.e(LOG_TAG, "Could not load ace lib. Exception: " + e.getMessage());
            }
            ALog.i(LOG_TAG, "Load ace lib ok");
            return jniLoaded;
        }
    }

    public static boolean loadPreloadLibrary() {
        if (jniPreLoaded) {
            ALog.i(LOG_TAG, "Has loaded ace_pre lib");
            return true;
        }
        try {
            System.loadLibrary(WATCH_PRELOAD_LIB_NAME);
            jniPreLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            ALog.e(LOG_TAG, "Could not load ace pre lib. Exception: " + e.getMessage());
        }
        return true;
    }
}
