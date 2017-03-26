package org.jdhp.android.senseofdirection;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jdhp.android.senseofdirection.data.Poi;
import org.jdhp.android.senseofdirection.data.PoiAdapter;

import java.util.ArrayList;

/*
 * Location doc:
 * - https://developer.android.com/guide/topics/location/index.html
 * - https://developer.android.com/guide/topics/location/strategies.html
 * - https://developer.android.com/training/location/index.html
 * - https://developer.android.com/reference/android/location/LocationManager.html
 * - https://android-developers.googleblog.com/2011/06/deep-dive-into-location.html
 *
 * To drop the database, see:
 * - http://stackoverflow.com/questions/4406067/how-to-delete-sqlite-database-from-android-programmatically
 * - http://stackoverflow.com/questions/5326918/how-to-drop-database-in-sqlite
 * - http://stackoverflow.com/questions/3598452/database-delete-android
 *   When your in MainActivity
 *        this.deleteDatabase("mydata.db");
 *   or when you have a context handle elsewhere
 *        context.deleteDatabase("mydata.db");
 *   or
 *        adb shell
 *        su
 *        cd /data/data
 *        cd <your.application.java.package>
 *        cd databases
 *        rm <your db name>.db
 *
 * On MacOSX: adb is in /Users/${USER}/Library/Android/sdk/platform-tools/adb
 *
 * To access the db with sqlite client:
 *        adb shell
 *        su
 *        cd /data/data
 *        cd <your.application.java.package>
 *        cd databases
 *        sqlite3 <your db name>.db
 *
 *        Main commands are:
 *        - .help
 *        - .databases
 *        - .tables
 *        - .schema
 *        - .fullschema
 *        - .show
 *        - .dbinfo
 */

public class MainActivity extends AppCompatActivity {

    // This tag will be used for logging
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    static int GPS_PERMISSION_REQUEST_ID = 1;            // An integer request code that is used to identify this permission request

    TextView tvDmsCoordinates;
    TextView tvPoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDmsCoordinates = (TextView) findViewById(R.id.tv_dms_coordinates);
        tvPoi = (TextView) findViewById(R.id.tv_poi);

        // Check For Permissions
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission != PackageManager.PERMISSION_GRANTED) {

            /*
             * See:
             * - http://stackoverflow.com/questions/33865445/gps-location-provider-requires-access-fine-location-permission-for-android-6-0
             * - https://developer.android.com/training/permissions/requesting.html
             */

            Log.i(LOG_TAG, "Request the missing permission");

            // Request the missing permissions
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, GPS_PERMISSION_REQUEST_ID);

        } else {

            Log.i(LOG_TAG, "Permission granted");
            requestLocation();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Poi> listOfPoi = PoiAdapter.getAllPoi(this);

        tvPoi.setText("");

        for(Poi poi: listOfPoi) {
            tvPoi.append(poi.getLabel() + "\n");
            tvPoi.append("  POI DMS:" + GpsCoordinatesFormat.formatDMSCoordinates(poi.getLatitude(), poi.getLongitude(), true) + "\n\n");;
        }
    }

    public void startAddPoiActivity(View view) {
        Context context = MainActivity.this;
        Class destinationActivity = AddPoiActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startActivity(startChildActivityIntent);
    }

    public void startRemovePoiActivity(View view) {
        Context context = MainActivity.this;
        Class destinationActivity = RemovePoiActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startActivity(startChildActivityIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == GPS_PERMISSION_REQUEST_ID) {
            // If request is cancelled, the result arrays are empty
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(LOG_TAG, "Permission granted");
                requestLocation();

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission

            }
            return;
        }
    }

    private void requestLocation() {

        // Get the location manager
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPS Service
        if (locationManager != null) {
            Log.i(LOG_TAG, "locationManager != null");

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    new LocationHandler(tvDmsCoordinates, tvPoi, this));

            /*
            // GpsStatusListener
            locationManager.addGpsStatusListener(new GpsStatus.Listener() {

                public void onGpsStatusChanged(int event) {
                    if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        Iterable<GpsSatellite> iterable = gpsStatus.getSatellites();

                        int cpt = 0;
                        for (GpsSatellite satellite : iterable) {
                            System.out.println("- " + satellite.toString());
                            cpt++;
                        }
                        System.out.println(cpt + " satellites found.");
                    }
                }

            });
            */

        } else {
            Log.i(LOG_TAG, "locationManager == null");
            // TODO
        }
    }
}
