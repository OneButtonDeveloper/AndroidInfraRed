package com.obd.infrared.transmit.concrete;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.htc.circontrol.CIRControl;
import com.htc.htcircontrol.HtcIrData;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;


public class HtcTransmitter extends Transmitter {

    private class SendRunnable implements Runnable {
        private int frequency;
        private int[] frame;

        public SendRunnable(int frequency, int[] frame) {
            this.frequency = frequency;
            this.frame = frame;
        }

        public void run() {
                try {
                    htcControl.transmitIRCmd(new HtcIrData (1, frequency, frame), false);
                }
                catch(IllegalArgumentException iae) {
                    Log.e("HtcTransmitter", "new HtcIrData: " + iae.getMessage());
                    iae.printStackTrace();
                    throw iae;
                }
        }
    }


    private final CIRControl htcControl;
    private final Handler htcHandler;

    public HtcTransmitter(Context context, Logger logger) {
        super(context, logger);
        logger.log("Try to create HtcTransmitter");
        htcHandler = new Handler(Looper.getMainLooper());
        htcControl = new CIRControl(context, htcHandler);
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
                htcHandler.post(new SendRunnable(transmitInfo.frequency, transmitInfo.pattern));
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
