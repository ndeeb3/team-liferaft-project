package alec.ratapplication;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A report submitted by a user indicating when
 * and where a rat was seen.
 *
 * Created by Sonia Thakur on 10/2/2017.
 */
public class RatSightingReport implements Serializable {
    private String key;
    private double latitude;
    private double longitude;
    private Date dateTime;
    private String locationType;
    private int zipcode;
    private String address;
    private String city;
    private  String borough;

    /**
     * Constructs a new report
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
    public RatSightingReport(String key, double latitude, double longitude, Date dateTime,
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

    public RatSightingReport() {
        this(null, 0, 0, null, null, 0, null, null, null);
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

    public Date getDateTime() {
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String toString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        return "Key: " + key + "\nTime: " + df.format(dateTime) + "\nAddress: " + address;
    }

}
