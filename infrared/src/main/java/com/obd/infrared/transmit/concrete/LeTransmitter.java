package com.obd.infrared.transmit.concrete;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.obd.infrared.log.Logger;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.Transmitter;

/**
 * Created by Andrew on 20.10.2017
 */

public class LeTransmitter extends Transmitter {

    public LeTransmitter(Context context, Logger logger) {
        super(context, logger);
    }

    @Override
    public void transmit(TransmitInfo transmitInfo) {
        if (remoteControl != null) {
            try {
                remoteControl.transmit(transmitInfo.frequency, transmitInfo.pattern);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private ServiceConnection mControlServiceConnection = new ConnectorListener();
    public IControl remoteControl;

    @Override
    public void start() {
        final String UEICONTROLPACKAGE = Build.BRAND.contains("Coolpad") ? "com.uei.quicksetsdk.letvitwo" : "com.uei.quicksetsdk.letv";

        try {
            Intent controlIntent = new Intent(IControl.DESCRIPTOR);
            controlIntent.setClassName(UEICONTROLPACKAGE, "com.uei.control.Service");
            context.bindService(controlIntent, this.mControlServiceConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            context.unbindService(this.mControlServiceConnection);
            remoteControl = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ConnectorListener implements ServiceConnection {
        ConnectorListener() {
        }

        public void onServiceDisconnected(ComponentName name) {
            remoteControl = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteControl = new IControl(service);
        }
    }

    private static class IControl {
        static final String DESCRIPTOR = "com.uei.control.IControl";

        static final int TRANSACTION_TRANSMIT = 18;
        private IBinder controlService = null;

        IControl(IBinder service) {
            this.controlService = service;
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
                    this.controlService.transact(TRANSACTION_TRANSMIT, _data, _reply, 0);
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
    }

}
