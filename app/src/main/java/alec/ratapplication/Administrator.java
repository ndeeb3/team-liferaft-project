/**
 * Created by micha on 10/2/2017.
 */
package alec.ratapplication;

public class Administrator extends User {
    private String loginName;

    public Administrator(String loginName,
                          String password,
                          String emailAddress){
        super(loginName, password, emailAddress);
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
