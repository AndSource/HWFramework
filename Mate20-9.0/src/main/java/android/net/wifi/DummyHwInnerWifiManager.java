package android.net.wifi;

import android.content.Context;
import android.net.wifi.HwQoE.IHwQoECallback;
import android.os.IBinder;
import android.os.Message;
import java.util.HashMap;
import java.util.List;

public class DummyHwInnerWifiManager implements HwInnerWifiManager {
    private static HwInnerWifiManager mInstance = new DummyHwInnerWifiManager();

    public HwInnerWifiManager getDefault() {
        return mInstance;
    }

    public String getAppendSsidWithRandomUuid(WifiConfiguration config, Context context) {
        return "";
    }

    public int calculateSignalLevelHW(int rssi) {
        return WifiManager.calculateSignalLevel(rssi, 5);
    }

    public int calculateSignalLevelHW(int freq, int rssi) {
        return WifiManager.calculateSignalLevel(rssi, 5);
    }

    public String getWpaSuppConfig() {
        return "";
    }

    public boolean setWifiEnterpriseConfigEapMethod(int eapMethod, HashMap<String, String> hashMap) {
        return false;
    }

    public boolean getHwMeteredHint(Context context) {
        return false;
    }

    public PPPOEInfo getPPPOEInfo() {
        return null;
    }

    public void startPPPOE(PPPOEConfig config) {
    }

    public void stopPPPOE() {
    }

    public List<String> getApLinkedStaList() {
        return null;
    }

    public void setSoftapMacFilter(String macFilter) {
    }

    public void setSoftapDisassociateSta(String mac) {
    }

    public void userHandoverWifi() {
    }

    public int[] getChannelListFor5G() {
        return null;
    }

    public void setWifiApEvaluateEnabled(boolean enabled) {
    }

    public byte[] fetchWifiSignalInfoForVoWiFi() {
        return null;
    }

    public boolean setVoWifiDetectMode(WifiDetectConfInfo info) {
        return false;
    }

    public WifiDetectConfInfo getVoWifiDetectMode() {
        return null;
    }

    public boolean setVoWifiDetectPeriod(int period) {
        return false;
    }

    public int getVoWifiDetectPeriod() {
        return -1;
    }

    public boolean isSupportVoWifiDetect() {
        return false;
    }

    public void enableHiLinkHandshake(boolean uiEnable, String bssid) {
    }

    public String getConnectionRawPsk() {
        return null;
    }

    public boolean requestWifiEnable(boolean flag, String reason) {
        return false;
    }

    public boolean setWifiTxPower(int power) {
        return false;
    }

    public boolean startHwQoEMonitor(int monitorType, int period, IHwQoECallback callback) {
        return false;
    }

    public boolean stopHwQoEMonitor(int monitorType) {
        return false;
    }

    public boolean evaluateNetworkQuality(IHwQoECallback callback) {
        return false;
    }

    public boolean updateVOWIFIStatus(int state) {
        return false;
    }

    public boolean updatelimitSpeedStatus(int mode, int reserve1, int reserve2) {
        return false;
    }

    public List<String> getSupportList() {
        return null;
    }

    public void notifyUIEvent(int event) {
    }

    public boolean updateAppRunningStatus(int uid, int type, int status, int scene, int reserved) {
        return false;
    }

    public boolean updateAppExperienceStatus(int uid, int experience, long rtt, int reserved) {
        return false;
    }

    public void extendWifiScanPeriodForP2p(Context context, boolean bExtend, int iTimes) {
    }

    public void refreshPackageWhitelist(int type, List<String> list) {
    }

    public boolean isRSDBSupported() {
        return false;
    }

    public void hwSetWifiAnt(Context context, String iface, int mode, int operation) {
    }

    public boolean disableWifiFilter(IBinder token, Context context) {
        return false;
    }

    public boolean enableWifiFilter(IBinder token, Context context) {
        return false;
    }

    public boolean startPacketKeepalive(Message msg) {
        return false;
    }

    public boolean stopPacketKeepalive(Message msg) {
        return false;
    }

    public boolean updateWaveMapping(int location, int action) {
        return false;
    }
}
