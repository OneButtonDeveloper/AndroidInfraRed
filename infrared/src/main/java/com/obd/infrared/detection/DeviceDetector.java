package com.obd.infrared.detection;

import android.os.Build;

public class DeviceDetector {

    public static boolean isSamsung() {
        return isDevice("SAMSUNG");
    }

    public static boolean isLg() {
        return isDevice("LG") || isDevice("LGE");
    }

    public static boolean isHtc() {
        return isDevice("HTC");
    }

    private static boolean isDevice(String manufactureName) {
        return Build.MANUFACTURER.equalsIgnoreCase(manufactureName);
    }
}
