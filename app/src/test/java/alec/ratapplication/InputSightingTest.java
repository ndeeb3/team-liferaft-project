package alec.ratapplication;

import org.junit.Test;

/**
 * JUnit Tests for the inputSighting method
 * in RatSightingAccessor.java
 *
 * Created by Sonia Thakur
 */
public class InputSightingTest {
    //filterMenuActivity.startDatePicker tester = new filterMenuActivity.startDatePicker();
    RatSightingAccessor tester = new RatSightingAccessor();
    /*public Date getDate(int year, int month, int day){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            return cal.getTime();

        }*/
    /**
     * Tests that inputSighting responds
     * appropriately to an incorrect input
     */
    @Test
    public void inputSightingTest() throws Exception{
        tester.inputSighting(null);
    }

    /**
     * Tests that getDate responds
     * appropriately to a today's date
     */
    /*
    @Test
    public void getDateTest() throws Exception{
        int year = 0;
        int month = 1;
        int day = 1;

        Date date = new Date(0);
        assertTrue(date == tester.getDate(year, month, day));
    }
*/
    /**
     * Tests that getDate responds
     * appropriately to an incorrect date
     */
    /*@Test
    /*public void getDateNullTest() throws Exception {
        int year = 0;
        int month = 0;
        int day = 0;
        Date date = new Date(year, month, day);
        assertTrue(date == tester.getDate(year, month, day));
    }*/
}