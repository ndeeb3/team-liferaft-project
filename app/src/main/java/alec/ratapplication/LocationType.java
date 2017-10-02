package alec.ratapplication;

/**
 * Created by Sonia Thakur on 10/2/2017.
 */

public enum LocationType {
    FAMILYDWELLING ("1-2 Family Dwelling"),
    APTBUILDING ("3+ Family Apt. Building"),
    MIXEDBUILDING ("3+ Family Mixed Use Building"),
    COMMERCIAL ("Commercial Building"),
    VACANTLOT ("Vacant Lot"),
    CONSTRUCTION ("Construction Site"),
    HOSPITAL ("Hospital"),
    SEWER ("Catch Basin/Sewer");

    private final String value;

    LocationType(final String value) {
            this.value = value;
    }

    public String getValue() { return value; }
}
