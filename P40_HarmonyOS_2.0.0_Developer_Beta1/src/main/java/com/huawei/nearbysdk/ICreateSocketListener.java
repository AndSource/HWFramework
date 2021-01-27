package com.huawei.nearbysdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.nearbysdk.InternalNearbySocket;

public interface ICreateSocketListener extends IInterface {
    void onCreateFail(int i) throws RemoteException;

    void onCreateSuccess(InternalNearbySocket internalNearbySocket) throws RemoteException;

    void onHwShareIRemote(InternalNearbySocket internalNearbySocket) throws RemoteException;

    public static abstract class Stub extends Binder implements ICreateSocketListener {
        private static final String DESCRIPTOR = "com.huawei.nearbysdk.ICreateSocketListener";
        static final int TRANSACTION_onCreateFail = 2;
        static final int TRANSACTION_onCreateSuccess = 1;
        static final int TRANSACTION_onHwShareIRemote = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICreateSocketListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICreateSocketListener)) {
                return new Proxy(obj);
            }
            return (ICreateSocketListener) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onCreateSuccess(InternalNearbySocket.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onCreateFail(data.readInt());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onHwShareIRemote(InternalNearbySocket.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICreateSocketListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.huawei.nearbysdk.ICreateSocketListener
            public void onCreateSuccess(InternalNearbySocket socket) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (socket != null) {
                        iBinder = socket.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.huawei.nearbysdk.ICreateSocketListener
            public void onCreateFail(int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.huawei.nearbysdk.ICreateSocketListener
            public void onHwShareIRemote(InternalNearbySocket socket) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (socket != null) {
                        iBinder = socket.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
