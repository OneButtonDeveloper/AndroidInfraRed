package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.obd.infrared.log.Logger;

public class LgWithoutDeviceTransmitter extends LgTransmitter {
    public LgWithoutDeviceTransmitter(Context context, Logger logger) {
        super(context, logger);
    }

    @Override
    protected void beforeSendIr() {
        // Do nothing before send ir command
    }
}
