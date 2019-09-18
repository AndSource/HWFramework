package com.android.server.rms.iaware.dev;

import android.content.Context;
import android.rms.iaware.AwareConfig;
import android.rms.iaware.AwareLog;
import android.rms.iaware.LogIAware;
import android.util.ArrayMap;
import com.android.server.rms.iaware.memory.data.content.AttrSegments;
import huawei.com.android.server.fingerprint.FingerViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GpsSchedFeatureRT extends DevSchedFeatureBase {
    private static final String ACTIVITY_SCENE_NAME = "Activity_Start";
    private static final String ATTR_RULE_ACTIVITY_IN = "Activity_In";
    private static final String CONFIG_GPS_STRATEGY = "gps_strategy";
    private static final String FEATURE_TITLE = "DevSchedFeature";
    public static final String GPS_SPLIT_SYMBOL = ",";
    private static final String ITEM_GPS_MODE = "mode";
    private static final String ITEM_PACKAGE_NAME = "package_name";
    private static final String ITEM_RULE = "rule";
    private static final String ITEM_SCENE_NAME = "scenename";
    private static final String TAG = "GpsSchedFeatureRT";
    private final List<GpsActivityInfo> mActivityInfoList = new ArrayList();
    private String mCurrentActivityName;
    private String mCurrentPackageName;

    public GpsSchedFeatureRT(Context context, String name) {
        super(context);
        loadGpsActivityInfo(this.mActivityInfoList);
        AwareLog.d(TAG, "create " + name + "GpsSchedFeatureRT success");
    }

    public boolean handleResAppData(long timestamp, int event, AttrSegments attrSegments) {
        if (event != 15019 && event != 85019) {
            return false;
        }
        handleActivityEvent(event, attrSegments);
        return true;
    }

    private void handleActivityEvent(int event, AttrSegments attrSegments) {
        int i = event;
        if (attrSegments.isValid()) {
            ArrayMap<String, String> appInfo = attrSegments.getSegment("calledApp");
            if (appInfo == null) {
                AwareLog.i(TAG, "appInfo is NULL");
                return;
            }
            String packageName = appInfo.get("packageName");
            String activityName = appInfo.get("activityName");
            if (15019 == i) {
                this.mCurrentPackageName = packageName;
                this.mCurrentActivityName = activityName;
            }
            try {
                int uid = Integer.parseInt(appInfo.get("uid"));
                try {
                    int pid = Integer.parseInt(appInfo.get("pid"));
                    if (inInvalidActivityInfo(packageName, activityName, uid, pid)) {
                        AwareLog.i(TAG, "isInvalidActivityInfo, packageName: " + packageName + ", activityName: " + activityName + ", uid: " + uid + ", pid:" + pid);
                        return;
                    }
                    GpsActivityInfo devActivityInfo = queryActivityInfo(packageName, activityName);
                    if (devActivityInfo != null) {
                        int mode = devActivityInfo.getLocationMode();
                        AwareLog.d(TAG, "activity match success, packageName : " + packageName + ", activityName : " + activityName + ", mode : " + mode + ", event : " + i + ", uid is " + uid + ", pid is " + pid);
                        int i2 = mode;
                        reportActivityStateToNRT(i, packageName, activityName, uid, pid, mode);
                    }
                } catch (NumberFormatException e) {
                    AwareLog.e(TAG, "get uid fail, happend NumberFormatException");
                }
            } catch (NumberFormatException e2) {
                AwareLog.e(TAG, "get uid fail, happend NumberFormatException");
            }
        }
    }

    private boolean inInvalidActivityInfo(String packageName, String activityName, int uid, int pid) {
        return packageName == null || activityName == null || uid <= 1000 || pid < 0;
    }

    private GpsActivityInfo queryActivityInfo(String packageName, String activityName) {
        if (packageName == null || activityName == null) {
            return null;
        }
        int size = this.mActivityInfoList.size();
        for (int index = 0; index < size; index++) {
            GpsActivityInfo devActivityInfo = this.mActivityInfoList.get(index);
            if (devActivityInfo == null) {
                return null;
            }
            if (devActivityInfo.isMatch(packageName, activityName)) {
                return devActivityInfo;
            }
        }
        return null;
    }

    private void reportActivityStateToNRT(int event, String packageName, String activityName, int uid, int pid, int mode) {
        if (packageName == null || activityName == null) {
            AwareLog.e(TAG, "input param error, packageName is" + packageName + ", activityName is " + activityName);
            return;
        }
        if (15019 == event) {
            reportActivityState(packageName, activityName, uid, pid, mode, true);
        } else if (85019 == event) {
            if (!packageName.equals(this.mCurrentPackageName) || queryActivityInfo(this.mCurrentPackageName, this.mCurrentActivityName) == null) {
                reportActivityState(packageName, activityName, uid, pid, mode, false);
            } else {
                AwareLog.d(TAG, "switch control activity " + activityName + " to control activity " + this.mCurrentActivityName + ", do not remove activity control. package name : " + packageName);
            }
        }
    }

    private void reportActivityState(String packageName, String activityName, int uid, int pid, int mode, boolean in) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(FEATURE_TITLE);
        stringBuffer.append(CPUCustBaseConfig.CPUCONFIG_INVALID_STR);
        stringBuffer.append(packageName);
        stringBuffer.append(CPUCustBaseConfig.CPUCONFIG_INVALID_STR);
        stringBuffer.append(activityName);
        stringBuffer.append(CPUCustBaseConfig.CPUCONFIG_INVALID_STR);
        stringBuffer.append(uid);
        stringBuffer.append(CPUCustBaseConfig.CPUCONFIG_INVALID_STR);
        stringBuffer.append(pid);
        stringBuffer.append(CPUCustBaseConfig.CPUCONFIG_INVALID_STR);
        stringBuffer.append(mode);
        LogIAware.report(in ? 2104 : FingerViewController.TYPE_FINGER_VIEW, stringBuffer.toString());
    }

    public boolean handleUpdateCustConfig() {
        LogIAware.report(2106, "");
        loadGpsActivityInfo(this.mActivityInfoList);
        AwareLog.d(TAG, "update cust config success, mActivityInfoList is " + this.mActivityInfoList);
        return true;
    }

    private void loadGpsActivityInfo(List<GpsActivityInfo> activityInfoList) {
        if (activityInfoList != null) {
            activityInfoList.clear();
            List<AwareConfig.Item> itemList = DevXmlConfig.getItemList(CONFIG_GPS_STRATEGY, 1);
            if (itemList == null) {
                AwareLog.e(TAG, "parse gps strategy config error!");
                return;
            }
            for (AwareConfig.Item item : itemList) {
                if (item != null) {
                    Map<String, String> configPropertries = item.getProperties();
                    if (configPropertries != null && ACTIVITY_SCENE_NAME.equals(configPropertries.get("scenename"))) {
                        List<AwareConfig.SubItem> subItemList = item.getSubItemList();
                        if (subItemList == null || subItemList.size() == 0) {
                            AwareLog.e(TAG, " subItemList is null");
                        } else {
                            parseSubItemList(subItemList, activityInfoList);
                        }
                    }
                }
            }
        }
    }

    private void parseSubItemList(List<AwareConfig.SubItem> subItemList, List<GpsActivityInfo> activityInfoList) {
        if (subItemList != null && activityInfoList != null) {
            for (AwareConfig.SubItem subItem : subItemList) {
                if (subItem != null) {
                    Map<String, String> properties = subItem.getProperties();
                    if (properties != null && ATTR_RULE_ACTIVITY_IN.equals(properties.get(ITEM_RULE))) {
                        String packageName = properties.get("package_name");
                        if (packageName != null) {
                            try {
                                GpsActivityInfo devActivityInfo = new GpsActivityInfo(packageName, Integer.parseInt(properties.get("mode")));
                                if (devActivityInfo.loadActivitys(subItem.getValue())) {
                                    activityInfoList.add(devActivityInfo);
                                }
                            } catch (NumberFormatException e) {
                                AwareLog.e(TAG, "NumberFormatException, mode is not number! String mode is " + properties.get("mode"));
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean handlerNaviStatus(boolean isInNavi) {
        return true;
    }

    public String toString() {
        return "GpsSchedFeatureRT : " + ", GpsActivityInfo num : " + this.mActivityInfoList.size() + ", GpsActivityInfo : " + this.mActivityInfoList.toString();
    }
}
