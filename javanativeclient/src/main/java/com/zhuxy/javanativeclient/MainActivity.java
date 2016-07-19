package com.zhuxy.javanativeclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "NativeClient";
    static {
        System.loadLibrary("BinderClient");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.tvname);
        final TextView getfullname = (TextView)findViewById(R.id.getfullname);
        final EditText editText = (EditText)findViewById(R.id.fullname);
        Button btnget = (Button)findViewById(R.id.btnget);
        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getfullname.setText(getFullName(editText.getText().toString()));

                }catch(Exception e){
                    Log.d(TAG,e.toString());
                }
            }
        });
        try{
            textView.setText(getName());
            editText.setText(textView.getText());
        }catch(Exception e){
            Log.d(TAG,e.toString());
        }
    }


    private native String getName();
    private native String getFullName(String part);
    private native int sum(int a,int b);
}
