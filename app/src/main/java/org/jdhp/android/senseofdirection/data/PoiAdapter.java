package org.jdhp.android.senseofdirection.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jdecock on 25/03/2017.
 */

public class PoiAdapter {

    // This tag will be used for logging
    private static final String LOG_TAG = PoiAdapter.class.getSimpleName();

    public static boolean addPoi(Poi poi, Context context) {
        Log.d(LOG_TAG, "Add " + poi.toString());

        ContentValues cv = new ContentValues();
        cv.put(PoiContract.PoiEntry.COLUMN_LATITUDE, poi.getLatitude());
        cv.put(PoiContract.PoiEntry.COLUMN_LONGITUDE, poi.getLongitude());
        cv.put(PoiContract.PoiEntry.COLUMN_LABEL, poi.getLabel());

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long res = db.insert(PoiContract.PoiEntry.TABLE_NAME, null, cv);

        db.close();

        return res != -1;
    }

    public static boolean removePoi(int key, Context context) {
        Log.d(LOG_TAG, "Remove POI ID " + Integer.toString(key));

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int res = db.delete(PoiContract.PoiEntry.TABLE_NAME,
                PoiContract.PoiEntry._ID + " = " + key,
                null);

        db.close();

        return res != 0;
    }

    public static ArrayList<Poi> getAllPoi(Context context) {
        ArrayList<Poi> listOfPoi = new ArrayList<Poi>();

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(PoiContract.PoiEntry.TABLE_NAME,
                new String[] {PoiContract.PoiEntry._ID, PoiContract.PoiEntry.COLUMN_LATITUDE, PoiContract.PoiEntry.COLUMN_LONGITUDE, PoiContract.PoiEntry.COLUMN_LABEL},
                null, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                int key = cursor.getInt(0);
                double latitude = cursor.getDouble(1);
                double longitude = cursor.getDouble(2);
                String label = cursor.getString(3);

                listOfPoi.add(new Poi(latitude, longitude, label, key));
            } while (cursor.moveToNext());
        }

        db.close();

        return listOfPoi;
    }

    public static String getAllPoiStr(Context context) {
        String str = "";

        ArrayList<Poi> listOfPoi = getAllPoi(context);

        for(Poi poi: listOfPoi) {
            str += poi.toString() + "\n\n";
        }

        return str;
    }
}