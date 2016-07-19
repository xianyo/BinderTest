package com.example.demoapiservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Service;
import android.util.Log;

public class DemoService extends Service{

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.d("DemoService","onBind");
        return new BnDemoAPI();
    }
}