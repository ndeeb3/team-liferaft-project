package alec.ratapplication;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class InputCleanser {
    /**
     * Private zipcode validation method that checks the zipcode string against a zipcode-styled regex
     * @param zipcode the zipcode string that is being validated
     * @return true if the string is a valid zipcode, false otherwise
     */
    static boolean isZipValid(String zipcode) {
        //Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return zipcode.matches("^[0-9]{5}(?:-[0-9]{4})?$");
    }

    /**
     *
     * @param time
     * @return
     */
    static boolean isTimeValid2(String time) {
        try{
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
            df.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Filters the current sightings by time and location
     *
     * @param locationType can Filter results by location Type Enum
     * @param startDate dateRange start >
     * @param endDate stateRange end <
     * @param borough  can Filter results by
     * @return list of rat Sightings report that match filter
     */
    public static ArrayList<RatSightingReport> filterSightings(List<RatSightingReport> parentList,
                                                               String locationType,
                                                               Date startDate,
                                                               Date endDate,
                                                               String borough){

        // Query tempQuery = mDatabase;//.limitToFirst(100);
        //tempQuery.addChildEventListener(dataListener);
        ArrayList<RatSightingReport> reports = new ArrayList<>();
        for (RatSightingReport report: parentList) {
            if ((locationType != null) && !report.getLocationType().equals(locationType)) {
                continue;
            }
            if ((startDate != null) && (!report.getDateTime().after(startDate))){
                continue;
            }
            if ((endDate != null) && (!report.getDateTime().before(endDate))) {
                continue;
            }
            if ((borough != null) && (!report.getBorough().equals(borough))) {
                continue;
            }
            reports.add(report);

        }
        return reports;
    }

    /**
     * Converts a String representation of a date into
     * a Date object
     *
     * @param strDateTime A String representing a date
     * @return The Date represented by the String, null if there is a parsing error
     */
    static Date convertStringToDate(String strDateTime) {
        if (strDateTime == null) {
            throw new NullPointerException("strDateTime is null");
        }
        Date dateTime = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        try {
            dateTime = df.parse(strDateTime);
        } catch (ParseException e) {
            Log.e("DEBUG", "The dateTime is in the wrong format in the database: ", e);
            throw new IllegalArgumentException("strDateTime Threw a parseException");

        }
        return dateTime;
    }

    /**
     * Returns a String representation of a Date
     *
     * @param dateTime The Date to be converted
     * @return The Date represented by a String
     * @throws NullPointerException when entered Date is null
     */
    static String convertDateToString(Date dateTime) throws NullPointerException {
        if (dateTime == null) {
            throw new NullPointerException("dateTime is null");
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);

        return df.format(dateTime);
    }

}
