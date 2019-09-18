package com.android.internal.telephony.msim;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.android.util.NoExtAPIException;

public interface IIccPhoneBookMSim extends IInterface {

    public static abstract class Stub extends Binder implements IIccPhoneBookMSim {
        private static final String DESCRIPTOR = "com.android.internal.telephony.msim.IIccPhoneBookMSim";
        static final int TRANSACTION_getAdnRecordsInEf = 1;
        static final int TRANSACTION_getAdnRecordsSize = 4;
        static final int TRANSACTION_updateAdnRecordsInEfByIndex = 3;
        static final int TRANSACTION_updateAdnRecordsInEfBySearch = 2;

        private static class Proxy implements IIccPhoneBookMSim {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public boolean updateAdnRecordsInEfBySearch(int efid, String oldTag, String oldPhoneNumber, String newTag, String newPhoneNumber, String pin2, int subscription) throws RemoteException {
                throw new NoExtAPIException("method not supported.");
            }

            public boolean updateAdnRecordsInEfByIndex(int efid, String newTag, String newPhoneNumber, int index, String pin2, int subscription) throws RemoteException {
                throw new NoExtAPIException("method not supported.");
            }

            public int[] getAdnRecordsSize(int efid, int subscription) throws RemoteException {
                throw new NoExtAPIException("method not supported.");
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIccPhoneBookMSim asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IIccPhoneBookMSim)) {
                return new Proxy(obj);
            }
            return (IIccPhoneBookMSim) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = code;
            Parcel parcel = data;
            Parcel parcel2 = reply;
            if (i != 1598968902) {
                switch (i) {
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean _result = updateAdnRecordsInEfBySearch(data.readInt(), data.readString(), data.readString(), data.readString(), data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean _result2 = updateAdnRecordsInEfByIndex(data.readInt(), data.readString(), data.readString(), data.readInt(), data.readString(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result2);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        int[] _result3 = getAdnRecordsSize(data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeIntArray(_result3);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }
    }

    int[] getAdnRecordsSize(int i, int i2) throws RemoteException;

    boolean updateAdnRecordsInEfByIndex(int i, String str, String str2, int i2, String str3, int i3) throws RemoteException;

    boolean updateAdnRecordsInEfBySearch(int i, String str, String str2, String str3, String str4, String str5, int i2) throws RemoteException;
}
