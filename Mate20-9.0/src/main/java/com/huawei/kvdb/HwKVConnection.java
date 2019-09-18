package com.huawei.kvdb;

import android.content.Context;
import android.os.Environment;
import android.os.UserHandle;
import android.util.Log;
import com.huawei.hsm.permission.StubController;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class HwKVConnection {
    private static final int DATABASE_MALFORMED = -1;
    private static final String DB_NAME = "thumbnail.db";
    private static final int FILE_NOT_DELETE = 0;
    private static final String MEDIA_PROVIDER = "com.android.providers.media";
    private static final int SQLITE_FULL = 13;
    private static final int STEP_CONTINUE = 0;
    private static final int STEP_DONE = 1;
    private static final int STEP_ERROR = 2;
    private static final int SUCCESS = 0;
    private static final String TABLE_NAME = "kv";
    private static final String TAG = "kvdb_thumbnail";
    public HwKVData kvData = null;
    private long mBlob = 0;
    private boolean mCanOpen = true;
    public byte[] mData = null;
    private int mDataSize = StubController.PERMISSION_CALL_FORWARD;
    private String mDbName = "";
    private long mHandle = 0;
    private String mPackageName = "";
    private String mPath = "";
    private boolean mReadOnly = true;
    private String mTableName = "";

    private native int nativeBlobBytes(long j);

    private native int nativeBlobClose(long j);

    private native long nativeBlobOpen(long j, long j2, String str);

    private native int nativeBlobRead(long j, byte[] bArr, int i);

    private native int nativeClose(long j);

    private native int nativeFinalize(long j);

    private native int nativeGetKeyNum(long j, String str);

    private native long nativeGetLong(long j);

    private native int nativeIsFileDeleted(long j);

    private native long nativeOpen(String str, String str2, String str3, boolean z);

    private native long nativePrepare(String str, long j);

    private native int nativePut(long j, long j2, byte[] bArr, int i, String str);

    private native int nativeRemove(long j, long j2, String str);

    private native int nativeStep(long j);

    static {
        System.loadLibrary("kvdb_jni");
    }

    private long callNativeOpen(String Path, String DbName, String TableName, boolean ReadOnly) {
        return nativeOpen(Path, DbName, TableName, ReadOnly);
    }

    private int callNativeClose(long Handle) {
        return nativeClose(Handle);
    }

    private int callNativePut(long Handle, long Key, byte[] Value, int ValueSize, String TableName) {
        return nativePut(Handle, Key, Value, ValueSize, TableName);
    }

    private int callNativeRemove(long Handle, long Key, String TableName) {
        return nativeRemove(Handle, Key, TableName);
    }

    private long callNativeBlobOpen(long Handle, long Key, String TableName) {
        return nativeBlobOpen(Handle, Key, TableName);
    }

    private int callNativeBlobBytes(long BlobHandle) {
        return nativeBlobBytes(BlobHandle);
    }

    private int callNativeBlobRead(long BlobHandle, byte[] Value, int ValueSize) {
        return nativeBlobRead(BlobHandle, Value, ValueSize);
    }

    private int callNativeBlobClose(long BlobHandle) {
        return nativeBlobClose(BlobHandle);
    }

    private long callNativePrepare(String TableName, long Handle) {
        return nativePrepare(TableName, Handle);
    }

    private int callNativeStep(long StmtHandle) {
        return nativeStep(StmtHandle);
    }

    private int callNativeFinalize(long StmtHandle) {
        return nativeFinalize(StmtHandle);
    }

    private long callNativeGetLong(long StmtHandle) {
        return nativeGetLong(StmtHandle);
    }

    private int callNativeIsFileDeleted(long Handle) {
        return nativeIsFileDeleted(Handle);
    }

    private int callNativeGetKeyNum(long Handle, String TableName) {
        return nativeGetKeyNum(Handle, TableName);
    }

    HwKVConnection(String path, String dbName, String tableName, String packageName, Context context, Boolean readOnly) {
        if (context == null || !context.getPackageName().equals(packageName)) {
            this.mCanOpen = false;
        } else {
            this.mPackageName = packageName;
        }
        if (!readOnly.booleanValue() || UserHandle.myUserId() == 0) {
            this.mPath = path;
        } else {
            String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            this.mPath = externalPath.substring(0, externalPath.lastIndexOf("/")) + "/0/" + "Android/data/com.android.providers.media/thumbnail_cache";
        }
        this.mReadOnly = readOnly.booleanValue();
        this.mDbName = dbName;
        this.mTableName = tableName;
    }

    private void deleteDbFiles() {
        String db;
        try {
            boolean res1 = new File(db + "-shm").delete();
            boolean res2 = new File(db + "-wal").delete();
            boolean res3 = new File(db).delete();
            if (!res1 || !res2 || !res3) {
                Log.e(TAG, "Failure: delete database files.");
            }
        } catch (IOException e) {
            Log.e(TAG, "Failure: delete dbPath is invalible.");
        }
    }

    private void doCheck() {
        if (!this.mReadOnly && !MEDIA_PROVIDER.equals(this.mPackageName)) {
            this.mCanOpen = false;
        } else if (this.mPath == null || this.mPath.equals("")) {
            this.mCanOpen = false;
        } else if (!DB_NAME.equals(this.mDbName)) {
            this.mCanOpen = false;
        } else if (!TABLE_NAME.equals(this.mTableName)) {
            this.mCanOpen = false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean open() {
        doCheck();
        if (!this.mCanOpen) {
            return false;
        }
        this.kvData = new HwKVData();
        this.mData = new byte[this.mDataSize];
        this.mHandle = callNativeOpen(this.mPath, this.mDbName, this.mTableName, this.mReadOnly);
        if (this.mHandle > 0) {
            return true;
        }
        if (this.mHandle == -1) {
            deleteDbFiles();
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean close() {
        return callNativeClose(this.mHandle) == 0;
    }

    /* JADX INFO: finally extract failed */
    public HwKVData get(long key) throws HwKVDatabaseDeleteException {
        byte[] value;
        if (callNativeIsFileDeleted(this.mHandle) == 0) {
            try {
                this.mBlob = callNativeBlobOpen(this.mHandle, key, this.mTableName);
                if (this.mBlob <= 0) {
                    if (this.mBlob == -1) {
                        deleteDbFiles();
                    }
                    if (this.mBlob > 0) {
                        callNativeBlobClose(this.mBlob);
                    }
                    this.mBlob = 0;
                    return null;
                }
                int size = callNativeBlobBytes(this.mBlob);
                if (size <= 0) {
                    if (this.mBlob > 0) {
                        callNativeBlobClose(this.mBlob);
                    }
                    this.mBlob = 0;
                    return null;
                }
                if (size > this.mDataSize || this.mData == null) {
                    value = new byte[size];
                } else {
                    value = this.mData;
                }
                int rc = callNativeBlobRead(this.mBlob, value, size);
                if (rc != 0) {
                    if (rc == -1) {
                        deleteDbFiles();
                    }
                    if (this.mBlob > 0) {
                        callNativeBlobClose(this.mBlob);
                    }
                    this.mBlob = 0;
                    return null;
                }
                this.kvData.value = value;
                this.kvData.size = size;
                HwKVData hwKVData = this.kvData;
                if (this.mBlob > 0) {
                    callNativeBlobClose(this.mBlob);
                }
                this.mBlob = 0;
                return hwKVData;
            } catch (Throwable th) {
                if (this.mBlob > 0) {
                    callNativeBlobClose(this.mBlob);
                }
                this.mBlob = 0;
                throw th;
            }
        } else {
            throw new HwKVDatabaseDeleteException();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean put(long key, byte[] value, int size) throws HwKVDatabaseDeleteException, HwKVFullException {
        if (this.mReadOnly || value == null || value.length == 0 || value.length != size) {
            return false;
        }
        if (callNativeIsFileDeleted(this.mHandle) == 0) {
            int rc = callNativePut(this.mHandle, key, value, size, this.mTableName);
            if (rc == 0) {
                return true;
            }
            if (rc == -1) {
                deleteDbFiles();
            } else if (rc == 13) {
                throw new HwKVFullException();
            }
            return false;
        }
        throw new HwKVDatabaseDeleteException();
    }

    /* access modifiers changed from: package-private */
    public boolean remove(long key) throws HwKVDatabaseDeleteException, HwKVFullException {
        if (this.mReadOnly) {
            return false;
        }
        if (callNativeIsFileDeleted(this.mHandle) == 0) {
            int rc = callNativeRemove(this.mHandle, key, this.mTableName);
            if (rc == 0) {
                return true;
            }
            if (rc == -1) {
                deleteDbFiles();
            } else if (rc == 13) {
                throw new HwKVFullException();
            }
            return false;
        }
        throw new HwKVDatabaseDeleteException();
    }

    /* access modifiers changed from: package-private */
    public boolean hasKey(long key) throws HwKVDatabaseDeleteException {
        boolean res;
        if (callNativeIsFileDeleted(this.mHandle) == 0) {
            try {
                this.mBlob = callNativeBlobOpen(this.mHandle, key, this.mTableName);
                if (this.mBlob <= 0) {
                    if (this.mBlob == -1) {
                        deleteDbFiles();
                    }
                    res = false;
                } else {
                    res = true;
                }
                return res;
            } finally {
                if (this.mBlob > 0) {
                    callNativeBlobClose(this.mBlob);
                }
                this.mBlob = 0;
            }
        } else {
            throw new HwKVDatabaseDeleteException();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public Hashtable<Long, Long> getAllKeys() throws HwKVDatabaseDeleteException {
        int rc;
        if (callNativeIsFileDeleted(this.mHandle) == 0) {
            long stmt = 0;
            try {
                stmt = callNativePrepare(this.mTableName, this.mHandle);
                if (stmt <= 0) {
                    if (stmt == -1) {
                        deleteDbFiles();
                    }
                    if (stmt > 0) {
                        callNativeFinalize(stmt);
                    }
                    return null;
                }
                Hashtable<Long, Long> hashTable = new Hashtable<>();
                while (true) {
                    rc = callNativeStep(stmt);
                    if (rc != 0) {
                        break;
                    }
                    long res = callNativeGetLong(stmt);
                    hashTable.put(Long.valueOf(res), Long.valueOf(res));
                }
                if (rc == -1) {
                    deleteDbFiles();
                }
                if (rc == 1) {
                    if (stmt > 0) {
                        callNativeFinalize(stmt);
                    }
                    return hashTable;
                }
                if (stmt > 0) {
                    callNativeFinalize(stmt);
                }
                return null;
            } catch (Throwable th) {
                if (stmt > 0) {
                    callNativeFinalize(stmt);
                }
                throw th;
            }
        } else {
            throw new HwKVDatabaseDeleteException();
        }
    }

    /* access modifiers changed from: package-private */
    public int getKeyNum() throws HwKVDatabaseDeleteException {
        if (callNativeIsFileDeleted(this.mHandle) == 0) {
            int res = callNativeGetKeyNum(this.mHandle, this.mTableName);
            if (res != -1) {
                return res;
            }
            deleteDbFiles();
            return 0;
        }
        throw new HwKVDatabaseDeleteException();
    }
}
