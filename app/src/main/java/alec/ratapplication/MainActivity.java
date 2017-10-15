package alec.ratapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Log.d("DEBUG", "Reading from Firebase");

        //begin loading the rat sightings from firebase
        RatSightingAccessor.loadSightings();
        Button button =  (Button) findViewById(R.id.LogoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = (view.getContext());
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                context.startActivity(intent);
                finish();
            }
        });

        //Launches the sightings view
        Button button2 = (Button) findViewById(R.id.SightingsView);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = (view.getContext());
                Intent intent = new Intent(view.getContext(), ListSightingsActivity.class);
                context.startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.goToInput);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = (view.getContext());
                Intent intent = new Intent(view.getContext(), input_sighting_activity.class);
                context.startActivity(intent);
            }
        });
        users = new ArrayList<>();
    }

}
