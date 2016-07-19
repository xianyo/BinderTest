package com.example.demoapiservice;

import java.lang.reflect.Method;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Service;
import android.binder.example.IDemoAPI;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
     static final String SERVICE_NAME="android.binder.example";
    private static final String TAG = "ServiceShow";
    private BnDemoAPI mBnDemoAPI=null;
    private Button mButton;
    private EditText mEditText;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Object object = new Object();
            Method addService;
            mButton = (Button)findViewById(R.id.btnset);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBnDemoAPI.setmName(mEditText.getText().toString());
                }
            });
            mEditText = (EditText)findViewById(R.id.editname);
            mBnDemoAPI = new BnDemoAPI();
            try {
                mEditText.setText(mBnDemoAPI.getName());
                addService = Class.forName("android.os.ServiceManager").getMethod("addService", String.class, IBinder.class);
                addService.invoke(object, new Object[]{SERVICE_NAME, mBnDemoAPI});
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG,e.toString());
            }
        }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

    @Override
        protected void onDestroy() {
            //this.unbindService(serviceConnection);
            super.onDestroy();
        }

}
