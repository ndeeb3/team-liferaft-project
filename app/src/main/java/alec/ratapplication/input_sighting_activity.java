package alec.ratapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class input_sighting_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner LocationTypeSpinner;
    private Spinner BoroughSpinner;
    private EditText addressText;
    private EditText zipcodeText;
    private EditText cityText;
    private EditText timeText;
    private EditText inputLatitude;
    private EditText inputLongitude;

    private RatSightingReport newReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newReport = new RatSightingReport();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sighting_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        BoroughSpinner = (Spinner) findViewById(R.id.BoroughSpinner);
        LocationTypeSpinner = (Spinner) findViewById(R.id.LocationTypeSpinner);
        addressText = (EditText) findViewById(R.id.addressInput);
        zipcodeText = (EditText) findViewById(R.id.zipcodeInput);
        cityText = (EditText) findViewById(R.id.cityInput);
        timeText = (EditText) findViewById(R.id.timeInput);
        inputLatitude = (EditText) findViewById(R.id.inputLatitude);
        inputLongitude = (EditText) findViewById(R.id.inputLongitude);

        Date time = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        timeText.setText(df.format(time));
        Log.d("DEBUG", time.toString());

        ArrayAdapter<LocationType> adapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, LocationType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationTypeSpinner.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogReport();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //code for the boroughSpinner java
        ArrayAdapter<Borough> BoroughAdapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, Borough.values());
        BoroughAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BoroughSpinner.setAdapter(BoroughAdapter);
    }

    private void attemptLogReport() {
        //Reset Errors
        addressText.setError(null);
        zipcodeText.setError(null);
        cityText.setError(null);
        timeText.setError(null);
        inputLongitude.setError(null);
        inputLatitude.setError(null);

        //Store current values
        String address = addressText.getText().toString();
        String zipcode = zipcodeText.getText().toString();
        String city = cityText.getText().toString();
        String time = timeText.getText().toString();
        String longitude = inputLatitude.getText().toString();
        String latitude = inputLongitude.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid address
        if (TextUtils.isEmpty(address)) {
            addressText.setError(getString(R.string.error_field_required));
            focusView = addressText;
            cancel = true;
        }

        // Check for a valid zipcode
        if (TextUtils.isEmpty(zipcode)) {
            zipcodeText.setError(getString(R.string.error_field_required));
            focusView = zipcodeText;
            cancel = true;
        } else if (!isZipValid(zipcode)) {
            zipcodeText.setError(getString(R.string.error_invalid_zipcode));
            focusView = zipcodeText;
            cancel = true;
        }

        // Check for a valid city
        if (TextUtils.isEmpty(city)) {
            cityText.setError(getString(R.string.error_field_required));
            focusView = cityText;
            cancel = true;
        }

        // Check for a valid time
        if (TextUtils.isEmpty(time)) {
            timeText.setError(getString(R.string.error_field_required));
            focusView = timeText;
            cancel = true;
        } else if (!isTimeValid2(time)) {
            timeText.setError(getString(R.string.error_invalid_time));
            focusView = timeText;
            cancel = true;
        }

        if (cancel) {
            Log.e("Error", "Cancellation");
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //create the new report
            newReport.setAddress(addressText.getText().toString());
            newReport.setZipcode(Integer.parseInt(zipcodeText.getText().toString()));
            newReport.setLocationType(LocationTypeSpinner.getSelectedItem().toString());
            newReport.setBorough(BoroughSpinner.getSelectedItem().toString());
            newReport.setCity(cityText.getText().toString());
            newReport.setDateTime(new Date()); //can we trust the user. i vote no
            newReport.setLatitude(Double.valueOf(inputLatitude.getText().toString()));
            newReport.setLongitude(Double.valueOf(inputLongitude.getText().toString()));
            RatSightingAccessor accessor = new RatSightingAccessor();
            accessor.inputSighting(newReport);
            finish();
        }
    }

    /**
     * Private zipcode validation method that checks the zipcode string against a zipcode-styled regex
     * @param zipcode the zipcode string that is being validated
     * @return true if the string is a valid zipcode, false otherwise
     */
    private boolean isZipValid(String zipcode) {
        //Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return zipcode.matches("^[0-9]{5}(?:-[0-9]{4})?$");
    }

    /**
     * Private time validation method that checks the time string against a time-styled regex
     * @param time the time string that is being validated
     * @return true if the string is a valid time, false otherwise
     */
    @Deprecated
    private boolean isTimeValid(String time) {
        //Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        return time.matches("\\d{2}/\\d{2}/\\d{4}\\s\\d{2}\\d{2}\\s(?:am|AM|pm|PM)");
    }
    private boolean isTimeValid2(String time) {
        try{
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            df.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
