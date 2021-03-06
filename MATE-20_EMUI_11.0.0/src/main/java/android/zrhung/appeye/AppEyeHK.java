package android.zrhung.appeye;

import android.util.Log;
import android.util.ZRHung;
import android.zrhung.ZrHungData;
import android.zrhung.ZrHungImpl;

public final class AppEyeHK extends ZrHungImpl {
    private static final int KEY_DOWN_ARRAY_SIZE = 16;
    private static final int KEY_DOWN_DURATION_MILLIS_MIN = 100;
    private static final int KEY_DOWN_THRESHOLD_MAX = 10;
    private static final int KEY_DOWN_THRESHOLD_MIN = 1;
    private static final String TAG = "ZrHung.AppEyeHK";
    private int mCount = 0;
    private long[] mDownTimes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int mDuration = 0;
    private int mEnd = 0;
    private int mIndex = 0;
    private boolean mIsConfiged = false;
    private boolean mIsEnabled = false;
    private int mStart = 0;
    private int mThreshold = 0;

    public AppEyeHK(String wpName) {
        super(wpName);
    }

    private int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e(TAG, "parseInt NumberFormatException");
            return -1;
        }
    }

    private boolean getHkConfig() {
        int i;
        int i2;
        if (this.mIsConfiged) {
            return this.mIsEnabled && this.mDuration >= KEY_DOWN_DURATION_MILLIS_MIN && (i2 = this.mThreshold) >= 1 && i2 <= KEY_DOWN_THRESHOLD_MAX;
        }
        ZRHung.HungConfig cfg = getConfig();
        if (cfg == null) {
            return false;
        }
        if (cfg.status != 0) {
            if (cfg.status != 1) {
                this.mIsConfiged = true;
            }
            return false;
        } else if (cfg.value == null) {
            this.mIsConfiged = true;
            return false;
        } else {
            String[] values = cfg.value.split(",");
            if (values.length < 3) {
                this.mIsConfiged = true;
                return false;
            }
            this.mIsEnabled = values[0].trim().equals("1");
            this.mThreshold = parseInt(values[1].trim());
            this.mDuration = parseInt(values[2].trim());
            this.mIsConfiged = true;
            return this.mIsEnabled && this.mDuration >= KEY_DOWN_DURATION_MILLIS_MIN && (i = this.mThreshold) >= 1 && i <= KEY_DOWN_THRESHOLD_MAX;
        }
    }

    private long matchDownPattern(long downTime) {
        long[] jArr = this.mDownTimes;
        int i = this.mIndex;
        jArr[i] = downTime;
        this.mIndex = (i + 1) % KEY_DOWN_ARRAY_SIZE;
        this.mCount++;
        int i2 = this.mCount;
        int i3 = this.mThreshold;
        if (i2 < i3) {
            return -1;
        }
        int i4 = this.mStart;
        this.mEnd = ((i4 + i3) - 1) % KEY_DOWN_ARRAY_SIZE;
        if (jArr[i4] + ((long) this.mDuration) >= jArr[this.mEnd]) {
            long startTime = jArr[i4];
            this.mCount = 0;
            this.mStart = (i4 + i3) % KEY_DOWN_ARRAY_SIZE;
            return startTime;
        }
        this.mCount = i2 - 1;
        this.mStart = (i4 + 1) % KEY_DOWN_ARRAY_SIZE;
        return -1;
    }

    @Override // android.zrhung.ZrHungImpl
    public int init(ZrHungData args) {
        getHkConfig();
        return 0;
    }

    @Override // android.zrhung.ZrHungImpl
    public boolean check(ZrHungData args) {
        if (args == null || !getHkConfig()) {
            return false;
        }
        long startTime = matchDownPattern(args.getLong("downTime"));
        if (startTime == -1) {
            return true;
        }
        return ZRHung.sendHungEvent(515, (String) null, "HK:" + startTime);
    }
}
