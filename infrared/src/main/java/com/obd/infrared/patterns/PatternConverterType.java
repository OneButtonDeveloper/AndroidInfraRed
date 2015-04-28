package com.obd.infrared.patterns;

import android.os.Build;

public enum PatternConverterType {

    None,
    ToObsoleteSamsungString,
    ToTimeLengthPattern;

    public static PatternConverterType getConverterType() {

        if (Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG")) {
            return getConverterTypeForSamsung();
        }

        return getDefault();
    }


    private static PatternConverterType getConverterTypeForSamsung() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return PatternConverterType.ToTimeLengthPattern;
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            int lastIdx = Build.VERSION.RELEASE.lastIndexOf(".");
            int VERSION_MR = Integer.valueOf(Build.VERSION.RELEASE.substring(lastIdx + 1));
            if (VERSION_MR < 3) {
                // Before version of Android 4.4.2
                return PatternConverterType.None;
            } else {
                // Later version of Android 4.4.3
                return PatternConverterType.ToTimeLengthPattern;
            }
        } else {
            // Before version of Android 4
            return PatternConverterType.ToObsoleteSamsungString;
        }
    }


    /**
     * Seems like universal solution for LG, Sony and HTC
     * TODO: Check that!
     */
    private static PatternConverterType getDefault() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return PatternConverterType.ToTimeLengthPattern;
        } else {
            return PatternConverterType.None;
        }
    }
}
