package com.mark.visitlorrha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    TextView textViewLogIn;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerButton = (Button) findViewById(R.id.registerBtn);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewLogIn = (TextView) findViewById(R.id.textViewLogIn);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){ //user is already logged in
            Toast.makeText(getApplicationContext(), "You are already signed in", Toast.LENGTH_LONG).show();
            Intent mainActivityIntent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
        }

        mProgressDialog = new ProgressDialog(this);

        onButtonRegisterClicked();

        textViewLogIn.setOnClickListener(new View.OnClickListener() { // already have an account, so goes to the log in page
            @Override
            public void onClick(View view) {
                // intent go to sign in page
                Intent profileActivityIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(profileActivityIntent);
            }
        });


    }

    private void onButtonRegisterClicked() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
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
        // if it is okay display a progress dialog
        mProgressDialog.setMessage("Registering User...");
        mProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // will run when registeraion is completed
                    Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent profileActivityIntent = new Intent(SignUpActivity.this, MainActivity.class); // returns to the main activity
                    startActivity(profileActivityIntent);
                }else{

                    Toast.makeText(SignUpActivity.this, "could not register, try again", Toast.LENGTH_LONG).show();

                }
                mProgressDialog.dismiss(); // ends diaglog
            }
        });

    }
}
