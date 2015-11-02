package com.obd.infrared;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;
import com.obd.infrared.transmit.TransmitterType;

public class InfraRed {

    private static final String VERSION = "InfraRed v3.1";

    private final Context context;
    private final Logger logger;

    public InfraRed(@NonNull Context context, @NonNull Logger logger) {
        this.context = context;
        this.logger = logger;
        Log.w("IR", VERSION);
        logger.log(VERSION);
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
