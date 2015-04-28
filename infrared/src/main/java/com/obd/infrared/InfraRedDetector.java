package com.obd.infrared;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.ConsumerIrManager;
import android.os.Build;

import com.obd.infrared.log.Logger;
import com.obd.infrared.patterns.PatternConverter;
import com.obd.infrared.patterns.PatternConverterType;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.TransmitterType;

import java.lang.reflect.Method;
import java.util.List;

import static android.content.Context.CONSUMER_IR_SERVICE;

public class InfraRedDetector {
    protected final Context context;
    protected final Logger logger;

    public InfraRedDetector(Context context, Logger logger) {
        this.context = context;
        this.logger = logger;
    }

    public TransmitterType detect() {
        logger.log("Detection started");
        TransmitterType transmitterType = TransmitterType.Undefined;
        if (isHTC()) {
            transmitterType = TransmitterType.HTC;
        } else if (hasObsoleteIR()) {
            transmitterType = TransmitterType.Obsolete;
        } else if (hasIR()) {
            transmitterType = TransmitterType.Actual;
        }
        logger.log("Detection result: " + transmitterType);
        return transmitterType;
    }

    /**
     * Code from samples in HTC IR SDK
     */
    private boolean isHTC() {
        try {
            boolean hasPackage = hasPackage("com.htc.cirmodule", context);
            logger.log("Check HTC IR interface: " + hasPackage);
            logger.log("Check HTC IR Build.VERSION.SDK_INT: < Build.VERSION_CODES.KITKAT: " + (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT));
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT && hasPackage;
        } catch (Exception e) {
            logger.error("On HTC ir error", e);
            return false;
        }
    }

    public static boolean hasPackage(String packageName, Context context) {
        PackageManager manager = context.getPackageManager();
        List<ApplicationInfo> packages = manager.getInstalledApplications(0);
        for (ApplicationInfo info : packages) {
            if (info.packageName.contains(packageName)) {
                return true;
            }
        }
        return false;
    }


    @SuppressWarnings("ResourceType")
    private boolean hasObsoleteIR() {
        try {
            logger.log("Check obsolete Samsung IR interface");
            Object irdaService = context.getSystemService("irda");
            if (irdaService == null) {
                logger.log("Not found obsolete Samsung IR service                                                                                                                                                                                                                                                                                                                                                                                                                                                             ");
                return false;
            }
            logger.log("Got irdaService");
            Method write_irsend = irdaService.getClass().getMethod("write_irsend", new Class[]{String.class});
            logger.log("Got write_irsend");

            TransmitInfo transmitInfo = PatternConverter.createTransmitInfo(38000, PatternConverterType.ToObsoleteSamsungString, 100, 100, 100);
            write_irsend.invoke(irdaService, transmitInfo.obsoletePattern);

            logger.log("Called write_irsend.invoke");
            return true;
        } catch (Exception e) {
            logger.error("On obsolete transmitter error", e);
            return false;
        }
    }

    private boolean hasIR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasActualIR();
    }

    @SuppressWarnings("ResourceType")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean hasActualIR() {
        try {
            logger.log("Check CONSUMER_IR_SERVICE");
            ConsumerIrManager consumerIrManager = (ConsumerIrManager) context.getSystemService(CONSUMER_IR_SERVICE);
            if (consumerIrManager.hasIrEmitter()) {

                logger.log("CONSUMER_IR_SERVICE: must be included TRANSMIT_IR permission to AndroidManifest.xml");
                TransmitInfo transmitInfo = PatternConverter.createTransmitInfo(38000, PatternConverterType.None, 100, 100, 100);
                consumerIrManager.transmit(transmitInfo.frequency, transmitInfo.pattern);

                logger.log("CONSUMER_IR_SERVICE: hasIrEmitter is true");
                return true;
            } else {
                logger.log("CONSUMER_IR_SERVICE: hasIrEmitter is false");
                return false;
            }
        } catch (Exception e) {
            logger.error("On actual transmitter error", e);
            return false;
        }
    }


}
