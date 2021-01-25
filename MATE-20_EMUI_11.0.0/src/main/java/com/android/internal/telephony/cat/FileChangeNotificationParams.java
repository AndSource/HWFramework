package com.android.internal.telephony.cat;

/* compiled from: CommandParams */
class FileChangeNotificationParams extends CommandParams {
    int[] fileList;

    FileChangeNotificationParams(CommandDetails cmdDet, int[] fileList2) {
        super(cmdDet);
        this.fileList = fileList2;
    }
}
