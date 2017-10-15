package alec.ratapplication;

import android.util.Log;

/**
 * Created by Sonia Thakur on 10/2/2017. DO NOT USE
 */

public enum LocationType2 {

    FAMILYDWELLING ("1-2 Family Dwelling"),
    APTBUILDING ("3+ Family Apt. Building"),
    MIXEDBUILDING ("3+ Family Mixed Use Building"),
    COMMERCIAL ("Commercial Building"),
    VACANTLOT ("Vacant Lot"),
    CONSTRUCTION ("Construction Site"),
    HOSPITAL ("Hospital"),
    SEWER ("Catch Basin/Sewer"),
    PUBLICSTAIRS ("Public Stairs"),
    VACANTBUILDING ("Vacant Building"),
    PARKING ("Parking Lot/Garage"),
    SMALLFAMILYMIXED ("1-2 Family Mixed Use Building"),
    PUBLICBUILDING("Public Garden"),
    OFFICEBUILDING("Office Building"),
    GOVERNMENTBUILDING("Government Building"),
    SCHOOL("School/Pre-School"),
    DayCare("Day Care/Nursery"),
    SINGLEROOM("Single Room Occupancy (SRO)"),
    SUMMERCAMP("Summer Camp"),
    OTHER ("Other");

    private final String value;

    LocationType2(String value) {
            this.value = value;
    }

    /**
     *
     * @param locationType the string representation of the location type ex "Public Garden". Converts entry in database to LocationType
     * @return
     */
    public static LocationType2 getLocationType(String locationType) {
        for (LocationType2 c : values()) {
            if (c.value.equals(locationType)) {
                return c;
            }
        }
        // either throw the IAE or return null, your choice.
        Log.d("location type ERROR", locationType);
        throw new IllegalArgumentException(locationType);
    }
    public String getValue() { return value; }

}
