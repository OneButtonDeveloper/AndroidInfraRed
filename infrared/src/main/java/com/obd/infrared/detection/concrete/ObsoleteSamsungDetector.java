package com.obd.infrared.detection.concrete;

import com.obd.infrared.detection.IDetector;
import com.obd.infrared.detection.InfraRedDetector;
import com.obd.infrared.patterns.PatternConverter;
import com.obd.infrared.patterns.PatternConverterType;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.TransmitterType;

import java.lang.reflect.Method;

public class ObsoleteSamsungDetector implements IDetector {

    @SuppressWarnings("ResourceType")
    @Override
    public boolean hasTransmitter(InfraRedDetector.DetectorParams detectorParams) {
        try {
            detectorParams.logger.log("Check obsolete Samsung IR interface");
            Object irdaService = detectorParams.context.getSystemService("irda");
            if (irdaService == null) {
                detectorParams.logger.log("Not found obsolete Samsung IR service                                                                                                                                                                                                                                                                                                                                                                                                                                                             ");
                return false;
            }
            detectorParams.logger.log("Got irdaService");
            Method write_irsend = irdaService.getClass().getMethod("write_irsend", new Class[]{String.class});
            detectorParams.logger.log("Got write_irsend");

            TransmitInfo transmitInfo = PatternConverter.createTransmitInfo(38000, PatternConverterType.ToObsoleteSamsungString, 100, 100, 100);
            write_irsend.invoke(irdaService, transmitInfo.obsoletePattern);

            detectorParams.logger.log("Called write_irsend.invoke");
            return true;
        } catch (Exception e) {
            detectorParams.logger.error("On obsolete transmitter error", e);
            return false;
        }
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.Obsolete;
    }
}
