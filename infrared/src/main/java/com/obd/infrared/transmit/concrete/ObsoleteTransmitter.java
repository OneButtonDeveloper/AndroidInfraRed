package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ObsoleteTransmitter extends Transmitter {


    private final Object irdaService;
    private Method write_irsend;

    @SuppressWarnings("ResourceType")
    public ObsoleteTransmitter(Context context, Logger logger) {
        super(context, logger);
        logger.log("Try to create ObsoleteTransmitter");
        irdaService = context.getSystemService("irda");
        try {
            write_irsend = irdaService.getClass().getMethod("write_irsend", new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            logger.error("ObsoleteTransmitter:NoSuchMethodException", e);
        }
        logger.log("ObsoleteTransmitter created");
    }

    @Override
    public void transmit(TransmitInfo transmitInfo) {
        try {
            write_irsend.invoke(irdaService, transmitInfo.obsoletePattern);
        } catch (IllegalAccessException e) {
            logger.error("ObsoleteTransmitter:IllegalAccessException", e);
        } catch (InvocationTargetException e) {
            logger.error("ObsoleteTransmitter:InvocationTargetException", e);
        }
    }
}
