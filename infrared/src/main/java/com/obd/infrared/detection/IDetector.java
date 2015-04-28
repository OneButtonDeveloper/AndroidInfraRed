package com.obd.infrared.detection;


import com.obd.infrared.transmit.TransmitterType;

public interface IDetector {
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams);
    public TransmitterType getTransmitterType();
}
