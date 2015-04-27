package com.obd.infrared;

import android.content.Context;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;
import com.obd.infrared.transmit.TransmitterType;

public class InfraRed {

    private final Context context;
    private final Logger logger;

    public InfraRed(Context context, Logger logger) {
        this.context = context;
        this.logger = logger;
    }


    public TransmitterType detect() {
        InfraRedDetector detector = new InfraRedDetector(context, logger);
        return detector.detect();
    }


    private Transmitter transmitter;
    public void createTransmitter(TransmitterType transmitterType) {
        if (transmitter == null) {
            try {
                transmitter = Transmitter.getTransmitterByType(transmitterType, context, logger);
            } catch (Exception e) {
                logger.error("Error on create transmitter: " + transmitterType, e);
            }
        } else {
            logger.warning("Transmitter already created!");
        }
    }

    public boolean transmit(TransmitInfo transmitInfo) {
        try {
            transmitter.transmit(transmitInfo);
            return true;
        } catch (Exception e) {
            logger.error("Cannot transmit data", e);
            return false;
        }
    }

    public void start() {
        try {
            transmitter.start();
        } catch (Exception e) {
            logger.error("Cannot start transmitter", e);
        }
    }

    public void stop() {
        try {
            transmitter.stop();
        } catch (Exception e) {
            logger.error("Cannot stop transmitter", e);
        }
    }

}
