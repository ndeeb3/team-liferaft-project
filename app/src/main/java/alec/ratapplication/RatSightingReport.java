package alec.ratapplication;

import java.util.Date;

/**
 * Created by Sonia Thakur on 10/2/2017.
 */

public class RatSightingReport {
    private String key;
    private double latitude;
    private double longitude;
    private String dateTime;
    private String locationType;
    private int zipcode;
    private String address;
    private String city;
    private  String borough;


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
    public RatSightingReport(String key, double latitude, double longitude, String dateTime,
                             String locationType, int zipcode, String address,
                             String city, String borough) {
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocationType() {
        return locationType;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getBorough() {
        return borough;
    }
    
}
