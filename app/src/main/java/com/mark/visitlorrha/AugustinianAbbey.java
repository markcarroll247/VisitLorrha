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

public class AugustinianAbbey extends AppCompatActivity {


    DatabaseReference mDatabase;

    //used for getting average rating
    float sum;
    float amountOfChildren;
    float averageRating;


    private String filename;
    private ImageView imageViewlocationAugustinianAbbey;
    private ImageView imageViewAudioGuideAugustinianAbbey;
    private ImageView imageViewReviewAugustinianAbbey;
    private ImageView imageViewPhotosphereAugustinianAbbey;
    private TextView textViewDescriptionOfAugustinianAbbey;
    private Button readReviewsButtonAugustinianAbbey;
    private TextView textViewOverViewOfAugustinianAbbey;
    private RatingBar averageRatingBarAugustinianAbbey;

    // for sign in/out functionality
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augustinian_abbey);

        filename = getLocalClassName().toLowerCase(); // augustinianabbey

        mAuth = FirebaseAuth.getInstance();  // for sign in/out

        imageViewlocationAugustinianAbbey = (ImageView) findViewById(R.id.imageViewLocationAugustinianAbbey);
        imageViewAudioGuideAugustinianAbbey = (ImageView)findViewById(R.id.imageViewAudioGuideAugustinianAbbey);
        imageViewReviewAugustinianAbbey = (ImageView) findViewById(R.id.imageViewReviewAugustinianAbbey);
        imageViewPhotosphereAugustinianAbbey= (ImageView) findViewById(R.id.imageViewPhotosphereAugustinianAbbey);
        textViewDescriptionOfAugustinianAbbey = (TextView) findViewById(R.id.textViewDescriptionOfAugustinianAbbey);
        readReviewsButtonAugustinianAbbey = (Button) findViewById(R.id.readReviewsButtonAugustinianAbbey);
        textViewOverViewOfAugustinianAbbey = (TextView) findViewById(R.id.textViewOverViewOfAugustinianAbbey);
        averageRatingBarAugustinianAbbey = (RatingBar) findViewById(R.id.averageRatingBarAugustinianAbbey);

        // next part get the image adapter for the image scroll, getting the viewPager and setting the adaptor to it
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroidAugustinianAbbey);
        AndroidImageAdaptorAugustinianAbbey adapterView = new AndroidImageAdaptorAugustinianAbbey(this);
        mViewPager.setAdapter(adapterView);
        // end of the image adapter part

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
        // testing average bar rating thing
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
                averageRatingBarAugustinianAbbey.setRating(averageRating); // sets the star rating to the average rating

                textViewOverViewOfAugustinianAbbey.setText("Augustinian Abbey has an average rating of " + averageRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("information failed: " + databaseError.getMessage());
            }

        });
    }

    private void launchPhotosphere() { // intent to launch photosphere
        imageViewPhotosphereAugustinianAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchPhotosphere = new Intent(getApplicationContext(), Photosphere.class);
                launchPhotosphere.putExtra("filename", filename); //augustinianabbey
                startActivity(launchPhotosphere);
            }
        });
    }

    private void launchReadReviews() {
        readReviewsButtonAugustinianAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchReadReviews = new Intent(getApplicationContext(), ReadReviews.class);
                launchReadReviews.putExtra("filename", filename); //augustinianabbey
                startActivity(launchReadReviews);
            }
        });
    }

    private void launchAudioGuide() {  // intent to launch audio guide
        imageViewAudioGuideAugustinianAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAudioPage = new Intent(getApplicationContext(), AudioGuide.class);
                startAudioPage.putExtra("filename", filename); //augustinianabbey
                startActivity(startAudioPage);
            }
        });
    }

    private void launchReview() {
        imageViewReviewAugustinianAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddReviewActivity = new Intent(getApplicationContext(), AddReviewActivity.class);
                startAddReviewActivity.putExtra("filename", filename); //augustinianabbey
                startActivity(startAddReviewActivity);
            }
        });
    }

    private void launchMap() { // intent to load map
        imageViewlocationAugustinianAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchMap = new Intent(getApplicationContext(), MapsActivityAugustinianAbbey.class);
                startActivity(launchMap);
            }
        });
    }

    private void lauchTextInfromationActivity() {
        textViewDescriptionOfAugustinianAbbey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchTextInformationActivity = new Intent(getApplicationContext(), TextInformationActivity.class);
                launchTextInformationActivity.putExtra("filename", filename); //augustinianabbey
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
