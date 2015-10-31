package com.obd.infrared.patterns;

import android.os.Build;

import com.obd.infrared.detection.DeviceDetector;

public enum PatternAdapterType {

    None,
    ToObsoleteSamsungString,
    ToTimeLengthPattern,
    ToPulsesHtcPattern;

    public static PatternAdapterType getConverterType() {
        if (DeviceDetector.isSamsung()) {
            return getConverterTypeForSamsung();
        }
        if (DeviceDetector.isLg()) {
            return PatternAdapterType.ToTimeLengthPattern;
        }
        if (DeviceDetector.isHtc()) {
            return PatternAdapterType.ToPulsesHtcPattern;
        }
        return PatternAdapterType.None;
    }


    /**
     * http://developer.samsung.com/technical-doc/view.do?v=T000000125
     */
    private static PatternAdapterType getConverterTypeForSamsung() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return PatternAdapterType.ToTimeLengthPattern;
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            int lastIdx = Build.VERSION.RELEASE.lastIndexOf(".");
            int VERSION_MR = Integer.valueOf(Build.VERSION.RELEASE.substring(lastIdx + 1));
            if (VERSION_MR < 3) {
                // Before version of Android 4.4.2
                return PatternAdapterType.None;
            } else {
                // Later version of Android 4.4.3
                return PatternAdapterType.ToTimeLengthPattern;
            }
        } else {
            // Before version of Android 4
            return PatternAdapterType.ToObsoleteSamsungString;
        }
    }


}
