package com.obd.infrared.patterns;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;

public class PatternAdapter {

    private final PatternAdapterType adapterType;

    public PatternAdapter(Logger logger) {
        adapterType = PatternAdapterType.getConverterType();
        logger.log("ConverterType: " + adapterType);
    }

    public TransmitInfo createTransmitInfo(PatternConverter patternConverter) {
        return createTransmitInfo(adapterType, patternConverter);
    }

    public static TransmitInfo createTransmitInfo(PatternAdapterType converterType, PatternConverter patternConverter) {
        int[] pattern;
        switch (converterType) {
            case ToObsoleteSamsungString:
                Object[] obsoletePattern = createObsoletePattern(patternConverter.getFrequency(), patternConverter.convertDataTo(PatternType.Cycles));
                return new TransmitInfo(patternConverter.getFrequency(), obsoletePattern);
            case ToPulsesHtcPattern:
                pattern = convertToPulsesHtcPattern(patternConverter.convertDataTo(PatternType.Cycles));
                break;
            case ToTimeLengthPattern:
                pattern = convertToPulsesHtcPattern(patternConverter.convertDataTo(PatternType.Intervals));
                break;
            default:
                pattern = convertToPulsesHtcPattern(patternConverter.convertDataTo(PatternType.Intervals));
                break;
        }
        return new TransmitInfo(patternConverter.getFrequency(), pattern);
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

    private static int[] convertToPulsesHtcPattern(int[] pulseCountPattern) {
        int count = pulseCountPattern.length;
        boolean isEven = count % 2 == 0;
        if (isEven) {
            return pulseCountPattern;
        } else {
            count += 1;
        }

        int[] newPattern = new int[count];
        System.arraycopy(pulseCountPattern, 0, newPattern, 0, pulseCountPattern.length);
        newPattern[count - 1] = 10;
        return newPattern;
    }

}
