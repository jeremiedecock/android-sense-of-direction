package org.jdhp.android.senseofdirection.data;

/**
 * Created by jdecock on 25/03/2017.
 */


import android.provider.BaseColumns;

public class PoiContract {

    public static final class PoiEntry implements BaseColumns {
        public static final String TABLE_NAME = "POI";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_LABEL = "label";
    }

}
