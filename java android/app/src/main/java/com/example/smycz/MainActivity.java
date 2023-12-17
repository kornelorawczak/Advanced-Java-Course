package com.example.smycz;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.telephony.SmsManager;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.smycz.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private boolean smsSend = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View fview = view;
                Snackbar.make(view, " GPS ERROR ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED

                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS}, 1233);
                    }
                }

                LocationManager mLocationManager = (LocationManager)
                        getSystemService(view.getContext().LOCATION_SERVICE);

                try {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000l, (float) 0.1, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if (location != null) {
                                Snackbar.make(fview, "wysłałem koordynaty :" + location.toString(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                if (!smsSend) {
                                    String[] numery = {"+48530712171", "+48508040172"};
                                    for (String n : numery) {
                                        SmsManager.getDefault().sendTextMessage(n, null, "Jestem Tu :https://www.google.com/maps/search/?api=1&query=" + location.getLatitude() + "," + location.getLongitude(), null, null);
                                    }
                                    smsSend = true;
                                }
                            }
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
                    });
                    Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    long GPSLocationTime = 0;
                    if (null != locationGPS) {
                        GPSLocationTime = locationGPS.getTime();
                    }

                    long NetLocationTime = 0;

                    if (null != locationNet) {
                        NetLocationTime = locationNet.getTime();
                    }


//                    if ( 0 < GPSLocationTime - NetLocationTime ) {
                    Snackbar.make(view, "wysłałem koordynaty początkowe :" + locationGPS, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (SecurityException e) {
                    Snackbar.make(view, e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}