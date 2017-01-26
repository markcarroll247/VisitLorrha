package com.mark.visitlorrha;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StRuadhansAbbey extends AppCompatActivity {

    DatabaseReference mDatabase;

    //used for getting average
    float sum;
    float amountOfChildren;
    float averageRating;

    private String filename;
    private ImageView imageViewlocationStRuadhansAbbey;
    private ImageView imageViewAudioGuideStRuadhansAbbey;
    private ImageView imageViewReviewStRuadhansAbbey;
    private ImageView imageViewPhotosphereStRuadhansAbbey;
    private TextView textViewDescriptionOfStRuadhansAbbey;
    private Button readReviewsButtonStRuadhansAbbey;
    private TextView textViewOverViewOfStRuadhansAbbey;
    private RatingBar averageRatingBarStRuadhansAbbey;

    // for sign in/out
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_ruadhans_abbey);

        filename = getLocalClassName().toLowerCase(); // struadhansabbey

        mAuth = FirebaseAuth.getInstance();  // for sign in/out

        imageViewlocationStRuadhansAbbey = (ImageView) findViewById(R.id.imageViewLocationStRuadhansAbbey);
        imageViewAudioGuideStRuadhansAbbey = (ImageView)findViewById(R.id.imageViewAudioGuideStRuadhansAbbey);
        imageViewReviewStRuadhansAbbey = (ImageView) findViewById(R.id.imageViewReviewStRuadhansAbbey);
        imageViewPhotosphereStRuadhansAbbey = (ImageView) findViewById(R.id.imageViewPhotosphereStRuadhansAbbey);
        textViewDescriptionOfStRuadhansAbbey = (TextView) findViewById(R.id.textViewDescriptionOfStRuadhansAbbey);
        readReviewsButtonStRuadhansAbbey = (Button) findViewById(R.id.readReviewsButtonStRuadhansAbbey);
        textViewOverViewOfStRuadhansAbbey = (TextView) findViewById(R.id.textViewOverViewOfStRuadhansAbbey);
        averageRatingBarStRuadhansAbbey = (RatingBar) findViewById(R.id.averageRatingBarStRuadhansAbbey);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroidStRuadhansAbbey);
        AndroidImageAdaptorStRuadhansAbbey adapterView = new AndroidImageAdaptorStRuadhansAbbey(this);
        mViewPager.setAdapter(adapterView);

        getAverageReviewScore(); // displays and fills the rating bar at the bottom of the activity
        launchMap(); // intent to show augustinian abbey on a map
        launchAudioGuide();  // intent to launch audio guide
        launchReview(); // launches a "add review" activity
        launchReadReviews(); // launches activity with a recycler view of all review of that attraction
        launchPhotosphere(); // intent to launch photosphere
        lauchTextInfromationActivity(); // launches an activity full of text information about the attraction

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows the up/back button in the action bar

    }

    private void getAverageReviewScore() {

        mDatabase = FirebaseDatabase.getInstance().getReference().child(filename);

        averageRating = 0;



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                amountOfChildren = (float) dataSnapshot.getChildrenCount(); // get the number of children used to divide by and get the average star rating
                System.out.println("the number of children in " + filename + " is: " + amountOfChildren);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review ratingScore = snapshot.getValue(Review.class); // referring back to the Review Class
                    sum = sum + ratingScore.getRating();
                }
                averageRating = sum / amountOfChildren; // gets the average rating
                averageRating = (float) (Math.round(averageRating*2)/2.0); // rounding to the nearest .5
                averageRatingBarStRuadhansAbbey.setRating(averageRating); // sets the star rating to the average rating

                textViewOverViewOfStRuadhansAbbey.setText("St Ruadhan's Abbey has an average rating of " + averageRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("infomation failed: " + databaseError.getMessage());
            }

        });
    }


    private void launchPhotosphere() { // intent to launch photosphere
        imageViewPhotosphereStRuadhansAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchPhotosphere = new Intent(getApplicationContext(), Photosphere.class);
                launchPhotosphere.putExtra("filename", filename); //struadhansabbey
                startActivity(launchPhotosphere);
            }
        });
    }

    private void launchReadReviews() {
        readReviewsButtonStRuadhansAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchReadReviews = new Intent(getApplicationContext(), ReadReviews.class);
                launchReadReviews.putExtra("filename", filename); //struadhansabbey
                startActivity(launchReadReviews);
            }
        });
    }

    private void launchAudioGuide() {  // intent to launch audio guide
        imageViewAudioGuideStRuadhansAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startAudioPage = new Intent(getApplicationContext(), AudioGuide.class);
                startAudioPage.putExtra("filename", filename); //struadhansabbey
                startActivity(startAudioPage);
            }
        });
    }

    private void launchReview() {
        imageViewReviewStRuadhansAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddReviewActivity = new Intent(getApplicationContext(), AddReviewActivity.class);
                startAddReviewActivity.putExtra("filename", filename); //struadhansabbey
                startActivity(startAddReviewActivity);
            }
        });
    }

    private void launchMap() {
        imageViewlocationStRuadhansAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchMap = new Intent(getApplicationContext(), MapsActivityStRuadhansAbbey.class);
                startActivity(launchMap);
            }
        });
    }

    private void lauchTextInfromationActivity() {
        textViewDescriptionOfStRuadhansAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchTextInformationActivity = new Intent(getApplicationContext(), TextInformationActivity.class);
                launchTextInformationActivity.putExtra("filename", filename); //struadhansabbey
                startActivity(launchTextInformationActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_settings);
        if(mAuth.getCurrentUser() == null){ // this menu option changes depending if the user is logged in or not from 'log in' to 'sign out'
            menuItem.setTitle("Log in");
        }else {
            menuItem.setTitle("Sign out");
        }
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =
                        "I've had a wonderful adventure in Lorrha. You should have a look \nDiscover Lorrha, discover the past.\n" +
                                "https://play.google.com/store/apps/details?id=com.mark.visitlorrha";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Visit Lorrha"); // e.g. the subject line in an email
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody); // the content to be sent/shared
                startActivity(Intent.createChooser(sharingIntent, "Share via")); // the pop up share box displays this
                return true;


            case android.R.id.home: // the back/up button
                finish();
                break;

            case R.id.action_settings:
                if(mAuth.getCurrentUser() == null){ // not signed in
                    startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                    return true;
                }else{
                    mAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_LONG).show();
                    return true;
                }

        }

        return super.onOptionsItemSelected(item);
    }


}
