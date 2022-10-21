package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {
    TextView nameText, pointsText, rankingText, levelText, nameProfile, userStreak;
    ImageView profPic;
    String userName, userPoints, userRanking, userPic, userLevel;
    String userStreakInt;



    DrawerLayout drawerlayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Intent loginPageIntent;
    Intent shopPageIntent;
    Intent leaderboardPageIntent;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        Button qBtn1 = findViewById(R.id.quizButton1);
        Button qBtn2 = findViewById(R.id.quizButton2);
        Button qBtn3 = findViewById(R.id.quizButton3);


        qBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, QuizActivity.class));
            }
        });
        qBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, QuizActivity.class));
            }
        });
        qBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, QuizActivity.class));
            }
        });

        loginPageIntent = new Intent(this, LoginPageActivity.class);
        shopPageIntent = new Intent(this, ShopActivity.class);
        leaderboardPageIntent = new Intent(this, LeaderboardActivity.class);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        drawerlayout = findViewById(R.id.dl);
        navigationView = findViewById(R.id.nv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userName = snapshot.child(userID).child("name").getValue(String.class);
                    nameText = findViewById(R.id.nameHomepage);
                    nameText.setText("Name: " + userName);

                    userPoints = snapshot.child(userID).child("points").getValue().toString();
                    pointsText = findViewById(R.id.pointsHomepage);
                    pointsText.setText("Points: " + userPoints);

                    userRanking = snapshot.child(userID).child("ranking").getValue().toString();
                    rankingText = findViewById(R.id.rankingHomepage);
                    rankingText.setText("Rank: " + userRanking);

                    userLevel = snapshot.child(userID).child("level").getValue().toString();
                    levelText = findViewById(R.id.levelHomepage);
                    levelText.setText("Level: " + userLevel);

                    userPic = snapshot.child(userID).child("profile").getValue().toString();
                    profPic = findViewById(R.id.imageView);
                    profPic.setImageResource(new ProfilePicture(Integer.parseInt(userPic)).pic);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomepageActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // update user points when homepage activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");

        usersReference.orderByChild("points").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userName = snapshot.child(userID).child("name").getValue(String.class);
                    nameText = findViewById(R.id.nameHomepage);
                    nameText.setText("Name: " +userName);

                    userPoints = snapshot.child(userID).child("points").getValue().toString();
                    pointsText = findViewById(R.id.pointsHomepage);
                    pointsText.setText("Points: " +userPoints);

                    userLevel = snapshot.child(userID).child("level").getValue().toString();
                    levelText = findViewById(R.id.pointsHomepage);
                    levelText.setText("Level: " + userLevel);

                    userRanking = snapshot.child(userID).child("ranking").getValue().toString();
                    rankingText = findViewById(R.id.rankingHomepage);
                    rankingText.setText("Rank: " +userRanking);

                    userPic = snapshot.child(userID).child("profile").getValue().toString();
                    profPic = findViewById(R.id.imageView);
                    profPic.setImageResource(new ProfilePicture(Integer.parseInt(userPic)).pic);

                    int index = (int) snapshot.getChildrenCount();
                    for (DataSnapshot snap : snapshot.getChildren()){

                        if (snap.child("email").getValue().toString() == snapshot.child(userID).child("email").getValue().toString()){
                            snapshot.getRef().child(userID).child("ranking").setValue(index);
                            break;
                        }
                        else {
                            index--;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomepageActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settingsMenu) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);

        }
        return super.onOptionsItemSelected(item);
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.homepageMenu) {
                // Do something
            } else if (id == R.id.shopMenu) {
                startActivity(shopPageIntent);

            } else if (id == R.id.leaderboardMenu) {
                startActivity(leaderboardPageIntent);
            }
            else if (id == R.id.logOutMenu){
                // make progress bar visible
                progressBar.setVisibility(View.VISIBLE);

                // add about 1.5 second delay to logout and show progress bar
                new CountDownTimer(1500, 1000) {
                    public void onFinish() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(HomepageActivity.this, "User has Successfully Logged Out", Toast.LENGTH_LONG).show();
                        startActivity(loginPageIntent);
                    }

                    public void onTick(long millisUntilFinished) {
                        FirebaseAuth.getInstance().signOut();
                    }
                }.start();

            }
            // close the drawer
            drawerlayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

}