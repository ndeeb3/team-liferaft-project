package alec.ratapplication;

import android.util.Log;

/**
 * Created by Sonia Thakur on 10/2/2017.
 */

public enum Borough2 {
    MANHATTAN, STATENISLAND, QUEENS, BROOKLYN, BRONX;

    /**
     *
     * @param borough the string representation of the borough type ex "Staten Island". Converts database input to Borough
     * @return
     */
    public static Borough2 getBorough(String borough) {
        borough.replace(" ", "");
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
