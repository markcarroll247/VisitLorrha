package com.mark.visitlorrha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddReviewActivity extends AppCompatActivity {

    float valueOfRating = 0; // the rating from the stars

    private ImageButton mSelectImage;

    private static final int GALLERY_REQUEST = 1;
    private EditText mPostTitle;
    private EditText mPostDesc;

    private RatingBar mRatingBar;

    private Button mSubmitBtn;



    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    String filename;  // use the filename to name the child of the VisitLorrha database

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if(b != null){
            filename = (String) b.get("filename"); // domincanabbey
        }

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){ //user is NOT logged in
            // intetn to profile activity
            Intent logInActivity = new Intent(AddReviewActivity.this, LogInActivity.class); // if NOT logged in, go to review page
            Toast.makeText(this, "You need to log in to post a review", Toast.LENGTH_LONG).show();
            startActivity(logInActivity);
            finish(); // removes this activity from the stack, preventing user from pressin back button in "log in" to come back to this activity
        }

        // need to put in a check here, if logged in or not, if not, redireced to log in screen, if logged in, contineu on this actavity

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(filename); //rename this to LorrhaReview i guess or would it be filename so it is different for each attraction

        // mSelectImage = (ImageButton) findViewById(R.id.imageSelecet);
        mPostTitle = (EditText) findViewById(R.id.titleField);
        mPostDesc = (EditText) findViewById(R.id.descField);
        mRatingBar = (RatingBar) findViewById(R.id.ratingfield); // finds the rating bar

        mSubmitBtn = (Button) findViewById(R.id.submitPostBtn);

        mProgress = new ProgressDialog(this);

        // setting the star rating bar
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                valueOfRating = rating; // this is for passing it onto the database
            }
        });



        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows the up/back button in the action bar


    }




    private void startPosting() {


        final String title_val = mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();

        // && mImageUri != null
        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val)  && mRatingBar != null){ // everything must be filled in
            //uploading process
            mProgress.setMessage("Posting to Visit Lorrha...");
            mProgress.show();



            DatabaseReference newPost = mDatabase.push();// creates a unique random id
            newPost.child("title").setValue(title_val);  // would be renamed to username
            newPost.child("desc").setValue(desc_val);   // this can stay the same
            newPost.child("rating").setValue(valueOfRating);  // this can stay the same

            mProgress.dismiss();

            startActivity(new Intent(AddReviewActivity.this, MainActivity.class));

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish(); // so when the user posts a review, they are returend to the attraction page - this
        // prevents them pressing the back button and submitting the same review twice.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
