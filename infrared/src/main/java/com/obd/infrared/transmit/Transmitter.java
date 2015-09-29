package com.obd.infrared.transmit;

import android.content.Context;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.concrete.ActualTransmitter;
import com.obd.infrared.transmit.concrete.HtcTransmitter;
import com.obd.infrared.transmit.concrete.LgTransmitter;
import com.obd.infrared.transmit.concrete.LgWithDeviceTransmitter;
import com.obd.infrared.transmit.concrete.LgWithoutDeviceTransmitter;
import com.obd.infrared.transmit.concrete.ObsoleteTransmitter;
import com.obd.infrared.transmit.concrete.UndefinedTransmitter;

public abstract class Transmitter {
    public static Transmitter getTransmitterByType(TransmitterType transmitterType, Context context, Logger logger) {
        logger.log("Get transmitter by type: " + transmitterType);
        switch (transmitterType) {
            case Actual:
                return new ActualTransmitter(context, logger);
            case Obsolete:
                return new ObsoleteTransmitter(context, logger);
            case HTC:
                return new HtcTransmitter(context, logger);
            case LG:
                return new LgWithDeviceTransmitter(context, logger);
            case LG_WithOutDevice:
                return new LgWithoutDeviceTransmitter(context, logger);
            default:
                return new UndefinedTransmitter(context, logger);
        }
    }


    protected final Context context;
    protected final Logger logger;

    public Transmitter(Context context, Logger logger) {
        this.context = context;
        this.logger = logger;
    }

    public void start() {
    }

    public abstract void transmit(TransmitInfo transmitInfo);

    public void stop() {
    }
}
