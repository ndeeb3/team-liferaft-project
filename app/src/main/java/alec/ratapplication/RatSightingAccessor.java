package alec.ratapplication;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.ArrayList;
import java.util.Date;

/**
 * Accesses the database to retrieve current rat sightings
 */
 class RatSightingAccessor {

    private final static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    //private ChildEventListener dataListener;
    RatSightingAccessor() {
        //Query recentPostsQuery = mDatabase;
    }

    /*/**
     * Converts a String representation of a date into
     * a Date object
     *
     * @param strDateTime A String representing a date
     * @return The Date represented by the String, null if there is a parsing error
     */
    /*static Date convertStringToDate(String strDateTime) {
        if (strDateTime == null) {
            throw new NullPointerException("strDateTime is null");
        }
        Date dateTime = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        try {
            dateTime = df.parse(strDateTime);
        } catch (ParseException e) {
            Log.e("Warn", "The dateTime is in the wrong format in the database: ", e);
            return null;

        }
        return dateTime;
    }*/

    /*/**
     * Returns a String representation of a Date
     *
     * @param dateTime The Date to be converted
     * @return The Date represented by a String
     * @throws NullPointerException when entered Date is null
     */
    /*private static String convertDateToString(Date dateTime) throws NullPointerException {
        if (dateTime == null) {
            throw new NullPointerException("dateTime is null");
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);

        return df.format(dateTime);
    }*/
    /**
     * Method that loads the sightings into the data model from the database
     * as firebase does this asynchronously, will not stop the progress of the program
     */
    public static void loadSightings() {
        //DatabaseReference reports = mDatabase.child("reports");
        Log.d("DEBUG", mDatabase.toString());
        if(DataModel.getInstance().reports.size() == 0) {
            //limit to first limits the number of entries the query will access
            Query dataQuery = mDatabase.child("reports").limitToLast(150);
            dataQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (/*dataSnapshot.child("Incident Address").getValue() != null &&*/ dataSnapshot.child("Longitude").getValue().equals("0.0")) {
                        mDatabase.child(dataSnapshot.getKey()).removeValue();
                        return;
                    }

                    Log.d("DEBUG", dataSnapshot.toString());
                    String key = (String) dataSnapshot.child("Unique Key").getValue();


                    double lat = 0;
                    if (!(dataSnapshot.child("Latitude").getValue()).equals("")) {
                        lat = Double.valueOf((String) dataSnapshot.child("Latitude").getValue());
                    }

                    double lon = 0;
                    if (!(dataSnapshot.child("Longitude").getValue()).equals("")) {
                        lon = Double.valueOf((String) dataSnapshot.child("Longitude").getValue());
                    }

                    Date dateTime = null;
                    if (!(dataSnapshot.child("Created Date").getValue()).toString().equals("")) {
                        String strDateTime = dataSnapshot.child("Created Date").getValue().toString();
                        dateTime = InputCleanser.convertStringToDate(strDateTime);
                    }

                    String loc = "NO LOCATION GIVEN";
                    if (!(dataSnapshot.child("Location Type").getValue()).equals("")) {
                        loc = (String) dataSnapshot.child("Location Type").getValue();
                    }

                    int zip = 0;
                    if (!(dataSnapshot.child("Incident Zip").getValue()).equals("")
                            && !(dataSnapshot.child("Incident Zip").getValue()).equals("N/A")) {
                        zip = Integer.valueOf((String) dataSnapshot.child("Incident Zip").getValue());
                    }

                    String address = "NO ADDRESS GIVEN";
                    if (!(dataSnapshot.child("Incident Address").getValue()).equals("")) {
                        address = (String) dataSnapshot.child("Incident Address").getValue();
                    }
                    //Log.d("DEBUG", "Address:" + dataSnapshot.child("Incident Address").getValue());

                    String city = "NO CITY GIVEN";
                    if (!(dataSnapshot.child("City").getValue()).equals("")) {
                        city = (String) dataSnapshot.child("City").getValue();
                    }

                    String borough = "NO BOROUGH GIVEN";
                    if (!(dataSnapshot.child("Borough").getValue()).equals("")) {
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
     * Filters the current sightings by time and sel_location
     *
     * @param locationType can Filter results by sel_location Type Enum
     * @param startDate dateRange start >
     * @param endDate stateRange end <=
     * @param borough  can Filter results by
     * @return list of rat Sightings report that match filter
     */
    static ArrayList<RatSightingReport> filterSightings(String locationType,
                                               Date startDate,
                                               Date endDate,
                                               String borough){

        return InputCleanser.filterSightings(DataModel.getInstance().reports,
                                             locationType,
                                             startDate,
                                             endDate,
                                             borough);
    }
/*
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
        newRef.child("Created Date").setValue(InputCleanser.convertDateToString(sighting.getDateTime()));
    }

    /*/**
     * Generates a new user entry and passes it to the database
     * @param user user to be added to the database
     */
    /*public void createAccount(User user) {
        // Todo this code is temporary
        Log.d("DEBUG", "Inside accessor" + user.getContactInfo());
        DataModel.getInstance().userList.add(user);
    }*/

    /*/**
     * Gets the list of users from the data model
     *
     * @return an array of the Users
     */
    /*public ArrayList<User> getUsers(){
        return DataModel.getInstance().userList;
       // mDatabase.
    }*/
}
