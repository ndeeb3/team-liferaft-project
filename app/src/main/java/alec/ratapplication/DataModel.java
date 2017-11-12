package alec.ratapplication;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A class holding the important data in the
 * application
 *
 * Created by michael on 10/2/2017.
 */
class DataModel {
    // try changing this to final
    private static DataModel ourInstance = new DataModel();
    //the list of user objects
    public ArrayList<User> userList;
    //the list of rat reports from the database
    public LinkedList<RatSightingReport> reports;
    public int newKey;

    static DataModel getInstance() {
        return ourInstance;
    }

    private DataModel() {
        userList = new ArrayList<>();
        reports = new LinkedList<>();
        newKey = 0;
    }
}
