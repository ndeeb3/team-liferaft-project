package alec.ratapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {


    private BarChart historyChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historyChart = (BarChart) findViewById(R.id.hist_chart);

        //temporarily gets all reports
        LinkedList<RatSightingReport> dateFilteredReports = DataModel.getInstance().reports;
        List<BarEntry> historyEntries = new ArrayList<>();

        // entry should be (report month / year, number of reports)
        /*for (RatSightingReport report : dateFilteredReports) {
            historyEntries.add(new BarEntry(report., report.));
        }*/
        historyEntries.add(new BarEntry(1,7));
        historyEntries.add(new BarEntry(2,5));
        historyEntries.add(new BarEntry(3,2));
        historyEntries.add(new BarEntry(4,9));
        historyEntries.add(new BarEntry(5,5));
        historyEntries.add(new BarEntry(6,5));
        historyEntries.add(new BarEntry(7,1));

        BarDataSet dataSet = new BarDataSet(historyEntries, "History Of Reports");
        dataSet.setColor(Color.RED);


        BarData barData = new BarData(dataSet);


        historyChart.setData(barData);
        historyChart.invalidate();

    }
}
