package com.android.internal.telephony.uicc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.text.TextUtils;
import com.android.internal.telephony.HwCarrierConfigCardManager;
import com.android.internal.telephony.HwHotplugController;
import com.android.internal.telephony.HwModemCapability;
import com.android.internal.telephony.fullnetwork.HwFullNetworkConfig;
import com.huawei.android.os.AsyncResultEx;
import com.huawei.android.os.SystemPropertiesEx;
import com.huawei.android.telephony.RlogEx;
import com.huawei.internal.telephony.CommandExceptionEx;
import com.huawei.internal.telephony.CommandsInterfaceEx;
import com.huawei.internal.telephony.MccTableEx;
import com.huawei.internal.telephony.uicc.IccCardApplicationStatusEx;
import com.huawei.internal.telephony.uicc.IccUtilsEx;
import com.huawei.internal.telephony.uicc.UiccCardApplicationEx;
import com.huawei.internal.telephony.uicc.UiccControllerExt;
import com.huawei.util.LogEx;
import java.util.Locale;

public class HwRuimRecords extends HwIccRecordsEx {
    private static final int CDMA_GSM_IMSI_ARRAY_LENGTH = 2;
    private static final int DELAY_GET_CDMA_GSM_IMSI_TIME = 2000;
    private static final int EVENT_DELAY_GET_CDMA_GSM_IMSI = 40;
    private static final int EVENT_GET_ICCID_DONE = 5;
    private static final int EVENT_GET_PBR_DONE = 501;
    private static final int EVENT_GET_SIM_APP_IMSI_DONE = 38;
    private static final int EVENT_HW_BASE = 500;
    private static final int EVENT_SET_MDN_DONE = 39;
    private static final int GSM_CDMA_IMSI_SPLIT_ARRAY_LEN = 2;
    private static final boolean HW_DBG = SystemPropertiesEx.getBoolean("ro.debuggable", false);
    private static final boolean IS_MODEM_CAPABILITY_GET_ICCID_AT = HwModemCapability.isCapabilitySupport(19);
    private static final int MAX_CG_IMSI_RETRIES = 10;
    private static final int MAX_MDN_BYTES = 11;
    private static final int MAX_MDN_NUMBERS = 15;
    private static final String TAG = "HwRUIMRecords";
    protected boolean bNeedSendRefreshBC = false;
    private int mCGImsiRetryNum = 0;
    HwCarrierConfigCardManager mHwCarrierCardManager;
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        /* class com.android.internal.telephony.uicc.HwRuimRecords.AnonymousClass1 */

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE".equals(intent.getAction())) {
                HwRuimRecords hwRuimRecords = HwRuimRecords.this;
                hwRuimRecords.log("Receives ACTION_SET_RADIO_CAPABILITY_DONE on slot " + HwRuimRecords.this.getSlotId());
                if (HwFullNetworkConfig.IS_FAST_SWITCH_SIMSLOT && HwRuimRecords.this.mIsSimPowerDown && HwRuimRecords.this.mParentApp != null && IccCardApplicationStatusEx.AppStateEx.APPSTATE_READY == HwRuimRecords.this.mParentApp.getState()) {
                    HwRuimRecords.this.log("fetchRuimRecords again.");
                    HwRuimRecords.this.mIsSimPowerDown = false;
                    HwRuimRecords.this.mIccRecordsInner.disableRequestIccRecords();
                    HwRuimRecords.this.mIccRecordsInner.recordsRequired();
                }
            }
        }
    };
    private boolean mIsSimPowerDown = false;
    protected String mNewMdnNumber = null;

    public HwRuimRecords(IIccRecordsInner iccRecordsInner, UiccCardApplicationEx app, Context c, CommandsInterfaceEx ci) {
        super(iccRecordsInner, app, c, ci);
        this.mHwCarrierCardManager = HwCarrierConfigCardManager.getDefault(c);
        this.mHwCarrierCardManager.reportIccRecordInstance(getSlotId(), this);
        if (getIccidSwitch()) {
            if (IS_MODEM_CAPABILITY_GET_ICCID_AT) {
                this.mCi.getICCID(obtainMessage(5));
            } else {
                this.mFh.loadEFTransparent(12258, obtainMessage(5));
            }
            this.mIccRecordsInner.addRecordsToLoadNum();
        }
        addIntentFilter(c);
    }

    private void addIntentFilter(Context c) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE");
        c.registerReceiver(this.mIntentReceiver, filter);
    }

    public boolean beforeHandleRuimRefresh(int refreshResult) {
        if (refreshResult == 0) {
            log("beforeHandleRuimRefresh with SIM_REFRESH_FILE_UPDATED");
            this.mIccRecordsInner.disableRequestIccRecords();
            return false;
        } else if (refreshResult == 1) {
            log("beforeHandleRuimRefresh with SIM_REFRESH_INIT");
            this.mIccRecordsInner.disableRequestIccRecords();
            return false;
        } else if (refreshResult != 2) {
            log("beforeHandleRuimRefresh with unknown operation");
            return false;
        } else {
            log("beforeHandleRuimRefresh with SIM_REFRESH_RESET");
            this.mIccRecordsInner.getAdnCacheHw().reset();
            return false;
        }
    }

    public boolean afterHandleRuimRefresh(int refreshResult) {
        if (refreshResult == 0) {
            log("afterHandleRuimRefresh with SIM_REFRESH_FILE_UPDATED");
            notifyRegisterForIccRefresh();
            return false;
        } else if (refreshResult == 1) {
            log("afterHandleRuimRefresh with SIM_REFRESH_INIT");
            if (!IS_HW_SIM_REFRESH) {
                return false;
            }
            this.bNeedSendRefreshBC = true;
            return false;
        } else if (refreshResult != 2) {
            log("afterHandleRuimRefresh with unknown operation");
            return false;
        } else {
            log("afterHandleRuimRefresh with SIM_REFRESH_RESET");
            if (!IS_HW_SIM_REFRESH) {
                return false;
            }
            this.bNeedSendRefreshBC = true;
            return false;
        }
    }

    public void onReady() {
        if (this.bNeedSendRefreshBC && IS_HW_SIM_REFRESH) {
            this.bNeedSendRefreshBC = false;
            notifyRegisterForIccRefresh();
        }
    }

    public void resetRecords() {
        this.mIs3Gphonebook = false;
        this.mIsGetPBRDone = false;
        this.mIsSimPowerDown = false;
    }

    public void onIccIdLoadedHw() {
        String iccId = this.mIccRecordsInner.getIccIdHw();
        if (getIccidSwitch()) {
            sendIccidDoneBroadcast(iccId);
        }
        if (HwHotplugController.IS_HOTSWAP_SUPPORT) {
            HwHotplugController.getInstance().onHotplugIccIdChanged(iccId, getSlotId());
        }
        updateCarrierFile(getSlotId(), 1, iccId);
    }

    public void updateCarrierFile(int slotId, int fileType, String fileValue) {
        this.mHwCarrierCardManager.updateCarrierFile(slotId, fileType, fileValue);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        boolean isRecordLoadResponse = false;
        if (this.mIccRecordsInner.judgeIfDestroyed()) {
            RlogEx.e(TAG, "Received message " + msg + "[" + msg.what + "] while being destroyed. Ignoring.");
            return;
        }
        try {
            int i = msg.what;
            if (i == 101) {
                log("get CDMA_GSM_IMSI");
                AsyncResultEx ar = AsyncResultEx.from(msg.obj);
                if (ar != null) {
                    String imsi = this.mIccRecordsInner.getImsiHw();
                    if (ar.getException() != null) {
                        RlogEx.e(TAG, "Exception querying CDMAGSM IMSI.");
                        if (this.mCGImsiRetryNum >= MAX_CG_IMSI_RETRIES) {
                            log("get CDMA_GSM_IMSI mImsiLoad is " + this.mIsImsiLoad);
                            if (this.mIsImsiLoad) {
                                this.mHwCarrierCardManager.setSingleModeCdmaCard(getSlotId(), true);
                                updateCarrierFile(getSlotId(), 4, imsi);
                                log("getCImsiFromCdmaGsmImsi done");
                            } else {
                                getCdmaGsmImsiDone(null);
                            }
                        }
                    } else {
                        this.mIccRecordsInner.setCdmaGsmImsi((String) ar.getResult());
                        if (isValidCdmaGsmImsi() || this.mCGImsiRetryNum >= MAX_CG_IMSI_RETRIES) {
                            log("get CDMA_GSM_IMSI mImsiLoad is " + this.mIsImsiLoad);
                            if (isValidCdmaGsmImsi() || !this.mIsImsiLoad) {
                                getCdmaGsmImsiDone(this.mIccRecordsInner.getCdmaGsmImsi());
                            } else {
                                this.mHwCarrierCardManager.setSingleModeCdmaCard(getSlotId(), true);
                                updateCarrierFile(getSlotId(), 4, imsi);
                                log("getCImsiFromCdmaGsmImsi done");
                            }
                            this.mCGImsiRetryNum = 0;
                        } else {
                            log("CDMA_GSM_IMSI not get, retry");
                            this.mCGImsiRetryNum++;
                            delayGetCdmaGsmImsi();
                        }
                    }
                }
            } else if (i != EVENT_GET_PBR_DONE) {
                switch (i) {
                    case EVENT_GET_SIM_APP_IMSI_DONE /* 38 */:
                        log("get SIM_APP_IMSI");
                        AsyncResultEx ar2 = AsyncResultEx.from(msg.obj);
                        if (ar2 != null) {
                            if (ar2.getException() == null) {
                                this.mIccRecordsInner.setCdmaGsmImsi(this.mIccRecordsInner.getImsiHw() + "," + ((String) ar2.getResult()));
                                break;
                            }
                        }
                        RlogEx.e(TAG, "Exception querying SIM APP IMSI");
                        break;
                    case EVENT_SET_MDN_DONE /* 39 */:
                        AsyncResultEx ar3 = AsyncResultEx.from(msg.obj);
                        if (ar3 != null) {
                            if (ar3.getException() == null) {
                                this.mIccRecordsInner.setMdn(this.mNewMdnNumber);
                            }
                            if (ar3.getUserObj() != null) {
                                AsyncResultEx.forMessage((Message) ar3.getUserObj()).setException(ar3.getException());
                                ((Message) ar3.getUserObj()).sendToTarget();
                            }
                            log("Success to update EF_MDN");
                            break;
                        } else {
                            break;
                        }
                    case EVENT_DELAY_GET_CDMA_GSM_IMSI /* 40 */:
                        log("EVENT_DELAY_GET_CDMA_GSM_IMSI");
                        this.mCi.getCdmaGsmImsi(obtainMessage(101));
                        break;
                    default:
                        this.mIccRecordsInner.handleMessageEx(msg);
                        break;
                }
            } else {
                AsyncResultEx ar4 = AsyncResultEx.from(msg.obj);
                isRecordLoadResponse = true;
                if (ar4 != null) {
                    if (ar4.getException() == null) {
                        this.mIs3Gphonebook = true;
                    } else if (CommandExceptionEx.isSpecificError(ar4.getException(), CommandExceptionEx.Error.SIM_ABSENT)) {
                        this.mIsSimPowerDown = true;
                        log("Get PBR Done,mIsSimPowerDown: " + this.mIsSimPowerDown);
                    }
                    this.mIsGetPBRDone = true;
                    log("Get PBR Done,mIs3Gphonebook: " + this.mIs3Gphonebook);
                }
            }
            if (!isRecordLoadResponse) {
                return;
            }
        } catch (RuntimeException exc) {
            RlogEx.w(TAG, "Exception parsing RUIM record", exc);
            if (0 == 0) {
                return;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                this.mIccRecordsInner.onRecordLoadedHw();
            }
            throw th;
        }
        this.mIccRecordsInner.onRecordLoadedHw();
    }

    public void getPbrRecordSize() {
        this.mFh.loadEFLinearFixedAll(20272, obtainMessage(EVENT_GET_PBR_DONE));
        this.mIccRecordsInner.addRecordsToLoadNum();
    }

    private int decodeImsiDigits(int digits, int length) {
        int constant = 0;
        for (int i = 0; i < length; i++) {
            constant = (constant * MAX_CG_IMSI_RETRIES) + 1;
        }
        int digits2 = digits + constant;
        int denominator = 1;
        for (int i2 = 0; i2 < length; i2++) {
            digits2 = (digits2 / denominator) % MAX_CG_IMSI_RETRIES == 0 ? digits2 - (denominator * MAX_CG_IMSI_RETRIES) : digits2;
            denominator *= MAX_CG_IMSI_RETRIES;
        }
        return digits2;
    }

    public String decodeCdmaImsi(byte[] data) {
        int mcc = decodeImsiDigits(((data[9] & 3) << 8) | (data[8] & 255), 3);
        int digits_11_12 = decodeImsiDigits(data[6] & Byte.MAX_VALUE, 2);
        int first3digits = ((data[2] & 3) << 8) + (data[1] & 255);
        int second3digits = (((data[5] & 255) << 8) | (data[4] & 255)) >> 6;
        int digit7 = (data[4] >> 2) & 15;
        if (digit7 > 9) {
            digit7 = 0;
        }
        int last3digits = ((data[4] & 3) << 8) | (data[3] & 255);
        int first3digits2 = decodeImsiDigits(first3digits, 3);
        int second3digits2 = decodeImsiDigits(second3digits, 3);
        int last3digits2 = decodeImsiDigits(last3digits, 3);
        return String.format(Locale.US, "%03d", Integer.valueOf(mcc)) + String.format(Locale.US, "%02d", Integer.valueOf(digits_11_12)) + String.format(Locale.US, "%03d", Integer.valueOf(first3digits2)) + String.format(Locale.US, "%03d", Integer.valueOf(second3digits2)) + String.format(Locale.US, "%d", Integer.valueOf(digit7)) + String.format(Locale.US, "%03d", Integer.valueOf(last3digits2));
    }

    public void setMdnNumber(String alphaTag, String number, Message onComplete) {
        byte[] mMdn = new byte[MAX_MDN_BYTES];
        if (number == null || number.length() == 0) {
            log("setMdnNumber, invalid number");
            this.mNewMdnNumber = null;
            for (int i = 0; i < MAX_MDN_BYTES; i++) {
                mMdn[i] = 0;
            }
            this.mFh.updateEFLinearFixed(28484, 1, mMdn, (String) null, obtainMessage(EVENT_SET_MDN_DONE, onComplete));
            return;
        }
        int length = number.length();
        int length2 = length > 15 ? 15 : length;
        log("setMdnNumber, validNumber input ");
        StringBuilder validNumber = new StringBuilder();
        for (int i2 = 0; i2 < length2; i2++) {
            char c = number.charAt(i2);
            if ((c >= '0' && c <= '9') || c == '*' || c == '#') {
                validNumber.append(c);
            } else {
                log("setMdnNumber, invalide char " + c + ", at index " + i2);
            }
        }
        byte[] convertByte = HwIccUtils.stringToCdmaDTMF(validNumber.toString());
        if (convertByte.length == 0 || convertByte.length > MAX_MDN_BYTES) {
            log("setMdnNumber, invalide convertByte");
            return;
        }
        for (int i3 = 0; i3 < MAX_MDN_BYTES; i3++) {
            mMdn[i3] = -1;
        }
        mMdn[0] = (byte) validNumber.length();
        System.arraycopy(convertByte, 0, mMdn, 1, convertByte.length < MAX_MDN_BYTES ? convertByte.length : MAX_CG_IMSI_RETRIES);
        if ("+".equals(number.substring(0, 1))) {
            mMdn[9] = 9;
            this.mNewMdnNumber = "+" + validNumber.toString();
        } else {
            mMdn[9] = 10;
            this.mNewMdnNumber = validNumber.toString();
        }
        mMdn[MAX_CG_IMSI_RETRIES] = 0;
        this.mFh.updateEFLinearFixed(28484, 1, mMdn, (String) null, obtainMessage(EVENT_SET_MDN_DONE, onComplete));
    }

    private void delayGetCdmaGsmImsi() {
        log("delayGetCdmaGsmImsi");
        sendMessageDelayed(obtainMessage(EVENT_DELAY_GET_CDMA_GSM_IMSI), 2000);
    }

    private boolean isValidCdmaGsmImsi() {
        String imsi = this.mIccRecordsInner.getCdmaGsmImsi();
        if (imsi == null) {
            return false;
        }
        String[] imsiArray = imsi.split(",");
        if (2 != imsiArray.length) {
            return false;
        }
        for (String str : imsiArray) {
            if (TextUtils.isEmpty(str.trim())) {
                return false;
            }
        }
        return true;
    }

    private void getCdmaGsmImsiDone(String cdmaGsmImsi) {
        String cdmaImsi = cdmaGsmImsi;
        if (cdmaGsmImsi != null) {
            String[] imsiArray = cdmaGsmImsi.split(",");
            if (imsiArray.length >= 2) {
                cdmaImsi = imsiArray[0];
            }
        }
        updateCarrierFile(getSlotId(), 4, cdmaImsi);
        log("getCImsiFromCdmaGsmImsi done");
    }

    public void dispose() {
        log("Disposing HwRuimRecords " + this);
        removeMessages(EVENT_DELAY_GET_CDMA_GSM_IMSI);
        this.mHwCarrierCardManager.destory(getSlotId(), this);
        this.mContext.unregisterReceiver(this.mIntentReceiver);
    }

    public String getOperatorNumericHw() {
        String imsi = this.mIccRecordsInner.getImsiHw();
        if (imsi == null) {
            String tempOperatorNumeric = SystemPropertiesEx.get("ro.cdma.home.operator.numeric");
            StringBuilder sb = new StringBuilder();
            sb.append("imsi is null tempOperatorNumeric = ");
            sb.append(LogEx.getLogHWInfo() ? tempOperatorNumeric : "***");
            log(sb.toString());
            return tempOperatorNumeric;
        } else if (this.mIccRecordsInner.getMncLength() != -1 && this.mIccRecordsInner.getMncLength() != 0) {
            return imsi.substring(0, this.mIccRecordsInner.getMncLength() + 3);
        } else {
            try {
                return imsi.substring(0, MccTableEx.smallestDigitsMccForMnc(Integer.parseInt(imsi.substring(0, 3))) + 3);
            } catch (RuntimeException e) {
                String tempOperatorNumeric2 = SystemPropertiesEx.get("ro.cdma.home.operator.numeric");
                log("imsi is not avalible,parseInt error, so return tempOperatorNumeric !");
                return tempOperatorNumeric2;
            }
        }
    }

    public void updateMccMncConfigWithCplmn(String operatorNumeric) {
        StringBuilder sb = new StringBuilder();
        sb.append("updateMccMncConfigWithCplmn: ");
        sb.append(LogEx.getLogHWInfo() ? operatorNumeric : "***");
        log(sb.toString());
        if (operatorNumeric != null && operatorNumeric.length() >= 5) {
            this.mIccRecordsInner.setSystemPropertyHw("gsm.sim.operator.numeric", operatorNumeric);
            MccTableEx.updateMccMncConfiguration(this.mContext, operatorNumeric);
        }
    }

    public void updateCsimImsi(byte[] data) {
        UiccCardApplicationEx simApp;
        if (true == HwModemCapability.isCapabilitySupport(18)) {
            try {
                this.mIccRecordsInner.setImsiHw(decodeCdmaImsi(data));
                log("IMSI: " + this.mIccRecordsInner.getIccIdHw().substring(0, 5) + "xxxxxxxxx");
                updateCarrierFile(getSlotId(), 4, this.mIccRecordsInner.getIccIdHw());
                updateMccMncConfigWithCplmn(this.mIccRecordsInner.getRUIMOperatorNumeric());
                if (this.mParentApp != null && this.mParentApp.getUiccCard() != null && (simApp = this.mParentApp.getUiccCard().getApplication(UiccControllerExt.APP_FAM_3GPP)) != null) {
                    this.mCi.getIMSIForApp(simApp.getAid(), obtainMessage(EVENT_GET_SIM_APP_IMSI_DONE));
                }
            } catch (RuntimeException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Illegal IMSI from CSIM_IMSIM=");
                sb.append(HW_DBG ? IccUtilsEx.bytesToHexString(data) : "***");
                RlogEx.e(TAG, sb.toString());
            }
        }
    }

    public void getCdmaGsmImsiFromHwRil() {
        this.mCi.getCdmaGsmImsi(obtainMessage(101));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void log(String s) {
        RlogEx.i(TAG, s);
    }
}
