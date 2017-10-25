package com.obd.infrared.detection.concrete;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.obd.infrared.detection.IDetector;
import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.transmit.TransmitterType;

import java.util.List;

public class HtcDetector implements IDetector {

    /**
     * Code from samples in HTC IR SDK
     */
    @Override
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams) {
        try {
            boolean hasPackage = hasPackage("com.htc.cirmodule", detectorParams.context);
            detectorParams.logger.log("Check HTC IR interface: " + hasPackage);
            return hasPackage;
        } catch (Exception e) {
            detectorParams.logger.error("On HTC ir error", e);
            return false;
        }
    }

    private boolean hasPackage(String packageName, Context context) {
        PackageManager manager = context.getPackageManager();
        List<ApplicationInfo> packages = manager.getInstalledApplications(0);
        for (ApplicationInfo info : packages) {
            if (info.packageName.contains(packageName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.HTC;
    }
}
