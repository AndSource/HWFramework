package com.android.internal.telephony.gsm;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.ServiceState;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.IServiceStateTrackerInner;
import com.android.internal.telephony.OnsDisplayParams;
import com.android.internal.telephony.ServiceStateTracker;

public class HwCustGsmServiceStateManager {
    protected Context mContext;
    protected ContentResolver mCr;
    protected GsmCdmaPhone mGsmPhone;
    protected ServiceStateTracker mGsst;

    public HwCustGsmServiceStateManager(IServiceStateTrackerInner sst, GsmCdmaPhone gsmPhone) {
        if (sst instanceof ServiceStateTracker) {
            this.mGsst = (ServiceStateTracker) sst;
        }
        this.mGsmPhone = gsmPhone;
        this.mContext = gsmPhone.getContext();
        this.mCr = this.mContext.getContentResolver();
    }

    public boolean setRoamingStateForOperatorCustomization(ServiceState currentState, boolean ParaRoamingState) {
        return ParaRoamingState;
    }

    public OnsDisplayParams getGsmOnsDisplayParamsForGlobalOperator(boolean showSpn, boolean showPlmn, int rule, String plmn, String spn) {
        return null;
    }

    public OnsDisplayParams getVirtualNetOnsDisplayParams() {
        return null;
    }

    public boolean skipPlmnUpdateFromCust() {
        return false;
    }

    public OnsDisplayParams setOnsDisplayCustomization(OnsDisplayParams odp, ServiceState currentState) {
        return odp;
    }

    public boolean notUseVirtualName(String imsi) {
        return false;
    }

    public void storeModemRoamingStatus(boolean roaming) {
    }

    public OnsDisplayParams getGsmOnsDisplayParamsForVideotron(boolean showSpn, boolean showPlmn, int rule, String plmn, String spn) {
        return null;
    }

    public boolean checkIsInternationalRoaming(boolean roaming, ServiceState currentState) {
        return roaming;
    }

    public IntentFilter getCustIntentFilter(IntentFilter filter) {
        return filter;
    }

    public int handleBroadcastReceived(Context context, Intent intent, int rac) {
        return rac;
    }

    public boolean iscustRoamingRuleAffect(boolean roaming) {
        return false;
    }
}
