package alec.ratapplication;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RatSightingAccessor {

    public static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();;
    private ChildEventListener dataListener;
    private String TAG = "DEBUG";
    public static List<String> snapshots = new LinkedList<>();
    public RatSightingAccessor() {
        Query recentPostsQuery = mDatabase;
    }

    /**
     * Method that loads the sightings into the list
     * as firebase does this asyncronusly, will not stop the progress of the program
     */
    public static void loadSightings() {
        //
        if(DataModel.getInstance().reports.size() == 0) {
            //limit to first limits the number of entries the query will access
            Query tempquery = mDatabase.limitToLast(100);
            tempquery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.child("Incident Address").getValue() != null && dataSnapshot.child("Incident Address").getValue().equals("TEST")) {
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

                    String dateTime = "NO TIME GIVEN";
                    if (!((String) dataSnapshot.child("Created Date").getValue()).equals("")) {
                        dateTime = (String) dataSnapshot.child("Created Date").getValue();
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
                    Log.d("DEBUG", "Address:" + dataSnapshot.child("Incident Address").getValue());

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
     *
     * @param locationType can Filter results by location Type Enum
     * @param startDate dateRange start >=
     * @param endDate stateRange end <=
     * @param borough  can Filter results by
     * @return list of rat Sightings report that match filter
     */
    public RatSightingReport[] filterSightings(LocationType locationType,
                                               Date startDate,
                                               Date endDate,
                                               Borough borough){
        Query tempQuery = mDatabase;//.limitToFirst(100);
        //tempQuery.addChildEventListener(dataListener);
        return new RatSightingReport[0];
    }

    /**
     *
     * @return a list of rat report sightings. Alec will replace the code we put here
     */
    public RatSightingReport[] getAllSightings() {
        LinkedList<RatSightingReport> sightings = new LinkedList<>();
        return new RatSightingReport[0];
    }

    /**
     *
     * @param reports the reports will get get added to the map
     */
    public void updateMap(RatSightingReport[] reports){
        // Todo
    }

    /**
     *
     * @param sighting rat sighting to be put into the database
     */
    public void inputSighting(RatSightingReport sighting) {
        DatabaseReference newRef = mDatabase.push();
        //newRef.setValue(sighting);
        newRef.child("Longitude").setValue(Double.toString(sighting.getLongitude()));
        newRef.child("Latitude").setValue(Double.toString(sighting.getLatitude()));
        newRef.child("Unique Key").setValue(Integer.toString(DataModel.getInstance().newKey));
        newRef.child("Incident Address").setValue(sighting.getAddress());
        newRef.child("Location Type").setValue(sighting.getLocationType());
        newRef.child("Incident Zip").setValue(Integer.toString(sighting.getZipcode()));
        newRef.child("City").setValue(sighting.getCity());
        newRef.child("Borough").setValue(sighting.getBorough());
        newRef.child("Created Date").setValue(sighting.getDateTime());
        Log.d("DEBUG", newRef.toString());
    }

    /**
     *
     * @param user user to be added to the database
     */
    public void createAccount(User user) {
        // Todo this code is temporary
        Log.d("DEBUG", "Inside accessor" + user.getContactInfo());
        DataModel.getInstance().userList.add(user);
    }
    /**
     *
     * @return an array of the Users
     */
    public ArrayList<User> getUsers(){
        // Todo this code is temporary
        return DataModel.getInstance().userList;
       // mDatabase.
    }
}
