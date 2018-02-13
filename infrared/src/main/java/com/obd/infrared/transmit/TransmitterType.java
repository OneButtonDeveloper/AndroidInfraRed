package com.obd.infrared.transmit;

public enum TransmitterType {
    Undefined,
    Obsolete, // Samsung
    Actual,   // ConsumerIRManager
    HTC,
    Le(true),
    LG(true),
    LG_WithOutDevice(true),
    LG_Actual; // ConsumerIRManager

    private final boolean hasIrDevices;

    TransmitterType() {
        this(false);
    }

    TransmitterType(boolean hasIrDevices) {
        this.hasIrDevices = hasIrDevices;
    }

    public boolean hasIrDevices() {
        return hasIrDevices;
    }
}
