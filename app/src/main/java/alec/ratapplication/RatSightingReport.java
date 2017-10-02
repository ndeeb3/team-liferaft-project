package alec.ratapplication;

import java.util.Date;

/**
 * Created by Sonia Thakur on 10/2/2017.
 */

public class RatSightingReport {
    private String key;
    private int latitude;
    private int longitude;
    private Date dateTime;
    private LocationType locationType;
    private int zipcode;
    private String address;
    private String city;
    public enum Borough {
        MANHATTAN, STATENISLAND, QUEENS, BROOKLYN, BRONX
    }
    private  Borough borough;

    public RatSightingReport(String key, int latitude, int longitude, Date dateTime,
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

    
}
