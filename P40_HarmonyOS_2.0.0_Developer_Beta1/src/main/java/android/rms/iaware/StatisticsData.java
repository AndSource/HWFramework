package android.rms.iaware;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.annotation.HwSystemApi;

@HwSystemApi
public class StatisticsData implements Parcelable {
    public static final Parcelable.Creator<StatisticsData> CREATOR = new Parcelable.Creator<StatisticsData>() {
        /* class android.rms.iaware.StatisticsData.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public StatisticsData createFromParcel(Parcel source) {
            return new StatisticsData(source.readInt(), source.readInt(), source.readString(), source.readInt(), source.readInt(), source.readInt(), source.readLong(), source.readLong());
        }

        @Override // android.os.Parcelable.Creator
        public StatisticsData[] newArray(int size) {
            return new StatisticsData[size];
        }
    };
    private int mEffect;
    private long mEndTime;
    private int mFeatureId;
    private int mOccurCount;
    private long mStartTime;
    private String mSubType;
    private int mTotalTime;
    private int mType;

    public StatisticsData(int featureId, int type, String subType, int occurCount, int totalTime, int effect) {
        this(featureId, type, subType, occurCount, totalTime, effect, 0, 0);
    }

    public StatisticsData(int featureId, int type, String subType, int occurCount, int totalTime, int effect, long startTime, long endTime) {
        this.mFeatureId = featureId;
        this.mType = type;
        this.mSubType = subType;
        this.mOccurCount = occurCount;
        this.mTotalTime = totalTime;
        this.mEffect = effect;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
    }

    public int getFeatureId() {
        return this.mFeatureId;
    }

    public void setFeatureId(int featureId) {
        this.mFeatureId = featureId;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getSubType() {
        return this.mSubType;
    }

    public void setSubType(String subType) {
        this.mSubType = subType;
    }

    public int getOccurCount() {
        return this.mOccurCount;
    }

    public void setOccurCount(int occurCount) {
        this.mOccurCount = occurCount;
    }

    public int getTotalTime() {
        return this.mTotalTime;
    }

    public void setTotalTime(int totalTime) {
        this.mTotalTime = totalTime;
    }

    public int getEffect() {
        return this.mEffect;
    }

    public void setEffect(int effect) {
        this.mEffect = effect;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public void setStartTime(long startTime) {
        this.mStartTime = startTime;
    }

    public long getEndTime() {
        return this.mEndTime;
    }

    public void setEndTime(long endTime) {
        this.mEndTime = endTime;
    }

    @Override // java.lang.Object
    public String toString() {
        return "StatisticsData[ FeatureId: " + this.mFeatureId + ", Type: " + this.mType + ", SubType: " + this.mSubType + ", OccurCount: " + this.mOccurCount + ", TotalTime: " + this.mTotalTime + ", Effect: " + this.mEffect + ", StartTime: " + this.mStartTime + ", EndTime: " + this.mEndTime + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mFeatureId);
        dest.writeInt(this.mType);
        dest.writeString(this.mSubType);
        dest.writeInt(this.mOccurCount);
        dest.writeInt(this.mTotalTime);
        dest.writeInt(this.mEffect);
        dest.writeLong(this.mStartTime);
        dest.writeLong(this.mEndTime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
