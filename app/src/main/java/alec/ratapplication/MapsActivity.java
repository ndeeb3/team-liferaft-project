package alec.ratapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

/**
 * Shows users the current rat sightings on a map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Serializable {

    private GoogleMap mMap;
    private List<RatSightingReport> sightings;
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        sightings = DataModel.getInstance().reports;
        if (savedInstanceState == null) { //without instance data it will check extras
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                sightings = (List) extras.getSerializable("filtered");
            }
        } else {
             sightings = (List) savedInstanceState.getSerializable("filtered"); //retrieve list
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, filterMenuActivity.class);
                intent.putExtra("activity", "Maps"); //pass into maps
                startActivity(intent);
            }
        });
    }


    private LatLng myLatLng;
    private void setLatLng(double latitude, double longitude) {
        myLatLng = new LatLng(latitude,longitude);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng nyc = new LatLng(40.7128, -74.0060);
        //getLocation();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DataModel.getInstance().location, 10));
        for (RatSightingReport report : sightings) {
            double latitude = report.getLatitude();
            double longitude = report.getLongitude();
            if (latitude != 0 && longitude != 0 && report.getDateTime() != null) {
                LatLng newMarker = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(newMarker).title("Report at "
                        + report.getAddress()).snippet(report.toString()));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(newMarker));
                //mMap.addMarker(new MarkerOptions().position(loc).title(r.getName()).snippet(r.getDescription()));
            }
        }
        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;
        @SuppressLint("InflateParams")
        CustomInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = (myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = (myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
