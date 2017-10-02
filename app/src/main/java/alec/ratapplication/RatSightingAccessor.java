package alec.ratapplication;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by micha on 10/2/2017.
 */

public class RatSightingAccessor {
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
        //// TODO
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
    public void CreateAccount(User user) {
        // Todo this code is temporary
        FakeDatabase.getInstance().userList.add(user);
    }
    /**
     *
     * @return an array of the Users
     */
    public ArrayList getUsers(){
        // Todo this code is temporary
        return FakeDatabase.getInstance().userList;
    }
}
