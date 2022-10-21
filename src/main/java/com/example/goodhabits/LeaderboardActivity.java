package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    RecyclerView leaderboardRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    LeaderboardRecyclerViewAdapter adapter;
    ArrayList<User> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        leaderboardRecyclerView.setLayoutManager(layoutManager);

        adapter = new LeaderboardRecyclerViewAdapter();
        leaderboardRecyclerView.setAdapter(adapter);

        data = new ArrayList<>();

        adapter.setData(data);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");

        usersReference.orderByChild("points").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot snap : snapshot.getChildren()){
                        User newUser = snap.getValue(User.class);
                        data.add(newUser);
                    }
                    Collections.reverse(data);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LeaderboardActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goBack(View v)
    {
        finish();
    }
}