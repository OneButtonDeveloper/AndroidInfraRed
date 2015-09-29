package com.obd.infrared.transmit.concrete;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.htc.circontrol.CIRControl;
import com.htc.htcircontrol.HtcIrData;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;

import java.util.Arrays;
import java.util.UUID;


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
                    htcControl.transmitIRCmd(new HtcIrData(1, frequency, frame), false);
                }
                catch(IllegalArgumentException iae) {
                    logger.error("Run: IllegalArgumentException", iae);
                }
                catch(Exception e) {
                    logger.error("Run: Exception", e);
                }
        }
    }

    private static class HtcHandler extends Handler {

        private final Logger logger;
        public HtcHandler(Looper looper, Logger logger) {
            super(looper);
            this.logger = logger;
        }

        @Override
        public void handleMessage(Message msg)
        {
            logger.log("HtcHandler.handleMessage:");
            logger.log("msg.what: " + msg.what + " arg1: " + msg.arg1 + " arg2: " + msg.arg2);
            logger.log("msg.toString: " + msg.toString());

            switch (msg.what) {
                case CIRControl.MSG_RET_LEARN_IR:
                    logger.log("MSG_RET_LEARN_IR");
                    break;
                case CIRControl.MSG_RET_TRANSMIT_IR:
                    logger.log("MSG_RET_TRANSMIT_IR");
                    switch(msg.arg1) {
                        case CIRControl.ERR_IO_ERROR:
                            logger.log("CIR hardware component is busy in doing early CIR command");
                            logger.log("Send IR Error=ERR_IO_ERROR");
                            break;
                        case CIRControl.ERR_INVALID_VALUE:
                            logger.log("Send IR Error=ERR_INVALID_VALUE");
                            break;
                        case CIRControl.ERR_CMD_DROPPED:
                            logger.log("SDK might be too busy to send IR key, developer can try later, or send IR key with non-droppable setting");
                            logger.log("Send IR Error=ERR_CMD_DROPPED");
                            break;
                        default:
                            logger.log("default");
                            break;
                    }
                    break;
                case CIRControl.MSG_RET_CANCEL:
                    logger.log("MSG_RET_CANCEL");
                    switch(msg.arg1) {
                        case CIRControl.ERR_IO_ERROR:
                            logger.log("CIR hardware component is busy in doing early CIR command");
                            logger.log("Send IR Error=ERR_IO_ERROR");
                            break;
                        case CIRControl.ERR_CANCEL_FAIL:
                            logger.log("CIR hardware component is busy in doing early CIR command");
                            logger.log("Cancel Error: ERR_CANCEL_FAIL");
                            break;
                        default:
                            logger.log("default");
                            break;
                    }
                    break;
                default:
                    logger.log("global default");
            }
        }
    }

    private final CIRControl htcControl;
    private final Handler htcHandler;

    public HtcTransmitter(Context context, Logger logger) {
        super(context, logger);

        logger.log("Try to create HtcTransmitter");
        htcHandler = new HtcHandler(Looper.getMainLooper(), logger);
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
