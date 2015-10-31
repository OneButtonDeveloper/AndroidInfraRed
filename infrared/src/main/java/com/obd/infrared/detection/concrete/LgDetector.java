package com.obd.infrared.detection.concrete;

import com.lge.hardware.IRBlaster.IRBlaster;
import com.obd.infrared.detection.DeviceDetector;
import com.obd.infrared.detection.IDetector;
import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.transmit.TransmitterType;

public class LgDetector implements IDetector {

    @Override
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams) {
        try {
            detectorParams.logger.log("Check LG: " + DeviceDetector.isLg());
            if (DeviceDetector.isLg()) {
                boolean isSdkSupported = IRBlaster.isSdkSupported(detectorParams.context);
                detectorParams.logger.log("Check LG IRBlaster " + isSdkSupported);
                return isSdkSupported;
            } else {
                return false;
            }
        } catch (Exception e) {
            detectorParams.logger.error("On LG ir detection error", e);
            return false;
        }
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.LG;
    }
}
