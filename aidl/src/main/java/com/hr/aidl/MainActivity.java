package com.hr.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hr.aidl.R;
import com.hr.aidlservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btTest = (Button) findViewById(R.id.bt_test);
        btTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MyConn conn = new MyConn();
        conn.initConnection();
    }

    private class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                Toast.makeText(MainActivity.this, iMyAidlInterface.getName(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iMyAidlInterface = null;
        }

        void initConnection() {
            Intent intent = new Intent();
            intent.setAction("com.hr.aidlservice.MyService");
            intent.setPackage("com.hr.aidlservice");
            bindService(intent, this, BIND_AUTO_CREATE);
        }
    }
}