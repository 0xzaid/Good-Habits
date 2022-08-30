package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    String userName, userEmail, userPassword, userCurrency, userPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Users ");
//        userID = user.getUid();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");


//        final TextView fullNameTextLayout = (TextView) findViewById(R.id.full_name_profile);
//        final TextView emailTextLayout = (TextView) findViewById(R.id.email_profile);
//        final TextView passwordTextLayout = (TextView) findViewById(R.id.password_profile);


        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // Display the user name at the top of the page
                    userName = snapshot.child(userID).child("name").getValue(String.class);
                    TextView nameField = findViewById(R.id.full_name_field);
                    nameField.setText(userName);
                    //TextView nameProfile = findViewById(R.id.display_Name);

                    // Display the user profile information
                    userName = snapshot.child(userID).child("name").getValue(String.class);
                    TextInputLayout nameProfile = findViewById(R.id.full_name_profile);
                    nameProfile.getEditText().setText(userName);

                    userEmail = snapshot.child(userID).child("email").getValue(String.class);
                    TextInputLayout emailProfile = findViewById(R.id.email_profile);
                    emailProfile.getEditText().setText(userEmail);

                    userPassword = snapshot.child(userID).child("password").getValue(String.class);
                    TextInputLayout passwordProfile = findViewById(R.id.password_profile);
                    passwordProfile.getEditText().setText(userPassword);

                    userCurrency = snapshot.child(userID).child("currency").getValue().toString();
                    TextView balance = findViewById(R.id.balance_label);
                    balance.setText(userCurrency);

                    userPoints = snapshot.child(userID).child("points").getValue().toString();
                    TextView points = findViewById(R.id.points_label);
                    points.setText(userPoints);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened in ProfileActivity", Toast.LENGTH_LONG).show();
            }
        });

    }
}