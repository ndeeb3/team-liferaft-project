package alec.ratapplication;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * JUnit Tests for the isPasswordValid method
 * in LoginActivity.java
 *
 * Created by Sonia Thakur
 */
public class isPasswordValidTest {
    LoginActivity tester = new LoginActivity();

    /**
     * Tests that isPasswordValid responds
     * appropriately to a null input
     */
    @Test(expected = NullPointerException.class)
    public void isPasswordValidNullTest(){
        String password = null;
        tester.isPasswordValid(password);
    }

    /**
     * Tests that isPasswordValid responds
     * appropriately to a 0 string input
     */
    @Test
    public void isPasswordValidIntTest(){
        String password = "0";
        tester.isPasswordValid(password);
    }

    /**
     * Tests that isPasswordValid responds
     * appropriately to a short string input < 4
     */
    @Test
    public void isPasswordValidShortTest(){
        String password = "tt";
        assertTrue(false == tester.isPasswordValid(password));
    }

    /**
     * Tests that isPasswordValid responds
     * appropriately to a string input > 4
     */
    @Test
    public void isPasswordValidLongTest(){
        String password = "qqqqq";
        assertTrue(tester.isPasswordValid(password));
    }

    /**
     * Tests that isPasswordValid responds
     * appropriately to a string input = 4
     */
    @Test
    public void isPasswordValidEqualTest(){
        String password = "1234";
        assertTrue(false == tester.isPasswordValid(password));
    }
}