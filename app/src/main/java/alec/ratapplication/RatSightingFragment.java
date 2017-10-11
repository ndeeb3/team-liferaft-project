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
    private RatSightingReport mItem;

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
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ITEM_ID);
            Log.d("RAT SIGHTING FRAGMENT", "Start details for: " + item_id);
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
        View rootView = inflater.inflate(R.layout.content_rat_sighting_detail, container, false);
        Log.d("MYAPP", "Getting ready to set data");
        //Shows the specific data in text views
        if (mItem != null) {
            Log.d("MYAPP", "Getting ready to set Things");
            ((TextView) rootView.findViewById(R.id.address_fragment)).setText(R.string.prompt_username);
            ((TextView) rootView.findViewById(R.id.Time)).setText(R.string.prompt_username);
            ((TextView) rootView.findViewById(R.id.key)).setText(R.string.prompt_username);
            ((TextView) rootView.findViewById(R.id.location)).setText(R.string.prompt_username);
        }
        return rootView;
    }
}
