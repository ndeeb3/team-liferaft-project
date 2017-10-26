package alec.ratapplication;

/**
 * Created by micha on 10/25/2017.
 */

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
import java.util.Calendar;
import java.util.Date;

/**
 * A login screen that offers login with email/password.
 */
public class filterMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_filter_menu);

        //start Date button
        Button button =  (Button) findViewById(R.id.startDateButton);
        button.setText("No date");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new startDatePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });
        //end Date Button
        Button button2 =  (Button) findViewById(R.id.endDateButton);
        button2.setText("No date");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new endDatePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });
        Button button3 = (Button) findViewById(R.id.buttonFilter);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataModel.getInstance().filteredReports = RatSightingAccessor.filterSightings(
                    null, DataModel.getInstance().startDate,
                    DataModel.getInstance().endDate, null);
                Log.e("FILTERED SIGHTINGS", DataModel.getInstance().filteredReports.toString());

                Context context = (view.getContext());
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra("filtered", (Serializable) DataModel.getInstance().filteredReports);
                context.startActivity(intent);
                finish();
            }
        });
        //mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);
    }
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
        public Date getDate(int year, int month, int day){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            return cal.getTime();

        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            DataModel.getInstance().startDate = this.getDate(year, month, day);
            Button button = (Button) this.getActivity().findViewById(R.id.startDateButton);
            button.setText(DataModel.getInstance().startDate.toString());
        }

    }
    public static class endDatePicker extends startDatePicker{
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            DataModel.getInstance().endDate = this.getDate(year, month, day);
            Button button = (Button) this.getActivity().findViewById(R.id.endDateButton);
            button.setText(DataModel.getInstance().endDate.toString());
        }
    }


}

