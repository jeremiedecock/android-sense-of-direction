package org.jdhp.android.senseofdirection.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jérémie Decock on 25/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // This tag will be used for logging
    private static final String LOG_TAG = DBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "poi.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "*** Create the DB ***");

        // Create a table to hold waitlist data
        final String SQL_CREATE_POI_TABLE = "CREATE TABLE " + PoiContract.PoiEntry.TABLE_NAME + " (" +
                PoiContract.PoiEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PoiContract.PoiEntry.COLUMN_LATITUDE  + " DECIMAL(10,7) NOT NULL, " +
                PoiContract.PoiEntry.COLUMN_LONGITUDE + " DECIMAL(10,7) NOT NULL, " +
                PoiContract.PoiEntry.COLUMN_LABEL + " CHAR(64) NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_POI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(LOG_TAG, "*** Update the DB ***");

        db.execSQL("DROP TABLE IF EXISTS " + PoiContract.PoiEntry.TABLE_NAME + ";");
        onCreate(db);
    }
}
