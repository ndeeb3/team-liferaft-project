package alec.ratapplication;
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
        //// TODO: 10/2/2017
        return new RatSightingReport[0];
    }

    /**
     * Dont know the purpose of this one
     */
    public void updateMap(){

    }

    /**
     *
     * @param sighting the RatSighting we are goign to put into the database
     */
    public void inputSighting(RatSightingReport sighting) {

    }

    /**
     *
     * @return a list of the Users
     */
    public User[] getUsers(){
        return new User[0];
    }
}
