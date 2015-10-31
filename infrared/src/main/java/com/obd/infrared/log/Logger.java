package com.obd.infrared.log;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class Logger {
    protected final String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public abstract void log(String message);

    public abstract void warning(String message);

    public abstract void error(String description, Exception exception);

    public void printLogCat(int delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec("logcat -d");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        log(line);
                    }
                } catch (Exception e) {
                    error("StartWriteLogs", e);
                }
            }
        }, delay);
    }

}
