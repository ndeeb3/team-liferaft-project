package alec.ratapplication;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class TimeValueFormatter implements IValueFormatter {


    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private BarLineChartBase<?> chart;

    public TimeValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        int month = (int) entry.getX();

        int year = 17;//determineYear(days);

        //int month = determineMonth(days);
        String monthName = mMonths[month % mMonths.length];
        String yearName = String.valueOf(year);

        return entry.getY() + ":" + monthName + " " + yearName;
    }
}