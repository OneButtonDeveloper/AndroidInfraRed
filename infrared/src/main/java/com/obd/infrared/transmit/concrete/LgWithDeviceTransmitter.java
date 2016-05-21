package com.obd.infrared.transmit.concrete;

import android.content.Context;

import com.lge.hardware.IRBlaster.Device;
import com.lge.hardware.IRBlaster.IRAction;
import com.lge.hardware.IRBlaster.IRFunction;
import com.lge.hardware.IRBlaster.ResultCode;
import com.obd.infrared.log.Logger;

public class LgWithDeviceTransmitter extends LgTransmitter {

    public LgWithDeviceTransmitter(Context context, Logger logger) {
        super(context, logger);
    }

    private Device deviceSelected = null;

    @Override
    public void IRBlasterReady() {
        super.IRBlasterReady();
        Device[] mDevices = irBlaster.getDevices();
        for (Device device : mDevices) {
            if (device.KeyFunctions != null && device.KeyFunctions.size() > 0) {
                deviceSelected = device;
                break;
            }
        }
        // logDevices(mDevices);
        logger.log("LG deviceSelected :" + (deviceSelected != null));

    }

    @SuppressWarnings("unused")
    private void logDevices(Device[] mDevices) {
        for(Device device: mDevices) {
            logger.log("Device ID:" + device.Id + " Name:" + device.Name + " B: " + device.Brand + " T:" + device.DeviceTypeName + " XZ: " + device.origName + " XZ: " + device.transName + " X: " + device.KeyFunctions.size());
            if (device.Name.contains("LG")) {
                int s = 0;
                for (IRFunction k : device.KeyFunctions) {
                    logger.log("Name: " + k.Name + " id: " + k.Id + " is: " + k.IsLearned);
                    s++;
                    if (s > 10) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void beforeSendIr() {
        if (deviceSelected != null) {
            int resultCode = irBlaster.sendIR(new IRAction(deviceSelected.Id, deviceSelected.KeyFunctions.get(0).Id, 0));
            logger.log("Try to IRBlaster.send known IR pattern. Result: " + ResultCode.getString(resultCode));
        }
    }

}
