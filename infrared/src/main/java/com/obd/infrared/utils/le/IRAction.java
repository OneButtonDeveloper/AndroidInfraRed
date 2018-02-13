package com.obd.infrared.utils.le;

import android.os.Parcel;
import android.os.Parcelable;

import com.obd.infrared.transmit.concrete.LeTransmitter;

public class IRAction implements Parcelable {
    public static final Creator<IRAction> CREATOR = new C07691();
    public int DeviceId;
    public int Duration;
    public int Function;

    static class C07691 implements Creator<IRAction> {
        C07691() {
        }

        public IRAction createFromParcel(Parcel in) {
            return new IRAction(in);
        }

        public IRAction[] newArray(int size) {
            return new IRAction[size];
        }
    }

    public IRAction(int deviceId, int functionId, int duration) {
        this.DeviceId = 0;
        this.Function = 0;
        this.Duration = 0;
        this.DeviceId = deviceId;
        this.Function = functionId;
        this.Duration = duration;
    }

    private IRAction(Parcel in) {
        this.DeviceId = 0;
        this.Function = 0;
        this.Duration = 0;
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeInt(this.DeviceId);
            dest.writeInt(this.Function);
            dest.writeInt(this.Duration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void readFromParcel(Parcel in) {
        try {
            this.DeviceId = in.readInt();
            this.Function = in.readInt();
            this.Duration = in.readInt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
