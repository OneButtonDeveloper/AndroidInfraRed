package com.obd.infrared.detection;

import android.os.Build;

public class DeviceDetector {

    public static boolean isSamsung() {
        return isDevice("SAMSUNG");
    }

    public static boolean isLg() {
        return isDevice("LG") || isDevice("LGE");
    }

    private static boolean isDevice(String manufactureName) {
        return Build.MANUFACTURER.equalsIgnoreCase(manufactureName);
    }
}
