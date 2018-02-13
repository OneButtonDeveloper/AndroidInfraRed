package com.obd.infrared.utils.le;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class IControl {
    public static final String DESCRIPTOR = "com.uei.control.IControl";

    private IBinder controlService = null;

    public IControl(IBinder service) {
        this.controlService = service;
    }

    public Device[] getDevices() throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            this.controlService.transact(8, _data, _reply, 0);
            _reply.readException();
            Device[] _result = (Device[]) _reply.createTypedArray(Device.CREATOR);
            return _result;
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }

    public int transmit(int carrierFrequency, int[] pattern) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        int _result = 1;
        if (this.controlService != null) {
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeInt(carrierFrequency);
                _data.writeIntArray(pattern);
                this.controlService.transact(18, _data, _reply, 0);
                _reply.readException();
                _result = _reply.readInt();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }
        return _result;
    }

    public String[] getAllFunctionLabelsByDevice(int deviceId, int[] functionIds) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeInt(deviceId);
            _data.writeIntArray(functionIds);
            this.controlService.transact(15, _data, _reply, 0);
            _reply.readException();
            String[] _result = _reply.createStringArray();
            return _result;
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }

    public int sendIR(IRAction action) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            if (action != null) {
                _data.writeInt(1);
                action.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            this.controlService.transact(1, _data, _reply, 0);
            _reply.readException();
            int _result = _reply.readInt();
            return _result;
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }
}
