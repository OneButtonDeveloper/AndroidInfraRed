package com.uei.control;

import com.uei.control.Device.DeviceTypes;

public interface IDevice {
    String getBrand();

    String getDeviceTypeName();

    int getId();

    String getModel();

    String getName();

    DeviceTypes getType();

    String getVersion();
}
