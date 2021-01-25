package com.android.server.devicepolicy;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.devicepolicy.PolicyStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class HwActiveAdminImpl extends HwActiveAdmin {
    private static final String ATTR_VALUE = "value";
    private static final String DOMAIN = "domain";
    private static final String FIELD = "field";
    private static final boolean HWDBG = false;
    private static final String ID = "id";
    private static final String INCOMING = "incoming";
    private static final String IPSECCACERT = "ipsecCaCert";
    private static final String IPSECIDENTIFIER = "ipsecIdentifier";
    private static final String IPSECSECRET = "ipsecSecret";
    private static final String IPSECSERVERCERT = "ipsecServerCert";
    private static final String IPSECUSERCERT = "ipsecUserCert";
    private static final String KEY = "key";
    private static final String L2TPSECRET = "l2tpSecret";
    private static final String LABEL = "label";
    private static final String MPPE = "mppe";
    private static final String NAME = "name";
    private static final String OUTGOING = "outgoing";
    private static final String PASSWORD = "password";
    private static final String PROVIDER = "provider";
    private static final String PROVIDERS_PRODUCT = "providers_product";
    private static final String SERVER = "server";
    private static final String TAG = "HwActiveAdminImpl";
    private static final String TAG_ALLOWED_INSTALL_PACKAGES = "allowed-install-packages";
    private static final String TAG_ALLOWED_INSTALL_PACKAGES_ITEM = "allowed-install-packages-item";
    private static final String TAG_DEACTIVE_TIME = "delay-deactive-time";
    private static final String TAG_DEFAULTE_LAUNCHER = "set-default-launcher";
    private static final String TAG_DISABLED_DEACTIVE_MDM_PACKAGES = "disabled-deactive-mdm-packages";
    private static final String TAG_DISABLED_DEACTIVE_MDM_PACKAGES_ITEM = "disabled-deactive-mdm-packages-item";
    private static final String TAG_DISABLE_ADB = "disable-adb";
    private static final String TAG_DISABLE_BACK_KEY = "disable-backKey";
    private static final String TAG_DISABLE_BLUETOOTH = "disable-bluetooth";
    private static final String TAG_DISABLE_BOOTLOADER = "disable-bootloader";
    private static final String TAG_DISABLE_CHANGE_LAUNCHER = "disable-change-launcher";
    private static final String TAG_DISABLE_DATA_CONNECTIVITY = "disable-dataconnectivity";
    private static final String TAG_DISABLE_DECRYPT_SD_CARD = "disable-decrypt-sd-card";
    private static final String TAG_DISABLE_EXTERNAL_STORAGE = "disable-externalstorage";
    private static final String TAG_DISABLE_GPS = "disable-gps";
    private static final String TAG_DISABLE_HOME_KEY = "disable-homekey";
    private static final String TAG_DISABLE_INSTALLSOURCE = "disable-installsource";
    private static final String TAG_DISABLE_NFC = "disable-nfc";
    private static final String TAG_DISABLE_SAFEMODE = "disable-safemode";
    private static final String TAG_DISABLE_SMS = "disable-sms";
    private static final String TAG_DISABLE_STATUS_BAR_EXPAND_PANEL = "disable-expandpanel";
    private static final String TAG_DISABLE_TASK_KEY = "disable-taskkey";
    private static final String TAG_DISABLE_USBDATA = "disable-usbdata";
    private static final String TAG_DISABLE_USBOTG = "disable-usbotg";
    private static final String TAG_DISABLE_VOICE = "disable-voice";
    private static final String TAG_DISABLE_WIFI = "disable-wifi";
    private static final String TAG_DISABLE_WIFIAP = "disable-wifiap";
    private static final String TAG_DISALLOWED_RUNNING_APP_LIST = "disallowedRunning-app-list";
    private static final String TAG_DISALLOWED_RUNNING_APP_LIST_ITEM = "disallowedRunning-app-list-item";
    private static final String TAG_DISALLOWED_UNINSTALL_PACKAGES = "disallowed-uninstall-packages";
    private static final String TAG_DISALLOWED_UNINSTALL_PACKAGES_ITEM = "disallowed-uninstall-packages-item";
    private static final String TAG_FORCED_ACTIVE = "forced-active";
    private static final String TAG_INSTALL_SOURCE_WHITELIST = "install-source-whitelist";
    private static final String TAG_INSTALL_SOURCE_WHITELIST_ITEM = "install-source-whitelist_item";
    private static final String TAG_NETWORK_ACCESS_ADDR_WHITELIST = "network-access-whitelist";
    private static final String TAG_NETWORK_ACCESS_ADDR_WHITELIST_ITEM = "network-access-whitelist-item";
    private static final String TAG_PERSISTENT_APP_LIST = "persistent-app-list";
    private static final String TAG_PERSISTENT_APP_LIST_ITEM = "persistent-app-list-item";
    private static final String TAG_POLICY_SYS_APP = "update-sys-app-install-list";
    private static final String TAG_POLICY_UNDETACHABLE_SYS_APP = "update-sys-app-undetachable-install-list";
    private static final String TYPE = "type";
    private static final String URI = "uri";
    private static final String USERNAME = "username";
    private static final String VPN_CONFIG = "vpn-config";
    private static final String VPN_ITEM = "vpn";

    public void writePoliciesToXml(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        HwLog.d(TAG, "write policy to xml out");
        out.startTag(null, "hw_policy");
        writeStructPolicies(out);
        writeDisabledConnectivityPolicies(out);
        writeDisabledFunctionPolicies(out);
        writeDisabledKeyPolicies(out);
        writeSpecialActiveAdminPolicies(out);
        if (this.disableChangeLauncher) {
            out.startTag(null, TAG_DISABLE_CHANGE_LAUNCHER);
            out.attribute(null, "value", Boolean.toString(this.disableChangeLauncher));
            out.endTag(null, TAG_DISABLE_CHANGE_LAUNCHER);
            out.startTag(null, TAG_DEFAULTE_LAUNCHER);
            out.attribute(null, "value", this.defaultLauncher);
            out.endTag(null, TAG_DEFAULTE_LAUNCHER);
        }
        writeListPolicies(out);
        out.endTag(null, "hw_policy");
    }

    private void writeSpecialActiveAdminPolicies(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        if (this.isForcedActive) {
            out.startTag(null, TAG_FORCED_ACTIVE);
            out.attribute(null, "value", Boolean.toString(this.isForcedActive));
            out.endTag(null, TAG_FORCED_ACTIVE);
        }
        if (!TextUtils.isEmpty(this.deactiveTime)) {
            out.startTag(null, TAG_DEACTIVE_TIME);
            out.attribute(null, "value", this.deactiveTime);
            out.endTag(null, TAG_DEACTIVE_TIME);
        }
    }

    private void writeDisabledFunctionPolicies(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        if (this.disableDecryptSDCard) {
            out.startTag(null, TAG_DISABLE_DECRYPT_SD_CARD);
            out.attribute(null, "value", Boolean.toString(this.disableDecryptSDCard));
            out.endTag(null, TAG_DISABLE_DECRYPT_SD_CARD);
        }
        if (this.disableBootLoader) {
            out.startTag(null, TAG_DISABLE_BOOTLOADER);
            out.attribute(null, "value", Boolean.toString(this.disableBootLoader));
            out.endTag(null, TAG_DISABLE_BOOTLOADER);
        }
        if (this.disableUSBData) {
            out.startTag(null, TAG_DISABLE_USBDATA);
            out.attribute(null, "value", Boolean.toString(this.disableUSBData));
            out.endTag(null, TAG_DISABLE_USBDATA);
        }
        if (this.disableExternalStorage) {
            out.startTag(null, TAG_DISABLE_EXTERNAL_STORAGE);
            out.attribute(null, "value", Boolean.toString(this.disableExternalStorage));
            out.endTag(null, TAG_DISABLE_EXTERNAL_STORAGE);
        }
        if (this.disableStatusBarExpandPanel) {
            out.startTag(null, TAG_DISABLE_STATUS_BAR_EXPAND_PANEL);
            out.attribute(null, "value", Boolean.toString(this.disableStatusBarExpandPanel));
            out.endTag(null, TAG_DISABLE_STATUS_BAR_EXPAND_PANEL);
        }
        if (this.disableInstallSource) {
            out.startTag(null, TAG_DISABLE_INSTALLSOURCE);
            out.attribute(null, "value", Boolean.toString(this.disableInstallSource));
            out.endTag(null, TAG_DISABLE_INSTALLSOURCE);
        }
        if (this.disableSafeMode) {
            out.startTag(null, TAG_DISABLE_SAFEMODE);
            out.attribute(null, "value", Boolean.toString(this.disableSafeMode));
            out.endTag(null, TAG_DISABLE_SAFEMODE);
        }
        if (this.disableAdb) {
            out.startTag(null, TAG_DISABLE_ADB);
            out.attribute(null, "value", Boolean.toString(this.disableAdb));
            out.endTag(null, TAG_DISABLE_ADB);
        }
        if (this.disableUSBOtg) {
            out.startTag(null, TAG_DISABLE_USBOTG);
            out.attribute(null, "value", Boolean.toString(this.disableUSBOtg));
            out.endTag(null, TAG_DISABLE_USBOTG);
        }
    }

    private void writeDisabledKeyPolicies(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        if (this.disableHomeKey) {
            out.startTag(null, TAG_DISABLE_HOME_KEY);
            out.attribute(null, "value", Boolean.toString(this.disableHomeKey));
            out.endTag(null, TAG_DISABLE_HOME_KEY);
        }
        if (this.disableBackKey) {
            out.startTag(null, TAG_DISABLE_BACK_KEY);
            out.attribute(null, "value", Boolean.toString(this.disableBackKey));
            out.endTag(null, TAG_DISABLE_BACK_KEY);
        }
        if (this.disableTaskKey) {
            out.startTag(null, TAG_DISABLE_TASK_KEY);
            out.attribute(null, "value", Boolean.toString(this.disableTaskKey));
            out.endTag(null, TAG_DISABLE_TASK_KEY);
        }
    }

    private void writeDisabledConnectivityPolicies(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        if (this.disableWifi) {
            out.startTag(null, TAG_DISABLE_WIFI);
            out.attribute(null, "value", Boolean.toString(this.disableWifi));
            out.endTag(null, TAG_DISABLE_WIFI);
        }
        if (this.disableBluetooth) {
            out.startTag(null, TAG_DISABLE_BLUETOOTH);
            out.attribute(null, "value", Boolean.toString(this.disableBluetooth));
            out.endTag(null, TAG_DISABLE_BLUETOOTH);
        }
        if (this.disableWifiAp) {
            out.startTag(null, TAG_DISABLE_WIFIAP);
            out.attribute(null, "value", Boolean.toString(this.disableWifiAp));
            out.endTag(null, TAG_DISABLE_WIFIAP);
        }
        if (this.disableNFC) {
            out.startTag(null, TAG_DISABLE_NFC);
            out.attribute(null, "value", Boolean.toString(this.disableNFC));
            out.endTag(null, TAG_DISABLE_NFC);
        }
        if (this.disableDataConnectivity) {
            out.startTag(null, TAG_DISABLE_DATA_CONNECTIVITY);
            out.attribute(null, "value", Boolean.toString(this.disableDataConnectivity));
            out.endTag(null, TAG_DISABLE_DATA_CONNECTIVITY);
        }
        if (this.disableVoice) {
            out.startTag(null, TAG_DISABLE_VOICE);
            out.attribute(null, "value", Boolean.toString(this.disableVoice));
            out.endTag(null, TAG_DISABLE_VOICE);
        }
        if (this.disableSMS) {
            out.startTag(null, TAG_DISABLE_SMS);
            out.attribute(null, "value", Boolean.toString(this.disableSMS));
            out.endTag(null, TAG_DISABLE_SMS);
        }
        if (this.disableGPS) {
            out.startTag(null, TAG_DISABLE_GPS);
            out.attribute(null, "value", Boolean.toString(this.disableGPS));
            out.endTag(null, TAG_DISABLE_GPS);
        }
    }

    private void writeStructPolicies(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        for (PolicyStruct.PolicyItem item : this.adminPolicyItems.values()) {
            if (item != null && item.hasAnyNonNullAttribute()) {
                HwLog.d(TAG, "write PolicyItem to xml :" + item.getPolicyTag());
                String policyName = item.getPolicyTag();
                if (!TAG_POLICY_UNDETACHABLE_SYS_APP.equals(policyName) && !TAG_POLICY_SYS_APP.equals(policyName)) {
                    XmlUtil.writeOnePolicy(out, item);
                }
            }
        }
    }

    private void writeListPolicies(XmlSerializer out) throws IllegalArgumentException, IllegalStateException, IOException {
        writeListToXml(out, TAG_ALLOWED_INSTALL_PACKAGES, TAG_ALLOWED_INSTALL_PACKAGES_ITEM, this.installPackageWhitelist);
        writeListToXml(out, TAG_DISALLOWED_UNINSTALL_PACKAGES, TAG_DISALLOWED_UNINSTALL_PACKAGES_ITEM, this.disallowedUninstallPackageList);
        writeListToXml(out, TAG_INSTALL_SOURCE_WHITELIST, TAG_INSTALL_SOURCE_WHITELIST_ITEM, this.installSourceWhitelist);
        writeListToXml(out, TAG_PERSISTENT_APP_LIST, TAG_PERSISTENT_APP_LIST_ITEM, this.persistentAppList);
        writeListToXml(out, TAG_DISALLOWED_RUNNING_APP_LIST, TAG_DISALLOWED_RUNNING_APP_LIST_ITEM, this.disallowedRunningAppList);
        writeListToXml(out, TAG_NETWORK_ACCESS_ADDR_WHITELIST, TAG_NETWORK_ACCESS_ADDR_WHITELIST_ITEM, this.networkAccessWhitelist);
        writeListToXml(out, TAG_DISABLED_DEACTIVE_MDM_PACKAGES, TAG_DISABLED_DEACTIVE_MDM_PACKAGES_ITEM, this.disabledDeactiveMdmPackagesList);
        writeProviderListToXml(out, PROVIDERS_PRODUCT, this.mailProviderlist);
        writeVpnProviderListToXml(out, VPN_CONFIG, this.vpnProviderlist);
    }

    public void readPoliciesFromXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1) {
                return;
            }
            if (type == 3 && parser.getDepth() <= outerDepth) {
                return;
            }
            if (!(type == 3 || type == 4)) {
                String tag = parser.getName();
                if (!readStructPolicies(parser, tag)) {
                    if (TAG_DISABLE_WIFI.equals(tag)) {
                        this.disableWifi = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_BLUETOOTH.equals(tag)) {
                        this.disableBluetooth = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_WIFIAP.equals(tag)) {
                        this.disableWifiAp = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_BOOTLOADER.equals(tag)) {
                        this.disableBootLoader = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_USBDATA.equals(tag)) {
                        this.disableUSBData = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_EXTERNAL_STORAGE.equals(tag)) {
                        this.disableExternalStorage = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_NFC.equals(tag)) {
                        this.disableNFC = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_DATA_CONNECTIVITY.equals(tag)) {
                        this.disableDataConnectivity = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_VOICE.equals(tag)) {
                        this.disableVoice = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_SMS.equals(tag)) {
                        this.disableSMS = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_STATUS_BAR_EXPAND_PANEL.equals(tag)) {
                        this.disableStatusBarExpandPanel = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_INSTALLSOURCE.equals(tag)) {
                        this.disableInstallSource = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_SAFEMODE.equals(tag)) {
                        this.disableSafeMode = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_ADB.equals(tag)) {
                        this.disableAdb = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_USBOTG.equals(tag)) {
                        this.disableUSBOtg = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_GPS.equals(tag)) {
                        this.disableGPS = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_HOME_KEY.equals(tag)) {
                        this.disableHomeKey = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_BACK_KEY.equals(tag)) {
                        this.disableBackKey = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_TASK_KEY.equals(tag)) {
                        this.disableTaskKey = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DISABLE_CHANGE_LAUNCHER.equals(tag)) {
                        this.disableChangeLauncher = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_INSTALL_SOURCE_WHITELIST.equals(tag)) {
                        this.installSourceWhitelist = readListFromXml(parser, TAG_INSTALL_SOURCE_WHITELIST_ITEM);
                    } else if (TAG_ALLOWED_INSTALL_PACKAGES.equals(tag)) {
                        this.installPackageWhitelist = readListFromXml(parser, TAG_ALLOWED_INSTALL_PACKAGES_ITEM);
                    } else if (TAG_DISALLOWED_UNINSTALL_PACKAGES.equals(tag)) {
                        this.disallowedUninstallPackageList = readListFromXml(parser, TAG_DISALLOWED_UNINSTALL_PACKAGES_ITEM);
                    } else if (TAG_DISABLED_DEACTIVE_MDM_PACKAGES.equals(tag)) {
                        this.disabledDeactiveMdmPackagesList = readListFromXml(parser, TAG_DISABLED_DEACTIVE_MDM_PACKAGES_ITEM);
                    } else if (TAG_PERSISTENT_APP_LIST.equals(tag)) {
                        this.persistentAppList = readListFromXml(parser, TAG_PERSISTENT_APP_LIST_ITEM);
                    } else if (TAG_DISALLOWED_RUNNING_APP_LIST.equals(tag)) {
                        this.disallowedRunningAppList = readListFromXml(parser, TAG_DISALLOWED_RUNNING_APP_LIST_ITEM);
                    } else if (TAG_NETWORK_ACCESS_ADDR_WHITELIST.equals(tag)) {
                        this.networkAccessWhitelist = readListFromXml(parser, TAG_NETWORK_ACCESS_ADDR_WHITELIST_ITEM);
                    } else if (PROVIDERS_PRODUCT.equals(tag)) {
                        this.mailProviderlist = readPorvidersList(parser, tag);
                    } else if (TAG_DISABLE_DECRYPT_SD_CARD.equals(tag)) {
                        this.disableDecryptSDCard = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (VPN_CONFIG.equals(tag)) {
                        this.vpnProviderlist = readVpnPorvidersList(parser, tag);
                    } else if (TAG_DEFAULTE_LAUNCHER.equals(tag)) {
                        this.defaultLauncher = parser.getAttributeValue(null, "value");
                    } else if (TAG_FORCED_ACTIVE.equals(tag)) {
                        this.isForcedActive = Boolean.parseBoolean(parser.getAttributeValue(null, "value"));
                    } else if (TAG_DEACTIVE_TIME.equals(tag)) {
                        this.deactiveTime = parser.getAttributeValue(null, "value");
                    } else {
                        Slog.w(TAG, "Unknown admin tag: " + tag);
                    }
                }
            }
        }
    }

    private boolean readStructPolicies(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        PolicyStruct.PolicyItem item = HwDevicePolicyManagerService.GLOBAL_POLICY_ITEMS.get(tag);
        if (item == null) {
            return false;
        }
        HwLog.d(TAG, "read PolicyItem from xml");
        PolicyStruct.PolicyItem adminItem = new PolicyStruct.PolicyItem(tag);
        adminItem.copyFrom(item);
        XmlUtil.readItems(parser, adminItem);
        this.adminPolicyItems.put(tag, adminItem);
        return true;
    }

    private List<Bundle> readPorvidersList(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        List<Bundle> result = new ArrayList<>();
        int outerDepth = parser.getDepth();
        while (true) {
            int outerType = parser.next();
            if (outerType == 1 || (outerType == 3 && parser.getDepth() <= outerDepth)) {
                break;
            } else if (!(outerType == 3 || outerType == 4)) {
                String tagnew = parser.getName();
                if (PROVIDER.equals(tagnew)) {
                    Bundle para = new Bundle();
                    para.putString(ID, parser.getAttributeValue(null, ID));
                    para.putString(LABEL, parser.getAttributeValue(null, LABEL));
                    para.putString(DOMAIN, parser.getAttributeValue(null, DOMAIN));
                    readProvidersItems(parser, para);
                    result.add(para);
                } else {
                    Slog.w(TAG, "missing value under inner tag[" + tagnew + "]");
                }
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    private void readProvidersItems(XmlPullParser parser, Bundle para) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (true) {
            int outerType = parser.next();
            if (outerType == 1) {
                return;
            }
            if (outerType == 3 && parser.getDepth() <= outerDepth) {
                return;
            }
            if (!(outerType == 3 || outerType == 4)) {
                String tagnew = parser.getName();
                if (INCOMING.equals(tagnew) && para != null) {
                    para.putString("incominguri", parser.getAttributeValue(null, URI));
                    para.putString("incomingusername", parser.getAttributeValue(null, USERNAME));
                    para.putString("incomingfield", parser.getAttributeValue(null, FIELD));
                } else if (!OUTGOING.equals(tagnew) || para == null) {
                    Slog.w(TAG, "missing value under inner tag[" + tagnew + "]");
                } else {
                    para.putString("outgoinguri", parser.getAttributeValue(null, URI));
                    para.putString("outgoingusername", parser.getAttributeValue(null, USERNAME));
                }
            }
        }
    }

    private void writeProviderListToXml(XmlSerializer out, String outerTag, List<Bundle> providerList) throws IllegalArgumentException, IllegalStateException, IOException {
        if (providerList != null) {
            out.startTag(null, outerTag);
            for (Bundle para : providerList) {
                out.startTag(null, PROVIDER);
                out.attribute(null, ID, para.getString(ID));
                out.attribute(null, LABEL, para.getString(LABEL));
                out.attribute(null, DOMAIN, para.getString(DOMAIN));
                out.startTag(null, INCOMING);
                out.attribute(null, URI, para.getString("incominguri"));
                out.attribute(null, USERNAME, para.getString("incomingusername"));
                out.attribute(null, FIELD, para.getString("incomingfield"));
                out.endTag(null, INCOMING);
                out.startTag(null, OUTGOING);
                out.attribute(null, URI, para.getString("outgoinguri"));
                out.attribute(null, USERNAME, para.getString("outgoingusername"));
                out.endTag(null, OUTGOING);
                out.endTag(null, PROVIDER);
            }
            out.endTag(null, outerTag);
        }
    }

    private List<String> readListFromXml(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        if (tag == null || tag.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        int outerDepth = parser.getDepth();
        while (true) {
            int outerType = parser.next();
            if (outerType == 1 || (outerType == 3 && parser.getDepth() <= outerDepth)) {
                break;
            } else if (!(outerType == 3 || outerType == 4)) {
                String outerTag = parser.getName();
                if (tag.equals(outerTag)) {
                    String value = parser.getAttributeValue(null, "value");
                    if (value != null) {
                        result.add(value);
                    } else {
                        Slog.w(TAG, "missing value under inner tag[" + outerTag + "]");
                    }
                }
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    private void writeListToXml(XmlSerializer out, String outerTag, String innerTag, List<String> someList) throws IllegalArgumentException, IllegalStateException, IOException {
        if (!(someList == null || someList.isEmpty())) {
            out.startTag(null, outerTag);
            for (String value : someList) {
                out.startTag(null, innerTag);
                out.attribute(null, "value", value);
                out.endTag(null, innerTag);
            }
            out.endTag(null, outerTag);
        }
    }

    private void writeVpnProviderListToXml(XmlSerializer out, String outerTag, List<Bundle> providerList) throws IllegalArgumentException, IllegalStateException, IOException {
        if (providerList != null) {
            out.startTag(null, outerTag);
            for (Bundle para : providerList) {
                out.startTag(null, VPN_ITEM);
                out.attribute(null, KEY, para.getString(KEY));
                out.attribute(null, NAME, para.getString(NAME));
                out.attribute(null, TYPE, para.getString(TYPE));
                out.attribute(null, SERVER, para.getString(SERVER));
                out.attribute(null, USERNAME, para.getString(USERNAME));
                out.attribute(null, PASSWORD, para.getString(PASSWORD));
                out.attribute(null, MPPE, para.getString(MPPE));
                out.attribute(null, L2TPSECRET, para.getString(L2TPSECRET));
                out.attribute(null, IPSECIDENTIFIER, para.getString(IPSECIDENTIFIER));
                out.attribute(null, IPSECSECRET, para.getString(IPSECSECRET));
                out.attribute(null, IPSECUSERCERT, para.getString(IPSECUSERCERT));
                out.attribute(null, IPSECCACERT, para.getString(IPSECCACERT));
                out.attribute(null, IPSECSERVERCERT, para.getString(IPSECSERVERCERT));
                out.endTag(null, VPN_ITEM);
            }
            out.endTag(null, outerTag);
        }
    }

    private List<Bundle> readVpnPorvidersList(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        List<Bundle> result = new ArrayList<>();
        int outerDepth = parser.getDepth();
        while (true) {
            int outerType = parser.next();
            if (outerType == 1 || (outerType == 3 && parser.getDepth() <= outerDepth)) {
                break;
            } else if (!(outerType == 3 || outerType == 4)) {
                String tagnew = parser.getName();
                if (VPN_ITEM.equals(tagnew)) {
                    Bundle para = new Bundle();
                    para.putString(KEY, parser.getAttributeValue(null, KEY));
                    para.putString(NAME, parser.getAttributeValue(null, NAME));
                    para.putString(TYPE, parser.getAttributeValue(null, TYPE));
                    para.putString(SERVER, parser.getAttributeValue(null, SERVER));
                    para.putString(USERNAME, parser.getAttributeValue(null, USERNAME));
                    para.putString(PASSWORD, parser.getAttributeValue(null, PASSWORD));
                    para.putString(MPPE, parser.getAttributeValue(null, MPPE));
                    para.putString(L2TPSECRET, parser.getAttributeValue(null, L2TPSECRET));
                    para.putString(IPSECIDENTIFIER, parser.getAttributeValue(null, IPSECIDENTIFIER));
                    para.putString(IPSECSECRET, parser.getAttributeValue(null, IPSECSECRET));
                    para.putString(IPSECUSERCERT, parser.getAttributeValue(null, IPSECUSERCERT));
                    para.putString(IPSECCACERT, parser.getAttributeValue(null, IPSECCACERT));
                    para.putString(IPSECSERVERCERT, parser.getAttributeValue(null, IPSECSERVERCERT));
                    result.add(para);
                } else {
                    Slog.w(TAG, "missing value under inner tag[" + tagnew + "]");
                }
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }
}
