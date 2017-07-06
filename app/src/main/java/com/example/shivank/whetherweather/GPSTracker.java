package com.example.shivank.whetherweather;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Shivank on 06-07-2017.
 */

public class GPSTracker extends Service implements LocationListener {
    private Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;
    protected LocationManager locationManager;
    double latitude, longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 METRES
    private static final long MIN_TIME_CHANGE_FOR_UPDATES = 1000 * 60 * 1; // 1 MINUTE

    public GPSTracker(Context context) {
        //Log.i("context"+context,"context");
        //Log.d(","+context,"context"+context);
        mContext = context;
        getLocation();
    }

    public Location getLocation() {
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
        } else {
            this.canGetLocation = true;
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_CHANGE_FOR_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("network","network");
                if(locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
            if(isGPSEnabled && location==null){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_CHANGE_FOR_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                Log.d("GPS enabled","GPS enabled");
                if(locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        }
        return location;
    }
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }
    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to Settings?");
        alertDialog.setPositiveButton("Settings",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int i){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }
    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
