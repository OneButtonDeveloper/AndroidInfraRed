package com.obd.infrared.detection.concrete;

import com.obd.infrared.detection.DeviceDetector;
import com.obd.infrared.detection.IDetector;
import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.transmit.TransmitterType;

/**
 * Created by Andrew on 20.10.2017
 */

public class LeDetector implements IDetector {
    @Override
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams) {
        return DeviceDetector.isLe(); // Don't know better way
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.Le;
    }
}
