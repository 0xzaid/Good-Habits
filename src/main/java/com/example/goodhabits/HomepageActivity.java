package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomepageActivity extends AppCompatActivity {
    TextView nameText, pointsText, rankingText;
    String userName, userPoints, userRanking;

    DrawerLayout drawerlayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Intent loginPageIntent;
    Intent shopPageIntent;

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
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userName = snapshot.child(userID).child("name").getValue(String.class);
                    nameText = findViewById(R.id.nameHomepage);
                    nameText.setText(userName);

                    userPoints = snapshot.child(userID).child("points").getValue().toString();
                    pointsText = findViewById(R.id.pointsHomepage);
                    pointsText.setText(userPoints);

                    userRanking = snapshot.child(userID).child("ranking").getValue().toString();
                    rankingText = findViewById(R.id.rankingHomepage);
                    rankingText.setText(userRanking);
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
                // Do something
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