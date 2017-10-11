package alec.ratapplication;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 *
 */
public class RatSightingFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ITEM_ID = "item_id";

    /**
     * The sighting this fragment is presenting.
     */
    private RatSightingReport report;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RatSightingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ITEM_ID)) {
            String item_id = getArguments().getString(ITEM_ID);
            Log.d("DEBUG", "Starting Fragment for sighting key: " + item_id);
            int item_loc = getArguments().getInt("loc");
            report = RatSightingAccessor.reports.get(item_loc);
            Log.d("DEBUG", "Report Fragment: " + report.getKey());
            //mItem = SimpleModel.INSTANCE.findItemById(item_id);
            //Activity activity = this.getActivity();
            //CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            /*if (appBarLayout != null) {
                appBarLayout.setTitle("Temp");
            }*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rat_sighting, container, false);
        Log.d("DEBUG", "Getting ready to set data");
        //Shows the specific data in text views
        if (report != null) {
            Log.d("DEBUG", "Getting ready to set Things");
            ((TextView) rootView.findViewById(R.id.address_fragment)).setText("Address: " + report.getAddress());
            ((TextView) rootView.findViewById(R.id.borough)).setText("Borough: " + report.getBorough());
            ((TextView) rootView.findViewById(R.id.latitude)).setText("Latitude: " + Double.toString(report.getLatitude()));
            ((TextView) rootView.findViewById(R.id.longitude)).setText("Longitude: " + Double.toString(report.getLongitude()));
            ((TextView) rootView.findViewById(R.id.city)).setText("City: " + report.getCity());
            ((TextView) rootView.findViewById(R.id.zipcode)).setText("Zip: " + Integer.toString(report.getZipcode()));
            ((TextView) rootView.findViewById(R.id.time_fragment)).setText("Date: " + report.getDateTime());
            ((TextView) rootView.findViewById(R.id.unique_key)).setText("Unique Report Key: " + report.getKey());
            ((TextView) rootView.findViewById(R.id.location)).setText("Location: " + report.getLocationType());
        }
        return rootView;
    }
}
