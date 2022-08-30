package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShopActivity extends AppCompatActivity {

    TextView pointsText;

    String userPoints;

    RelativeLayout avatar1, avatar2, avatar3, avatar4;
    RelativeLayout unlock1, unlock2, unlock3;

    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userPoints = snapshot.child(userID).child("points").getValue().toString();
                    pointsText = findViewById(R.id.displayPoints);
                    pointsText.setText("Points: " + userPoints);
                    points = Integer.parseInt(userPoints);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        avatar1 = (RelativeLayout) findViewById(R.id.avatar1);
        avatar2 = (RelativeLayout) findViewById(R.id.avatar2);
        avatar3 = (RelativeLayout) findViewById(R.id.avatar3);
        avatar4 = (RelativeLayout) findViewById(R.id.avatar4);

        unlock1 = (RelativeLayout) findViewById(R.id.unlock1);
        unlock2 = (RelativeLayout) findViewById(R.id.unlock2);
        unlock3 = (RelativeLayout) findViewById(R.id.unlock3);

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShopActivity.this, "Avatar Selected", Toast.LENGTH_SHORT).show();
            }
        });

        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (points >= 30) {
                    points -= 30;
                    pointsText.setText("Points: " + points);
                    unlock1.setVisibility(View.GONE);
                    Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                }

                 else {
                    Toast.makeText(ShopActivity.this, "Not enough points", Toast.LENGTH_SHORT).show();
                }
            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (points >= 50) {
                    points -= 50;
                    pointsText.setText("Points: " + points);
                    unlock2.setVisibility(View.GONE);
                    Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(ShopActivity.this, "Not enough points", Toast.LENGTH_SHORT).show();
                }
            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (points >= 80) {
                    points -= 80;
                    pointsText.setText("Points: " + points);
                    unlock3.setVisibility(View.GONE);
                    Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(ShopActivity.this, "Not enough points", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}