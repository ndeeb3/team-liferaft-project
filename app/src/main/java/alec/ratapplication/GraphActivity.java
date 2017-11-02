package alec.ratapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private BarChart historyChart;
    private List<RatSightingReport> sightings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sightings = DataModel.getInstance().reports;
        if (savedInstanceState == null) { //without instance data it will check extras
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                sightings = (List) extras.getSerializable("filtered");
            }
        } else {
            sightings = (List) savedInstanceState.getSerializable("filtered"); //retrieve list
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historyChart = (BarChart) findViewById(R.id.hist_chart);

        //temporarily gets all reports
        List<BarEntry> historyEntries = new ArrayList<>();

        // entry should be (report month / year, number of reports)

        for (RatSightingReport report : sightings) {
            //get number of months since epoch for report
            int months = getMonthsFromEpoch(report.getDateTime());
            Log.d("DEBUG", "MONTHS: " + months);
            //if that entry does exist, increment the y number
            boolean inEntries = false;
            for(Entry entry : historyEntries){
                if(entry.getX() == months) {
                    float newAmt = entry.getY() + 1;
                    entry.setY(newAmt);
                    inEntries = !inEntries;
                }
            }

            if(!inEntries) {
                historyEntries.add(new BarEntry(months, 1));
                Log.d("DEBUG", "Added new entry:" + months);
            }
        }
        Log.d("DEBUG", "hist:" + historyEntries.toString());
        Button filterButton = (Button) findViewById(R.id.filterButtonGraph);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GraphActivity.this, filterMenuActivity.class);
                intent.putExtra("activity", "Graphs"); //pass Graphs so it will go back on submit
                startActivity(intent);
            }
        });
        /*historyEntries.add(new BarEntry(1,7));
        historyEntries.add(new BarEntry(2,5));
        //historyEntries.add(new BarEntry(3,2));
        //historyEntries.add(new BarEntry(4,9));
        historyEntries.add(new BarEntry(5,5));
        historyEntries.add(new BarEntry(6,5));
        historyEntries.add(new BarEntry(7,1));*/

        BarDataSet dataSet = new BarDataSet(historyEntries, "History Of Reports");
        dataSet.setColor(Color.RED);


        BarData barData = new BarData(dataSet);
        barData.setValueFormatter(new TimeValueFormatter(historyChart));
        
        XAxis timeAxis = historyChart.getXAxis();
        timeAxis.setValueFormatter(new DateAxisValueFormatter(historyChart));
        historyChart.setData(barData);

        //timeAxis.setGranularity();
        timeAxis.setLabelCount(12);
        timeAxis.setAxisMinimum(0f);
        timeAxis.setAxisMaximum(11f);
        timeAxis.setLabelRotationAngle(45);
        //historyChart.set/
        //historyChart.setDescription(" ");

        historyChart.invalidate();

    }

    /**
     *
     * @param time
     * @return
     */
    private int getMonthsFromEpoch(Date time) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //long months = cal.getTimeInMillis() / (60L*60L*1000L*24L*30L);
        //Log.d("DEBUG", "MONTHS???: " + months);
        //return (int) ((time - 0) / (1000*60*60*24*12));
        //Log.d("DEBUG", "Year: " + cal.get(Calendar.YEAR));
        return cal.get(Calendar.MONTH); //+ 12 * (cal.get(Calendar.YEAR) - 1970);
    }

}
