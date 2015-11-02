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
            case ToCycles:
                pattern = patternConverter.convertDataTo(PatternType.Cycles);
                break;
            case ToIntervals:
                pattern = patternConverter.convertDataTo(PatternType.Intervals);
                break;
            case ToCyclesHtcPattern:
                pattern = convertToCyclesHtcPattern(patternConverter.convertDataTo(PatternType.Cycles));
                break;
            case ToObsoleteSamsungString:
                Object[] obsoletePattern = createObsoletePattern(patternConverter.getFrequency(), patternConverter.convertDataTo(PatternType.Cycles));
                return new TransmitInfo(patternConverter.getFrequency(), obsoletePattern);
            default:
                throw new IllegalArgumentException("PatternAdapterType " + converterType + " not supported");
        }
        return new TransmitInfo(patternConverter.getFrequency(), pattern);
    }


    private static Object[] createObsoletePattern(int frequency, int[] cycleCountPattern) {
        StringBuilder result = new StringBuilder();
        result.append(frequency);
        for (Integer i : cycleCountPattern) {
            result.append(',');
            result.append(i);
        }
        return new Object[]{result.toString()};
    }

    private static int[] convertToCyclesHtcPattern(int[] cycleCountPattern) {
        int count = cycleCountPattern.length;
        boolean isEven = count % 2 == 0;
        if (isEven) {
            return cycleCountPattern;
        } else {
            count += 1;
        }

        int[] newPattern = new int[count];
        System.arraycopy(cycleCountPattern, 0, newPattern, 0, cycleCountPattern.length);
        newPattern[count - 1] = 10;
        return newPattern;
    }

}
