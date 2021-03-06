package android.rms;

import android.iawareperf.RtgSched;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.rms.iaware.IAwareSdk;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class HwAppInnerBoostImpl extends DefaultHwAppInnerBoostImpl {
    private static final boolean BOOST_FLAG = (SystemProperties.getBoolean("persist.sys.enable_iaware", false) && SystemProperties.getBoolean("persist.sys.iaware.appboost.switch", false));
    private static final boolean DEBUG = (SystemProperties.getInt("ro.logsystem.usertype", 1) == 3);
    private static final int DOWN_INTERVAL = 2000;
    private static final int EVENT_TYPE_CLICK = 2;
    private static final int EVENT_TYPE_INVALID = 0;
    private static final int EVENT_TYPE_SLIDE = 1;
    private static final int MAX_MOVE_COUNT = 20;
    private static final float MOVE_DISTANCE = 24.0f;
    private static final String MSG_TYPE_JITTER = "2";
    private static final String MSG_TYPE_TRAVERSAL = "1";
    private static final int REPORT_DURATION_CLICK = SystemProperties.getInt("persist.sys.iaware.appboost.click_duration", 1000);
    private static final int REPORT_DURATION_SLIDE = SystemProperties.getInt("persist.sys.iaware.appboost.slide_duration", 5000);
    private static final int REPORT_INTERVAL = 250;
    private static final int REPORT_TIMES_CLICK = SystemProperties.getInt("persist.sys.iaware.appboost.click_times", 3);
    private static final int REPORT_TIMES_SLIDE = SystemProperties.getInt("persist.sys.iaware.appboost.slide_times", 16);
    private static final boolean RTG_FRAME_ENABLE;
    private static final int SCENE_TYPE_CLICK = 2;
    private static final int SCENE_TYPE_INVALID = 0;
    private static final int SCENE_TYPE_SLIDE = 1;
    private static final int SKIPPED_FRAMES = 3;
    private static final String TAG = "HwAppInnerBoostImpl";
    private static volatile HwAppInnerBoostImpl instance = null;
    private float downX = 0.0f;
    private float downY = 0.0f;
    private boolean initialized = false;
    private int jitterReportTimes = 0;
    private long lastDownTime = 0;
    private long lastJitterReportTime = 0;
    private long lastTraversalReportTime = 0;
    private boolean mIsFinishScroll = false;
    private MotionEventListener mMotionEventListener = null;
    private float mRtgDownX = 0.0f;
    private float mRtgDownY = 0.0f;
    private int mRtgEventType = 0;
    private int mRtgMoveCount = 0;
    private int moveCount = 0;
    private String packageName = null;
    private int reportDuration = 0;
    private int reportTimes = 0;
    private int sceneType = 0;
    private int traversalReportTimes = 0;

    public interface MotionEventListener {
        void onMotionEvent(MotionEvent motionEvent);
    }

    static {
        boolean z = true;
        if (!SystemProperties.getBoolean("persist.sys.enable_iaware", false) || !SystemProperties.getBoolean("persist.sys.iaware.rtg.frame", true)) {
            z = false;
        }
        RTG_FRAME_ENABLE = z;
    }

    public static HwAppInnerBoostImpl getDefault() {
        if (instance == null) {
            synchronized (HwAppInnerBoostImpl.class) {
                if (instance == null) {
                    instance = new HwAppInnerBoostImpl();
                }
            }
        }
        return instance;
    }

    public void setMotionEventListener(MotionEventListener listener) {
        this.mMotionEventListener = listener;
    }

    public void initialize(String packageName2) {
        if (!this.initialized) {
            this.initialized = true;
            this.packageName = packageName2;
            if (DEBUG) {
                Log.d(TAG, "set config for " + packageName2 + " BOOST_FLAG=" + BOOST_FLAG + " REPORT_DURATION_CLICK=" + REPORT_DURATION_CLICK + " REPORT_TIMES_CLICK=" + REPORT_TIMES_CLICK + " REPORT_DURATION_SLIDE=" + REPORT_DURATION_SLIDE + " REPORT_TIMES_SLIDE=" + REPORT_TIMES_SLIDE);
            }
        }
    }

    public void onInputEvent(InputEvent event) {
        if (BOOST_FLAG || RTG_FRAME_ENABLE) {
            long now = SystemClock.uptimeMillis();
            if (event instanceof MotionEvent) {
                onMotionEvent((MotionEvent) event, now);
            } else if (event instanceof KeyEvent) {
                onKeyEvent((KeyEvent) event, now);
            }
        }
    }

    public void onTraversal() {
        int i;
        if (!this.mIsFinishScroll && BOOST_FLAG && this.sceneType >= 1) {
            long now = SystemClock.uptimeMillis();
            if (now - this.lastDownTime > ((long) this.reportDuration) || (i = this.traversalReportTimes) >= this.reportTimes) {
                this.sceneType = 0;
            } else if (now - this.lastTraversalReportTime >= 250) {
                this.lastTraversalReportTime = now;
                this.traversalReportTimes = i + 1;
                asyncReport(now, "1", this.traversalReportTimes, 0);
            }
        }
    }

    public void onScrollState(boolean isFinished) {
        this.mIsFinishScroll = isFinished;
        if (DEBUG) {
            Log.d(TAG, "onScrollState isFinished " + isFinished);
        }
    }

    public void onJitter(long skippedFrames) {
        int i;
        if (BOOST_FLAG && this.sceneType >= 1 && skippedFrames >= 3) {
            long now = SystemClock.uptimeMillis();
            if (now - this.lastDownTime > ((long) this.reportDuration) || (i = this.jitterReportTimes) >= this.reportTimes) {
                this.sceneType = 0;
            } else if (now - this.lastJitterReportTime >= 250) {
                this.lastJitterReportTime = now;
                this.jitterReportTimes = i + 1;
                asyncReport(now, MSG_TYPE_JITTER, this.jitterReportTimes, skippedFrames);
            }
        }
    }

    private void onMotionEvent(MotionEvent event, long now) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            onDown(now, event.getX(), event.getY(), event);
        } else if (actionMasked == 1) {
            onMotionUp(now, event.getX(), event.getY());
        } else if (actionMasked == 2) {
            onMove(now, event.getX(), event.getY());
        }
        MotionEventListener motionEventListener = this.mMotionEventListener;
        if (motionEventListener != null) {
            motionEventListener.onMotionEvent(event);
        }
    }

    private void onKeyEvent(KeyEvent event, long now) {
        int action = event.getAction();
        if (action == 0) {
            onDown(now, 0.0f, 0.0f, event);
        } else if (action == 1) {
            onKeyUp(now, event);
        }
    }

    private void rtgSchedOnDown(float positionX, float positionY, InputEvent event) {
        if ((event instanceof KeyEvent) && ((KeyEvent) event).getKeyCode() == 4) {
            RtgSched.getInstance().markFrameSchedStart(1);
        }
        this.mRtgEventType = 0;
        this.mRtgDownX = positionX;
        this.mRtgDownY = positionY;
        this.mRtgMoveCount = 0;
    }

    private void onDown(long now, float positionX, float positionY, InputEvent event) {
        if (RTG_FRAME_ENABLE) {
            rtgSchedOnDown(positionX, positionY, event);
        }
        if (this.mIsFinishScroll) {
            if (DEBUG) {
                Log.d(TAG, "onDown isFinishScroll false");
            }
            this.mIsFinishScroll = false;
        }
        if (now - this.lastDownTime >= 2000) {
            this.downX = positionX;
            this.downY = positionY;
            this.moveCount = 0;
            this.traversalReportTimes = 0;
            this.jitterReportTimes = 0;
            this.reportDuration = 0;
            this.reportTimes = 0;
            this.lastDownTime = now;
            this.sceneType = 0;
        }
    }

    private void rtgSchedOnMove(float positionX, float positionY) {
        boolean isMoved = isMoved(this.mRtgDownX, this.mRtgDownY, positionX, positionY);
        if (isMoved) {
            RtgSched.getInstance().markFrameSchedStart(2);
        }
        if (this.mRtgEventType == 0) {
            this.mRtgMoveCount++;
            if (this.mRtgMoveCount < 20 && isMoved) {
                this.mRtgEventType = 1;
            }
        }
    }

    private void onMove(long now, float positionX, float positionY) {
        if (RTG_FRAME_ENABLE) {
            rtgSchedOnMove(positionX, positionY);
        }
        if (this.mIsFinishScroll) {
            if (DEBUG) {
                Log.d(TAG, "onMove isFinishScroll false");
            }
            this.mIsFinishScroll = false;
        }
        if (this.sceneType == 0 && now - this.lastDownTime <= ((long) this.reportDuration)) {
            this.moveCount++;
            if (this.moveCount < 20 && isMoved(this.downX, this.downY, positionX, positionY)) {
                this.sceneType = 1;
                this.reportDuration = REPORT_DURATION_SLIDE;
                this.reportTimes = REPORT_TIMES_SLIDE;
            }
        }
    }

    private void rtgSchedOnMotionUp(float positionX, float positionY) {
        if (this.mRtgEventType == 1) {
            return;
        }
        if (isMoved(this.mRtgDownX, this.mRtgDownY, positionX, positionY)) {
            this.mRtgEventType = 1;
            return;
        }
        this.mRtgEventType = 2;
        RtgSched.getInstance().markFrameSchedStart(1);
    }

    private void onMotionUp(long now, float positionX, float positionY) {
        if (RTG_FRAME_ENABLE) {
            rtgSchedOnMotionUp(positionX, positionY);
        }
        if (this.sceneType != 1) {
            if (isMoved(this.downX, this.downY, positionX, positionY)) {
                this.sceneType = 1;
                this.reportDuration = REPORT_DURATION_SLIDE;
                this.reportTimes = REPORT_TIMES_SLIDE;
            } else {
                this.sceneType = 2;
                this.reportDuration = REPORT_DURATION_CLICK;
                this.reportTimes = REPORT_TIMES_CLICK;
            }
            if (!RTG_FRAME_ENABLE) {
                return;
            }
            if (this.sceneType != 2) {
                RtgSched.getInstance().markFrameSchedStart(3);
            }
        }
    }

    private void rtgSchedOnKeyUp(KeyEvent event) {
        if (event.getKeyCode() != 4) {
            RtgSched.getInstance().markFrameSchedStart(1);
        }
    }

    private void onKeyUp(long now, KeyEvent event) {
        if (RTG_FRAME_ENABLE) {
            rtgSchedOnKeyUp(event);
        }
        if (event.getKeyCode() != 4) {
            this.sceneType = 2;
            this.reportDuration = REPORT_DURATION_CLICK;
            this.reportTimes = REPORT_TIMES_CLICK;
            return;
        }
        this.sceneType = 0;
    }

    private void asyncReport(long frameTime, String msgType, int frameTimes, long skippedFrames) {
        StringBuilder message = new StringBuilder();
        message.append(this.packageName);
        message.append(',');
        message.append(this.sceneType);
        message.append(',');
        message.append(msgType);
        message.append(',');
        message.append(frameTimes);
        message.append(',');
        message.append(skippedFrames);
        message.append(',');
        message.append(this.lastDownTime);
        IAwareSdk.asyncReportData(3033, message.toString(), frameTime);
        if (DEBUG) {
            Log.d(TAG, "asyncReportData " + ((Object) message) + " interval=" + (frameTime - this.lastDownTime));
        }
    }

    private boolean isMoved(float x1, float y1, float x2, float y2) {
        return Math.abs(x1 - x2) > MOVE_DISTANCE || Math.abs(y1 - y2) > MOVE_DISTANCE;
    }
}
