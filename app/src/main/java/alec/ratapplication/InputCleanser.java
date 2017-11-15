package alec.ratapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
}
