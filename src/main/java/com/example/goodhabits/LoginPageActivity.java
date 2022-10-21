package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginPageActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailText = findViewById(R.id.logInEmailText);
        passwordText = findViewById(R.id.logInPasswordText);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogIn);

        mAuth = FirebaseAuth.getInstance();
    }

    public void goToSignUp(View v)
    {
        Intent myIntent = new Intent(this, SignUpActivity.class);
        startActivity(myIntent);
    }

    public void goToHomepage(View v)
    {
        Intent myIntent = new Intent(this, HomepageActivity.class);
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please Enter a Valid Email");
            emailText.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginPageActivity.this, "User has Successfully Signed In", Toast.LENGTH_LONG).show();
                    startActivity(myIntent);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginPageActivity.this, "User has Failed Sign In", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}