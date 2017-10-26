package alec.ratapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A fragment used to present the details of a rat sighting
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
     * Mandatory empty constructor for the fragment manager
     */
    public RatSightingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gets the unique ID and location in data list
        if (getArguments().containsKey(ITEM_ID)) {
            String item_id = getArguments().getString(ITEM_ID);
            Log.d("DEBUG", "Starting Fragment for sighting key: " + item_id);
            int item_loc = getArguments().getInt("loc");
            report = DataModel.getInstance().reports.get(item_loc);
            Log.d("DEBUG", "Report Fragment: " + report.getKey());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rat_sighting, container, false);
        Log.d("DEBUG", "Getting ready to set data");
        //Shows the specific data of the report in assorted text views
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
