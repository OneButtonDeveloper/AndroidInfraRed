package com.obd.infrared.utils.le;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Device implements Parcelable, IDevice {
    public static final Creator<Device> CREATOR = new DeviceCreator();
    public String Brand = "";
    public DeviceTypes DeviceType = DeviceTypes.IRDevice;
    public String DeviceTypeName = "";
    public int[] Functions = null;
    public int Id = 0;
    public List<IRFunction> KeyFunctions = new ArrayList();
    public String Model = "";
    public String Name = "";

    static class DeviceCreator implements Creator<Device> {
        DeviceCreator() {
        }

        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    }

    public enum DeviceTypes {
        IRDevice
    }

    protected Device(Parcel in) {
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public void readFromParcel(Parcel in) {
        try {
            this.Id = in.readInt();
            this.Name = in.readString();
            this.Brand = in.readString();
            this.Model = in.readString();
            this.DeviceTypeName = in.readString();
            int count = in.readInt();
            resetKeyFunctions();
            if (count > 0) {
                this.Functions = new int[count];
                in.readIntArray(this.Functions);
                if (in.dataAvail() > 0) {
                    try {
                        Parcelable[] parcelableArray = in.readParcelableArray(IRFunction.class.getClassLoader());
                        if (parcelableArray != null) {
                            for (Parcelable pin : parcelableArray) {
                                this.KeyFunctions.add((IRFunction) pin);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }

    public String toString() {
        return this.Name;
    }

    public String getName() {
        return this.Name;
    }

    public String getBrand() {
        return this.Brand;
    }

    public String getModel() {
        return this.Model;
    }

    public String getDeviceTypeName() {
        return this.DeviceTypeName;
    }

    public String getVersion() {
        return "";
    }

    public int getId() {
        return this.Id;
    }

    public DeviceTypes getType() {
        return this.DeviceType;
    }

    private void resetKeyFunctions() {
        if (this.KeyFunctions == null) {
            this.KeyFunctions = new ArrayList();
        } else {
            this.KeyFunctions.clear();
        }
    }
}
