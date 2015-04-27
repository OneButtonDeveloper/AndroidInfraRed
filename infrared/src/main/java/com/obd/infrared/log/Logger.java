package com.obd.infrared.log;

public abstract class Logger {
    protected final String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public abstract void log(String message);

    public abstract void warning(String message);

    public abstract void error(String description, Exception exception);
}
