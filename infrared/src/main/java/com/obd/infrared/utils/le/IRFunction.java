package com.obd.infrared.utils.le;

import android.os.Parcel;
import android.os.Parcelable;

public class IRFunction implements Parcelable {
    public static final Creator<IRFunction> CREATOR = new FunctionCreator();
    public int Id;
    public boolean IsLearned;
    public Short LearnedCode;
    public String Name;

    static class FunctionCreator implements Creator<IRFunction> {
        FunctionCreator() {
        }

        public IRFunction createFromParcel(Parcel in) {
            return new IRFunction(in);
        }

        public IRFunction[] newArray(int size) {
            return new IRFunction[size];
        }
    }

    public IRFunction() {
        this.Name = "";
        this.Id = 0;
        this.IsLearned = false;
        this.LearnedCode = Short.valueOf((short) 0);
    }

    private IRFunction(Parcel in) {
        this.Name = "";
        this.Id = 0;
        this.IsLearned = false;
        this.LearnedCode = Short.valueOf((short) 0);
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public void readFromParcel(Parcel in) {
        boolean z = true;
        try {
            this.Id = in.readInt();
            this.Name = in.readString();
            if (in.readInt() != 1) {
                z = false;
            }
            this.IsLearned = z;
            this.LearnedCode = Short.valueOf((short) in.readInt());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String toString() {
        return this.Name;
    }
}
