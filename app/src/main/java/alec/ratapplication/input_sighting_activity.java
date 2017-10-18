package alec.ratapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;

import java.text.DateFormat;
import java.util.Date;

public class input_sighting_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner LocationTypeSpinner;
    private Spinner BoroughSpinner;
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

        LocationTypeSpinner = (Spinner) findViewById(R.id.LocationTypeSpinner);
        final EditText addressText = (EditText) findViewById(R.id.addressInput);
        final EditText zipcodeText = (EditText) findViewById(R.id.zipcodeInput);
        final EditText cityText = (EditText) findViewById(R.id.cityInput);
        final EditText timeText = (EditText) findViewById(R.id.timeInput);
        final String time = DateFormat.getDateTimeInstance().format(new Date());
        timeText.setText(time);
        final EditText inputLatitude = (EditText) findViewById(R.id.inputLatitude);
        final EditText inputLongitude = (EditText) findViewById(R.id.inputLongitude);


        ArrayAdapter<LocationType> adapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, LocationType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationTypeSpinner.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(input_sighting_activity.this, MainActivity.class);
                newReport.setAddress(addressText.getText().toString());
//                Log.d("INFO", "The address: " + addressText.getText().toString());
                newReport.setZipcode(Integer.parseInt(zipcodeText.getText().toString()));
//                Log.d("INFO", "The zipcode" + zipcodeText.getText().toString());
                newReport.setLocationType(LocationTypeSpinner.getSelectedItem().toString());
//                Log.d("INFO", "The location type" + ((LocationType) LocationTypeSpinner.getSelectedItem()).getValue());
                newReport.setBorough(BoroughSpinner.getSelectedItem().toString());
//                Borough bor = (Borough) BoroughSpinner.getSelectedItem();
//                Log.d("INFO", "The borough" + bor.toString());
                newReport.setCity(cityText.getText().toString());
                newReport.setDateTime(timeText.getText().toString());
                newReport.setLatitude(Double.valueOf(inputLatitude.getText().toString()));
                newReport.setLongitude(Double.valueOf(inputLongitude.getText().toString()));

                //startActivity(intent);
                RatSightingAccessor accessor = new RatSightingAccessor();
                accessor.inputSighting(newReport);
                finish();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(input_sighting_activity.this, MainActivity.class);
                startActivity(intent);*/
                finish();

            }
        });

        //code for the boroughSpinner java
        BoroughSpinner = (Spinner) findViewById(R.id.BoroughSpinner);
        ArrayAdapter<Borough> BoroughAdapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, Borough.values());
        BoroughAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BoroughSpinner.setAdapter(BoroughAdapter);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
