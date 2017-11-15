package alec.ratapplication;

import org.junit.Test;

import static alec.ratapplication.LocationType.*;
import static junit.framework.Assert.assertTrue;

/**
 * JUnit Tests for the getLocationType method
 * in LocationType.java
 *
 * Created by Andrew Sheffield on 11/13/2017.
 */
public class GetLocationTypeTest {
    /**
     * Tests that getLocationType responds
     * appropriately to a null value
     */
    @Test (expected = IllegalArgumentException.class)
    public void nullStringTest() {
        String value = null;
        getLocationType(value);
    }

    /**
     * Tests getLocationType for FAMILYDWELLING
     */
    @Test
    public void familyDwellingTest() {
        String value = "1-2 Family Dwelling";
        assertTrue( FAMILYDWELLING == getLocationType(value));
    }

    /**
     * Tests getLocationType for APTBUILDING
     */
    @Test
    public void aptBuildingTest() {
        String value = "3+ Family Apt. Building";
        assertTrue( APTBUILDING == getLocationType(value));
    }

    /**
     * Tests getLocationType for SUMMERCAMP
     */
    @Test
    public void summerCampTest() {
        String value = "Summer Camp";
        assertTrue( SUMMERCAMP == getLocationType(value));
    }

    /**
     * Tests that the method will throw an
     * IllegalArgumentException when an unexpected
     * value is passed in
     */
    @Test (expected = IllegalArgumentException.class)
    public void falseLocationTest() {
        String value = "Moon";
        getLocationType(value);
    }

    /**
     * Tests that the method will throw an
     * IllegalArgumentException when a value
     * similar to a real value is passed in
     */
    @Test (expected = IllegalArgumentException.class)
    public void falseLocationTest2() {
        String value = "33+ Family Apt. Building";
        getLocationType(value);
    }
}