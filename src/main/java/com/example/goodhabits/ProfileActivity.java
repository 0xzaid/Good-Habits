package com.example.goodhabits;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
    String userName, userEmail, userPassword, userCurrency, userPoints, userPic;
    TextInputLayout nameProfile, emailProfile, passwordProfile, currencyProfile, pointsProfile;

    ImageView profPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");

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
                    nameProfile = findViewById(R.id.full_name_profile);
                    nameProfile.getEditText().setText(userName);

                    userEmail = snapshot.child(userID).child("email").getValue(String.class);
                    emailProfile = findViewById(R.id.email_profile);
                    emailProfile.getEditText().setText(userEmail);

                    userPassword = snapshot.child(userID).child("password").getValue(String.class);
                    passwordProfile = findViewById(R.id.password_profile);
                    passwordProfile.getEditText().setText(userPassword);

                    userCurrency = snapshot.child(userID).child("currency").getValue().toString();
                    TextView balance = findViewById(R.id.balance_label);
                    balance.setText(userCurrency);

                    userPoints = snapshot.child(userID).child("points").getValue().toString();
                    TextView points = findViewById(R.id.points_label);
                    points.setText(userPoints);

                    userPic = snapshot.child(userID).child("profile").getValue().toString();
                    profPic = findViewById(R.id.profile_image);
                    profPic.setImageResource(new ProfilePicture(Integer.parseInt(userPic)).pic);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened in ProfileActivity", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void update(View view) {
        // change one of the fields
        if(isNameChanged() || isPasswordChanged() || isEmailChanged()) {
            Toast.makeText(ProfileActivity.this, "Data has been updated", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(ProfileActivity.this, "Data is the same and cannot be updated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNameChanged() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        if(!userName.equals(nameProfile.getEditText().getText().toString())) {
            reference.child(userID).child("name").setValue(nameProfile.getEditText().getText().toString());
            userName = nameProfile.getEditText().getText().toString();

            return true;
        } else {
            return false;
        }
    }
    private boolean isEmailChanged() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        if(!userEmail.equals(emailProfile.getEditText().getText().toString())) {
            // update auth
            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(userEmail, userPassword);
            user1.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "User re-authenticated.");

                            // Change email in auth
                            FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
                            user2.updateEmail(emailProfile.getEditText().getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                            }
                                        }
                                    });
                        }
                    });


            // update database
            reference.child(userID).child("email").setValue(emailProfile.getEditText().getText().toString());
            userEmail = emailProfile.getEditText().getText().toString();

            return true;
        } else {
            return false;
        }
    }
    private boolean isPasswordChanged() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        if(!userPassword.equals(passwordProfile.getEditText().getText().toString())) {
            // update auth
            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(userEmail, userPassword);
            user1.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "User re-authenticated.");

                            // Change email in auth
                            FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();user2.updatePassword(passwordProfile.getEditText().getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User password updated.");
                                            }
                                        }
                                    });
                        }
                    });

            // update database
            reference.child(userID).child("password").setValue(passwordProfile.getEditText().getText().toString());
            userPassword = passwordProfile.getEditText().getText().toString();

            return true;
        } else {
            return false;
        }
    }

}