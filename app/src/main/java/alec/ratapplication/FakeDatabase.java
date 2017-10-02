package alec.ratapplication;

import java.util.ArrayList;

/**
 * Created by micha on 10/2/2017.
 */

class FakeDatabase {
    // try changing this to final
    private static FakeDatabase ourInstance = new FakeDatabase();
    public ArrayList<User> userList;

    static FakeDatabase getInstance() {
        return ourInstance;
    }

    private FakeDatabase() {
        userList = new ArrayList<>();
    }
}
