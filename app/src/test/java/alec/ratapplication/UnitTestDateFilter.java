
package alec.ratapplication;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Created by micha on 11/13/2017.
 */

public class UnitTestDateFilter {
    private String[] boroughList = {"MANHATTAN", "STATENISLAND", "QUEENS", "BROOKLYN", "BRONX"}; //the possible boroughs
    private String[] LocationList = {"1-2 Family Mixed Use Building", //some random locations
            "Public Garden",
            "Office Building",
            "Government Building",
            "School/Pre-School",
    };
    private List<RatSightingReport> reportList;

    /**
     * at the end of this test you get two identical lists
     */
    @Before
    public void populateReportList(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(1776, 7, 4));
        reportList = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            RatSightingReport report = new RatSightingReport();
            report.setDateTime(c.getTime());
            report.setBorough(boroughList[i % boroughList.length]);

            report.setLocationType(LocationList[i % LocationList.length]);
            reportList.add(report);
            c.add(Calendar.YEAR, 1);
        }
    }
    private String getDateString(Date date){
        Calendar bs = Calendar.getInstance();
        bs.setTime(date);
        return "year: " + (bs.get(Calendar.YEAR) - 1900) + "month: " + bs.get(Calendar.MONTH) + "day: " + bs.get(Calendar.DAY_OF_WEEK);
    }
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    private int countLocationType(String LocationType){
        int num = 0;
        for(RatSightingReport report: reportList){
            if (report.getLocationType().equals(LocationType)){
                num++;
            }
        }
        return num;
    }
    private int countBoroughType(String borough){
        int num = 0;
        for(RatSightingReport report: reportList){
            if (report.getBorough().equals(borough)){
                num++;
            }
        }
        return num;
    }
    /**
     * This test sees if it correctly does not filter based on locationType. The list should remain the same and totally unchanged.
     * So this test also checks if if doesn't filter based on date or borough or anything
     * @throws Exception
     */
    @Test
    public void DONTFILTER_isCorrect1() throws Exception {
        ArrayList<RatSightingReport> list = InputCleanser.filterSightings(reportList,
                null, null, null , null); //ive set the locationTypeToNull
        assertEquals(list.size(), reportList.size());

    }
    /**
     * This test sees if it correctly filters only the reports for public garden
     * @throws Exception
     */
    @Test
    public void locationFilter_isCorrect1() throws Exception {
        int originalNum = countLocationType("Public Garden");
        System.out.println(originalNum);
        reportList = InputCleanser.filterSightings(reportList, "Public Garden", null, null , null);
        for(RatSightingReport report: reportList) {
            assertEquals("Public Garden", report.getLocationType());
        }
        assertEquals(originalNum, countLocationType("Public Garden"));
    }



    /**
     * tests to see if it correctly filters based on borough
     * @throws Exception
     */
    @Test
    public void boroughFilter_isCorrect1() throws Exception {
        int originalNum = countBoroughType("BROOKLYN");
        System.out.println(originalNum);
        reportList = InputCleanser.filterSightings(reportList,  null, null, null , "BROOKLYN");
        for(RatSightingReport report: reportList) {
            assertEquals("BROOKLYN", report.getBorough());
            System.out.println(report.getBorough());
        }

        assertEquals(originalNum, countBoroughType("BROOKLYN"));

    }

    /**
     * for debugging it prints the reportList to the console
     */
    @Test
    public void printReportList() {
        for(RatSightingReport report: reportList) {
            System.out.println(report + "Date: " + getDateString(report.getDateTime())+ "Location: " + report.getLocationType() + "Borough: " + report.getBorough());
        }
    }

    /*
 *This method will try to filter the dates
 * the first item in the list is: year: 1776month: 7day: 3
 *last item: year: 1790month: 7day: 6
 *
 * This test is designed to test the capability to filter dates that are exactly equal to the filter parameter
 * for startDate. It should not include them because it is strictly greater
 */
    @Test
    public void startDateFilter_isCorrect1() throws Exception {
        Date start = reportList.get(0).getDateTime();
        reportList = InputCleanser.filterSightings(reportList,  null, start, null, null);
        for(RatSightingReport report: reportList) {
            assertNotEquals(report.getDateTime().getTime(), start.getTime());
        }

    }

    /*
    *This method will try to filter the dates
    * the first item in the list is: year: 1776month: 7day: 3
    *last item: year: 1790month: 7day: 6
    *
    * This test is designed to test the capability to filter start dates very close to actual values
    *It should include them because the startDate filter is very slightly less
    *
    */
    @Test
    public void startDateFilter_isCorrect2() throws Exception {
        Date originalTime = reportList.get(0).getDateTime();
        Calendar c = Calendar.getInstance();
        c.setTime(originalTime);
        c.add(Calendar.HOUR, -1); //adding 1 hour to the date
        Date start = c.getTime();
        reportList = InputCleanser.filterSightings(reportList,  null, start, null, null);
        assertEquals(originalTime.getTime(), reportList.get(0).getDateTime().getTime());
    }

    /*
*This method will try to filter the dates
* the first item in the list is: year: 1776month: 7day: 3
*last item: year: 1790month: 7day: 6
*
* This test is designed to test the capability to filter dates that are exactly equal to the filter parameter
* for endDate. It should remove one of the dates
*/
    @Test
    public void endDateFilter_isCorrect1() throws Exception {
        Date end = reportList.get(reportList.size()-1).getDateTime();
        reportList = InputCleanser.filterSightings(reportList,  null, null, end, null);
        for(RatSightingReport report: reportList) {
            assertNotEquals(report.getDateTime().getTime(), end.getTime());
        }

    }
    /*
*This method will try to filter the dates
* the first item in the list is: year: 1776month: 7day: 3
*last item: year: 1790month: 7day: 6
*
* This test is designed to test the capability to filter start dates very close to actual values
    *It should include them because the endDate filter is very slightly more
*/
    @Test
    public void endDateFilter_isCorrect2() throws Exception {
        Date originalTime = reportList.get(reportList.size()-1).getDateTime();
        Calendar c = Calendar.getInstance();
        c.setTime(originalTime);
        c.add(Calendar.HOUR, 1); //adding 1 hour to the date
        Date end = c.getTime();
        reportList = InputCleanser.filterSightings(reportList,  null, null, end, null);
        assertEquals(originalTime.getTime(), reportList.get(reportList.size()-1).getDateTime().getTime());
    }
}
