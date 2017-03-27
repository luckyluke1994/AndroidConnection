package com.example.maidaidien.androidconnection;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class WIFIActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private BroadcastReceiver wifiReciever;
    private ArrayAdapter adapter;

    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        ListView listview = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1);

        listview.setAdapter(adapter);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        wifiReciever = new WiFiScanReceiver();
    }

    public void onToggleClicked(View view) {

        adapter.clear();

        ToggleButton toggleButton = (ToggleButton) view;

        if (wifiManager == null) {
            // Device does not support Wi-Fi
            Toast.makeText(getApplicationContext(), "Oop! Your device does not support Wi-Fi",
                    Toast.LENGTH_SHORT).show();
            toggleButton.setChecked(false);

        } else {
            if (toggleButton.isChecked()) { // To turn on Wi-Fi
                if (!wifiManager.isWifiEnabled()) {

                    Toast.makeText(getApplicationContext(), "Wi-Fi is turned on." +
                                    "\n" + "Scanning for access points...",
                            Toast.LENGTH_SHORT).show();

                    wifiManager.setWifiEnabled(true);

                } else {
                    Toast.makeText(getApplicationContext(), "Wi-Fi is already turned on." +
                                    "\n" + "Scanning for access points...",
                            Toast.LENGTH_SHORT).show();
                }
                wifiManager.startScan();

            } else { // To turn off Wi-Fi
                Toast.makeText(getApplicationContext(), "Wi-Fi is turned off.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    class WiFiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
                List<ScanResult> wifiScanResultList = wifiManager.getScanResults();
                for(int i = 0; i < wifiScanResultList.size(); i++){
                    ScanResult accessPoint = wifiScanResultList.get(i);
                    String listItem = accessPoint.SSID+", "+accessPoint.BSSID;
                    adapter.add(listItem);
                }
            }
        }
    }

    protected void onResume() {
        super.onResume();
        // Register the BroadcastReceiver for SCAN_RESULTS_AVAILABLE_ACTION
        IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiReciever, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiReciever);
    }
}
