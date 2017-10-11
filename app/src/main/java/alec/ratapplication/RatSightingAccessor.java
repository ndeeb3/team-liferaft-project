package alec.ratapplication;
import android.os.Debug;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RatSightingAccessor {

    public static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();;
    private ChildEventListener dataListener;
    private String TAG = "DEBUG";
    public static List<String> snapshots = new LinkedList<>();
    public static List<RatSightingReport> reports = new LinkedList<>();
    public RatSightingAccessor() {

        //List<DataSnapshot> snapshots = new LinkedList<>();
        Query recentPostsQuery = mDatabase;
        /*recentPostsQuery.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "Child Num:" + dataSnapshot.getKey());
                snapshots.add(dataSnapshot.toString());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, child.toString());
                }
                //Comment comment = dataSnapshot.getValue(Comment.class);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/
    }


    public static void loadSightings() {
        Query tempquery = mDatabase.limitToFirst(100);
        tempquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = (String) dataSnapshot.child("Unique Key").getValue();
                Log.d("DEBUG", dataSnapshot.toString());
                double lat = 0;
                if(((String)dataSnapshot.child("Latitude").getValue()) == null) {
                    lat = Double.valueOf((String)dataSnapshot.child("Latitude").getValue());
                }
                double lon = 0;
                if(((String)dataSnapshot.child("Longitude").getValue()) == null) {
                    lon = Double.valueOf((String)dataSnapshot.child("Longitude").getValue());
                }
                String dateTime = (String)dataSnapshot.child("Created Date").getValue();
                String loc = (String)dataSnapshot.child("Location Type").getValue();
                int zip = 0;
                if(((String)dataSnapshot.child("Incident Zip").getValue()) == null) {
                    zip = Integer.valueOf((String)dataSnapshot.child("Incident Zip").getValue());
                }
                String address = (String) dataSnapshot.child("Address").getValue();
                String city = (String) dataSnapshot.child("City").getValue();
                String borough = (String) dataSnapshot.child("Borough").getValue();
                RatSightingReport newSighting = new RatSightingReport(key,lat,lon,dateTime,loc,zip,address,city,borough);
                reports.add(newSighting);
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
        Log.d(TAG, "SNAP1234" + snapshots.toString());
        Query tempQuery = mDatabase.limitToFirst(100);
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
        // Todo
    }

    /**
     *
     * @param user user to be added to the database
     */
    public void createAccount(User user) {
        // Todo this code is temporary
        Log.d("DEBUG", "Inside accessor" + user.getContactInfo());
        FakeDatabase.getInstance().userList.add(user);
    }
    /**
     *
     * @return an array of the Users
     */
    public ArrayList<User> getUsers(){
        // Todo this code is temporary
        return FakeDatabase.getInstance().userList;
       // mDatabase.
    }
}
