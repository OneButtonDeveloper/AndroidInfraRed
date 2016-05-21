package com.obd.infrared.detection.concrete;

import android.os.Build;

import com.lge.hardware.IRBlaster.IRBlaster;
import com.obd.infrared.detection.DeviceDetector;
import com.obd.infrared.detection.IDetector;
import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.transmit.TransmitterType;

public class LgDetector implements IDetector {

    private TransmitterType transmitterType = TransmitterType.LG;

    @Override
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams) {
        try {
            detectorParams.logger.log("Check LG: " + DeviceDetector.isLg());
            if (DeviceDetector.isLg()) {
                // in the Android version 5.1 on LG G4 work ConsumerIRManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    ActualDetector actualDetector = new ActualDetector();
                    if (actualDetector.hasTransmitter(detectorParams)) {
                        transmitterType = TransmitterType.LG_Actual;
                        return true;
                    }
                }

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
        return transmitterType;
    }
}
