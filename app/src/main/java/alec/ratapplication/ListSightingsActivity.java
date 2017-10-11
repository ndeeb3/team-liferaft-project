package alec.ratapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import java.util.List;


public class ListSightingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list_sightings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        RecyclerView recylerView = (RecyclerView) findViewById(R.id.RatSightingsRecycler);
        assert recylerView != null;
        setupRecyclerView(recylerView);

    }
    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        FakeDatabase model = FakeDatabase.getInstance();
        SimpleSightingsRecyclerViewAdapter adapter = new SimpleSightingsRecyclerViewAdapter((model.reportList));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Course object to a text field.
     */
    public class SimpleSightingsRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleSightingsRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<RatSightingReport> sightings;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public SimpleSightingsRecyclerViewAdapter(List<RatSightingReport> items) {
            sightings = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sighting_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final FakeDatabase model = FakeDatabase.getInstance();
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.report = sightings.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.myKeyView.setText("" + sightings.get(position).getKey());

            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mTwoPane) {
//                        //if a two pane window, we change the contents on the main screen
//                        Bundle arguments = new Bundle();
//                        arguments.putInt(CourseDetailFragment.ARG_COURSE_ID, holder.mCourse.getId());
//
//                        CourseDetailFragment fragment = new CourseDetailFragment();
//                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.course_detail_container, fragment)
//                                .commit();
//                    } else {
//                        //on a phone, we need to change windows to the detail view
//                        Context context = v.getContext();
//                        //create our new intent with the new screen (activity)
//                        Intent intent = new Intent(context, CourseDetailActivity.class);
//                        /*
//                            pass along the id of the course so we can retrieve the correct data in
//                            the next window
//                         */
//                        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, holder.mCourse.getId());
//
//                        model.setCurrentCourse(holder.mCourse);
//
//                        //now just display the new window
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return sightings.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView myKeyView;
            public RatSightingReport report;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                myKeyView = view.findViewById(R.id.sighting_detail);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + myKeyView.getText() + "'";
            }
        }
    }
}