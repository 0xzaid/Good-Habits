package com.example.goodhabits;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        TextView au = findViewById(R.id.aboutUs);
        au.setText("Good Habits is an Educational Platform. Here we will provide you interesting content, which you will like very much. We're dedicated to providing you the best of Good Habits, with a focus on dependability and Children Habits. We hope you enjoy our app as much as we enjoy offering them to you.");

    }

    public void goBack(View v)
    {
        finish();
    }
}