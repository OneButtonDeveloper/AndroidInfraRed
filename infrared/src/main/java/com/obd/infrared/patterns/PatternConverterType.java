package com.obd.infrared.patterns;

import android.os.Build;

import com.obd.infrared.detection.DeviceDetector;

public enum PatternConverterType {

    None,
    ToObsoleteSamsungString,
    ToTimeLengthPattern,
    ToPulsesHtcPattern;

    public static PatternConverterType getConverterType() {
        if (DeviceDetector.isSamsung()) {
            return getConverterTypeForSamsung();
        }
        if (DeviceDetector.isLg()) {
            return PatternConverterType.ToTimeLengthPattern;
        }
        if (DeviceDetector.isHtc()) {
            return PatternConverterType.ToPulsesHtcPattern;
        }
        return PatternConverterType.None;
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


}
