package alec.ratapplication;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Accesses the database to retrieve current rat sightings
 */
public class RatSightingAccessor {

    public static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    private ChildEventListener dataListener;
    private String TAG = "DEBUG";
    public static List<String> snapshots = new LinkedList<>();
    public RatSightingAccessor() {
        Query recentPostsQuery = mDatabase;
    }

    /**
     * Converts a String representation of a date into
     * a Date object
     *
     * @param strDateTime A String representing a date
     * @return The Date represented by the String
     */
    public static Date convertStringToDate(String strDateTime) {
        Date dateTime = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        try {
            dateTime = df.parse(strDateTime);
        } catch (Exception e) {
            Log.e("Warn", "The dateTime is in the wrong format in the database: ", e);
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
    public static String convertDateToString(Date dateTime) throws NullPointerException {
        if (dateTime == null) {
            throw new NullPointerException("DATETIME IS A NULL");
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

        return df.format(dateTime);
    }
    /**
     * Method that loads the sightings into the data model from the database
     * as firebase does this asyncronusly, will not stop the progress of the program
     */
    public static void loadSightings() {
        //DatabaseReference reports = mDatabase.child("reports");
        Log.d("DEBUG", mDatabase.toString());
        if(DataModel.getInstance().reports.size() == 0) {
            //limit to first limits the number of entries the query will access
            Query tempquery = mDatabase.child("reports").limitToLast(50);
            tempquery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (/*dataSnapshot.child("Incident Address").getValue() != null &&*/ dataSnapshot.child("Longitude").getValue().equals("0.0")) {
                        mDatabase.child(dataSnapshot.getKey()).removeValue();
                        return;
                    }

                    Log.d("DEBUG", dataSnapshot.toString());
                    String key = (String) dataSnapshot.child("Unique Key").getValue();


                    double lat = 0;
                    if (!((String) dataSnapshot.child("Latitude").getValue()).equals("")) {
                        lat = Double.valueOf((String) dataSnapshot.child("Latitude").getValue());
                    }

                    double lon = 0;
                    if (!((String) dataSnapshot.child("Longitude").getValue()).equals("")) {
                        lon = Double.valueOf((String) dataSnapshot.child("Longitude").getValue());
                    }

                    Date dateTime = null;
                    if (!(dataSnapshot.child("Created Date").getValue()).toString().equals("")) {
                        String strDateTime = dataSnapshot.child("Created Date").getValue().toString();
                        dateTime = RatSightingAccessor.convertStringToDate(strDateTime);
                    }

                    String loc = "NO LOCATION GIVEN";
                    if (!((String) dataSnapshot.child("Location Type").getValue()).equals("")) {
                        loc = (String) dataSnapshot.child("Location Type").getValue();
                    }

                    int zip = 0;
                    if (!((String) dataSnapshot.child("Incident Zip").getValue()).equals("")
                            && !((String) dataSnapshot.child("Incident Zip").getValue()).equals("N/A")) {
                        zip = Integer.valueOf((String) dataSnapshot.child("Incident Zip").getValue());
                    }

                    String address = "NO ADDRESS GIVEN";
                    if (!((String) dataSnapshot.child("Incident Address").getValue()).equals("")) {
                        address = (String) dataSnapshot.child("Incident Address").getValue();
                    }
                    //Log.d("DEBUG", "Address:" + dataSnapshot.child("Incident Address").getValue());

                    String city = "NO CITY GIVEN";
                    if (!((String) dataSnapshot.child("City").getValue()).equals("")) {
                        city = (String) dataSnapshot.child("City").getValue();
                    }

                    String borough = "NO BOROUGH GIVEN";
                    if (!((String) dataSnapshot.child("Borough").getValue()).equals("")) {
                        borough = (String) dataSnapshot.child("Borough").getValue();
                    }

                    RatSightingReport newSighting = new RatSightingReport(key, lat, lon, dateTime, loc, zip, address, city, borough);
                    DataModel.getInstance().reports.add(newSighting);
                    DataModel.getInstance().newKey = Integer.valueOf(key) + 1;

                    /*DatabaseReference reports = mDatabase.child("reports");
                    DatabaseReference movedNode = reports.push();
                    movedNode.child("Longitude").setValue(Double.toString(newSighting.getLongitude()));
                    movedNode.child("Latitude").setValue(Double.toString(newSighting.getLatitude()));
                    movedNode.child("Unique Key").setValue(key);
                    movedNode.child("Incident Address").setValue(newSighting.getAddress());
                    movedNode.child("Location Type").setValue(newSighting.getLocationType());
                    movedNode.child("Incident Zip").setValue(Integer.toString(newSighting.getZipcode()));
                    movedNode.child("City").setValue(newSighting.getCity());
                    movedNode.child("Borough").setValue(newSighting.getBorough());
                    movedNode.child("Created Date").setValue(RatSightingAccessor.convertDateToString(newSighting.getDateTime()));
                    //Log.d("DEBUG", mDatabase.child("reports").toString());

                    mDatabase.child(dataSnapshot.getKey()).removeValue();*/

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("DEBUG", "ERROR", databaseError.toException());
                }
            });
        }
    }


    /**
     * Filters the current sightings by time and location
     *
     * @param locationType can Filter results by location Type Enum
     * @param startDate dateRange start >
     * @param endDate stateRange end <=
     * @param borough  can Filter results by
     * @return list of rat Sightings report that match filter
     */
    public static ArrayList<RatSightingReport> filterSightings(LocationType locationType,
                                               Date startDate,
                                               Date endDate,
                                               Borough borough){

       // Query tempQuery = mDatabase;//.limitToFirst(100);
        //tempQuery.addChildEventListener(dataListener);
        ArrayList<RatSightingReport> reports = new ArrayList<>();
        for (RatSightingReport report: DataModel.getInstance().reports) {
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
     *
     * @return a list of rat report sightings. Alec will replace the code we put here
     */
    /*public RatSightingReport[] getAllSightings() {
        LinkedList<RatSightingReport> sightings = new LinkedList<>();
        return new RatSightingReport[0];
    }-*


    /**
     * This method inputs a sighting into the database
     * @param sighting rat sighting to be put into the database
     */
    public void inputSighting(RatSightingReport sighting) {
        DatabaseReference newRef = mDatabase.child("reports").push();
        //newRef.setValue(sighting);
        newRef.child("Longitude").setValue(Double.toString(sighting.getLongitude()));
        newRef.child("Latitude").setValue(Double.toString(sighting.getLatitude()));
        newRef.child("Unique Key").setValue(Integer.toString(DataModel.getInstance().newKey));
        newRef.child("Incident Address").setValue(sighting.getAddress());
        newRef.child("Location Type").setValue(sighting.getLocationType());
        newRef.child("Incident Zip").setValue(Integer.toString(sighting.getZipcode()));
        newRef.child("City").setValue(sighting.getCity());
        newRef.child("Borough").setValue(sighting.getBorough());
        newRef.child("Created Date").setValue(RatSightingAccessor.convertDateToString(sighting.getDateTime()));
        Log.e("Error", "LOOOOOOOOOOK" + sighting.getDateTime().toString());
    }

    /**
     * Generates a new user entry and passes it to the database
     * @param user user to be added to the database
     */
    public void createAccount(User user) {
        // Todo this code is temporary
        Log.d("DEBUG", "Inside accessor" + user.getContactInfo());
        DataModel.getInstance().userList.add(user);
    }
    /**
     * Gets the list of users from the data model
     *
     * @return an array of the Users
     */
    public ArrayList<User> getUsers(){
        // Todo this code is temporary
        return DataModel.getInstance().userList;
       // mDatabase.
    }
}
