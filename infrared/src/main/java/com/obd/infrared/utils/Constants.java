package com.obd.infrared.utils;

/**
 * Created by Andrew on 10/25/2017
 */

public class Constants {
    public static final String LE_COOLPAD_IR_SERVICE_PACKAGE = "com.uei.quicksetsdk.letvitwo";
    public static final String LE_DEFAULT_IR_SERVICE_PACKAGE_2 = "com.uei.quicksetsdk.letv";

    /**
     * Remote SDC don't have normal way to send signal,
     * so using big enough number for duration of ir functions
     * */
    public static final int DEFAULT_IR_FUNCTION_DURATION_MS = 500;
}
