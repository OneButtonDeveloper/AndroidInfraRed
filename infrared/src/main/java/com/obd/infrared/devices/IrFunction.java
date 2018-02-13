package com.obd.infrared.devices;

public class IrFunction {
    private int id;
    private String name;
    private Boolean isLearned;

    public IrFunction(int id, String name, Boolean isLearned) {
        this.id = id;
        this.name = name == null || name.trim().length() <= 0 ? "V:" + id : name.trim();
        this.isLearned = isLearned;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsLearned() {
        return isLearned;
    }
}
