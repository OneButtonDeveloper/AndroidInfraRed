package com.obd.infrared.patterns;

public class PatternConverterUtils {

    private final static int DEC = 10;
    private final static int HEX = 16;

    private static String[] splitLine(String line) {
        return line.split("[^xXabcdefABCDEF\\d]+");
    }

    private static String removeHexPrefix(String s) {
        return s.replaceAll("0x|0X|x|X", "");
    }

    private static int[] convertToIntegerArray(String line, int radix) {
        String[] data = splitLine(line);
        int[] result = new int[data.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = radix == DEC ? Integer.parseInt(data[i]) : Integer.parseInt(removeHexPrefix(data[i]), radix);
        }
        return result;
    }

    public static PatternConverter fromString(PatternType type, int frequency, String line) {
        int[] result = convertToIntegerArray(line, DEC);
        return new PatternConverter(type, frequency, result);
    }

    public static PatternConverter fromHexString(PatternType type, int frequency, String line) {
        int[] result = convertToIntegerArray(line, HEX);
        return new PatternConverter(type, frequency, result);
    }

}
