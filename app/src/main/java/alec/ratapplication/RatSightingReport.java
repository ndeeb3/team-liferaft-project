package alec.ratapplication;

import java.util.Date;

/**
 * Created by Sonia Thakur on 10/2/2017.
 */

public class RatSightingReport {
    private String key;
    private float latitude;
    private float longitude;
    private Date dateTime;
    private LocationType locationType;
    private int zipcode;
    private String address;
    private String city;
    private  Borough borough;

    /**
     *
     * @param key the unique key auto-assigned by the system
     * @param latitude the latitude of the rat sighting
     * @param longitude the longitude of the rat sighting
     * @param dateTime the date and time of the rat sighting
     * @param locationType the location from the LocationType enum
     * @param zipcode the zipcode of the rat sighting
     * @param address the address of the rat sighting
     * @param city the city of the rat sighting
     * @param borough the borough from the Borough enum
     */
    public RatSightingReport(String key, float latitude, float longitude, Date dateTime,
                             LocationType locationType, int zipcode, String address,
                             String city, Borough borough) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
        this.locationType = locationType;
        this.zipcode = zipcode;
        this.address = address;
        this.city = city;
        this.borough = borough;
    }
    public String getKey() {
        return key;
    }
    
}
