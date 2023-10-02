package com.cloudjiashhub.accounts.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudjiashhub.accounts.R;

import java.util.List;

public class CheckWifi extends AppCompatActivity {

    ImageView imageArrow;
    TextView textSpeed , text_about_speed;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_wifi);

        textSpeed = (TextView)findViewById(R.id.speed_wifi);
        text_about_speed = (TextView)findViewById(R.id.about_speed_wifi);

        ConnectivityManager connManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            wifiInfo.getSSID();
            String name = networkInfo.getExtraInfo();
            String ssid = "\"" + wifiInfo.getSSID() + "\"";

            // Level of a Scan Result
            List<ScanResult> wifiList = wifiManager.getScanResults();
            for (ScanResult scanResult : wifiList) {
                int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
                System.out.println("Level is " + level + " out of 5");
            }

// Level of current connection
            int rssi = wifiManager.getConnectionInfo().getRssi();
            int level = WifiManager.calculateSignalLevel(rssi, 5);
            System.out.println("Level is " + level + " out of 5");

        if (level >= -50) {
            Toast.makeText(CheckWifi.this,"Best signal",Toast.LENGTH_LONG).show();
            textSpeed.setText("أشارة قوية جدا"+level+"dBm");
            text_about_speed.setText("hgh");
            //Best signal
        } else if (level >= -70) {
            Toast.makeText(CheckWifi.this,"Good signal  ",Toast.LENGTH_LONG).show();
            //Good signal
        } else if (level >= -80) {
            Toast.makeText(CheckWifi.this,"Low signal",Toast.LENGTH_LONG).show();
            //Low signal
        } else if (level >= -100) {
            Toast.makeText(CheckWifi.this,"Very weak signal",Toast.LENGTH_LONG).show();
            //Very weak signal
        } else {
            //Too low signal
            Toast.makeText(CheckWifi.this,"Too low signal",Toast.LENGTH_LONG).show();

        }

        }

    }
}