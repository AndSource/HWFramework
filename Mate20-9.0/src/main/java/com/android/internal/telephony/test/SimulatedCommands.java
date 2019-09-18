package com.android.internal.telephony.test;

import android.hardware.radio.V1_0.DataRegStateResult;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.VoiceRegStateResult;
import android.net.KeepalivePacketData;
import android.net.LinkProperties;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.SystemClock;
import android.os.WorkSource;
import android.service.carrier.CarrierIdentifier;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.Rlog;
import android.telephony.SignalStrength;
import android.telephony.data.DataProfile;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.AbstractGsmCdmaPhone;
import com.android.internal.telephony.BaseCommands;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.LastCallFailCause;
import com.android.internal.telephony.ProxyController;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.SmsResponse;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.cdma.CdmaSmsBroadcastConfigInfo;
import com.android.internal.telephony.dataconnection.KeepaliveStatus;
import com.android.internal.telephony.gsm.SmsBroadcastConfigInfo;
import com.android.internal.telephony.gsm.SuppServiceNotification;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccSlotStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulatedCommands extends BaseCommands implements CommandsInterface, SimulatedRadioControl {
    public static final int DEFAULT_PIN1_ATTEMPT = 5;
    public static final int DEFAULT_PIN2_ATTEMPT = 5;
    public static final String DEFAULT_SIM_PIN2_CODE = "5678";
    public static final String DEFAULT_SIM_PIN_CODE = "1234";
    public static final String FAKE_ESN = "1234";
    public static final String FAKE_IMEI = "012345678901234";
    public static final String FAKE_IMEISV = "99";
    public static final String FAKE_LONG_NAME = "Fake long name";
    public static final String FAKE_MCC_MNC = "310260";
    public static final String FAKE_MEID = "1234";
    public static final String FAKE_SHORT_NAME = "Fake short name";
    private static final SimFdnState INITIAL_FDN_STATE = SimFdnState.NONE;
    private static final SimLockState INITIAL_LOCK_STATE = SimLockState.NONE;
    private static final String LOG_TAG = "SimulatedCommands";
    private static final String SIM_PUK2_CODE = "87654321";
    private static final String SIM_PUK_CODE = "12345678";
    private final AtomicInteger getNetworkSelectionModeCallCount;
    private AtomicBoolean mAllowed;
    private List<CellInfo> mCellInfoList;
    private int mChannelId;
    public boolean mCssSupported;
    private int mDataRadioTech;
    private int mDataRegState;
    private boolean mDcSuccess;
    public int mDefaultRoamingIndicator;
    private final AtomicInteger mGetDataRegistrationStateCallCount;
    private final AtomicInteger mGetOperatorCallCount;
    private final AtomicInteger mGetVoiceRegistrationStateCallCount;
    HandlerThread mHandlerThread;
    private IccCardStatus mIccCardStatus;
    private IccIoResult mIccIoResultForApduLogicalChannel;
    private IccSlotStatus mIccSlotStatus;
    private String mImei;
    private String mImeiSv;
    private int[] mImsRegState;
    private boolean mIsRadioPowerFailResponse;
    public int mMaxDataCalls;
    int mNetworkType;
    int mNextCallFailCause;
    int mPausedResponseCount;
    ArrayList<Message> mPausedResponses;
    int mPin1attemptsRemaining = 5;
    String mPin2Code;
    int mPin2UnlockAttempts;
    String mPinCode;
    int mPinUnlockAttempts;
    int mPuk2UnlockAttempts;
    int mPukUnlockAttempts;
    public int mReasonForDenial;
    public int mRoamingIndicator;
    private SetupDataCallResult mSetupDataCallResult;
    private SignalStrength mSignalStrength;
    boolean mSimFdnEnabled;
    SimFdnState mSimFdnEnabledState;
    boolean mSimLockEnabled;
    SimLockState mSimLockedState;
    boolean mSsnNotifyOn;
    public int mSystemIsInPrl;
    private int mVoiceRadioTech;
    private int mVoiceRegState;
    SimulatedGsmCallState simulatedCallState;

    private enum SimFdnState {
        NONE,
        REQUIRE_PIN2,
        REQUIRE_PUK2,
        SIM_PERM_LOCKED
    }

    private enum SimLockState {
        NONE,
        REQUIRE_PIN,
        REQUIRE_PUK,
        SIM_PERM_LOCKED
    }

    public SimulatedCommands() {
        super(null);
        boolean z = false;
        this.mSsnNotifyOn = false;
        this.mVoiceRegState = 1;
        this.mVoiceRadioTech = 3;
        this.mDataRegState = 1;
        this.mDataRadioTech = 3;
        this.mChannelId = -1;
        this.mPausedResponses = new ArrayList<>();
        this.mNextCallFailCause = 16;
        this.mDcSuccess = true;
        this.mIsRadioPowerFailResponse = false;
        this.mGetVoiceRegistrationStateCallCount = new AtomicInteger(0);
        this.mGetDataRegistrationStateCallCount = new AtomicInteger(0);
        this.mGetOperatorCallCount = new AtomicInteger(0);
        this.getNetworkSelectionModeCallCount = new AtomicInteger(0);
        this.mAllowed = new AtomicBoolean(false);
        this.mHandlerThread = new HandlerThread(LOG_TAG);
        this.mHandlerThread.start();
        this.simulatedCallState = new SimulatedGsmCallState(this.mHandlerThread.getLooper());
        setRadioState(CommandsInterface.RadioState.RADIO_ON);
        this.mSimLockedState = INITIAL_LOCK_STATE;
        this.mSimLockEnabled = this.mSimLockedState != SimLockState.NONE;
        this.mPinCode = "1234";
        this.mSimFdnEnabledState = INITIAL_FDN_STATE;
        this.mSimFdnEnabled = this.mSimFdnEnabledState != SimFdnState.NONE ? true : z;
        this.mPin2Code = DEFAULT_SIM_PIN2_CODE;
    }

    public void dispose() {
        if (this.mHandlerThread != null) {
            this.mHandlerThread.quit();
        }
    }

    private void log(String str) {
        Rlog.d(LOG_TAG, str);
    }

    public void getIccCardStatus(Message result) {
        SimulatedCommandsVerifier.getInstance().getIccCardStatus(result);
        if (this.mIccCardStatus != null) {
            resultSuccess(result, this.mIccCardStatus);
        } else {
            resultFail(result, null, new RuntimeException("IccCardStatus not set"));
        }
    }

    public void setIccSlotStatus(IccSlotStatus iccSlotStatus) {
        this.mIccSlotStatus = iccSlotStatus;
    }

    public void getIccSlotsStatus(Message result) {
        SimulatedCommandsVerifier.getInstance().getIccSlotsStatus(result);
        if (this.mIccSlotStatus != null) {
            resultSuccess(result, this.mIccSlotStatus);
        } else {
            resultFail(result, null, new CommandException(CommandException.Error.REQUEST_NOT_SUPPORTED));
        }
    }

    public void setLogicalToPhysicalSlotMapping(int[] physicalSlots, Message result) {
        unimplemented(result);
    }

    public void supplyIccPin(String pin, Message result) {
        if (this.mSimLockedState != SimLockState.REQUIRE_PIN) {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin: wrong state, state=" + this.mSimLockedState);
            resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        } else if (pin == null || !pin.equals(this.mPinCode)) {
            if (result != null) {
                this.mPinUnlockAttempts++;
                Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin: failed! attempt=" + this.mPinUnlockAttempts);
                if (this.mPinUnlockAttempts >= 5) {
                    Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin: set state to REQUIRE_PUK");
                    this.mSimLockedState = SimLockState.REQUIRE_PUK;
                }
                resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            }
        } else {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin: success!");
            this.mPinUnlockAttempts = 0;
            this.mSimLockedState = SimLockState.NONE;
            this.mIccStatusChangedRegistrants.notifyRegistrants();
            resultSuccess(result, null);
        }
    }

    public void supplyIccPuk(String puk, String newPin, Message result) {
        if (this.mSimLockedState != SimLockState.REQUIRE_PUK) {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk: wrong state, state=" + this.mSimLockedState);
            resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        } else if (puk == null || !puk.equals(SIM_PUK_CODE)) {
            if (result != null) {
                this.mPukUnlockAttempts++;
                Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk: failed! attempt=" + this.mPukUnlockAttempts);
                if (this.mPukUnlockAttempts >= 10) {
                    Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk: set state to SIM_PERM_LOCKED");
                    this.mSimLockedState = SimLockState.SIM_PERM_LOCKED;
                }
                resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            }
        } else {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk: success!");
            this.mSimLockedState = SimLockState.NONE;
            this.mPukUnlockAttempts = 0;
            this.mIccStatusChangedRegistrants.notifyRegistrants();
            resultSuccess(result, null);
        }
    }

    public void supplyIccPin2(String pin2, Message result) {
        if (this.mSimFdnEnabledState != SimFdnState.REQUIRE_PIN2) {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin2: wrong state, state=" + this.mSimFdnEnabledState);
            resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        } else if (pin2 == null || !pin2.equals(this.mPin2Code)) {
            if (result != null) {
                this.mPin2UnlockAttempts++;
                Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin2: failed! attempt=" + this.mPin2UnlockAttempts);
                if (this.mPin2UnlockAttempts >= 5) {
                    Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin2: set state to REQUIRE_PUK2");
                    this.mSimFdnEnabledState = SimFdnState.REQUIRE_PUK2;
                }
                resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            }
        } else {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPin2: success!");
            this.mPin2UnlockAttempts = 0;
            this.mSimFdnEnabledState = SimFdnState.NONE;
            resultSuccess(result, null);
        }
    }

    public void supplyIccPuk2(String puk2, String newPin2, Message result) {
        if (this.mSimFdnEnabledState != SimFdnState.REQUIRE_PUK2) {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk2: wrong state, state=" + this.mSimLockedState);
            resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
        } else if (puk2 == null || !puk2.equals(SIM_PUK2_CODE)) {
            if (result != null) {
                this.mPuk2UnlockAttempts++;
                Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk2: failed! attempt=" + this.mPuk2UnlockAttempts);
                if (this.mPuk2UnlockAttempts >= 10) {
                    Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk2: set state to SIM_PERM_LOCKED");
                    this.mSimFdnEnabledState = SimFdnState.SIM_PERM_LOCKED;
                }
                resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            }
        } else {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPuk2: success!");
            this.mSimFdnEnabledState = SimFdnState.NONE;
            this.mPuk2UnlockAttempts = 0;
            resultSuccess(result, null);
        }
    }

    public void changeIccPin(String oldPin, String newPin, Message result) {
        if (oldPin == null || !oldPin.equals(this.mPinCode)) {
            Rlog.i(LOG_TAG, "[SimCmd] changeIccPin: pin failed!");
            resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            return;
        }
        this.mPinCode = newPin;
        resultSuccess(result, null);
    }

    public void changeIccPin2(String oldPin2, String newPin2, Message result) {
        if (oldPin2 == null || !oldPin2.equals(this.mPin2Code)) {
            Rlog.i(LOG_TAG, "[SimCmd] changeIccPin2: pin2 failed!");
            resultFail(result, null, new CommandException(CommandException.Error.PASSWORD_INCORRECT));
            return;
        }
        this.mPin2Code = newPin2;
        resultSuccess(result, null);
    }

    public void changeBarringPassword(String facility, String oldPwd, String newPwd, Message result) {
        unimplemented(result);
    }

    public void setSuppServiceNotifications(boolean enable, Message result) {
        resultSuccess(result, null);
        if (enable && this.mSsnNotifyOn) {
            Rlog.w(LOG_TAG, "Supp Service Notifications already enabled!");
        }
        this.mSsnNotifyOn = enable;
    }

    public void queryFacilityLock(String facility, String pin, int serviceClass, Message result) {
        queryFacilityLockForApp(facility, pin, serviceClass, null, result);
    }

    public void queryFacilityLockForApp(String facility, String pin, int serviceClass, String appId, Message result) {
        if (facility != null && facility.equals(CommandsInterface.CB_FACILITY_BA_SIM)) {
            if (result != null) {
                int[] r = {this.mSimLockEnabled};
                StringBuilder sb = new StringBuilder();
                sb.append("[SimCmd] queryFacilityLock: SIM is ");
                sb.append(r[0] == 0 ? "unlocked" : "locked");
                Rlog.i(LOG_TAG, sb.toString());
                resultSuccess(result, r);
            }
        } else if (facility == null || !facility.equals(CommandsInterface.CB_FACILITY_BA_FD)) {
            unimplemented(result);
        } else {
            if (result != null) {
                int[] r2 = {this.mSimFdnEnabled};
                StringBuilder sb2 = new StringBuilder();
                sb2.append("[SimCmd] queryFacilityLock: FDN is ");
                sb2.append(r2[0] == 0 ? "disabled" : "enabled");
                Rlog.i(LOG_TAG, sb2.toString());
                resultSuccess(result, r2);
            }
        }
    }

    public void setFacilityLock(String facility, boolean lockEnabled, String pin, int serviceClass, Message result) {
        setFacilityLockForApp(facility, lockEnabled, pin, serviceClass, null, result);
    }

    public void setFacilityLockForApp(String facility, boolean lockEnabled, String pin, int serviceClass, String appId, Message result) {
        if (facility == null || !facility.equals(CommandsInterface.CB_FACILITY_BA_SIM)) {
            if (facility == null || !facility.equals(CommandsInterface.CB_FACILITY_BA_FD)) {
                unimplemented(result);
            } else if (pin == null || !pin.equals(this.mPin2Code)) {
                Rlog.i(LOG_TAG, "[SimCmd] setFacilityLock: pin2 failed!");
                resultFail(result, null, new CommandException(CommandException.Error.GENERIC_FAILURE));
            } else {
                Rlog.i(LOG_TAG, "[SimCmd] setFacilityLock: pin2 is valid");
                this.mSimFdnEnabled = lockEnabled;
                resultSuccess(result, null);
            }
        } else if (pin == null || !pin.equals(this.mPinCode)) {
            Rlog.i(LOG_TAG, "[SimCmd] setFacilityLock: pin failed!");
            resultFail(result, null, new CommandException(CommandException.Error.GENERIC_FAILURE));
        } else {
            Rlog.i(LOG_TAG, "[SimCmd] setFacilityLock: pin is valid");
            this.mSimLockEnabled = lockEnabled;
            resultSuccess(result, null);
        }
    }

    public void supplyNetworkDepersonalization(String netpin, Message result) {
        unimplemented(result);
    }

    public void getCurrentCalls(Message result) {
        SimulatedCommandsVerifier.getInstance().getCurrentCalls(result);
        if (this.mState != CommandsInterface.RadioState.RADIO_ON || isSimLocked()) {
            resultFail(result, null, new CommandException(CommandException.Error.RADIO_NOT_AVAILABLE));
        } else {
            resultSuccess(result, this.simulatedCallState.getDriverCalls());
        }
    }

    @Deprecated
    public void getPDPContextList(Message result) {
        getDataCallList(result);
    }

    public void getDataCallList(Message result) {
        resultSuccess(result, new ArrayList(0));
    }

    public void dial(String address, int clirMode, Message result) {
        SimulatedCommandsVerifier.getInstance().dial(address, clirMode, result);
        this.simulatedCallState.onDial(address);
        resultSuccess(result, null);
    }

    public void dial(String address, int clirMode, UUSInfo uusInfo, Message result) {
        SimulatedCommandsVerifier.getInstance().dial(address, clirMode, uusInfo, result);
        this.simulatedCallState.onDial(address);
        resultSuccess(result, null);
    }

    public void getIMSI(Message result) {
        getIMSIForApp(null, result);
    }

    public void getIMSIForApp(String aid, Message result) {
        resultSuccess(result, FAKE_IMEI);
    }

    public void setIMEI(String imei) {
        this.mImei = imei;
    }

    public void getIMEI(Message result) {
        SimulatedCommandsVerifier.getInstance().getIMEI(result);
        resultSuccess(result, this.mImei != null ? this.mImei : FAKE_IMEI);
    }

    public void setIMEISV(String imeisv) {
        this.mImeiSv = imeisv;
    }

    public void getIMEISV(Message result) {
        SimulatedCommandsVerifier.getInstance().getIMEISV(result);
        resultSuccess(result, this.mImeiSv != null ? this.mImeiSv : FAKE_IMEISV);
    }

    public void hangupConnection(int gsmIndex, Message result) {
        if (!this.simulatedCallState.onChld('1', (char) (48 + gsmIndex))) {
            Rlog.i(AbstractGsmCdmaPhone.GSM_PHONE, "[SimCmd] hangupConnection: resultFail");
            resultFail(result, null, new RuntimeException("Hangup Error"));
            return;
        }
        Rlog.i(AbstractGsmCdmaPhone.GSM_PHONE, "[SimCmd] hangupConnection: resultSuccess");
        resultSuccess(result, null);
    }

    public void hangupWaitingOrBackground(Message result) {
        if (!this.simulatedCallState.onChld('0', 0)) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void hangupForegroundResumeBackground(Message result) {
        if (!this.simulatedCallState.onChld('1', 0)) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void switchWaitingOrHoldingAndActive(Message result) {
        if (!this.simulatedCallState.onChld('2', 0)) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void conference(Message result) {
        if (!this.simulatedCallState.onChld('3', 0)) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void explicitCallTransfer(Message result) {
        if (!this.simulatedCallState.onChld('4', 0)) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void separateConnection(int gsmIndex, Message result) {
        if (!this.simulatedCallState.onChld('2', (char) (gsmIndex + 48))) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void acceptCall(Message result) {
        SimulatedCommandsVerifier.getInstance().acceptCall(result);
        if (!this.simulatedCallState.onAnswer()) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void rejectCall(Message result) {
        if (!this.simulatedCallState.onChld('0', 0)) {
            resultFail(result, null, new RuntimeException("Hangup Error"));
        } else {
            resultSuccess(result, null);
        }
    }

    public void getLastCallFailCause(Message result) {
        LastCallFailCause mFailCause = new LastCallFailCause();
        mFailCause.causeCode = this.mNextCallFailCause;
        resultSuccess(result, mFailCause);
    }

    @Deprecated
    public void getLastPdpFailCause(Message result) {
        unimplemented(result);
    }

    public void getLastDataCallFailCause(Message result) {
        unimplemented(result);
    }

    public void setMute(boolean enableMute, Message result) {
        unimplemented(result);
    }

    public void getMute(Message result) {
        unimplemented(result);
    }

    public void setSignalStrength(SignalStrength signalStrength) {
        this.mSignalStrength = signalStrength;
    }

    public void getSignalStrength(Message result) {
        if (this.mSignalStrength == null) {
            this.mSignalStrength = null;
            SignalStrength signalStrength = new SignalStrength(20, 0, -1, -1, -1, -1, -1, 99, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, 0, 0, -1);
            this.mSignalStrength = signalStrength;
        }
        resultSuccess(result, this.mSignalStrength);
    }

    public void setBandMode(int bandMode, Message result) {
        resultSuccess(result, null);
    }

    public void queryAvailableBandMode(Message result) {
        resultSuccess(result, new int[]{4, 2, 3, 4});
    }

    public void sendTerminalResponse(String contents, Message response) {
        resultSuccess(response, null);
    }

    public void sendEnvelope(String contents, Message response) {
        resultSuccess(response, null);
    }

    public void sendEnvelopeWithStatus(String contents, Message response) {
        resultSuccess(response, null);
    }

    public void handleCallSetupRequestFromSim(boolean accept, Message response) {
        resultSuccess(response, null);
    }

    public void setVoiceRadioTech(int voiceRadioTech) {
        this.mVoiceRadioTech = voiceRadioTech;
    }

    public void setVoiceRegState(int voiceRegState) {
        this.mVoiceRegState = voiceRegState;
    }

    public void getVoiceRegistrationState(Message result) {
        this.mGetVoiceRegistrationStateCallCount.incrementAndGet();
        VoiceRegStateResult ret = new VoiceRegStateResult();
        ret.regState = this.mVoiceRegState;
        ret.rat = this.mVoiceRadioTech;
        ret.cssSupported = this.mCssSupported;
        ret.roamingIndicator = this.mRoamingIndicator;
        ret.systemIsInPrl = this.mSystemIsInPrl;
        ret.defaultRoamingIndicator = this.mDefaultRoamingIndicator;
        ret.reasonForDenial = this.mReasonForDenial;
        resultSuccess(result, ret);
    }

    @VisibleForTesting
    public int getGetVoiceRegistrationStateCallCount() {
        return this.mGetVoiceRegistrationStateCallCount.get();
    }

    public void setDataRadioTech(int radioTech) {
        this.mDataRadioTech = radioTech;
    }

    public void setDataRegState(int dataRegState) {
        this.mDataRegState = dataRegState;
    }

    public void getDataRegistrationState(Message result) {
        this.mGetDataRegistrationStateCallCount.incrementAndGet();
        DataRegStateResult ret = new DataRegStateResult();
        ret.regState = this.mDataRegState;
        ret.rat = this.mDataRadioTech;
        ret.maxDataCalls = this.mMaxDataCalls;
        ret.reasonDataDenied = this.mReasonForDenial;
        resultSuccess(result, ret);
    }

    @VisibleForTesting
    public int getGetDataRegistrationStateCallCount() {
        return this.mGetDataRegistrationStateCallCount.get();
    }

    public void getOperator(Message result) {
        this.mGetOperatorCallCount.incrementAndGet();
        resultSuccess(result, new String[]{FAKE_LONG_NAME, FAKE_SHORT_NAME, FAKE_MCC_MNC});
    }

    @VisibleForTesting
    public int getGetOperatorCallCount() {
        int i = this.mGetOperatorCallCount.get();
        return this.mGetOperatorCallCount.get();
    }

    public void sendDtmf(char c, Message result) {
        resultSuccess(result, null);
    }

    public void startDtmf(char c, Message result) {
        resultSuccess(result, null);
    }

    public void stopDtmf(Message result) {
        resultSuccess(result, null);
    }

    public void sendBurstDtmf(String dtmfString, int on, int off, Message result) {
        SimulatedCommandsVerifier.getInstance().sendBurstDtmf(dtmfString, on, off, result);
        resultSuccess(result, null);
    }

    public void sendSMS(String smscPDU, String pdu, Message result) {
        SimulatedCommandsVerifier.getInstance().sendSMS(smscPDU, pdu, result);
        resultSuccess(result, new SmsResponse(0, null, 0));
    }

    public void sendSMSExpectMore(String smscPDU, String pdu, Message result) {
        unimplemented(result);
    }

    public void deleteSmsOnSim(int index, Message response) {
        Rlog.d(LOG_TAG, "Delete message at index " + index);
        unimplemented(response);
    }

    public void deleteSmsOnRuim(int index, Message response) {
        Rlog.d(LOG_TAG, "Delete RUIM message at index " + index);
        unimplemented(response);
    }

    public void writeSmsToSim(int status, String smsc, String pdu, Message response) {
        Rlog.d(LOG_TAG, "Write SMS to SIM with status " + status);
        unimplemented(response);
    }

    public void writeSmsToRuim(int status, String pdu, Message response) {
        Rlog.d(LOG_TAG, "Write SMS to RUIM with status " + status);
        unimplemented(response);
    }

    public void setDataCallResult(boolean success, SetupDataCallResult dcResult) {
        this.mSetupDataCallResult = dcResult;
        this.mDcSuccess = success;
    }

    public void triggerNITZupdate(String NITZStr) {
        if (NITZStr != null) {
            this.mNITZTimeRegistrant.notifyRegistrant(new AsyncResult(null, new Object[]{NITZStr, Long.valueOf(SystemClock.elapsedRealtime())}, null));
        }
    }

    public void setupDataCall(int accessNetworkType, DataProfile dataProfile, boolean isRoaming, boolean allowRoaming, int reason, LinkProperties linkProperties, Message result) {
        SimulatedCommandsVerifier.getInstance().setupDataCall(accessNetworkType, dataProfile, isRoaming, allowRoaming, reason, linkProperties, result);
        if (this.mSetupDataCallResult == null) {
            try {
                this.mSetupDataCallResult = new SetupDataCallResult();
                this.mSetupDataCallResult.status = 0;
                this.mSetupDataCallResult.suggestedRetryTime = -1;
                this.mSetupDataCallResult.cid = 1;
                this.mSetupDataCallResult.active = 2;
                this.mSetupDataCallResult.type = "IP";
                this.mSetupDataCallResult.ifname = "rmnet_data7";
                this.mSetupDataCallResult.addresses = "12.34.56.78";
                this.mSetupDataCallResult.dnses = "98.76.54.32";
                this.mSetupDataCallResult.gateways = "11.22.33.44";
                this.mSetupDataCallResult.pcscf = "";
                this.mSetupDataCallResult.mtu = 1440;
            } catch (Exception e) {
            }
        }
        if (this.mDcSuccess) {
            resultSuccess(result, this.mSetupDataCallResult);
        } else {
            resultFail(result, this.mSetupDataCallResult, new RuntimeException("Setup data call failed!"));
        }
    }

    public void deactivateDataCall(int cid, int reason, Message result) {
        SimulatedCommandsVerifier.getInstance().deactivateDataCall(cid, reason, result);
        resultSuccess(result, null);
    }

    public void setPreferredNetworkType(int networkType, Message result) {
        SimulatedCommandsVerifier.getInstance().setPreferredNetworkType(networkType, result);
        this.mNetworkType = networkType;
        resultSuccess(result, null);
    }

    public void getPreferredNetworkType(Message result) {
        SimulatedCommandsVerifier.getInstance().getPreferredNetworkType(result);
        resultSuccess(result, new int[]{this.mNetworkType});
    }

    public void getNeighboringCids(Message result, WorkSource workSource) {
        int[] ret = new int[7];
        ret[0] = 6;
        for (int i = 1; i < 7; i++) {
            ret[i] = i;
        }
        resultSuccess(result, ret);
    }

    public void setLocationUpdates(boolean enable, Message response) {
        SimulatedCommandsVerifier.getInstance().setLocationUpdates(enable, response);
        resultSuccess(response, null);
    }

    public void getSmscAddress(Message result) {
        unimplemented(result);
    }

    public void setSmscAddress(String address, Message result) {
        unimplemented(result);
    }

    public void reportSmsMemoryStatus(boolean available, Message result) {
        resultSuccess(result, null);
        SimulatedCommandsVerifier.getInstance().reportSmsMemoryStatus(available, result);
    }

    public void reportStkServiceIsRunning(Message result) {
        resultSuccess(result, null);
    }

    public void getCdmaSubscriptionSource(Message result) {
        unimplemented(result);
    }

    private boolean isSimLocked() {
        if (this.mSimLockedState != SimLockState.NONE) {
            return true;
        }
        return false;
    }

    public void setRadioPower(boolean on, Message result) {
        if (this.mIsRadioPowerFailResponse) {
            resultFail(result, null, new RuntimeException("setRadioPower failed!"));
            return;
        }
        if (on) {
            setRadioState(CommandsInterface.RadioState.RADIO_ON);
        } else {
            setRadioState(CommandsInterface.RadioState.RADIO_OFF);
        }
        resultSuccess(result, null);
    }

    public void acknowledgeLastIncomingGsmSms(boolean success, int cause, Message result) {
        unimplemented(result);
        SimulatedCommandsVerifier.getInstance().acknowledgeLastIncomingGsmSms(success, cause, result);
    }

    public void acknowledgeLastIncomingCdmaSms(boolean success, int cause, Message result) {
        unimplemented(result);
    }

    public void acknowledgeIncomingGsmSmsWithPdu(boolean success, String ackPdu, Message result) {
        unimplemented(result);
    }

    public void iccIO(int command, int fileid, String path, int p1, int p2, int p3, String data, String pin2, Message response) {
        iccIOForApp(command, fileid, path, p1, p2, p3, data, pin2, null, response);
    }

    public void iccIOForApp(int command, int fileid, String path, int p1, int p2, int p3, String data, String pin2, String aid, Message result) {
        unimplemented(result);
    }

    public void queryCLIP(Message response) {
        unimplemented(response);
    }

    public void getCLIR(Message result) {
        unimplemented(result);
    }

    public void setCLIR(int clirMode, Message result) {
        unimplemented(result);
    }

    public void queryCallWaiting(int serviceClass, Message response) {
        unimplemented(response);
    }

    public void setCallWaiting(boolean enable, int serviceClass, Message response) {
        unimplemented(response);
    }

    public void setCallForward(int action, int cfReason, int serviceClass, String number, int timeSeconds, Message result) {
        SimulatedCommandsVerifier.getInstance().setCallForward(action, cfReason, serviceClass, number, timeSeconds, result);
        resultSuccess(result, null);
    }

    public void queryCallForwardStatus(int cfReason, int serviceClass, String number, Message result) {
        SimulatedCommandsVerifier.getInstance().queryCallForwardStatus(cfReason, serviceClass, number, result);
        resultSuccess(result, null);
    }

    public void setNetworkSelectionModeAutomatic(Message result) {
        unimplemented(result);
    }

    public void exitEmergencyCallbackMode(Message result) {
        unimplemented(result);
    }

    public void setNetworkSelectionModeManual(String operatorNumeric, Message result) {
        unimplemented(result);
    }

    public void getNetworkSelectionMode(Message result) {
        SimulatedCommandsVerifier.getInstance().getNetworkSelectionMode(result);
        this.getNetworkSelectionModeCallCount.incrementAndGet();
        resultSuccess(result, new int[]{0});
    }

    @VisibleForTesting
    public int getGetNetworkSelectionModeCallCount() {
        return this.getNetworkSelectionModeCallCount.get();
    }

    public void getAvailableNetworks(Message result) {
        unimplemented(result);
    }

    public void startNetworkScan(NetworkScanRequest nsr, Message result) {
        unimplemented(result);
    }

    public void stopNetworkScan(Message result) {
        unimplemented(result);
    }

    public void getBasebandVersion(Message result) {
        SimulatedCommandsVerifier.getInstance().getBasebandVersion(result);
        resultSuccess(result, LOG_TAG);
    }

    public void triggerIncomingStkCcAlpha(String alphaString) {
        if (this.mCatCcAlphaRegistrant != null) {
            this.mCatCcAlphaRegistrant.notifyResult(alphaString);
        }
    }

    public void sendStkCcAplha(String alphaString) {
        triggerIncomingStkCcAlpha(alphaString);
    }

    public void triggerIncomingUssd(String statusCode, String message) {
        if (this.mUSSDRegistrant != null) {
            this.mUSSDRegistrant.notifyResult(new String[]{statusCode, message});
        }
    }

    public void sendUSSD(String ussdString, Message result) {
        if (ussdString.equals("#646#")) {
            resultSuccess(result, null);
            triggerIncomingUssd(ProxyController.MODEM_0, "You have NNN minutes remaining.");
            return;
        }
        resultSuccess(result, null);
        triggerIncomingUssd(ProxyController.MODEM_0, "All Done");
    }

    public void cancelPendingUssd(Message response) {
        resultSuccess(response, null);
    }

    public void resetRadio(Message result) {
        unimplemented(result);
    }

    public void invokeOemRilRequestRaw(byte[] data, Message response) {
        if (response != null) {
            AsyncResult.forMessage(response).result = data;
            response.sendToTarget();
        }
    }

    public void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo, Message response) {
        if (response != null) {
            AsyncResult.forMessage(response).result = imsiEncryptionInfo;
            response.sendToTarget();
        }
    }

    public void invokeOemRilRequestStrings(String[] strings, Message response) {
        if (response != null) {
            AsyncResult.forMessage(response).result = strings;
            response.sendToTarget();
        }
    }

    public void triggerRing(String number) {
        this.simulatedCallState.triggerRing(number);
        this.mCallStateRegistrants.notifyRegistrants();
    }

    public void progressConnectingCallState() {
        this.simulatedCallState.progressConnectingCallState();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    public void progressConnectingToActive() {
        this.simulatedCallState.progressConnectingToActive();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    public void setAutoProgressConnectingCall(boolean b) {
        this.simulatedCallState.setAutoProgressConnectingCall(b);
    }

    public void setNextDialFailImmediately(boolean b) {
        this.simulatedCallState.setNextDialFailImmediately(b);
    }

    public void setNextCallFailCause(int gsmCause) {
        this.mNextCallFailCause = gsmCause;
    }

    public void triggerHangupForeground() {
        this.simulatedCallState.triggerHangupForeground();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    public void triggerHangupBackground() {
        this.simulatedCallState.triggerHangupBackground();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    public void triggerSsn(int type, int code) {
        SuppServiceNotification not = new SuppServiceNotification();
        not.notificationType = type;
        not.code = code;
        this.mSsnRegistrant.notifyRegistrant(new AsyncResult(null, not, null));
    }

    public void shutdown() {
        setRadioState(CommandsInterface.RadioState.RADIO_UNAVAILABLE);
        Looper looper = this.mHandlerThread.getLooper();
        if (looper != null) {
            looper.quit();
        }
    }

    public void triggerHangupAll() {
        this.simulatedCallState.triggerHangupAll();
        this.mCallStateRegistrants.notifyRegistrants();
    }

    public void triggerIncomingSMS(String message) {
    }

    public void pauseResponses() {
        this.mPausedResponseCount++;
    }

    public void resumeResponses() {
        this.mPausedResponseCount--;
        if (this.mPausedResponseCount == 0) {
            int s = this.mPausedResponses.size();
            for (int i = 0; i < s; i++) {
                this.mPausedResponses.get(i).sendToTarget();
            }
            this.mPausedResponses.clear();
            return;
        }
        Rlog.e(AbstractGsmCdmaPhone.GSM_PHONE, "SimulatedCommands.resumeResponses < 0");
    }

    private void unimplemented(Message result) {
        if (result != null) {
            AsyncResult.forMessage(result).exception = new RuntimeException("Unimplemented");
            if (this.mPausedResponseCount > 0) {
                this.mPausedResponses.add(result);
            } else {
                result.sendToTarget();
            }
        }
    }

    private void resultSuccess(Message result, Object ret) {
        if (result != null) {
            AsyncResult.forMessage(result).result = ret;
            if (this.mPausedResponseCount > 0) {
                this.mPausedResponses.add(result);
            } else {
                result.sendToTarget();
            }
        }
    }

    private void resultFail(Message result, Object ret, Throwable tr) {
        if (result != null) {
            AsyncResult.forMessage(result, ret, tr);
            if (this.mPausedResponseCount > 0) {
                this.mPausedResponses.add(result);
            } else {
                result.sendToTarget();
            }
        }
    }

    public void getDeviceIdentity(Message response) {
        SimulatedCommandsVerifier.getInstance().getDeviceIdentity(response);
        resultSuccess(response, new String[]{FAKE_IMEI, FAKE_IMEISV, "1234", "1234"});
    }

    public void getCDMASubscription(Message result) {
        resultSuccess(result, new String[]{"123", "456", "789", "234", "345"});
    }

    public void setCdmaSubscriptionSource(int cdmaSubscriptionType, Message response) {
        unimplemented(response);
    }

    public void queryCdmaRoamingPreference(Message response) {
        unimplemented(response);
    }

    public void setCdmaRoamingPreference(int cdmaRoamingType, Message response) {
        unimplemented(response);
    }

    public void setPhoneType(int phoneType) {
    }

    public void getPreferredVoicePrivacy(Message result) {
        unimplemented(result);
    }

    public void setPreferredVoicePrivacy(boolean enable, Message result) {
        unimplemented(result);
    }

    public void setTTYMode(int ttyMode, Message response) {
        Rlog.w(LOG_TAG, "Not implemented in SimulatedCommands");
        unimplemented(response);
    }

    public void queryTTYMode(Message response) {
        unimplemented(response);
    }

    public void sendCDMAFeatureCode(String FeatureCode, Message response) {
        unimplemented(response);
    }

    public void sendCdmaSms(byte[] pdu, Message response) {
        SimulatedCommandsVerifier.getInstance().sendCdmaSms(pdu, response);
        resultSuccess(response, null);
    }

    public void setCdmaBroadcastActivation(boolean activate, Message response) {
        unimplemented(response);
    }

    public void getCdmaBroadcastConfig(Message response) {
        unimplemented(response);
    }

    public void setCdmaBroadcastConfig(CdmaSmsBroadcastConfigInfo[] configs, Message response) {
        unimplemented(response);
    }

    public void forceDataDormancy(Message response) {
        unimplemented(response);
    }

    public void setGsmBroadcastActivation(boolean activate, Message response) {
        unimplemented(response);
    }

    public void setGsmBroadcastConfig(SmsBroadcastConfigInfo[] config, Message response) {
        unimplemented(response);
    }

    public void getGsmBroadcastConfig(Message response) {
        unimplemented(response);
    }

    public void supplyIccPinForApp(String pin, String aid, Message response) {
        SimulatedCommandsVerifier.getInstance().supplyIccPinForApp(pin, aid, response);
        if (this.mPinCode == null || !this.mPinCode.equals(pin)) {
            Rlog.i(LOG_TAG, "[SimCmd] supplyIccPinForApp: pin failed!");
            CommandException ex = new CommandException(CommandException.Error.PASSWORD_INCORRECT);
            int[] iArr = new int[1];
            int i = this.mPin1attemptsRemaining - 1;
            this.mPin1attemptsRemaining = i;
            iArr[0] = i < 0 ? 0 : this.mPin1attemptsRemaining;
            resultFail(response, iArr, ex);
            return;
        }
        resultSuccess(response, null);
    }

    public void supplyIccPukForApp(String puk, String newPin, String aid, Message response) {
        unimplemented(response);
    }

    public void supplyIccPin2ForApp(String pin2, String aid, Message response) {
        unimplemented(response);
    }

    public void supplyIccPuk2ForApp(String puk2, String newPin2, String aid, Message response) {
        unimplemented(response);
    }

    public void changeIccPinForApp(String oldPin, String newPin, String aidPtr, Message response) {
        SimulatedCommandsVerifier.getInstance().changeIccPinForApp(oldPin, newPin, aidPtr, response);
        changeIccPin(oldPin, newPin, response);
    }

    public void changeIccPin2ForApp(String oldPin2, String newPin2, String aidPtr, Message response) {
        unimplemented(response);
    }

    public void requestIccSimAuthentication(int authContext, String data, String aid, Message response) {
        unimplemented(response);
    }

    public void getVoiceRadioTechnology(Message response) {
        SimulatedCommandsVerifier.getInstance().getVoiceRadioTechnology(response);
        resultSuccess(response, new int[]{this.mVoiceRadioTech});
    }

    public void setCellInfoList(List<CellInfo> list) {
        this.mCellInfoList = list;
    }

    public void getCellInfoList(Message response, WorkSource WorkSource) {
        if (this.mCellInfoList == null) {
            Parcel p = Parcel.obtain();
            p.writeInt(1);
            p.writeInt(1);
            p.writeInt(2);
            p.writeLong(1453510289108L);
            p.writeInt(310);
            p.writeInt(android.hardware.radio.V1_0.LastCallFailCause.ACCESS_CLASS_BLOCKED);
            p.writeInt(123);
            p.writeInt(456);
            p.writeInt(99);
            p.writeInt(3);
            p.setDataPosition(0);
            new ArrayList<>().add((CellInfoGsm) CellInfoGsm.CREATOR.createFromParcel(p));
        }
        resultSuccess(response, this.mCellInfoList);
    }

    public int getRilVersion() {
        return 11;
    }

    public void setCellInfoListRate(int rateInMillis, Message response, WorkSource workSource) {
        unimplemented(response);
    }

    public void setInitialAttachApn(DataProfile dataProfile, boolean isRoaming, Message result) {
    }

    public void setDataProfile(DataProfile[] dps, boolean isRoaming, Message result) {
    }

    public void setImsRegistrationState(int[] regState) {
        this.mImsRegState = regState;
    }

    public void getImsRegistrationState(Message response) {
        if (this.mImsRegState == null) {
            this.mImsRegState = new int[]{1, 0};
        }
        resultSuccess(response, this.mImsRegState);
    }

    public void sendImsCdmaSms(byte[] pdu, int retry, int messageRef, Message response) {
        SimulatedCommandsVerifier.getInstance().sendImsCdmaSms(pdu, retry, messageRef, response);
        resultSuccess(response, new SmsResponse(0, null, 0));
    }

    public void sendImsGsmSms(String smscPDU, String pdu, int retry, int messageRef, Message response) {
        SimulatedCommandsVerifier.getInstance().sendImsGsmSms(smscPDU, pdu, retry, messageRef, response);
        resultSuccess(response, new SmsResponse(0, null, 0));
    }

    public void iccOpenLogicalChannel(String AID, int p2, Message response) {
        SimulatedCommandsVerifier.getInstance().iccOpenLogicalChannel(AID, p2, response);
        resultSuccess(response, new int[]{this.mChannelId});
    }

    public void iccCloseLogicalChannel(int channel, Message response) {
        unimplemented(response);
    }

    public void iccTransmitApduLogicalChannel(int channel, int cla, int instruction, int p1, int p2, int p3, String data, Message response) {
        Message message = response;
        SimulatedCommandsVerifier.getInstance().iccTransmitApduLogicalChannel(channel, cla, instruction, p1, p2, p3, data, message);
        if (this.mIccIoResultForApduLogicalChannel != null) {
            resultSuccess(message, this.mIccIoResultForApduLogicalChannel);
        } else {
            resultFail(message, null, new RuntimeException("IccIoResult not set"));
        }
    }

    public void iccTransmitApduBasicChannel(int cla, int instruction, int p1, int p2, int p3, String data, Message response) {
        unimplemented(response);
    }

    public void nvReadItem(int itemID, Message response) {
        unimplemented(response);
    }

    public void nvWriteItem(int itemID, String itemValue, Message response) {
        unimplemented(response);
    }

    public void nvWriteCdmaPrl(byte[] preferredRoamingList, Message response) {
        unimplemented(response);
    }

    public void nvResetConfig(int resetType, Message response) {
        unimplemented(response);
    }

    public void getHardwareConfig(Message result) {
        unimplemented(result);
    }

    public void requestShutdown(Message result) {
        setRadioState(CommandsInterface.RadioState.RADIO_UNAVAILABLE);
    }

    public void startLceService(int report_interval_ms, boolean pullMode, Message result) {
        SimulatedCommandsVerifier.getInstance().startLceService(report_interval_ms, pullMode, result);
    }

    public void stopLceService(Message result) {
        unimplemented(result);
    }

    public void pullLceData(Message result) {
        unimplemented(result);
    }

    public void registerForLceInfo(Handler h, int what, Object obj) {
        SimulatedCommandsVerifier.getInstance().registerForLceInfo(h, what, obj);
    }

    public void unregisterForLceInfo(Handler h) {
        SimulatedCommandsVerifier.getInstance().unregisterForLceInfo(h);
    }

    public void getModemActivityInfo(Message result) {
        unimplemented(result);
    }

    public void setAllowedCarriers(List<CarrierIdentifier> list, Message result) {
        unimplemented(result);
    }

    public void getAllowedCarriers(Message result) {
        unimplemented(result);
    }

    public void getRadioCapability(Message result) {
        SimulatedCommandsVerifier.getInstance().getRadioCapability(result);
        RadioCapability radioCapability = new RadioCapability(0, 0, 0, 65535, null, 0);
        resultSuccess(result, radioCapability);
    }

    public void notifySmsStatus(Object result) {
        if (this.mSmsStatusRegistrant != null) {
            this.mSmsStatusRegistrant.notifyRegistrant(new AsyncResult(null, result, null));
        }
    }

    public void notifyGsmBroadcastSms(Object result) {
        if (this.mGsmBroadcastSmsRegistrant != null) {
            this.mGsmBroadcastSmsRegistrant.notifyRegistrant(new AsyncResult(null, result, null));
        }
    }

    public void notifyIccSmsFull() {
        if (this.mIccSmsFullRegistrant != null) {
            this.mIccSmsFullRegistrant.notifyRegistrant();
        }
    }

    public void notifyEmergencyCallbackMode() {
        if (this.mEmergencyCallbackModeRegistrant != null) {
            this.mEmergencyCallbackModeRegistrant.notifyRegistrant();
        }
    }

    public void setEmergencyCallbackMode(Handler h, int what, Object obj) {
        SimulatedCommandsVerifier.getInstance().setEmergencyCallbackMode(h, what, obj);
        super.setEmergencyCallbackMode(h, what, obj);
    }

    public void notifyExitEmergencyCallbackMode() {
        if (this.mExitEmergencyCallbackModeRegistrants != null) {
            this.mExitEmergencyCallbackModeRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        }
    }

    public void notifyImsNetworkStateChanged() {
        if (this.mImsNetworkStateChangedRegistrants != null) {
            this.mImsNetworkStateChangedRegistrants.notifyRegistrants();
        }
    }

    public void notifyModemReset() {
        if (this.mModemResetRegistrants != null) {
            this.mModemResetRegistrants.notifyRegistrants(new AsyncResult(null, "Test", null));
        }
    }

    public void registerForExitEmergencyCallbackMode(Handler h, int what, Object obj) {
        SimulatedCommandsVerifier.getInstance().registerForExitEmergencyCallbackMode(h, what, obj);
        super.registerForExitEmergencyCallbackMode(h, what, obj);
    }

    public void notifyRadioOn() {
        this.mOnRegistrants.notifyRegistrants();
    }

    @VisibleForTesting
    public void notifyNetworkStateChanged() {
        this.mNetworkStateRegistrants.notifyRegistrants();
    }

    @VisibleForTesting
    public void notifyOtaProvisionStatusChanged() {
        if (this.mOtaProvisionRegistrants != null) {
            this.mOtaProvisionRegistrants.notifyRegistrants(new AsyncResult(null, new int[]{8}, null));
        }
    }

    public void notifySignalStrength() {
        if (this.mSignalStrength == null) {
            SignalStrength signalStrength = new SignalStrength(20, 0, -1, -1, -1, -1, -1, 99, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, KeepaliveStatus.INVALID_HANDLE, 0, 0, -1);
            this.mSignalStrength = signalStrength;
        }
        if (this.mSignalStrengthRegistrant != null) {
            this.mSignalStrengthRegistrant.notifyRegistrant(new AsyncResult(null, this.mSignalStrength, null));
        }
    }

    public void setIccCardStatus(IccCardStatus iccCardStatus) {
        this.mIccCardStatus = iccCardStatus;
    }

    public void setIccIoResultForApduLogicalChannel(IccIoResult iccIoResult) {
        this.mIccIoResultForApduLogicalChannel = iccIoResult;
    }

    public void setOpenChannelId(int channelId) {
        this.mChannelId = channelId;
    }

    public void setPin1RemainingAttempt(int pin1attemptsRemaining) {
        this.mPin1attemptsRemaining = pin1attemptsRemaining;
    }

    public void setDataAllowed(boolean allowed, Message result) {
        log("setDataAllowed = " + allowed);
        this.mAllowed.set(allowed);
        resultSuccess(result, null);
    }

    @VisibleForTesting
    public boolean isDataAllowed() {
        return this.mAllowed.get();
    }

    public void registerForPcoData(Handler h, int what, Object obj) {
    }

    public void unregisterForPcoData(Handler h) {
    }

    public void registerForModemReset(Handler h, int what, Object obj) {
        SimulatedCommandsVerifier.getInstance().registerForModemReset(h, what, obj);
        super.registerForModemReset(h, what, obj);
    }

    public void sendDeviceState(int stateType, boolean state, Message result) {
        SimulatedCommandsVerifier.getInstance().sendDeviceState(stateType, state, result);
        resultSuccess(result, null);
    }

    public void setUnsolResponseFilter(int filter, Message result) {
        SimulatedCommandsVerifier.getInstance().setUnsolResponseFilter(filter, result);
        resultSuccess(result, null);
    }

    public void setSignalStrengthReportingCriteria(int hysteresisMs, int hysteresisDb, int[] thresholdsDbm, int ran, Message result) {
    }

    public void setLinkCapacityReportingCriteria(int hysteresisMs, int hysteresisDlKbps, int hysteresisUlKbps, int[] thresholdsDlKbps, int[] thresholdsUlKbps, int ran, Message result) {
    }

    public void setSimCardPower(int state, Message result) {
    }

    @VisibleForTesting
    public void triggerRestrictedStateChanged(int restrictedState) {
        if (this.mRestrictedStateRegistrant != null) {
            this.mRestrictedStateRegistrant.notifyRegistrant(new AsyncResult(null, Integer.valueOf(restrictedState), null));
        }
    }

    public void setOnRestrictedStateChanged(Handler h, int what, Object obj) {
        super.setOnRestrictedStateChanged(h, what, obj);
        SimulatedCommandsVerifier.getInstance().setOnRestrictedStateChanged(h, what, obj);
    }

    public void setRadioPowerFailResponse(boolean fail) {
        this.mIsRadioPowerFailResponse = fail;
    }

    public void registerForIccRefresh(Handler h, int what, Object obj) {
        super.registerForIccRefresh(h, what, obj);
        SimulatedCommandsVerifier.getInstance().registerForIccRefresh(h, what, obj);
    }

    public void unregisterForIccRefresh(Handler h) {
        super.unregisterForIccRefresh(h);
        SimulatedCommandsVerifier.getInstance().unregisterForIccRefresh(h);
    }

    public void registerForNattKeepaliveStatus(Handler h, int what, Object obj) {
        SimulatedCommandsVerifier.getInstance().registerForNattKeepaliveStatus(h, what, obj);
    }

    public void unregisterForNattKeepaliveStatus(Handler h) {
        SimulatedCommandsVerifier.getInstance().unregisterForNattKeepaliveStatus(h);
    }

    public void startNattKeepalive(int contextId, KeepalivePacketData packetData, int intervalMillis, Message result) {
        SimulatedCommandsVerifier.getInstance().startNattKeepalive(contextId, packetData, intervalMillis, result);
    }

    public void stopNattKeepalive(int sessionHandle, Message result) {
        SimulatedCommandsVerifier.getInstance().stopNattKeepalive(sessionHandle, result);
    }
}
