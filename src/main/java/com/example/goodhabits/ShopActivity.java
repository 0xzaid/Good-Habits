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

    TextView currencyText;

    String userCurrency;

    RelativeLayout avatar1, avatar2, avatar3, avatar4;
    RelativeLayout unlock1, unlock2, unlock3;

    int currency;
    int profilePicture;

    boolean unlocked2;
    boolean unlocked3;
    boolean unlocked4;


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
                    userCurrency = snapshot.child(userID).child("currency").getValue().toString();
                    currencyText = findViewById(R.id.displayCurrency);
                    currencyText.setText("Currency: " + userCurrency);
                    currency = Integer.parseInt(userCurrency);

                    unlocked2 = (boolean) snapshot.child(userID).child("unlockedItem2").getValue();
                    unlocked3 = (boolean) snapshot.child(userID).child("unlockedItem3").getValue();
                    unlocked4 = (boolean) snapshot.child(userID).child("unlockedItem4").getValue();

                    if (unlocked2) {
                        unlock1.setVisibility(View.GONE);
                    }

                    if (unlocked3) {
                        unlock2.setVisibility(View.GONE);
                    }

                    if (unlocked4) {
                        unlock3.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);

        unlock1 = findViewById(R.id.unlock1);
        unlock2 = findViewById(R.id.unlock2);
        unlock3 = findViewById(R.id.unlock3);

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePicture = 2;
                updateProfilePic(2);
                Toast.makeText(ShopActivity.this, "Avatar Selected", Toast.LENGTH_SHORT).show();
            }
        });

        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePicture = 3;
                if (!unlocked2) {
                    if (currency >= 30) {
                        unlocked2 = true;
                        updateProfilePic(profilePicture);
                        updateInventory("unlockedItem2");
                        currency -= 30;
                        currencyText.setText("Currency: " + currency);
                        unlock1.setVisibility(View.GONE);
                        updateCurrency(currency);
                        Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ShopActivity.this, "Not enough currency", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    updateProfilePic(profilePicture);
                    Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePicture = 4;
                if (!unlocked3) {
                    if (currency >= 50) {
                        unlocked3 = true;
                        updateProfilePic(profilePicture);
                        updateInventory("unlockedItem3");
                        currency -= 50;
                        currencyText.setText("Currency: " + currency);
                        unlock2.setVisibility(View.GONE);
                        updateCurrency(currency);
                        Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ShopActivity.this, "Not enough currency", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    updateProfilePic(profilePicture);
                    Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePicture = 5;
                if (!unlocked4) {
                    if (currency >= 80) {
                        unlocked4 = true;
                        updateProfilePic(profilePicture);
                        updateInventory("unlockedItem4");
                        currency -= 80;
                        currencyText.setText("Currency: " + currency);
                        unlock3.setVisibility(View.GONE);
                        updateCurrency(currency);
                        Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ShopActivity.this, "Not enough currency", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    updateProfilePic(profilePicture);
                    Toast.makeText(ShopActivity.this, "Avatar selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // update user currency on Firebase
    public void updateCurrency(int currency) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    snapshot.getRef().child(userID).child("currency").setValue(currency);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopActivity.this, "ERROR: ShopActivity", Toast.LENGTH_LONG).show();
            }
        });
    }

    // update user profile picture on Firebase
    public void updateProfilePic(int pic) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    snapshot.getRef().child(userID).child("profile").setValue(pic);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopActivity.this, "ERROR: ShopActivity", Toast.LENGTH_LONG).show();
            }
        });
    }

    // update user shop inventory
    public void updateInventory(String key) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    snapshot.getRef().child(userID).child(key).setValue(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopActivity.this, "ERROR: ShopActivity", Toast.LENGTH_LONG).show();
            }
        });
    }

}