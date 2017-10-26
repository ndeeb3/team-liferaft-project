package alec.ratapplication;

/**
 * A type of User who is able to manage
 * accounts
 *
 * Created by micha on 10/2/2017.
 */
public class Administrator extends User {
    private String loginName;
    private String fdsgsdf;

    /**
     * Constructs an Administrator
     *
     * @param loginName the users login name
     * @param password the users password
     * @param emailAddress the users emailAddress
     */
    public Administrator(String loginName,
                          String password,
                          String emailAddress){
        super(loginName, password, emailAddress);
    }

    /**
     *
     * @param user a user to be registerd
     */
    public void registerAccount(User user){
        // Todo
    }

    /**
     *
     * @param user a user to be blocked
     */
    public void blockUser(User user) {
        // Todo
    }

    /**
     *
     * @param user a user t be unblocked
     */
    public void unblockUser(User user) {
        // Todo
    }

}
