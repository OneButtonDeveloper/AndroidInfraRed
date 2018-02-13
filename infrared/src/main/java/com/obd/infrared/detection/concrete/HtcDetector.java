package com.obd.infrared.detection.concrete;

import com.obd.infrared.detection.IDetector;
import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.transmit.TransmitterType;

public class HtcDetector implements IDetector {

    /**
     * Code from samples in HTC IR SDK
     */
    @Override
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams) {
        try {
            boolean hasPackage = detectorParams.hasAnyPackage("com.htc.cirmodule");
            detectorParams.logger.log("Check HTC IR interface: " + hasPackage);
            return hasPackage;
        } catch (Exception e) {
            detectorParams.logger.error("On HTC ir error", e);
            return false;
        }
    }


    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.HTC;
    }
}
