package com.obd.infrared.log;

import android.util.Log;

public class LogToConsole extends Logger {

    public LogToConsole(String tag) {
        super(tag);
    }

    @Override
    public void log(String message) {
        Log.d(tag, message);
    }

    @Override
    public void warning(String message) {
        Log.w(tag, message);
    }

    @Override
    public void error(String description, Exception exception) {
        Log.e(tag, "ERROR { Description: " + description + "; Exception: " + exception.getMessage() + " }");
        exception.printStackTrace();
    }
}
