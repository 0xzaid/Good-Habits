package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    //DatabaseReference fireBase;

    EditText emailText, nameText, passwordText, confirmPasswordText;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        fireBase = database.getReference("User/test");

        emailText = findViewById(R.id.signUpEmailText);
        nameText = findViewById(R.id.signUpNameText);
        passwordText = findViewById(R.id.signUpPasswordText);
        confirmPasswordText = findViewById(R.id.signUpConfirmPasswordText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signUpButtonClicked(View v) {
        String email = emailText.getText().toString().trim();
        String name = nameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();

        if (email.isEmpty()) {
            emailText.setError("Please enter an email");
            emailText.requestFocus();
        }

        if (name.isEmpty()) {
            confirmPasswordText.setError("Please confirm your password");
            confirmPasswordText.requestFocus();
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordText.setError("Please enter a password containing 6 characters");
            passwordText.requestFocus();
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordText.setError("Please confirm your password");
            confirmPasswordText.requestFocus();
        }

        if (!signUpPasswordMatch(password, confirmPassword)) {
            confirmPasswordText.setError("Passwords do not match");
            confirmPasswordText.requestFocus();
        }

        else {

            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                User user = new User(email, password, name, 0, 0, 1, 0, "N", 1, 1, true, false, false, false);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignUpActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "User Failed to Register", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(SignUpActivity.this, "User Failed to Register", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }


    public boolean signUpPasswordMatch(String pass1, String pass2) {
        boolean passwordMatch = false;
        if (pass1.equals(pass2)){
            passwordMatch = true;
        }
        return passwordMatch;
    }
}