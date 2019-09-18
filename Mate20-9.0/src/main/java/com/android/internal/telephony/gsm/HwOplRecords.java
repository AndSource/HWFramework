package com.android.internal.telephony.gsm;

import android.telephony.Rlog;
import android.text.TextUtils;
import java.util.ArrayList;

public final class HwOplRecords {
    private static final boolean DBG = true;
    static final String TAG = "HwOplRecords";
    static final int wildCardDigit = 13;
    private ArrayList<OplRecord> mRecords = new ArrayList<>();

    public static class OplRecord {
        /* access modifiers changed from: private */
        public int mLac1;
        /* access modifiers changed from: private */
        public int mLac2;
        /* access modifiers changed from: private */
        public int[] mPlmn = {0, 0, 0, 0, 0, 0};
        private int mPnnRecordNumber;

        OplRecord(byte[] record) {
            getPlmn(record);
            getLac(record);
            this.mPnnRecordNumber = record[7] & 255;
        }

        private void getPlmn(byte[] record) {
            this.mPlmn[0] = record[0] & 15;
            this.mPlmn[1] = (record[0] >> 4) & 15;
            this.mPlmn[2] = record[1] & 15;
            this.mPlmn[3] = record[2] & 15;
            this.mPlmn[4] = (record[2] >> 4) & 15;
            this.mPlmn[5] = (record[1] >> 4) & 15;
            if (this.mPlmn[5] == 15) {
                this.mPlmn[5] = 0;
            }
        }

        private void getLac(byte[] record) {
            this.mLac1 = ((record[3] & 255) << 8) | (record[4] & 255);
            this.mLac2 = ((record[5] & 255) << 8) | (record[6] & 255);
        }

        public int getPnnRecordNumber() {
            return this.mPnnRecordNumber;
        }

        public String toString() {
            return "PLMN=" + Integer.toHexString(this.mPlmn[0]) + Integer.toHexString(this.mPlmn[1]) + Integer.toHexString(this.mPlmn[2]) + Integer.toHexString(this.mPlmn[3]) + Integer.toHexString(this.mPlmn[4]) + Integer.toHexString(this.mPlmn[5]) + ", LAC1=" + this.mLac1 + ", LAC2=" + this.mLac2 + ", PNN Record=" + this.mPnnRecordNumber;
        }
    }

    HwOplRecords(ArrayList<byte[]> records) {
        if (records != null) {
            int list_size = records.size();
            for (int i = 0; i < list_size; i++) {
                this.mRecords.add(new OplRecord(records.get(i)));
                log("Record " + this.mRecords.size() + ": " + this.mRecords.get(this.mRecords.size() - 1));
            }
        }
    }

    private void log(String s) {
        Rlog.d(TAG, "[HwOplRecords EONS] " + s);
    }

    private void loge(String s) {
        Rlog.e(TAG, "[HwOplRecords EONS] " + s);
    }

    public int size() {
        if (this.mRecords != null) {
            return this.mRecords.size();
        }
        return 0;
    }

    public int getMatchingPnnRecord(String operator, int lac, boolean useLac) {
        int[] bcchPlmn = {0, 0, 0, 0, 0, 0};
        if (TextUtils.isEmpty(operator)) {
            loge("No registered operator.");
            return 0;
        } else if (!useLac || lac != -1) {
            int length = operator.length();
            if (length == 5 || length == 6) {
                for (int i = 0; i < length; i++) {
                    bcchPlmn[i] = operator.charAt(i) - '0';
                }
                if (this.mRecords != null) {
                    int list_size = this.mRecords.size();
                    for (int i2 = 0; i2 < list_size; i2++) {
                        OplRecord record = this.mRecords.get(i2);
                        if (matchPlmn(record.mPlmn, bcchPlmn) && (!useLac || (record.mLac1 <= lac && lac <= record.mLac2))) {
                            return record.getPnnRecordNumber();
                        }
                    }
                }
                loge("No matching OPL record found.");
                return 0;
            }
            loge("Invalid registered operator length " + length);
            return 0;
        } else {
            loge("Invalid LAC");
            return 0;
        }
    }

    private boolean matchPlmn(int[] simPlmn, int[] bcchPlmn) {
        boolean match = true;
        for (int i = 0; i < bcchPlmn.length; i++) {
            match &= bcchPlmn[i] == simPlmn[i] || simPlmn[i] == 13;
        }
        return match;
    }
}
