package com.vzw.nfc.dos;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class VzwTlv {
    private byte[] mRawData = null;
    private int mTag = 0;
    private int mValueIndex = 0;
    private int mValueLength = 0;

    public VzwTlv(byte[] rawData, int tag, int valueIndex, int valueLength) {
        this.mRawData = rawData;
        this.mTag = tag;
        this.mValueIndex = valueIndex;
        this.mValueLength = valueLength;
    }

    public static VzwTlv parse(byte[] data, int startIndex) throws DoParserException {
        if (data == null || data.length == 0) {
            throw new DoParserException("No data given!");
        }
        int curIndex = startIndex;
        if (curIndex < data.length) {
            int curIndex2 = curIndex + 1;
            int curIndex3 = data[curIndex] & 255;
            if (curIndex2 < data.length) {
                return new VzwTlv(data, curIndex3, curIndex2 + 1, data[curIndex2] & 255);
            }
            throw new DoParserException("Index " + curIndex2 + " out of range! [0..[" + data.length);
        }
        throw new DoParserException("Index out of bound");
    }

    public void translate() throws DoParserException {
    }

    public int getTag() {
        return this.mTag;
    }

    public int getValueIndex() {
        return this.mValueIndex;
    }

    public byte[] getValue() {
        if (this.mRawData == null || this.mValueLength == 0 || this.mValueIndex < 0 || this.mValueIndex > this.mRawData.length || this.mValueIndex + this.mValueLength > this.mRawData.length) {
            return null;
        }
        byte[] data = new byte[this.mValueLength];
        System.arraycopy(this.mRawData, this.mValueIndex, data, 0, this.mValueLength);
        return data;
    }

    /* access modifiers changed from: protected */
    public byte[] getRawData() {
        return this.mRawData;
    }

    public int getValueLength() {
        return this.mValueLength;
    }

    public static void encodeLength(int length, ByteArrayOutputStream stream) {
        stream.write(length & 255);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{new Integer(Integer.valueOf(Arrays.hashCode(this.mRawData)).intValue()), new Integer(this.mTag), new Integer(this.mValueIndex), new Integer(this.mValueLength)});
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VzwTlv)) {
            return false;
        }
        VzwTlv berTlv = (VzwTlv) obj;
        boolean equals = this.mTag == berTlv.mTag;
        if (!equals) {
            return equals;
        }
        byte[] test1 = getValue();
        byte[] test2 = berTlv.getValue();
        if (test1 != null) {
            return equals & Arrays.equals(test1, test2);
        }
        if (test1 == null && test2 == null) {
            return equals & true;
        }
        return equals;
    }
}
