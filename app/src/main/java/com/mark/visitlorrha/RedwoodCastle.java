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

public class RedwoodCastle extends AppCompatActivity {
    DatabaseReference mDatabase;

    //used for getting average
    float sum;
    float amountOfChildren;
    float averageRating;

    private String filename;
    private ImageView imageViewlocationRedwoodCastle;
    private ImageView imageViewAudioGuideRedwoodCastle;
    private ImageView imageViewReviewRedwoodCastle;
    private ImageView imageViewPhotosphereRedwoodCastle;
    private TextView textViewDescriptionOfRedwoodCastle;
    private Button readReviewsButtonRedwoodCastle;
    private TextView textViewOverViewOfRedwoodCastle;
    private RatingBar averageRatingBarRedwoodCastle;

    // for sign in/out
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redwood_castle);


        filename = getLocalClassName().toLowerCase(); // redwoodcastle

        mAuth = FirebaseAuth.getInstance();  // for sign in/out

        imageViewlocationRedwoodCastle = (ImageView) findViewById(R.id.imageViewLocationRedwoodCastle);
        imageViewAudioGuideRedwoodCastle = (ImageView)findViewById(R.id.imageViewAudioGuideRedwoodCastle);
        imageViewReviewRedwoodCastle = (ImageView) findViewById(R.id.imageViewReviewRedwoodCastle);
        imageViewPhotosphereRedwoodCastle = (ImageView) findViewById(R.id.imageViewPhotosphereRedwoodCastle);
        textViewDescriptionOfRedwoodCastle = (TextView) findViewById(R.id.textViewDescriptionOfRedwoodCastle);
        readReviewsButtonRedwoodCastle = (Button) findViewById(R.id.readReviewsButtonRedwoodCastle);
        textViewOverViewOfRedwoodCastle = (TextView) findViewById(R.id.textViewOverViewOfRedwoodCastle);
        averageRatingBarRedwoodCastle = (RatingBar) findViewById(R.id.averageRatingBarRedwoodCastle);

        // next part get the image adapter for the image scroll, getting the viewPager and setting the adaptor to it
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroidRedwoodCastle);
        AndroidImageAdaptorRedwoodCastle adapterView = new AndroidImageAdaptorRedwoodCastle(this);
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
                averageRatingBarRedwoodCastle.setRating(averageRating); // sets the star rating to the average rating

                textViewOverViewOfRedwoodCastle.setText("Redwood Castle has an average rating of " + averageRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("infomation failed: " + databaseError.getMessage());
            }

        });
    }


    private void launchPhotosphere() { // intent to launch photosphere
        imageViewPhotosphereRedwoodCastle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchPhotosphere = new Intent(getApplicationContext(), Photosphere.class);
                launchPhotosphere.putExtra("filename", filename); //redwoodcastle
                startActivity(launchPhotosphere);
            }
        });
    }

    private void launchReadReviews() {
        readReviewsButtonRedwoodCastle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchReadReviews = new Intent(getApplicationContext(), ReadReviews.class);
                launchReadReviews.putExtra("filename", filename); //redwoodcastle
                startActivity(launchReadReviews);
            }
        });
    }

    private void launchAudioGuide() {  // intent to launch audio guide
        imageViewAudioGuideRedwoodCastle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startAudioPage = new Intent(getApplicationContext(), AudioGuide.class);
                startAudioPage.putExtra("filename", filename); //redwoodcastle
                startActivity(startAudioPage);
            }
        });
    }

    private void launchReview() {
        imageViewReviewRedwoodCastle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddReviewActivity = new Intent(getApplicationContext(), AddReviewActivity.class);
                startAddReviewActivity.putExtra("filename", filename); //redwoodcastle
                startActivity(startAddReviewActivity);
            }
        });
    }

    private void launchMap() { // intent to show dominican abbey on a map
        imageViewlocationRedwoodCastle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchMap = new Intent(getApplicationContext(), MapsActivityRedwoodCastle.class);
                startActivity(launchMap);
            }
        });
    }

    private void lauchTextInfromationActivity() {
        textViewDescriptionOfRedwoodCastle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchTextInformationActivity = new Intent(getApplicationContext(), TextInformationActivity.class);
                launchTextInformationActivity.putExtra("filename", filename); //redwoodcastle
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
