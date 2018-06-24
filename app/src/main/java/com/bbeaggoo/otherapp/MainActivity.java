package com.bbeaggoo.otherapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "OtherApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        ComponentName compo = new ComponentName("com.bbeaggoo.myapplication", "com.bbeaggoo.myapplication.BackgroundMonitorService");
        Intent intentForOb = new Intent();
        switch (v.getId()) {
            case R.id.startService:
                intentForOb.setComponent(compo);
                startService(intentForOb);

                Log.i(TAG, "Start BackgroundMonitorService Service");
                break;
            case R.id.stopService:
                intentForOb.setComponent(compo);
                stopService(intentForOb);

                Log.i(TAG, "End BackgroundMonitorService Service");
                break;
        }
    }
}
