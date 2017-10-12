package alec.ratapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by micha on 10/2/2017.
 */

class FakeDatabase {
    // try changing this to final
    private static FakeDatabase ourInstance = new FakeDatabase();
    public ArrayList<User> userList;

    public List<RatSightingReport> reports;

    static FakeDatabase getInstance() {
        return ourInstance;
    }

    private FakeDatabase() {
        userList = new ArrayList<>();
        reports = new LinkedList<>();
    }
}
