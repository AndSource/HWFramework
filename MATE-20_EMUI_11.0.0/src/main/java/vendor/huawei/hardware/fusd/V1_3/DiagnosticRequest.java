package vendor.huawei.hardware.fusd.V1_3;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class DiagnosticRequest {
    public int diagCmd;
    public int interval;
    public int reserved;
    public int status;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != DiagnosticRequest.class) {
            return false;
        }
        DiagnosticRequest other = (DiagnosticRequest) otherObject;
        if (this.diagCmd == other.diagCmd && this.status == other.status && this.interval == other.interval && this.reserved == other.reserved) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.diagCmd))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.interval))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.reserved))));
    }

    public final String toString() {
        return "{.diagCmd = " + this.diagCmd + ", .status = " + this.status + ", .interval = " + this.interval + ", .reserved = " + this.reserved + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(16), 0);
    }

    public static final ArrayList<DiagnosticRequest> readVectorFromParcel(HwParcel parcel) {
        ArrayList<DiagnosticRequest> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 16), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            DiagnosticRequest _hidl_vec_element = new DiagnosticRequest();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 16));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.diagCmd = _hidl_blob.getInt32(0 + _hidl_offset);
        this.status = _hidl_blob.getInt32(4 + _hidl_offset);
        this.interval = _hidl_blob.getInt32(8 + _hidl_offset);
        this.reserved = _hidl_blob.getInt32(12 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(16);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<DiagnosticRequest> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 16);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 16));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.diagCmd);
        _hidl_blob.putInt32(4 + _hidl_offset, this.status);
        _hidl_blob.putInt32(8 + _hidl_offset, this.interval);
        _hidl_blob.putInt32(12 + _hidl_offset, this.reserved);
    }
}
