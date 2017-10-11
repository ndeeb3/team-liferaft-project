package alec.ratapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by micha on 10/2/2017.
 */

class FakeDatabase {
    // try changing this to final
    private static FakeDatabase ourInstance = new FakeDatabase();
    public ArrayList<User> userList;
    public ArrayList<RatSightingReport> reportList;

    static FakeDatabase getInstance() {
        return ourInstance;
    }

    private FakeDatabase() {
        userList = new ArrayList<>();
        reportList = new ArrayList<>();
    }

    /**
     *
     * @param line a line of the database file for parsing. Not the first line
     * @param lineNum for debuggig and finding database errors
     * @return
     */
    private RatSightingReport convertLineToReport(String line, int lineNum) {
        String[] stringList = line.split(",");
        //getting the key from the stringList
        String key;
        if (stringList.length >= 1) {
            key = stringList[0];
        } else {
            Log.d("Warn", "The first column for key is missing" + "lineNum: "+ lineNum);
            return null; //no sense returning anyreport with all nulls
        }

        float latitude, longitude;

        //getting lat and long from stringList
        if (stringList.length >= 51) {
            try {
                latitude = Float.parseFloat(stringList[49]);
                longitude = Float.parseFloat(stringList[50]);
            } catch (java.lang.NumberFormatException e) {
                Log.d("Warn", "the longitude and latitude is incorrect" + "lineNum: "+ lineNum);
                return null;
            }
        } else {
            Log.d("warn", "The file dosent have enough columns for longitude and latitude" + "lineNum: " + lineNum +" ff" + stringList.length);
            return null;
        }
        //getting dateTime from string List
        Date dateTime;
        if (stringList.length >= 2) {
            DateFormat df = new SimpleDateFormat("mm/dd/yyyy hh:mm");
            try {
                dateTime = df.parse(stringList[1]);
            } catch (ParseException e) {
                Log.d("Warn", "The dateTime is in the wrong format in the database: " + "Line Nume: " +lineNum);
                return null;
            }
        } else {
            Log.d("Warn", "There arnt enough cols for datetime " + "line NUm" + lineNum);
            return null;
        }
        LocationType locationType;
        if (stringList.length >= 8) {
            //Log.d("DEBUG", "The raw stringList[7" + stringList[7]);
            if (stringList[7].split(" ")[0].equals("Other")){
                locationType = LocationType.getLocationType("Other");
            } else {
                try {
                    locationType = LocationType.getLocationType(stringList[7]);
                } catch (IllegalArgumentException e) {
                    Log.d("ERROR",
                            "Location Type in wrong format" + stringList[7] + lineNum );
                    return null;
                }
            }
        } else {
            Log.d("Warn", "There are not enough cols for location Type" + "lineNum: " + lineNum);
            return null;
        }
        int zipcode;
        if (stringList.length >= 8) {
            try {
                zipcode = Integer.parseInt(stringList[8]);
            } catch (java.lang.NumberFormatException e) {
                Log.d("Warn", "zipcode in wrong format" + "lineNum: " + lineNum);
                return null;
            }
        } else {
            Log.d("DEBUG", "There arnt enough cols for zipcode" + "lineNum: " + lineNum);
            return null;
        }
        String address;
        if (stringList.length >= 10) {
            address = stringList[9];
        } else {
            Log.d("DEBUG", "There are not enough cols for address" + "lineNum" + lineNum);
            return null;
        }
        String city;
        if (stringList.length >= 17) {
            city = stringList[16];
        } else {
            Log.d("Warn", "There are not enough cols for city" + "lineNum" + lineNum);
            return null;
        }
        Borough borough;
        if (stringList.length >= 24) {
            String rawBorough = stringList[23];
            try {
                borough = Borough.valueOf(
                        stringList[23].replace(" ", "")
                );
            } catch(IllegalArgumentException e) {
                Log.d("Warn", "The borugh col is in wrong format" + "lineNum" + lineNum);
                return null;
            }
        } else {
            Log.d("Warn", "There are not enough cols for borough" + "lineNum" + lineNum);
            return null;
        }

        return new RatSightingReport(key, latitude, longitude, dateTime,
                locationType, zipcode, address, city, borough
        );
    }
    /**
     *
     * @param context the context where I am calling this
     */
    public void populateReportList(Context context) throws java.io.IOException {
        InputStream asset = context.getAssets().open("Rat_Sightings.csv");
        InputStreamReader input = new InputStreamReader(asset);
        BufferedReader bf = new BufferedReader(input);
        bf.readLine(); //first line is useless
        String line = bf.readLine();
        int lineNum = 2;
        while (line != null) {
         //   Log.d("ERROR", line);
            RatSightingReport report = convertLineToReport(line, lineNum);
            if (report != null){
                reportList.add(report);
            }
            lineNum++;
            line = bf.readLine();
        }
        bf.close();
    }

    /**
     * Set up an adapter and hook it to the provided view
     *
     * @param recyclerView  the view that needs this adapter
     */



}
