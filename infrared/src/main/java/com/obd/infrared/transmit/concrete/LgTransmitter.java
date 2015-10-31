package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;
import com.lge.hardware.IRBlaster.ResultCode;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;


public abstract class LgTransmitter extends Transmitter implements IRBlasterCallback {

    protected final IRBlaster irBlaster;

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
                beforeSendIr();
                logger.log("Try to transmit LG IRBlaster");
                int resultCode = irBlaster.sendIRPattern(transmitInfo.frequency, transmitInfo.pattern);
                logger.log("Result: " + ResultCode.getString(resultCode));

            } else {
                logger.log("LG IRBlaster not ready");
            }
        } catch (Exception e) {
            logger.error("On try to transmit LG IRBlaster", e);
        }
    }

    protected abstract void beforeSendIr();

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

    @Override
    public void IRBlasterReady() {
        isReady = true;
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

