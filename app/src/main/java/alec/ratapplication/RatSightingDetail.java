package alec.ratapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class RatSightingDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_sighting_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Log.d("DEBUG", "Setting up fragment:" + getIntent().getStringExtra(RatSightingFragment.ITEM_ID));
            Bundle arguments = new Bundle();
            arguments.putString(RatSightingFragment.ITEM_ID, getIntent().getStringExtra(RatSightingFragment.ITEM_ID));
            arguments.putInt("loc", getIntent().getIntExtra("loc", 0));
            RatSightingFragment fragment = new RatSightingFragment();
            fragment.setArguments(arguments);
            Log.d("DEBUG", "Begining fragment transaction");
            getSupportFragmentManager().beginTransaction().add(R.id.rat_sighting_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
