package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;

public class UndefinedTransmitter extends Transmitter {
    public UndefinedTransmitter(Context context, Logger logger) {
        super(context, logger);
        logger.warning("Created empty transmitter for undefined type of IR");
    }

    @Override
    public void transmit(TransmitInfo transmitInfo) {
        logger.warning("Data was transmitted for undefined type of IR");
    }
}
