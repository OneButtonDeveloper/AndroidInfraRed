package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.lge.hardware.IRBlaster.IRBlasterCallback;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;
import com.sony.remotecontrol.ir.Device;
import com.sony.remotecontrol.ir.IntentParams;
import com.sony.remotecontrol.ir.IrManager;
import com.sony.remotecontrol.ir.IrManagerFactory;
import com.sony.remotecontrol.ir.Status;



public class SonyTransmitter extends Transmitter implements IRBlasterCallback {

    public SonyTransmitter(Context context, Logger logger) {
        super(context, logger);
        logger.log("Try to create LG IRBlaster");
        logger.log("IRBlaster created");
    }

    @Override
    public void start() {
        logger.log("Start not supported in LG IRBlaster");
    }

    @Override
    public void transmit(TransmitInfo transmitInfo) {
        try {
        } catch (Exception e) {
            logger.error("On try to transmit LG IRBlaster", e);
        }
    }

    @Override
    public void stop() {
        try {
        } catch (Exception e) {
            logger.error("On try to close LG IRBlaster", e);
        }
    }

    private boolean isReady = false;

    @Override
    public void IRBlasterReady() {
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

