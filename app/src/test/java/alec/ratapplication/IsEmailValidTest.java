package alec.ratapplication;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for email validity in LoginActivity.java and RegistrationActivity.java
 *
 * Created by Nichole Deeb on 11/13/17.
 */

public class IsEmailValidTest {
    /**
     * Tests to ensure a null email throws a NullPointerException
     */
    @Test (expected = NullPointerException.class)
    public void testNull() {
        String testEmail = null;
        LoginActivity.isEmailValid(testEmail);
        RegistrationActivity.isEmailValid(testEmail);
    }

    /**
     * Tests a general email with a normal URL and a single alphabetic word
     * in the beginning of the email
     */
    @Test
    public void testGeneral() {
        String testEmail = "test@yahoo.com";
        assertTrue(LoginActivity.isEmailValid(testEmail));
        assertTrue(RegistrationActivity.isEmailValid(testEmail));
    }

    /**
     * Tests that a URL can have multiple parts to web address
     */
    @Test
    public void testMultiWebAddress() {
        String testEmail = "test@mail.gatech.edu";
        assertTrue(LoginActivity.isEmailValid(testEmail));
        assertTrue(RegistrationActivity.isEmailValid(testEmail));
    }

    /**
     * Tests a more advanced user (non-alphabetic characters in username)
     */
    @Test
    public void testAdvancedUserName() {
        String testEmail = "this_1s.a-tesTT22@mail.gatech.edu";
        assertTrue(LoginActivity.isEmailValid(testEmail));
        assertTrue(RegistrationActivity.isEmailValid(testEmail));
    }

    /**
     * Tests a false email without a proper webaddress
     */
    @Test
    public void falseEmailTest() {
        String testEmail = "notanemail@gmail";
        assertFalse(LoginActivity.isEmailValid(testEmail));
        assertFalse(RegistrationActivity.isEmailValid(testEmail));
    }

    /**
     * Tests a false email without a username
     */
    @Test
    public void falseEmailTest2() {
        String testEmail = "@gmail.com";
        assertFalse(LoginActivity.isEmailValid(testEmail));
        assertFalse(RegistrationActivity.isEmailValid(testEmail));
    }

    /**
     * Tests a false email without an @ symbol
     */
    @Test
    public void falseEmailTest3() {
        String testEmail = "TESTgmail.com";
        assertFalse(LoginActivity.isEmailValid(testEmail));
        assertFalse(RegistrationActivity.isEmailValid(testEmail));
    }
}
