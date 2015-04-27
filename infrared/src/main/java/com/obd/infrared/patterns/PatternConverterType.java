package com.obd.infrared.patterns;

import android.os.Build;

public enum PatternConverterType {

    None,
    ToObsoleteSamsung,
    ToLollipopSamsung;

    public static PatternConverterType getConverterType() {

        if (Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG")) {
            return getConverterTypeForSamsung();
        }

        if (Build.MANUFACTURER.equalsIgnoreCase("HTC")) {
            return getConverterTypeForHTC();
        }

        // TODO: LG, Sony ...

        return getDefault();
    }


    private static PatternConverterType getConverterTypeForSamsung() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return PatternConverterType.ToLollipopSamsung;
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            int lastIdx = Build.VERSION.RELEASE.lastIndexOf(".");
            int VERSION_MR = Integer.valueOf(Build.VERSION.RELEASE.substring(lastIdx + 1));
            if (VERSION_MR < 3) {
                // Before version of Android 4.4.2
                return PatternConverterType.None;
            } else {
                // Later version of Android 4.4.3
                return PatternConverterType.ToLollipopSamsung;
            }
        } else {
            // Before version of Android 4
            return PatternConverterType.ToObsoleteSamsung;
        }
    }


    /**
     * Not working with htc devices!
     */
    private static PatternConverterType getConverterTypeForHTC() {
        // TODO: Fix for HTC
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return PatternConverterType.ToLollipopSamsung;
        } else {
            return PatternConverterType.None;
        }
    }


    private static PatternConverterType getDefault() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return PatternConverterType.ToLollipopSamsung;
        } else {
            return PatternConverterType.None;
        }
    }
}
