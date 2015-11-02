package com.obd.infrared.detection;


import com.obd.infrared.transmit.TransmitterType;

public interface IDetector {
    boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams);
    TransmitterType getTransmitterType();
}
