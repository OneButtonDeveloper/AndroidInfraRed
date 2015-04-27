package com.obd.infrared.transmit.concrete;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.htc.circontrol.CIRControl;
import com.htc.htcircontrol.HtcIrData;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;


public class HtcTransmitter extends Transmitter {

    private CIRControl htcControl;

    public HtcTransmitter(Context context, Logger logger) {
        super(context, logger);
        logger.log("Try to create HtcTransmitter");
        htcControl = new CIRControl(context, new Handler(Looper.getMainLooper()) {
        });
        logger.log("HtcTransmitter created");
    }

    @Override
    public void start() {
        try {
            logger.log("Try to start HTC CIRControl");
            htcControl.start();
        } catch (Exception e) {
            logger.error("On try to start HTC CIRControl", e);
        }
    }

    @Override
    public void transmit(TransmitInfo transmitInfo) {
        try {
            if (htcControl.isStarted()) {
                logger.log("Try to transmit HTC");
                htcControl.transmitIRCmd(new HtcIrData(1, transmitInfo.frequency, transmitInfo.pattern), false);
            } else {
                logger.log("htcControl not started");
            }
        } catch (Exception e) {
            logger.error("On try to transmit", e);
        }
    }

    @Override
    public void stop() {
        try {
            logger.log("Try to stop HTC CIRControl");
            htcControl.stop();
        } catch (Exception e) {
            logger.error("On try to stop HTC CIRControl", e);
        }
    }
}
