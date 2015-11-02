package com.obd.infrared.patterns;

import android.os.Build;

import com.obd.infrared.detection.DeviceDetector;

public enum PatternAdapterType {

    ToCycles,
    ToIntervals,
    ToObsoleteSamsungString,
    ToCyclesHtcPattern;

    public static PatternAdapterType getConverterType() {
        if (DeviceDetector.isSamsung()) {
            return getConverterTypeForSamsung();
        }
        if (DeviceDetector.isLg()) {
            return PatternAdapterType.ToIntervals;
        }
        if (DeviceDetector.isHtc()) {
            return PatternAdapterType.ToCyclesHtcPattern;
        }
        /**
         * {@link android.hardware.ConsumerIrManager#transmit(int, int[])} used microseconds (time intervals)
         */
        return PatternAdapterType.ToIntervals;
    }


    /**
     * http://developer.samsung.com/technical-doc/view.do?v=T000000125
     */
    private static PatternAdapterType getConverterTypeForSamsung() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return PatternAdapterType.ToIntervals;
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            int lastIdx = Build.VERSION.RELEASE.lastIndexOf(".");
            int VERSION_MR = Integer.valueOf(Build.VERSION.RELEASE.substring(lastIdx + 1));
            if (VERSION_MR < 3) {
                // Before version of Android 4.4.2
                return PatternAdapterType.ToCycles;
            } else {
                // Later version of Android 4.4.3
                return PatternAdapterType.ToIntervals;
            }
        } else {
            // Before version of Android 4
            return PatternAdapterType.ToObsoleteSamsungString;
        }
    }


}
