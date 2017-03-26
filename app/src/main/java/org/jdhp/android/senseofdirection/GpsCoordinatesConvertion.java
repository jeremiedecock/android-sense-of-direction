package org.jdhp.android.senseofdirection;

/**
 * Created by jdecock on 26/03/2017.
 */

public class GpsCoordinatesConvertion {

    public static double meterPerLatitudeDegree(double latitudeDeg) {
        /*
         * See:
         * - https://en.wikipedia.org/wiki/Geographic_coordinate_system#Expressing_latitude_and_longitude_as_linear_units
         * - http://gis.stackexchange.com/questions/75528/understanding-terms-in-length-of-degree-formula
         * - https://en.wikipedia.org/wiki/Latitude#Length_of_a_degree_of_latitude
         * - https://en.wikipedia.org/wiki/Longitude#Length_of_a_degree_of_longitude
         *
         * TODO: this is a LOCAL approximation. When this function is used to measure the distance
         *       of 2 points, this approximation is completely wrong if the latitude of these
         *       2 points is very different!!!
         */

        double latitudeRad = Math.toRadians(latitudeDeg);

        double metersPerDeg = 111132.92
                - 559.82 * Math.cos(2. * latitudeRad)
                + 1.175  * Math.cos(4. * latitudeRad)
                - 0.0023 * Math.cos(6. * latitudeRad);


        return metersPerDeg;
    }

    public static double meterPerLongitudeDegree(double latitudeDeg) {
        /*
         * See:
         * - https://en.wikipedia.org/wiki/Geographic_coordinate_system#Expressing_latitude_and_longitude_as_linear_units
         * - http://gis.stackexchange.com/questions/75528/understanding-terms-in-length-of-degree-formula
         * - https://en.wikipedia.org/wiki/Latitude#Length_of_a_degree_of_latitude
         * - https://en.wikipedia.org/wiki/Longitude#Length_of_a_degree_of_longitude
         *
         * TODO: this is a LOCAL approximation. When this function is used to measure the distance
         *       of 2 points, this approximation is completely wrong if the latitude of these
         *       2 points is very different!!!
         */

        double latitudeRad = Math.toRadians(latitudeDeg);

        double metersPerDeg = 111412.84 * Math.cos(latitudeRad)
                - 93.5  * Math.cos(3. * latitudeRad)
                + 0.118 * Math.cos(5. * latitudeRad);

        /*
        // https://en.wikipedia.org/wiki/Longitude#Length_of_a_degree_of_longitude
        double a = 6378137.0;     // m
        double b = 6356752.3142;  // m
        double e_square = (Math.pow(a, 2) - Math.pow(b, 2)) / Math.pow(a, 2);
        double metersPerDeg = (Math.PI * a * Math.cos(latitudeRad)) / (180. * Math.sqrt(1. - e_square * Math.pow(Math.sin(latitudeRad), 2)));
        */

        return metersPerDeg;
    }

}
