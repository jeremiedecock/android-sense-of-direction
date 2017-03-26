package org.jdhp.android.senseofdirection;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jdhp.android.senseofdirection.data.Poi;
import org.jdhp.android.senseofdirection.data.PoiAdapter;

import java.util.ArrayList;

import static org.jdhp.android.senseofdirection.GpsCoordinatesFormat.formatDMSCoordinates;

/**
 * Created by Jérémie Decock (www.jdhp.org) on 21/03/2017.
 */

public class LocationHandler implements LocationListener {

    /*
     * This tag will be used for logging. It is best practice to use the class's name using
     * getSimpleName as that will greatly help to identify the location from which your logs are
     * being posted.
     */
    private static final String LOG_TAG = LocationHandler.class.getSimpleName();

    private TextView tvDmsCoordinates;
    private TextView tvPoi;

    private Context context;

    public LocationHandler(TextView tvDmsCoordinates, TextView tvPoi, Context context) {
        this.tvDmsCoordinates = tvDmsCoordinates;
        this.tvPoi = tvPoi;
        this.context = context;
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v(LOG_TAG, "StatusChanged");
        // TODO
    }

    public void onProviderEnabled(String provider) {
        // GPS ON
        Log.v(LOG_TAG, "ProviderEnabled (GPS ON)");

        //String toastMessage = "GPS turned ON";
        //Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        // TODO
    }

    public void onProviderDisabled(String provider) {
        // GPS OFF
        Log.v(LOG_TAG, "ProviderDisabled (GPS OFF)");

        //String toastMessage = "GPS turned ON";
        //Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        // TODO
    }

    public void onLocationChanged(Location location) {
        // GPS position updated
        Log.v(LOG_TAG, "LocationChanged (GPS position updated)");

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        String dmsCoordinatesString = formatDMSCoordinates(latitude, longitude);
        Log.v(LOG_TAG, dmsCoordinatesString);
        this.tvDmsCoordinates.setText(dmsCoordinatesString);

        //String gpsCoordinatesString =  "Latitude: " + formatLatitude(latitude) + "\n";
        //gpsCoordinatesString += "Longitude: " + formatLongitude(longitude) + "\n";
        //gpsCoordinatesString += "Altitude: " + formatAltitude(altitude) + "\n";
        //Log.v(LOG_TAG, gpsCoordinatesString);

        ArrayList<Poi> listOfPoi = PoiAdapter.getAllPoi(this.context);

        tvPoi.setText("");

        for(Poi poi: listOfPoi) {
            double relativeLatitude = poi.getLatitude() - latitude;
            double relativeLongitude = poi.getLongitude() - longitude;

            Log.v(LOG_TAG, "Latitude: " + latitude);
            Log.v(LOG_TAG, "Meter per degree (latitude): " + GpsCoordinatesConvertion.meterPerLatitudeDegree(latitude));
            Log.v(LOG_TAG, "Meter per degree (longitude): " + GpsCoordinatesConvertion.meterPerLongitudeDegree(latitude));

            double relLatitudeMeter = relativeLatitude * GpsCoordinatesConvertion.meterPerLatitudeDegree(latitude);
            double relLongitudeMeter = relativeLongitude * GpsCoordinatesConvertion.meterPerLongitudeDegree(latitude);
            String latitudeMeterSuffix = (relLatitudeMeter < 0) ? "south" : "north";
            String longitudeMeterSuffix = (relLongitudeMeter < 0) ? "west" : "est";

            double distMeter = Math.sqrt(Math.pow(relLatitudeMeter, 2) + Math.pow(relLongitudeMeter, 2));

            double relAngle = Math.toDegrees(Math.atan2(relLatitudeMeter, relLongitudeMeter));
            relAngle = (360. - relAngle) % 360;

            String relLatitudeMeterStr = String.format("%.1f", Math.abs(relLatitudeMeter)) + "m " + latitudeMeterSuffix;
            String relLongitudeMeterStr = String.format("%.1f", Math.abs(relLongitudeMeter)) + "m " + longitudeMeterSuffix;
            String distMeterStr = String.format("%.1f", distMeter) + "m";
            String relAngleStr = String.format("%.2f", relAngle) + "°";

            tvPoi.append(poi.getLabel() + "\n");
            tvPoi.append("  POI DMS: " + GpsCoordinatesFormat.formatDMSCoordinates(poi.getLatitude(), poi.getLongitude(), true) + "\n");
            tvPoi.append("  Rel DMS: " + GpsCoordinatesFormat.formatDMSCoordinates(relativeLatitude, relativeLongitude, true) + "\n");
            tvPoi.append("  Rel Pos: " + relLatitudeMeterStr + "  " + relLongitudeMeterStr + "\n");
            tvPoi.append("  Dist:    " + distMeterStr + "\n");
            tvPoi.append("  Dir:     " + relAngleStr + "\n\n");
        }
    }
}
