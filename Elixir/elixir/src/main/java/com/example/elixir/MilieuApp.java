package com.example.elixir;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by manojthakur on 3/1/15.
 */
public class MilieuApp extends Application {
    public static String phone = "";
    public static Location loc = null;
    @Override
    public void onCreate() {
        super.onCreate();

        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        phone = tMgr.getLine1Number().trim();
        Log.i("INIT", "Got Phone Number");

        LocationListener loc = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                MilieuApp.loc = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc);
    }
}
