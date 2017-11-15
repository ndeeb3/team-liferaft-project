package alec.ratapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<RatSightingReport> sightings = DataModel.getInstance().reports;
        if (savedInstanceState == null) { //without instance data it will check extras
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                sightings = (List) extras.getSerializable("filtered");
            }
        } else {
            sightings = (List) savedInstanceState.getSerializable("filtered"); //retrieve list
        }
        Log.d("DEBUG", "length of sitings" + sightings.size());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BarChart historyChart = findViewById(R.id.hist_chart);

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
        Button filterButton = findViewById(R.id.filterButtonGraph);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GraphActivity.this, filterMenuActivity.class);
                intent.putExtra("activity", "Graphs"); //pass Graphs so it will go back on submit
                startActivity(intent);
            }
        });

        BarDataSet dataSet = new BarDataSet(historyEntries, "History Of Reports");
        dataSet.setColor(Color.RED);


        BarData barData = new BarData(dataSet);
        barData.setValueFormatter(new TimeValueFormatter(historyChart));

        XAxis timeAxis = historyChart.getXAxis();
        timeAxis.setValueFormatter(new DateAxisValueFormatter(historyChart));
        historyChart.setData(barData);

        timeAxis.setLabelCount(12);
        timeAxis.setAxisMinimum(0f);
        timeAxis.setAxisMaximum(12f);
        timeAxis.setLabelRotationAngle(45);

        historyChart.invalidate();

    }

    /**
     * Calculates the number of months from the epoch date to the date given
     * @param time A date
     * @return The number of months since epoch date
     */
    private int getMonthsFromEpoch(Date time) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.MONTH);
    }

}
