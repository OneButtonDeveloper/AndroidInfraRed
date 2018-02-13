package com.obd.infrared.detection.concrete;

import com.obd.infrared.detection.IDetector;
import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.transmit.TransmitterType;
import com.obd.infrared.utils.Constants;

/**
 * Created by Andrew on 20.10.2017
 */

public class LeDetector implements IDetector {
    @Override
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams) {
        try {
            boolean hasPackage = detectorParams.hasAnyPackage(Constants.LE_COOLPAD_IR_SERVICE_PACKAGE, Constants.LE_DEFAULT_IR_SERVICE_PACKAGE_2);
            detectorParams.logger.log("Check Le IR interface: " + hasPackage);
            return hasPackage;
        } catch (Exception e) {
            detectorParams.logger.error("On Le ir error", e);
            return false;
        }
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.Le;
    }
}
