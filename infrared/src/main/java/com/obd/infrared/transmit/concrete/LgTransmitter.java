package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRAction;
import com.lge.hardware.IRBlaster.IRBlaster;
import com.lge.hardware.IRBlaster.IRBlasterCallback;
import com.lge.hardware.IRBlaster.IRFunction;
import com.lge.hardware.IRBlaster.ResultCode;
import com.obd.infrared.devices.IrDevice;
import com.obd.infrared.devices.IrFunction;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;
import com.obd.infrared.utils.Constants;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public void transmit(int deviceId, int functionId, int duration) {
        try {
            if (isReady) {
                logger.log("Transmitting with device id " + deviceId + " and function id " + functionId + " and duration " + duration);
                irBlaster.sendIR(new IRAction(deviceId, functionId, duration));
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
    public boolean isReady() {
        return isReady;
    }

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

    @Override
    public List<IrDevice> getIrDevices(Logger logger) {
        try {
            Device[] devices = irBlaster.getDevices();
            List<IrDevice> result = new ArrayList<>();
            if (devices != null && devices.length > 0) {
                logger.log("Devices size: " + devices.length);

                for (Device device : devices) {
                    IrDevice irDevice = new IrDevice(device.Id, device.Name);

                    if (device.KeyFunctions != null && device.KeyFunctions.size() > 0) {
                        for (IRFunction function : device.KeyFunctions) {
                            IrFunction irFunction = new IrFunction(function.Id, function.Name, function.IsLearned);
                            irDevice.addFunction(irFunction);
                        }
                        result.add(irDevice);
                        continue;
                    }

                    if (device.Functions != null && device.Functions.length > 0) {
                        String[] labelsByDevice = irBlaster.getAllFunctionLabels(device.Id, device.Functions);
                        for (int i = 0; i < device.Functions.length; i++) {
                            IrFunction irFunction = new IrFunction(device.Functions[i], labelsByDevice[i], null);
                            irDevice.addFunction(irFunction);
                        }
                        result.add(irDevice);
                    }
                }
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

