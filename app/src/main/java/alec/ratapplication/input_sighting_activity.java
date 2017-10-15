package alec.ratapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

public class input_sighting_activity extends AppCompatActivity {
    public String address;
    private Spinner LocationTypeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        final EditText editText = (EditText) findViewById(R.id.addressInput);

        ArrayAdapter<LocationType> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, LocationType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationTypeSpinner.setAdapter(adapter);

//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                boolean handled = true;
//                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    handled = true;
//                    address = editText.getText().toString();
//                    Log.d("INFO", address);
//                }
//                return handled;
//            }
//        }) ;

        Button button= (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ERROR", editText.getText().toString());
                Log.d("ERROR", (String) LocationTypeSpinner.getSelectedItem());
            }
        });



    }

}
