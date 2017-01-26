package com.mark.visitlorrha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        buttonLogIn = (Button) findViewById(R.id.logInBtn);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

        onClickTextViewSignUp();

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogIn();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows the up/back button in the action bar

    }

    private void onClickTextViewSignUp() {
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSignUpActivity = new Intent(LogInActivity.this, SignUpActivity.class); // doesnt have an account so goes to Sign Up
                startActivity(startSignUpActivity);
            }
        });
    }

    private void userLogIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || email.length() > 50 ){ // must not be empty and have less than 50 characters
            // error in email
            Toast.makeText(this, "Check email credentials", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password) || password.length() < 8 || password.length() > 20){ // 8 characters min, 20 characters max // does this work okay?
            // error in password
            Toast.makeText(this, "Check password credentials", Toast.LENGTH_LONG).show();
            return;
        }
        // if it is accepted, display  a progress dialog
        mProgressDialog.setMessage("Logging in ...");
        mProgressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // called when sing in is compete
                mProgressDialog.dismiss();
                if(task.isSuccessful()){
                    Intent startMainActivity = new Intent(LogInActivity.this, MainActivity.class);  // when sign in complete, redirect to MainActivity
                    startActivity(startMainActivity);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // shows the up/back button in the action bar
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
