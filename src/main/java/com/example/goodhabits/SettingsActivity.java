package com.example.goodhabits;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        Button btn = findViewById(R.id.editProfile);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
            }
        });


    }
    public void goToAboutUsPage(View v)
    {
        Intent myIntent = new Intent(this, AboutUsActivity.class);
        startActivity(myIntent);

    }

    public void goToPrivacyPolicyPage(View v)
    {
        Intent myIntent = new Intent(this, PrivacyPolicyActivity.class);
        startActivity(myIntent);

    }

    public void goBack(View v)
    {
        finish();
    }




}