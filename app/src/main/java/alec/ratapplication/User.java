package alec.ratapplication;

import java.io.Serializable;

/**
 * Created by Sonia Thakur on 10/2/2017.
 */

public class User implements Serializable {
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
    /**
     * @return the login name for the user
     */
    public String getLoginName() {
        return loginName;
    }
    /**
     *
     * @param loginName the login name of the user
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    /**
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return whether or not the account is locked
     */
    public boolean getAccountStatus() {
        return locked;
    }
    /**
     * @param accountState the new account state of the user
     */
    public void setAccountState(boolean accountState) {
        locked = accountState;
    }
    /**
     * @return the contact information of the user
     */
    public String getContactInfo() {
        return contactInfo;
    }
    /**
     * @param contactInfo the new contact information of the user
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /*public reportSighting(RatSightingReport report) {
        return new RatSightingReport();
    }*/
}
