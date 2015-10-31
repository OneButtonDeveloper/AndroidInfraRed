package com.obd.infrared.patterns;

public class PatternConverter {

    private final PatternType type;
    private final int frequency;
    private final int[] data;

    public PatternConverter(PatternType type, int frequency, int... data) {
        this.type = type;
        this.frequency = frequency;
        this.data = data;
    }

    public int getFrequency() {
        return this.frequency;
    }

    /** Convert and return data in @newPatternType */
    public int[] convertDataTo(PatternType newPatternType) {
        if (newPatternType == type) {
            return data;
        }

        if (type == PatternType.Intervals && newPatternType == PatternType.Cycles) {
            return convertIntervalsToCycles(frequency, data);
        }

        if (type == PatternType.Cycles && newPatternType == PatternType.Intervals) {
            return convertCyclesToIntervals(frequency, data);
        }

        throw new IllegalArgumentException("Unsupported PatternType: " + newPatternType);
    }

    public static int[] convertCyclesToIntervals(int frequency, int[] cycles) {
        int[] intervals = new int[cycles.length];
        int k = 1000000 / frequency;
        for (int i = 0; i < intervals.length; i++) {
            intervals[i] = cycles[i] * k;
        }
        return intervals;
    }

    public static int[] convertIntervalsToCycles(int frequency, int[] intervals) {
        int[] cycles = new int[intervals.length];
        int k = 1000000 / frequency;
        for (int i = 0; i < cycles.length; i++) {
            cycles[i] = intervals[i] / k;
        }
        return cycles;
    }

}
