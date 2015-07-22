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

    @SuppressWarnings("unused")
    public static boolean isSony() {
        try {
            Class libraryToInvestigate = Class.forName("com.sony.remotecontrol.ir.v1"); // Dynamically initiate the library
            return libraryToInvestigate != null;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
