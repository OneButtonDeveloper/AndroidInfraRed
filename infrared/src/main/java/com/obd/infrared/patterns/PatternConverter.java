package com.obd.infrared.patterns;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;

public class PatternConverter {


    private final PatternConverterType converterType;

    public PatternConverter(Logger logger) {
        converterType = PatternConverterType.getConverterType();
        logger.log("ConverterType: " + converterType);
    }

    public TransmitInfo createTransmitInfo(int frequency, int... pulseCountPattern) {
        return createTransmitInfo(frequency, converterType, pulseCountPattern);
    }

    public static TransmitInfo createTransmitInfo(int frequency, PatternConverterType converterType, int... pulseCountPattern) {
        switch (converterType) {
            case ToPulsesHtcPattern:
                return new TransmitInfo(frequency, convertToPulsesHtcPattern(pulseCountPattern));
            case ToTimeLengthPattern:
                return new TransmitInfo(frequency, convertToTimeLengthPattern(frequency, pulseCountPattern));
            case ToObsoleteSamsungString:
                return new TransmitInfo(frequency, createObsoletePattern(frequency, pulseCountPattern));
            default:
                return new TransmitInfo(frequency, pulseCountPattern);
        }
    }


    private static Object[] createObsoletePattern(int frequency, int[] pulseCountPattern) {
        StringBuilder result = new StringBuilder();
        result.append(frequency);
        for (Integer i : pulseCountPattern) {
            result.append(',');
            result.append(i);
        }
        return new Object[]{result.toString()};
    }

    /**
     * http://developer.samsung.com/technical-doc/view.do?v=T000000125
     */
    private static int[] convertToTimeLengthPattern(int frequency, int[] pulseCountPattern) {
        int k = 1000000 / frequency;
        for (int i = 0; i < pulseCountPattern.length; i++) {
            pulseCountPattern[i] = pulseCountPattern[i] * k;
        }
        return pulseCountPattern;
    }


    private static int[] convertToPulsesHtcPattern(int[] pulseCountPattern) {
        int count = pulseCountPattern.length;
        boolean isEven = count % 2 == 0;
        if (!isEven) {
            count += 1;
        }
        int[] newPattern = new int[count];
        System.arraycopy(pulseCountPattern, 0, newPattern, 0, pulseCountPattern.length);
        if (!isEven) {
            newPattern[count - 1] = 10;
        }
        return newPattern;

    }

}
