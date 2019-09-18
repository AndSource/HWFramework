package huawei.android.security.facerecognition;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.Surface;
import huawei.android.security.facerecognition.IFaceRecognizeService;
import huawei.android.security.facerecognition.IFaceRecognizeServiceReceiver;
import huawei.android.security.facerecognition.base.HwSecurityEventTask;
import huawei.android.security.facerecognition.base.HwSecurityMsgCenter;
import huawei.android.security.facerecognition.base.HwSecurityTaskThread;
import huawei.android.security.facerecognition.request.AuthenticateRequest;
import huawei.android.security.facerecognition.request.CancelRequest;
import huawei.android.security.facerecognition.request.EnrollRequest;
import huawei.android.security.facerecognition.request.FaceRecognizeRequest;
import huawei.android.security.facerecognition.request.RemoveRequest;
import huawei.android.security.facerecognition.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

public class FaceRecognizeManagerImpl {
    public static final int CODE_CALLBACK_ACQUIRE = 3;
    public static final int CODE_CALLBACK_BUSY = 4;
    public static final int CODE_CALLBACK_CANCEL = 2;
    public static final int CODE_CALLBACK_OUT_OF_MEM = 5;
    public static final int CODE_CALLBACK_RESULT = 1;
    private static final int FACERECOGNITION_OFF = 0;
    private static final int FACERECOGNITION_ON = 1;
    private static final String FACERECOGNIZE_SERVICE = "facerecognition";
    public static final int FACE_MODE_SUPPORT_MASK = -1;
    public static final int FACE_MODE_SUPPORT_POS = 5;
    public static final int FACE_RECONITION_SUPPORT_MASK = 1;
    public static final int FACE_RECONITION_SUPPORT_POS = 4;
    public static final int FACE_SECURE_LEVEL_MASK = 15;
    public static final int FACE_SECURE_LEVEL_POS = 0;
    public static final int FLAG_SHEATH = 1;
    public static final int REQUEST_OK = 0;
    /* access modifiers changed from: private */
    public static final String TAG = FaceRecognizeManagerImpl.class.getSimpleName();
    public static final int TYPE_CALLBACK_AUTH = 2;
    public static final int TYPE_CALLBACK_ENROLL = 1;
    public static final int TYPE_CALLBACK_REMOVE = 3;
    private Context mContext;
    private FaceRecognizeRequest mCurrentReq;
    private FaceRecognizeRequest mPendingReq;

    public interface AcquireInfo {
        public static final int FACE_UNLOCK_FACE_BAD_QUALITY = 4;
        public static final int FACE_UNLOCK_FACE_BLUR = 28;
        public static final int FACE_UNLOCK_FACE_DARKLIGHT = 30;
        public static final int FACE_UNLOCK_FACE_DARKPIC = 39;
        public static final int FACE_UNLOCK_FACE_DOWN = 18;
        public static final int FACE_UNLOCK_FACE_EYE_CLOSE = 22;
        public static final int FACE_UNLOCK_FACE_EYE_OCCLUSION = 21;
        public static final int FACE_UNLOCK_FACE_HALF_SHADOW = 32;
        public static final int FACE_UNLOCK_FACE_HIGHTLIGHT = 31;
        public static final int FACE_UNLOCK_FACE_KEEP = 19;
        public static final int FACE_UNLOCK_FACE_MOUTH_OCCLUSION = 23;
        public static final int FACE_UNLOCK_FACE_MULTI = 27;
        public static final int FACE_UNLOCK_FACE_NOT_COMPLETE = 29;
        public static final int FACE_UNLOCK_FACE_NOT_FOUND = 5;
        public static final int FACE_UNLOCK_FACE_OFFSET_BOTTOM = 11;
        public static final int FACE_UNLOCK_FACE_OFFSET_LEFT = 8;
        public static final int FACE_UNLOCK_FACE_OFFSET_RIGHT = 10;
        public static final int FACE_UNLOCK_FACE_OFFSET_TOP = 9;
        public static final int FACE_UNLOCK_FACE_RISE = 16;
        public static final int FACE_UNLOCK_FACE_ROTATED_LEFT = 15;
        public static final int FACE_UNLOCK_FACE_ROTATED_RIGHT = 17;
        public static final int FACE_UNLOCK_FACE_SCALE_TOO_LARGE = 7;
        public static final int FACE_UNLOCK_FACE_SCALE_TOO_SMALL = 6;
        public static final int FACE_UNLOCK_FAILURE = 3;
        public static final int FACE_UNLOCK_IMAGE_BLUR = 20;
        public static final int FACE_UNLOCK_INVALID_ARGUMENT = 1;
        public static final int FACE_UNLOCK_INVALID_HANDLE = 2;
        public static final int FACE_UNLOCK_LIVENESS_FAILURE = 14;
        public static final int FACE_UNLOCK_LIVENESS_WARNING = 13;
        public static final int FACE_UNLOCK_OK = 0;
        public static final int MG_UNLOCK_COMPARE_FAILURE = 12;
    }

    public static class CallbackHolder {
        private FaceRecognizeCallback mCallback;

        private static class SingletonInstance {
            /* access modifiers changed from: private */
            public static final CallbackHolder instance = new CallbackHolder();

            private SingletonInstance() {
            }
        }

        public static CallbackHolder getInstance() {
            return SingletonInstance.instance;
        }

        public void init(FaceRecognizeCallback callback) {
            this.mCallback = callback;
        }

        public void onCallbackEvent(int reqId, int type, int code, int errorCode) {
            String access$300 = FaceRecognizeManagerImpl.TAG;
            LogUtil.i(access$300, "reqId(" + reqId + "), type(" + FaceRecognizeManagerImpl.getTypeString(type) + "), code(" + FaceRecognizeManagerImpl.getCodeString(code) + "), result(" + FaceRecognizeManagerImpl.getErrorCodeString(code, errorCode) + ")");
            if (this.mCallback != null) {
                if (type == 2 && code == 1 && errorCode == 0) {
                    long current = System.nanoTime();
                    LogUtil.d("PerformanceTime", "Time 6. Authenticate Success --- " + current);
                }
                this.mCallback.onCallbackEvent(reqId, type, code, errorCode);
                return;
            }
            LogUtil.w(FaceRecognizeManagerImpl.TAG, "callback is null, construct FaceRecognizeManager with correct Callback!");
        }
    }

    public interface FaceErrorCode {
        public static final int ALGORITHM_NOT_INIT = 5;
        public static final int BUSY = 13;
        public static final int CAMERA_FAIL = 12;
        public static final int CANCELED = 2;
        public static final int COMPARE_FAIL = 3;
        public static final int FAILED = 1;
        public static final int HAL_INVALIDE = 6;
        public static final int INVALID_PARAMETERS = 9;
        public static final int IN_LOCKOUT_MODE = 8;
        public static final int LOW_TEMP_CAP = 11;
        public static final int NO_FACE_DATA = 10;
        public static final int OVER_MAX_FACES = 7;
        public static final int SUCCESS = 0;
        public static final int TIMEOUT = 4;
        public static final int UNKNOWN = 100;
    }

    public static final class FaceInfo {
        public int faceId;
        public boolean hasAlternateAppearance;
    }

    public static final class FaceRecognitionAbility {
        public int faceMode;
        public boolean isFaceRecognitionSupport;
        public int reserve;
        public int secureLevel;
    }

    public interface FaceRecognizeCallback {
        void onCallbackEvent(int i, int i2, int i3, int i4);
    }

    public static class ServiceHolder implements IBinder.DeathRecipient {
        public static final int HAS_ALTERNATE_APPEARANCE = 1;
        public static final int NOT_HAVE_ALTERNATE_APPEARANCE = 0;
        public static final int OP_FAILED = -1;
        public static final int OP_OK = 0;
        public static final int RANDOM_FAILED = 0;
        private Context mContext;
        private final IFaceRecognizeServiceReceiver mReceiver;
        private IFaceRecognizeService mService;
        private final IBinder mToken;

        private static class SingletonInstance {
            /* access modifiers changed from: private */
            public static final ServiceHolder instance = new ServiceHolder();

            private SingletonInstance() {
            }
        }

        private ServiceHolder() {
            this.mToken = new Binder();
            this.mReceiver = new IFaceRecognizeServiceReceiver.Stub() {
                public void onEnrollResult(int faceId, int userId, int errorCode) throws RemoteException {
                    sendEvent(new FaceRecognizeEvent(7, (long) errorCode, (long) faceId, (long) userId));
                }

                public void onEnrollCancel() throws RemoteException {
                    sendEvent(new FaceRecognizeEvent(9, new long[0]));
                }

                public void onEnrollAcquired(int acquiredInfo, int process) throws RemoteException {
                    sendEvent(new FaceRecognizeEvent(8, (long) acquiredInfo, (long) process));
                }

                public void onAuthenticationResult(int userId, int errorCode) throws RemoteException {
                    sendEvent(new FaceRecognizeEvent(10, (long) errorCode, (long) userId));
                }

                public void onAuthenticationCancel() throws RemoteException {
                    sendEvent(new FaceRecognizeEvent(12, new long[0]));
                }

                public void onAuthenticationAcquired(int acquiredInfo) throws RemoteException {
                    sendEvent(new FaceRecognizeEvent(11, (long) acquiredInfo));
                }

                public void onRemovedResult(int userId, int errorCode) throws RemoteException {
                    sendEvent(new FaceRecognizeEvent(13, (long) errorCode, (long) userId));
                }

                private void sendEvent(FaceRecognizeEvent event) {
                    HwSecurityTaskThread.staticPushTask(new HwSecurityEventTask(event), 2);
                }

                public IBinder asBinder() {
                    return this;
                }
            };
            getFRService();
        }

        /* access modifiers changed from: private */
        public void init(Context context) {
            this.mContext = context;
        }

        public static ServiceHolder getInstance() {
            return SingletonInstance.instance;
        }

        public int authenticate(long opId, int flags) {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                LogUtil.d("auth receiver", this.mReceiver.toString());
                service.authenticate(this.mToken, opId, flags, getCurrentUserId(), this.mReceiver, this.mContext.getOpPackageName());
                return 0;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int cancelAuthentication() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                service.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
                return 0;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int preparePayInfo(byte[] aaid, byte[] nonce, byte[] extra) {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            if (aaid == null) {
                aaid = new byte[0];
            }
            if (nonce == null) {
                nonce = new byte[0];
            }
            if (extra == null) {
                extra = new byte[0];
            }
            try {
                return service.preparePayInfo(this.mToken, aaid, nonce, extra);
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int getPayResult(int[] faceId, byte[] token, int[] tokenLen, byte[] reserve) {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            if (faceId == null || token == null || tokenLen == null) {
                LogUtil.e("getPayResult", "param null");
                return -1;
            }
            if (reserve == null) {
                reserve = new byte[0];
            }
            try {
                return service.getPayResult(this.mToken, faceId, token, tokenLen, reserve);
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int enroll(byte[] authToken, int flags) {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                service.enroll(this.mToken, authToken, flags, getCurrentUserId(), this.mReceiver, this.mContext.getOpPackageName());
                return 0;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int cancelEnrollment() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                service.cancelEnrollment(this.mToken);
                return 0;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int remove(int faceId) {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                service.remove(this.mToken, faceId, getCurrentUserId(), this.mReceiver);
                return 0;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int initAlgo() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.init(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int releaseAlgo() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.release(this.mToken, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int setSecureFaceMode(int mode) {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.setSecureFaceMode(this.mToken, mode);
            } catch (RemoteException e) {
                return -1;
            }
        }

        /* access modifiers changed from: package-private */
        public int[] getEnrolledFaceRecognizes() {
            int[] ret = new int[0];
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return ret;
            }
            try {
                List<FaceRecognition> faces = service.getEnrolledFaceRecognizes(getCurrentUserId(), this.mContext.getOpPackageName());
                if (faces != null && !faces.isEmpty()) {
                    int size = faces.size();
                    ret = new int[size];
                    for (int i = 0; i < size; i++) {
                        ret[i] = faces.get(i).getFaceId();
                    }
                }
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
            }
            return ret;
        }

        /* access modifiers changed from: package-private */
        public FaceInfo getFaceInfo(int faceId) {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                LogUtil.e("", "get face recognize service fail");
                return null;
            }
            try {
                int result = service.hasAlternateAppearance(this.mToken, faceId);
                if (result == 1 || result == 0) {
                    FaceInfo faceInfo = new FaceInfo();
                    faceInfo.faceId = faceId;
                    if (result == 1) {
                        faceInfo.hasAlternateAppearance = true;
                    } else {
                        faceInfo.hasAlternateAppearance = false;
                    }
                    return faceInfo;
                }
                LogUtil.e("", "hasAlternateAppearance fail " + result);
                return null;
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public FaceRecognitionAbility getFaceRecognitionAbility() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                LogUtil.e("", "get face recognize service fail");
                return null;
            }
            try {
                int result = service.getHardwareSupportType();
                FaceRecognitionAbility ability = new FaceRecognitionAbility();
                ability.secureLevel = (result >> 0) & 15;
                boolean z = true;
                if (((result >> 4) & 1) != 1) {
                    z = false;
                }
                ability.isFaceRecognitionSupport = z;
                ability.faceMode = (result >> 5) & -1;
                ability.reserve = 0;
                return ability;
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public int getHardwareSupportType() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.getHardwareSupportType() >>> 4;
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return -1;
            }
        }

        /* access modifiers changed from: package-private */
        public int getAngleDim() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.getAngleDim(this.mToken);
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return -1;
            }
        }

        /* access modifiers changed from: package-private */
        public long preEnroll() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return 0;
            }
            try {
                return service.preEnroll(this.mToken);
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return 0;
            }
        }

        /* access modifiers changed from: package-private */
        public int postEnroll() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.postEnroll(this.mToken);
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return -1;
            }
        }

        /* access modifiers changed from: package-private */
        public void resetTimeout() {
            IFaceRecognizeService service = getFRService();
            if (service != null) {
                try {
                    service.resetTimeout(null);
                } catch (RemoteException ex) {
                    LogUtil.e("", ex.getMessage());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int getRemainingNum() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.getRemainingNum();
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return -1;
            }
        }

        /* access modifiers changed from: package-private */
        public long getRemainingTime() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.getRemainingTime();
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return -1;
            }
        }

        /* access modifiers changed from: package-private */
        public int getTotalAuthFailedTimes() {
            IFaceRecognizeService service = getFRService();
            if (service == null) {
                return -1;
            }
            try {
                return service.getTotalAuthFailedTimes();
            } catch (RemoteException ex) {
                LogUtil.e("", ex.getMessage());
                return -1;
            }
        }

        public void binderDied() {
            LogUtil.e(FaceRecognizeManagerImpl.TAG, "FaceService died");
            synchronized (this) {
                this.mService = null;
            }
        }

        private int getCurrentUserId() {
            return 0;
        }

        private synchronized IFaceRecognizeService getFRService() {
            if (this.mService == null) {
                IBinder binder = ServiceManager.getService(FaceRecognizeManagerImpl.FACERECOGNIZE_SERVICE);
                if (binder == null) {
                    LogUtil.w(FaceRecognizeManagerImpl.TAG, "getService binder null");
                    return null;
                }
                this.mService = IFaceRecognizeService.Stub.asInterface(binder);
                if (this.mService == null) {
                    LogUtil.w(FaceRecognizeManagerImpl.TAG, "getService Service null");
                } else {
                    try {
                        this.mService.asBinder().linkToDeath(this, 0);
                    } catch (RemoteException e) {
                        String access$300 = FaceRecognizeManagerImpl.TAG;
                        LogUtil.w(access$300, "failed to linkToDeath" + e.getMessage());
                    }
                }
            }
            return this.mService;
        }
    }

    public FaceRecognizeManagerImpl(Context context, FaceRecognizeCallback callback) {
        ServiceHolder.getInstance().init(context);
        FaceCamera.getInstance().init(context);
        CallbackHolder.getInstance().init(callback);
        this.mContext = context;
        if (HwSecurityTaskThread.getInstance() == null) {
            HwSecurityTaskThread.createInstance();
            HwSecurityTaskThread.getInstance().startThread();
        }
        if (HwSecurityMsgCenter.getInstance() == null) {
            HwSecurityMsgCenter.createInstance();
        }
    }

    public int authenticate(long opId, int flags, Surface preview) {
        List<Surface> surfaces = new ArrayList<>();
        if (preview != null) {
            surfaces.add(preview);
        }
        AuthenticateRequest request = new AuthenticateRequest(opId, this, flags, surfaces, this.mContext);
        HwSecurityTaskThread.staticPushTask(request, 1);
        long current = System.nanoTime();
        LogUtil.d("PerformanceTime", "Time 1. start auth --- " + current);
        return 0;
    }

    public int cancelAuthenticate(long opId) {
        HwSecurityTaskThread.staticPushTask(new CancelRequest(opId, this, 2), 1);
        return 0;
    }

    public int preparePayInfo(byte[] aaid, byte[] nonce, byte[] extra) {
        return ServiceHolder.getInstance().preparePayInfo(aaid, nonce, extra);
    }

    public int getPayResult(int[] faceId, byte[] token, int[] tokenLen, byte[] reserve) {
        return ServiceHolder.getInstance().getPayResult(faceId, token, tokenLen, reserve);
    }

    public int enroll(int reqId, int flags, byte[] token, Surface preview) {
        List<Surface> surfaces = new ArrayList<>();
        if (preview != null) {
            surfaces.add(preview);
        }
        EnrollRequest request = new EnrollRequest(reqId, this, token, flags, surfaces);
        HwSecurityTaskThread.staticPushTask(request, 1);
        return 0;
    }

    public int cancelEnroll(int reqId) {
        HwSecurityTaskThread.staticPushTask(new CancelRequest((long) reqId, this, 1), 1);
        return 0;
    }

    public long preEnroll() {
        return ServiceHolder.getInstance().preEnroll();
    }

    public int postEnroll() {
        return ServiceHolder.getInstance().postEnroll();
    }

    public int remove(int reqId, int faceId) {
        HwSecurityTaskThread.staticPushTask(new RemoveRequest(reqId, this, faceId), 1);
        return 0;
    }

    public int init() {
        return ServiceHolder.getInstance().initAlgo();
    }

    public int release() {
        return ServiceHolder.getInstance().releaseAlgo();
    }

    public int[] getEnrolledFaceIDs() {
        return ServiceHolder.getInstance().getEnrolledFaceRecognizes();
    }

    public FaceInfo getFaceInfo(int faceId) {
        return ServiceHolder.getInstance().getFaceInfo(faceId);
    }

    public FaceRecognitionAbility getFaceRecognitionAbility() {
        return ServiceHolder.getInstance().getFaceRecognitionAbility();
    }

    public int getHardwareSupportType() {
        return ServiceHolder.getInstance().getHardwareSupportType();
    }

    public int getAngleDim() {
        return ServiceHolder.getInstance().getAngleDim();
    }

    public void resetTimeout() {
        ServiceHolder.getInstance().resetTimeout();
    }

    public int getRemainingNum() {
        return ServiceHolder.getInstance().getRemainingNum();
    }

    public long getRemainingTime() {
        return ServiceHolder.getInstance().getRemainingTime();
    }

    public int getTotalAuthFailedTimes() {
        return ServiceHolder.getInstance().getTotalAuthFailedTimes();
    }

    public void onNewRequest(FaceRecognizeRequest req) {
        if (this.mCurrentReq != null) {
            if (!this.mCurrentReq.isActiveCanceled() || this.mPendingReq != null) {
                String str = TAG;
                LogUtil.d(str, "current busy, " + this.mCurrentReq.toString());
                sendCallback((int) req.getReqId(), req.getType(), 4, 0);
                sendCallback((int) req.getReqId(), req.getType(), 1, 13);
                return;
            }
            LogUtil.d(TAG, "add to pending");
            this.mPendingReq = req;
        } else if (this.mPendingReq != null) {
            String str2 = TAG;
            LogUtil.d(str2, "pending busy" + this.mPendingReq.toString());
            sendCallback((int) req.getReqId(), req.getType(), 4, 0);
            sendCallback((int) req.getReqId(), req.getType(), 1, 13);
        } else if (req.onReqStart()) {
            this.mCurrentReq = req;
        } else {
            LogUtil.w("request", "out of memory");
            sendCallback((int) req.getReqId(), req.getType(), 5, 0);
        }
    }

    public void onRequestEnd(FaceRecognizeRequest req) {
        this.mCurrentReq = null;
        if (this.mPendingReq != null) {
            this.mCurrentReq = this.mPendingReq;
            this.mPendingReq = null;
            if (this.mCurrentReq.onReqStart()) {
                LogUtil.i(TAG, "replace current with pending");
                return;
            }
            LogUtil.w("request", "out of memory");
            sendCallback((int) req.getReqId(), req.getType(), 5, 0);
        }
    }

    public boolean onCancelReq(long reqId, int cancelType) {
        int type = cancelType == 2 ? 2 : 1;
        if (hasRequest(this.mCurrentReq, reqId)) {
            this.mCurrentReq.setActiveCanceled();
            return false;
        } else if (hasRequest(this.mPendingReq, reqId)) {
            this.mPendingReq = null;
            CallbackHolder.getInstance().onCallbackEvent((int) reqId, type, 1, 2);
            CallbackHolder.getInstance().onCallbackEvent((int) reqId, type, 2, 0);
            return true;
        } else {
            CallbackHolder.getInstance().onCallbackEvent((int) reqId, type, 2, 0);
            return true;
        }
    }

    private void sendCallback(int reqId, int reqType, int code, int errorCode) {
        int type;
        switch (reqType) {
            case 0:
                type = 1;
                break;
            case 1:
                type = 2;
                break;
            case 2:
                type = 3;
                break;
            default:
                type = 100;
                break;
        }
        CallbackHolder.getInstance().onCallbackEvent(reqId, type, code, errorCode);
    }

    private static boolean hasRequest(FaceRecognizeRequest request, long reqId) {
        return request != null && request.getReqId() == reqId;
    }

    public static String getTypeString(int type) {
        switch (type) {
            case 1:
                return "ENROLL";
            case 2:
                return "AUTH";
            case 3:
                return "REMOVE";
            default:
                return "" + type;
        }
    }

    public static String getCodeString(int code) {
        switch (code) {
            case 1:
                return "result";
            case 2:
                return "cancel";
            case 3:
                return "acquire";
            case 4:
                return "request busy";
            default:
                return "" + code;
        }
    }

    public static String getErrorCodeString(int code, int errorCode) {
        if (code == 1) {
            switch (errorCode) {
                case 0:
                    return "success";
                case 1:
                    return "failed";
                case 2:
                    return "cancelled";
                case 3:
                    return "compare fail";
                case 4:
                    return "time out";
                case 5:
                    return "invoke init first";
                case 6:
                    return "hal invalid";
                case 7:
                    return "over max faces";
                case 8:
                    return "in lockout mode";
                case 9:
                    return "invalid parameters";
                case 10:
                    return "no face data";
                case 11:
                    return "low temp & cap";
                case 12:
                    return "camera fail";
                case 13:
                    return "busy";
            }
        } else if (code == 3) {
            switch (errorCode) {
                case 4:
                    return "bad quality";
                case 5:
                    return "no face detected";
                case 6:
                    return "face too small";
                case 7:
                    return "face too large";
                case 8:
                    return "offset left";
                case 9:
                    return "offset top";
                case 10:
                    return "offset right";
                case 11:
                    return "offset bottom";
                case 13:
                    return "aliveness warning";
                case 14:
                    return "aliveness failure";
                case 15:
                    return "rotate left";
                case 16:
                    return "face rise to high";
                case 17:
                    return "rotate right";
                case 18:
                    return "face too low";
                case 19:
                    return "keep still";
                case 21:
                    return "eyes occlusion";
                case 22:
                    return "eyes closed";
                case 23:
                    return "mouth occlusion";
                case 27:
                    return "multi faces";
                case 28:
                    return "face blur";
                case 29:
                    return "face not complete";
                case 30:
                    return "too dark";
                case 31:
                    return "too light";
                case 32:
                    return "half shadow";
                case 39:
                    return "picture too dark";
            }
        }
        return "" + errorCode;
    }
}
