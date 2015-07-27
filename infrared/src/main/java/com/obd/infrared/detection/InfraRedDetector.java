package com.obd.infrared.detection;

import android.content.Context;
import android.os.Build;

import com.obd.infrared.detection.concrete.ActualDetector;
import com.obd.infrared.detection.concrete.HtcDetector;
import com.obd.infrared.detection.concrete.LgDetector;
import com.obd.infrared.detection.concrete.ObsoleteSamsungDetector;
import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitterType;

import java.util.ArrayList;
import java.util.List;

public class InfraRedDetector {
    public static class DetectorParams {
        public final Context context;
        public final Logger logger;

        public DetectorParams(Context context, Logger logger) {
            this.context = context;
            this.logger = logger;
        }
    }

    protected final DetectorParams detectorParams;
    protected final List<IDetector> detectors = new ArrayList<>();

    public InfraRedDetector(Context context, Logger logger) {
        this.detectorParams = new DetectorParams(context, logger);

        logger.log("Build.MANUFACTURER: " + Build.MANUFACTURER);

        this.detectors.add(new LgDetector());
        this.detectors.add(new HtcDetector());
        this.detectors.add(new ObsoleteSamsungDetector());
        this.detectors.add(new ActualDetector());
    }

    private TransmitterType detect(DetectorParams detectorParams) {
        for (IDetector detector : detectors) {
            if (detector.hasTransmitter(detectorParams)) {
                return detector.getTransmitterType();
            }
        }
        return TransmitterType.Undefined;
    }

    public TransmitterType detect() {
        detectorParams.logger.log("Detection started");
        TransmitterType transmitterType = detect(detectorParams);
        detectorParams.logger.log("Detection result: " + transmitterType);
        return transmitterType;
    }
}
