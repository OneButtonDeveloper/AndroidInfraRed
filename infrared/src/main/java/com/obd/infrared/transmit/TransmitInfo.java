package com.obd.infrared.transmit;

public class TransmitInfo {

    public final int frequency;
    public final int[] pattern;
    public final Object[] obsoletePattern;

    public TransmitInfo(int frequency, int[] pattern) {
        this.frequency = frequency;
        this.pattern = pattern;
        this.obsoletePattern = null;
    }

    public TransmitInfo(int frequency, Object[] pattern) {
        this.frequency = frequency;
        this.pattern = null;
        this.obsoletePattern = pattern;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("TransmitInfo [").append(frequency).append("]: ");
        if (pattern != null) {
            stringBuilder.append(" Count:").append(this.pattern.length).append(": ");
            for (int v : pattern) {
                stringBuilder.append(", ").append(v);
            }
        } else {
            stringBuilder.append(obsoletePattern[0].toString());
        }
        return stringBuilder.toString();
    }


}
