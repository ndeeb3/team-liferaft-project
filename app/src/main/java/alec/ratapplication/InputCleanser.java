package alec.ratapplication;

import java.text.DateFormat;
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
}
