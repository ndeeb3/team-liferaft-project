package alec.ratapplication;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

/**
 * A class holding the important data in the
 * application
 *
 * Created by michael on 10/2/2017.
 */
class DataModel {
    // try changing this to final
    private final static DataModel ourInstance = new DataModel();
    //the list of user objects
    //private final ArrayList<User> userList;
    //the list of rat reports from the database
    public final LinkedList<RatSightingReport> reports;
    public int newKey;
    public LatLng sel_location;
    public LatLng act_location;

    static DataModel getInstance() {
        return ourInstance;
    }

    private DataModel() {
      //  userList = new ArrayList<>();
        reports = new LinkedList<>();
        newKey = 0;
        sel_location = new LatLng(0,0);
        act_location = new LatLng(0,0);

    }
}
