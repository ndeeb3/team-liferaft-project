package alec.ratapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A login screen that offers login with email/password.
 *
 * Created by michael on 10/25/2017.
 */
public class filterMenuActivity extends AppCompatActivity {
    public Date startDate = null; // a date which is used by the filter menu
    public Date endDate = null; // a date which is used by the filter menu
    public List<RatSightingReport> filteredReports = new ArrayList<>();

    private String PreviousActivity = null; //this is where i will be going back to

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) { //without instance data it will check extras
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                PreviousActivity = (String) extras.getSerializable("activity");
            }
        } else {
            PreviousActivity = (String) savedInstanceState.getSerializable("activity"); //retrieve list
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_filter_menu);

        //start Date button
        Button button = findViewById(R.id.startDateButton);
        button.setText("No date");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new startDatePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        //end Date Button
        Button button2 =  findViewById(R.id.endDateButton);
        button2.setText("No date");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new endDatePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        //filter button.
        //if you just hit filter with no options it will show all the originals
        Button button3 = findViewById(R.id.buttonFilter);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filteredReports = RatSightingAccessor.filterSightings(
                    null, startDate,
                    endDate, null); //got filtered reports
                Context context = (view.getContext());
                Intent intent;
                if (PreviousActivity == null) {
                    Log.e("ERROR", "YOU DIDN'T CALL PUT EXTRA in the last activity. HOW COULD YOU DO THIS");
                    intent = new Intent(view.getContext(), MapsActivity.class);

                } else if (PreviousActivity.equals("Maps")) {
                    Log.d("INFO", "everything is proceeding as I had foreseen");
                    intent = new Intent(view.getContext(), MapsActivity.class);
                } else if (PreviousActivity.equals("Graphs")) {
                    Log.d("INFO", "Your overconfidence is your weakness...");
                    intent = new Intent(view.getContext(), GraphActivity.class);
                } else {
                    Log.e("ERROR", "THAT ACTIVITY DOES NOT EXIST. HOW COULD YOU DO THIS");
                    intent = new Intent(view.getContext(), MapsActivity.class);
                }
                intent.putExtra("filtered", (Serializable) filteredReports); //pass into maps
                context.startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Class which allows you to choose your start date
     */
    public static class startDatePicker extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * @param year year
         * @param month month
         * @param day day
         * @return conversion to date
         */
        public Date getDate(int year, int month, int day){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            return cal.getTime();

        }

        /**
         * This method updates the startDate and passes the text to the button
         *
         * @param view Used to select startDate
         * @param year year
         * @param month month
         * @param day day
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            filterMenuActivity activity = (filterMenuActivity)this.getActivity();
            activity.startDate = this.getDate(year, month, day);
            Button button = this.getActivity().findViewById(R.id.startDateButton);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            button.setText(df.format(activity.startDate));
        }

    }

    /**
     * Class which allows you to select your end date
     */
    public static class endDatePicker extends startDatePicker{

        /**
         * This method updates the endDate and passes the text to the button
         *
         * @param view Used to select endDate
         * @param year year
         * @param month month
         * @param day day
         */
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            filterMenuActivity activity = (filterMenuActivity)this.getActivity();
            activity.endDate = this.getDate(year, month, day);
            Button button = this.getActivity().findViewById(R.id.endDateButton);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            button.setText(df.format(activity.endDate));
        }
    }


}

