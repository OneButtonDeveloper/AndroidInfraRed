package com.obd.infrared.transmit.concrete;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;

import static android.content.Context.CONSUMER_IR_SERVICE;

public class ActualTransmitter extends Transmitter {

    private final ConsumerIrManager consumerIrManager;

    public ActualTransmitter(Context context, Logger logger) {
        super(context, logger);
        logger.log("Try to create ActualTransmitter");
        this.consumerIrManager = getConsumerIrManager();
        logger.log("ActualTransmitter created");
    }

    @SuppressWarnings("ResourceType")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private ConsumerIrManager getConsumerIrManager() {
        return (ConsumerIrManager) context.getSystemService(CONSUMER_IR_SERVICE);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void transmit(TransmitInfo transmitInfo) {
        logger.log("Try to transmit");
        consumerIrManager.transmit(transmitInfo.frequency, transmitInfo.pattern);
    }

}
