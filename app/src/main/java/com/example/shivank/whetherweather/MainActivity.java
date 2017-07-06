package com.example.shivank.whetherweather;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    static TextView placeTextView;
    static TextView temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        placeTextView = (TextView) findViewById(R.id.placeTextView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);

        DownloadTask task = new DownloadTask();
        double lat=30,lon=30;
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            lat = gps.getLatitude();
            lon = gps.getLongitude();
            Toast.makeText(getApplicationContext(), String.format("LATITUDE=%s,LONGITUDE=%s", lat, lon),Toast.LENGTH_LONG).show();
        }else{
            gps.showSettingsAlert();
        }
        Log.i("Jimmy","hi "+gps);
        task.execute("http://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(lat)+"&lon="+String.valueOf(lon)+"&appid=83a584c6fc3ba726bc6517a8a133bbee");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}