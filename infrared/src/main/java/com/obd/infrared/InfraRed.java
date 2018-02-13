package com.obd.infrared;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.devices.IrDevice;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;
import com.obd.infrared.transmit.TransmitterType;

import java.util.List;

public class InfraRed {

    // v3.5.2 -> Added duration for transmitting with devices
    // v3.5.1 -> Fixed bug with getting Ir functions for Lg and Le
    // v3.5 -> Added support for getting ir-devices for Lg and Le
    // v3.4 -> Added support for Le Eco devices
    private static final String VERSION = "InfraRed v3.5.2";

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
    private TransmitterType transmitterType;

    public boolean hasIrDevices() {
        return transmitterType.hasIrDevices();
    }

    public boolean isReady() {
        return transmitter != null && transmitter.isReady();
    }

    public void createTransmitter(TransmitterType transmitterType) {
        if (transmitter == null) {
            try {
                this.transmitterType = transmitterType;
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

    public List<IrDevice> getDevices() {
        if (transmitterType.hasIrDevices()) {
            logger.log("getIrDevices");
            return transmitter.getIrDevices(logger);
        } else {
            logger.log("no IrDevices");
            return null;
        }
    }

    public void transmit(int deviceId, int functionId, int duration) {
        if (transmitterType.hasIrDevices()) {
            transmitter.transmit(deviceId, functionId, duration);
        } else {
            logger.log("Transmitting by device id " + deviceId + " and function id " + functionId + " with duration " + duration + " not supported");
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
