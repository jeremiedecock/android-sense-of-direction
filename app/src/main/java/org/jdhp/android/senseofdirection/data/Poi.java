package org.jdhp.android.senseofdirection.data;

/**
 * Created by jdecock on 25/03/2017.
 */

public class Poi {

    private int key;
    private double latitude;
    private double longitude;
    private String label;

    public Poi(double latitude, double longitude, String label) {
        this.key = -1;
        this.latitude = latitude;
        this.longitude = longitude;
        this.label = label;
    }

    public Poi(double latitude, double longitude, String label, int key) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
        this.label = label;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLabel() {
        return label;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Poi{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", label='" + label + '\'' +
                '}';
    }
}
