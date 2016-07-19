/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.helloactivitytest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.binder.example.IDemoAPI;
import android.util.Log;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.*;


/**
 * A minimal "Hello, World!" application.
 */
public class HelloActivity extends Activity {
        private IDemoAPI mService;  
        private static final String TAG = "ClientShow";

    private Context mContext = null;
    private ServiceConnection mIDemoAPIConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // Connecting to a secondary interface is the same as any
            // other interface.
            if(mService!=null) {
                mService = IDemoAPI.Stub.asInterface(service);

                try {
                    Log.d(TAG, "xxx 1+2=" + Integer.toString(mService.sum(1, 2)));
                    Log.d(TAG, "xx Name=" + mService.getName());
                    Log.d(TAG, "xx FullName=" + mService.getFullName("AAABBBCCC"));
                } catch (Exception e) {
                    Log.d(TAG, "ee" + e.toString());
                }
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG,"disc");
            mService = null;
        }
    };

    /**
     * 设备管理类构造函数
     * @param context 应用上下文
     */
    public void setApiService(Context context){
        mContext = context;
        Intent intent = new Intent("com.example.demoapiservice.DemoService");
        //intent.setComponent(new ComponentName("com.example.demoapiservice", "com.example.demoapiservice.DemoService"));
        mContext.bindService(intent,
                mIDemoAPIConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Called with the activity is first created.
     */
    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getDemoAPIService();
            setApiService(this);
            // Set the layout for this activity.  You can find it
            // in res/layout/hello_activity.xml
            View view = getLayoutInflater().inflate(R.layout.hello_activity, null);
            setContentView(view);
            final TextView textView = (TextView)findViewById(R.id.tvname);
        final TextView getfullname = (TextView)findViewById(R.id.getfullname);
        final EditText editText = (EditText)findViewById(R.id.fullname);
        Button btnget = (Button)findViewById(R.id.btnget);
        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getfullname.setText(mService.getFullName(editText.getText().toString()));

                }catch(Exception e){
                    Log.d(TAG,e.toString());
                }
            }
        });
            try{
                textView.setText(mService.getName());
                editText.setText(textView.getText());
            }catch(Exception e){
                Log.d(TAG,e.toString());
            }
        }

    static final String SERVICE_NAME="android.binder.example";

    /*
     * Get binder service
     */
    private void getDemoAPIService()
    {
        IBinder binder=null;
        Log.d(TAG,"getDemoAPIService");
        try{
            //android.os.ServiceManager is hide class, we can not invoke them from SDK. So we have to use reflect to invoke these classes.
            Object object = new Object();
            Method getService = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            Object obj = getService.invoke(object, new Object[]{new String(SERVICE_NAME)});
            binder = (IBinder)obj;
        }catch(Exception e){
            Log.d(TAG, e.toString());
        }
        if(binder != null){
            mService = IDemoAPI.Stub.asInterface(binder);
            Log.d(TAG, "Find binder");
        }
        else
            Log.d(TAG,"Service is null.");
    }

}

