package com.example.maidaidien.androidconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getBluetoothActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
        startActivity(intent);
    }

    public void getWifiActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), WIFIActivity.class);
        startActivity(intent);
    }
}
