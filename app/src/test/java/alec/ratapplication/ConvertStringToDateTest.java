package alec.ratapplication;

import android.util.Log;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *  JUnit tests for the ConvertStringToDate method in RatSightingAccessor
 *  Alec's
 */

public class ConvertStringToDateTest {

    /**
     * Tests when a null is passed in, throws a nullpointerexception
     */
    @Test (expected = NullPointerException.class)
    public void testNull() {
        String testDate = null;
        InputCleanser.convertStringToDate(testDate);
    }

    /**
     * Tests when a correctly formatted string is passed in
     */
    @Test
    public void testCorrect() {
        String testDate = "12/11/2016 09:31:50 AM";
        Date correctDate = new Date(1481466710000L);
        assertEquals(correctDate, InputCleanser.convertStringToDate(testDate));

        testDate = "12/11/1960 09:31:50 AM";
        correctDate = new Date(-285758890000L);
        assertEquals(correctDate, InputCleanser.convertStringToDate(testDate));
    }

    /**
     * Tests when a incorrectly formatted string is passed in
     */
    @Test (expected = IllegalArgumentException.class)
    public void testParseException() {
        String testDate = "9:31 AM, 12-11-16";
        InputCleanser.convertStringToDate(testDate);

        testDate = "January 11, 1987, 11:30 PM";
        InputCleanser.convertStringToDate(testDate);

        testDate = "APARTMENT";
        InputCleanser.convertStringToDate(testDate);
    }
}
