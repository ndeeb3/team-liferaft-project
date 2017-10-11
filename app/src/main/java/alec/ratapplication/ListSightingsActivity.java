package alec.ratapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ListSightingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sightings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sightings_View);
        //setupRecyclerView((RecyclerView) recyclerView);
        Log.d("DEBUG", "setting up recycler view");
        recyclerView.setAdapter(new RatRecyclerViewAdapter(RatSightingAccessor.reports));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RatRecyclerViewAdapter(RatSightingAccessor.reports));
    }

    public class RatRecyclerViewAdapter
            extends RecyclerView.Adapter<RatRecyclerViewAdapter.RatViewHolder> {

        private List<RatSightingReport> sightings;
        private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

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
            Log.d("Debug", "Generating new ViewHolder");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rat_sighting_list_content, parent, false);
            return new RatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RatViewHolder holder, int position) {

            holder.sighting = sightings.get(position);
            holder.addressView.setText("" + sightings.get(position).getAddress());
            holder.timeView.setText(sightings.get(position).getKey());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RatSightingDetail.class);
                        Log.d("DEBUG", "Switch to detailed view for rat sighting: " + holder.sighting.getKey());
                        intent.putExtra(RatSightingFragment.ITEM_ID, holder.sighting.getKey());
                        //intent.putExtra("sighting", holder.sighting);
                        context.startActivity(intent);
                    }
                }
            );
        }

        public class RatViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView addressView;
            public final TextView timeView;
            public RatSightingReport sighting;

            public RatViewHolder(View view) {
                super(view);
                mView = view;
                addressView = (TextView) view.findViewById(R.id.address);
                timeView = (TextView) view.findViewById(R.id.time);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + addressView.getText() + "'";
            }
        }
    }
}
