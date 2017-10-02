/**
 * Created by micha on 10/2/2017.
 */

public class Administrator extends User {
    private String loginName;

    public Administrator(String loginName,
                          String password,
                          boolean accountState,
                          String emailAddress)
        super(loginName, password, accountState, emailAddress);
    }

    /**
     *
     * @param user
     */
    public void registerAccount(User user){

    }
    public void blockUser(User user) {

    }
    public void unblockUser(User user) {

    }

}
