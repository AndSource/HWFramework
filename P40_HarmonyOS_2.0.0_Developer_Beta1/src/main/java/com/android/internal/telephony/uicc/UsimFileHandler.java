package com.android.internal.telephony.uicc;

import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;

public final class UsimFileHandler extends IccFileHandler implements IccConstants {
    static final String LOG_TAG = "UsimFH";

    public UsimFileHandler(UiccCardApplication app, String aid, CommandsInterface ci) {
        super(app, aid, ci);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.internal.telephony.uicc.IccFileHandler
    public String getEFPath(int efid) {
        if (efid == 28475 || efid == 28476 || efid == 28491 || efid == 28492) {
            return "3F007FFF";
        }
        switch (efid) {
            case IccConstants.EF_PBR /* 20272 */:
                return "3F007F105F3A";
            case IccConstants.EF_LI /* 28421 */:
            case IccConstants.EF_VOICE_MAIL_INDICATOR_CPHS /* 28433 */:
            case IccConstants.EF_HPPLMN /* 28465 */:
            case IccConstants.EF_SST /* 28472 */:
            case IccConstants.EF_SPN /* 28486 */:
            case IccConstants.EF_SDN /* 28489 */:
            case IccConstants.EF_EXT5 /* 28494 */:
            case IccConstants.EF_FPLMN /* 28539 */:
            case IccConstants.EF_AD /* 28589 */:
            case IccConstants.EF_SPDI /* 28621 */:
            case IccConstants.EF_EHPLMN /* 28633 */:
            case IccConstants.EF_LRPLMNSI /* 28636 */:
                return "3F007FFF";
            default:
                switch (efid) {
                    case IccConstants.EF_CFF_CPHS /* 28435 */:
                    case IccConstants.EF_SPN_CPHS /* 28436 */:
                    case IccConstants.EF_CSP_CPHS /* 28437 */:
                    case IccConstants.EF_INFO_CPHS /* 28438 */:
                    case IccConstants.EF_MAILBOX_CPHS /* 28439 */:
                    case IccConstants.EF_SPN_SHORT_CPHS /* 28440 */:
                        return "3F007FFF";
                    default:
                        switch (efid) {
                            case IccConstants.EF_GID1 /* 28478 */:
                            case IccConstants.EF_GID2 /* 28479 */:
                            case IccConstants.EF_MSISDN /* 28480 */:
                                return "3F007FFF";
                            default:
                                switch (efid) {
                                    case IccConstants.EF_PLMN_W_ACT /* 28512 */:
                                    case IccConstants.EF_OPLMN_W_ACT /* 28513 */:
                                    case IccConstants.EF_HPLMN_W_ACT /* 28514 */:
                                        return "3F007FFF";
                                    default:
                                        switch (efid) {
                                            case IccConstants.EF_PNN /* 28613 */:
                                            case IccConstants.EF_OPL /* 28614 */:
                                            case IccConstants.EF_MBDN /* 28615 */:
                                            case IccConstants.EF_EXT6 /* 28616 */:
                                            case IccConstants.EF_MBI /* 28617 */:
                                            case IccConstants.EF_MWIS /* 28618 */:
                                            case IccConstants.EF_CFIS /* 28619 */:
                                                return "3F007FFF";
                                            default:
                                                String path = getCommonIccEFPath(efid);
                                                if (path == null) {
                                                    return "3F007F105F3A";
                                                }
                                                return path;
                                        }
                                }
                        }
                }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.internal.telephony.uicc.IccFileHandler
    public void logd(String msg) {
        Rlog.i(LOG_TAG, msg);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.internal.telephony.uicc.IccFileHandler
    public void loge(String msg) {
        Rlog.e(LOG_TAG, msg);
    }
}
