package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.vsim.HwVSimConstants;

public class HwPhoneProxyReference {
    private static final String CHINA_OPERATOR_MCC = "460";
    protected static final String LOG_TAG = "HwPhoneProxy";
    private BroadcastHelper broadcastHelper;
    /* access modifiers changed from: private */
    public boolean firstQueryDone = false;
    private Context mContext;
    /* access modifiers changed from: private */
    public GsmCdmaPhone mPhoneProxy;

    private class BroadcastHelper {
        private static final int MIN_MATCH = 7;
        /* access modifiers changed from: private */
        public GlobalParamsAdaptor globalParamsAdaptor;
        private BroadcastReceiver mPhoneProxyReceiver;

        public BroadcastHelper() {
            this.globalParamsAdaptor = new GlobalParamsAdaptor(HwPhoneProxyReference.this.mPhoneProxy.getPhoneId());
            this.mPhoneProxyReceiver = new BroadcastReceiver(HwPhoneProxyReference.this) {
                public void onReceive(Context context, Intent intent) {
                    if (TelephonyManager.getDefault().isMultiSimEnabled()) {
                        if (intent.getIntExtra("phone", 0) != HwPhoneProxyReference.this.mPhoneProxy.getPhoneId()) {
                            HwPhoneProxyReference.this.logd("ignore HwPhoneProxy BroadcastHelper onReceive action = " + intent.getAction() + "with phoneId = " + phoneId);
                            return;
                        }
                    }
                    HwPhoneProxyReference.this.logd("HwPhoneProxy BroadcastHelper onReceive action = " + intent.getAction());
                    if ("com.huawei.intent.action.ACTION_SIM_RECORDS_READY".equals(intent.getAction())) {
                        String mccmnc = intent.getStringExtra("mccMnc");
                        int simId = HwPhoneProxyReference.this.mPhoneProxy.getPhoneId();
                        if (TelephonyManager.getDefault().isMultiSimEnabled()) {
                            HwPhoneProxyReference.this.logd("RoamingBroker.getRBOperatorNumeric begin:" + mccmnc);
                            if (HwTelephonyFactory.getHwPhoneManager().isRoamingBrokerActivated(Integer.valueOf(simId))) {
                                mccmnc = HwTelephonyFactory.getHwPhoneManager().getRoamingBrokerOperatorNumeric(Integer.valueOf(simId));
                            }
                            HwPhoneProxyReference.this.logd("RoamingBroker.getRBOperatorNumeric end:" + mccmnc);
                        } else {
                            HwPhoneProxyReference.this.logd("RoamingBroker.getRBOperatorNumeric begin:" + mccmnc);
                            if (HwTelephonyFactory.getHwPhoneManager().isRoamingBrokerActivated()) {
                                mccmnc = HwTelephonyFactory.getHwPhoneManager().getRoamingBrokerOperatorNumeric();
                            }
                            HwPhoneProxyReference.this.logd("RoamingBroker.getRBOperatorNumeric end:" + mccmnc);
                        }
                        String imsi = intent.getStringExtra(HwVSimConstants.ENABLE_PARA_IMSI);
                        BroadcastHelper.this.globalParamsAdaptor.checkPrePostPay(mccmnc, imsi, HwPhoneProxyReference.this.getContext());
                        if (imsi == null || imsi.length() < 7 || imsi.length() > 15) {
                            Rlog.e(HwPhoneProxyReference.LOG_TAG, "invalid IMSI");
                        } else if (imsi.substring(0, 7).equals("2400768")) {
                            BroadcastHelper.this.globalParamsAdaptor.checkGlobalEccNum("24205", HwPhoneProxyReference.this.getContext());
                        } else {
                            BroadcastHelper.this.globalParamsAdaptor.checkGlobalEccNum(mccmnc, HwPhoneProxyReference.this.getContext());
                        }
                        BroadcastHelper.this.globalParamsAdaptor.checkGlobalAutoMatchParam(mccmnc, HwPhoneProxyReference.this.getContext());
                        BroadcastHelper.this.globalParamsAdaptor.checkAgpsServers(mccmnc);
                        BroadcastHelper.this.globalParamsAdaptor.checkCustLongVMNum(mccmnc, HwPhoneProxyReference.this.getContext());
                    } else if ("android.intent.action.SERVICE_STATE".equals(intent.getAction())) {
                        int phoneCount = TelephonyManager.getDefault().getPhoneCount();
                        String networkOperator = null;
                        String networkCountryIso = null;
                        for (int i = 0; i < phoneCount; i++) {
                            networkCountryIso = TelephonyManager.getDefault().getNetworkCountryIso(i);
                            networkOperator = TelephonyManager.getDefault().getNetworkOperator(i);
                            if (!TextUtils.isEmpty(networkCountryIso)) {
                                break;
                            }
                        }
                        boolean rplmnChanged = false;
                        String lastNetworkOperator = SystemProperties.get("gsm.hw.operator.numeric.old", "");
                        if (!TextUtils.isEmpty(lastNetworkOperator) && !TextUtils.isEmpty(networkOperator) && !networkOperator.equals(lastNetworkOperator)) {
                            rplmnChanged = true;
                            HwPhoneProxyReference.this.logd("ACTION_SERVICE_STATE_CHANGED, network operator changed from " + lastNetworkOperator + " to " + networkOperator);
                        }
                        if (!TextUtils.isEmpty(networkOperator)) {
                            SystemProperties.set("gsm.hw.operator.numeric.old", networkOperator);
                        }
                        SystemProperties.set("gsm.hw.operator.iso-country", networkCountryIso);
                        SystemProperties.set("gsm.hw.operator.numeric", networkOperator);
                        boolean isNetworkRoaming = false;
                        for (int i2 = 0; i2 < phoneCount; i2++) {
                            isNetworkRoaming = TelephonyManager.getDefault().isNetworkRoaming(i2);
                            if (isNetworkRoaming) {
                                break;
                            }
                        }
                        SystemProperties.set("gsm.hw.operator.isroaming", isNetworkRoaming ? "true" : "false");
                        if (SystemProperties.getBoolean("gsm.hw.operator.isroaming", false) && !TextUtils.isEmpty(networkOperator) && (!HwPhoneProxyReference.this.firstQueryDone || rplmnChanged)) {
                            boolean unused = HwPhoneProxyReference.this.firstQueryDone = true;
                            BroadcastHelper.this.globalParamsAdaptor.queryRoamingNumberMatchRuleByNetwork(networkOperator, HwPhoneProxyReference.this.getContext());
                            BroadcastHelper.this.globalParamsAdaptor.checkValidityOfRoamingNumberMatchRule();
                        }
                    }
                }
            };
        }

        public void init() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.huawei.intent.action.ACTION_SIM_RECORDS_READY");
            intentFilter.addAction("android.intent.action.SERVICE_STATE");
            HwPhoneProxyReference.this.getContext().registerReceiver(this.mPhoneProxyReceiver, intentFilter);
            HwPhoneProxyReference.this.logd("HwPhoneProxy BroadcastHelper register complelte");
        }
    }

    public HwPhoneProxyReference(GsmCdmaPhone phoneProxy, Context context) {
        this.mContext = context;
        this.mPhoneProxy = phoneProxy;
        this.broadcastHelper = new BroadcastHelper();
        this.broadcastHelper.init();
    }

    /* access modifiers changed from: private */
    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: private */
    public void logd(String string) {
        Rlog.d(LOG_TAG, string);
    }
}
