package alec.ratapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A menu which lists available rat sightings
 */
public class ListSightingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sightings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Sets up the recycler view, adding the adapter and layout manager
        RecyclerView recyclerView = findViewById(R.id.sightings_View);
        Log.d("DEBUG", "setting up recycler view");
        recyclerView.setAdapter(new RatRecyclerViewAdapter(DataModel.getInstance().reports));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
    }

    /**
     * Inner adapter class for the recycler view
     * sets up the formatting of the list and what to do when an item is clicked on
     */
    public class RatRecyclerViewAdapter
            extends RecyclerView.Adapter<RatRecyclerViewAdapter.RatViewHolder> {

        private final List<RatSightingReport> sightings;
        //private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        public RatRecyclerViewAdapter(List<RatSightingReport> reports) {
            sightings = reports;
            Log.d("DEBUG", sightings.toString());
            //notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return sightings.size();
        }

        @Override
        public RatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Log.d("Debug", "Generating new ViewHolder");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rat_sighting_list_content, parent, false);
            return new RatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RatViewHolder holder, int position) {
            //Sets up the views in the list using the holder
            holder.sighting = sightings.get(position);
            holder.addressView.setText(sightings.get(position).getAddress());
            if (sightings.get(position).getDateTime() == null) {
                holder.timeView.setText("no date");
            } else {
                holder.timeView.setText(sightings.get(position).getDateTime().toString());
            }

            //Opens a new detailed sighting view
            //also passes in the display fragment and position of the clicked item
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RatSightingDetail.class);
                        Log.d("DEBUG", "Switch to detailed view for rat sighting key: " + holder.sighting.getKey());
                        intent.putExtra(RatSightingFragment.ITEM_ID, holder.sighting.getKey());
                        intent.putExtra("loc", holder.getAdapterPosition());
                        context.startActivity(intent);
                    }
                }
            );
        }

        /**
         * inner class for the RatRecyclerViewAdapter
         * actually sets up the text views for the list
         */
        public class RatViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView addressView;
            public final TextView timeView;
            public RatSightingReport sighting;

            public RatViewHolder(View view) {
                super(view);
                mView = view;
                addressView = view.findViewById(R.id.address);
                timeView = view.findViewById(R.id.time);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + addressView.getText() + "'";
            }
        }
    }
}
