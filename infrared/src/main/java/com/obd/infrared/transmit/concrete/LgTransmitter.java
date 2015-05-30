package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRAction;
import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;


public class LgTransmitter extends Transmitter implements IRBlasterCallback {

    private final IRBlaster irBlaster;

    public LgTransmitter(Context context, Logger logger) {
        super(context, logger);
        logger.log("Try to create LG IRBlaster");
        irBlaster = IRBlaster.getIRBlaster(context, this);
        logger.log("IRBlaster created");
    }

    @Override
    public void start() {
        logger.log("Start not supported in LG IRBlaster");
    }

    @Override
    public void transmit(TransmitInfo transmitInfo) {
        try {
            if (isReady) {
                if (deviceSelected != null){
                    irBlaster.sendIR(new IRAction(deviceSelected.Id, deviceSelected.KeyFunctions.get(0).Id, 0));
                    logger.log("Try to IRBlaster.send IR");
                }
                logger.log("Try to transmit LG IRBlaster");
                irBlaster.sendIRPattern(transmitInfo.frequency, transmitInfo.pattern);
            } else {
                logger.log("LG IRBlaster not ready");
            }
        } catch (Exception e) {
            logger.error("On try to transmit LG IRBlaster", e);
        }
    }

    @Override
    public void stop() {
        try {
            logger.log("Try to close LG IRBlaster");
            irBlaster.close();
        } catch (Exception e) {
            logger.error("On try to close LG IRBlaster", e);
        }
    }

    private boolean isReady = false;
    private Device deviceSelected = null;

    @Override
    public void IRBlasterReady() {
        isReady = true;
        Device[] mDevices = irBlaster.getDevices();
        for (Device device:mDevices){
            if(device.KeyFunctions != null && device.KeyFunctions.size() > 0) {
                deviceSelected = device;
                break;
            }
        }
        logger.log("LG IRBlaster ready");
    }

    @Override
    public void learnIRCompleted(int i) {
        logger.log("LG IRBlaster.learnIRCompleted : " + i);
    }

    @Override
    public void newDeviceId(int i) {
        logger.log("LG IRBlaster.newDeviceId : " + i);
    }

    @Override
    public void failure(int i) {
        logger.log("LG IRBlaster.failure : " + i);
    }
}

