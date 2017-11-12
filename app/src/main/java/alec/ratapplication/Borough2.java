package alec.ratapplication;

import android.util.Log;

/**
 * An enum representing the boroughs of NYC
 * in which a rat sighting may occur.
 *
 * Created by Sonia Thakur on 10/2/2017.
 */

public enum Borough2 {
    MANHATTAN, STATENISLAND, QUEENS, BROOKLYN, BRONX;

    /**
     * Converts database input to Borough
     * @param borough the string representation of the borough type ex "Staten Island".
     * @return Borough representation of data from database
     */
    public static Borough2 getBorough(String borough) {
        borough = borough.replace(" ", "");
        for (Borough2 c : values()) {
            if (c.equals(borough)) {
                return c;
            }
        }
        // either throw the IAE or return null, your choice.
        Log.d("location type ERROR", borough);
        throw new IllegalArgumentException(borough);
    }


}
