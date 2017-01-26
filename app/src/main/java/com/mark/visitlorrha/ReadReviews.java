package com.mark.visitlorrha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReadReviews extends AppCompatActivity {

    private RecyclerView mReviewList; // was mBlogList

    private DatabaseReference mDatabase;

    private String filename;
    TextView textViewReadReviewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_reviews);

        textViewReadReviewTitle = (TextView) findViewById(R.id.textViewReadReviewTitle);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if(b != null){
            filename = (String) b.get("filename"); // domincanabbey
        }

        textViewReadReviewTitle.setText("Reviews of #" + filename); // unless i also send in a string of it without being lower case?

        // getting review information from Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child(filename); // gives the reference to the root url, ie the link to the database using the filename of the activity, eg dominicanabbey

        mReviewList = (RecyclerView) findViewById(R.id.review_list);
        mReviewList.setHasFixedSize(true);
        mReviewList.setLayoutManager(new LinearLayoutManager(this)); //vertical format
        // end of getting review stuff

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows the up/back button in the action bar

    }

    @Override
    protected void onStart() {
        super.onStart();
        filename = getLocalClassName().toLowerCase(); // dominicanabbey

        // need a view holder to make recycleView, Will be using ReviewHolder below
        FirebaseRecyclerAdapter<Review, ReviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Review, ReviewHolder>(
                Review.class,
                R.layout.blog_row,
                ReviewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ReviewHolder viewHolder, Review model, int position) {
                // using the Review activity
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setRating(model.getRating());
            }
        };

        mReviewList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class ReviewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ReviewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title){

            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }

        public void setDesc(String desc){

            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);

        }

        public void setRating(float rating){
            RatingBar post_rating = (RatingBar) mView.findViewById(R.id.post_ratingBar);
            post_rating.setRating(rating);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // shows the up/back button in the action bar
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
