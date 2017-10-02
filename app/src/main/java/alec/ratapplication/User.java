package alec.ratapplication;
/**
 * Created by Sonia Thakur on 10/2/2017.
 */

public class User {
    /*login name (in recognition of current popular trends, this can be the email address)
    password
    account state (locked or unlocked)
    contact info (email address)*/

    private String loginName;
    private String password;
    private boolean locked = false;
    private String contactInfo;

    public User(String loginName, String password, String contactInfo) {
        this.loginName = loginName;
        this.password = password;
        this.contactInfo = contactInfo;
    }

    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getAccountStatus() {
        return locked;
    }
    public void setAccountSTate(boolean accountState) {
        locked = accountState;
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /*public reportSighting(RatSightingReport report) {

    }*/
}
