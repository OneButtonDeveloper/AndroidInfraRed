package com.obd.infrared.devices;

import java.util.ArrayList;
import java.util.List;

public class IrDevice {

    private int id;
    private String name;
    private List<IrFunction> functions = new ArrayList<>();

    public IrDevice(int id, String name) {
        this.id = id;
        this.name = name == null || name.trim().length() <= 0 ? "D:" + id : name.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<IrFunction> getFunctions() {
        return functions;
    }

    public void addFunction(IrFunction function) {
        if (function != null)
            functions.add(function);
    }
}
