package com.example.demoapiservice;

import android.binder.example.IDemoAPI;
import android.os.RemoteException;
import android.util.Log;

public class BnDemoAPI extends IDemoAPI.Stub {
    private final static String TAG = "BnDemoAPI";
    private  String mName = "BnDemoAPI";

    @Override
    public String getName() throws RemoteException {
        Log.d(TAG,"getName "+ mName);
        return mName;
    }
    
    @Override
    public String getFullName(String part) throws RemoteException {
        StringBuilder builder = new StringBuilder("DemoAPI+");
        builder.append(part);
        Log.d(TAG,"getFullName part" + part + "build "+builder.toString());
        return builder.toString();
    }
    
    @Override
    public int sum(int a, int b) throws RemoteException {
        Log.d(TAG,"sum "+ a +" "+b);
        return a + b;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
