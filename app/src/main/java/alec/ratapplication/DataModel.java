package alec.ratapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by micha on 10/2/2017.
 */

class DataModel {
    // try changing this to final
    private static DataModel ourInstance = new DataModel();
    public ArrayList<User> userList;
    public List<RatSightingReport> reports;
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
