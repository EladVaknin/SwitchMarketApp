package com.example.swichmarketapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;

public class RatingProfileActivity extends AppCompatActivity {
    private RatingBar mRatingBar;
    private Button mSubmit;
    private String id_cur = "";
    private String count_rates;
    private String rates;
    private int c = 0;
    private double r = 0.0;
    private boolean flag = false;
    private boolean flag2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_profile);
        initViews();

    }

    private void initViews() {
        mRatingBar = findViewById(R.id.rating_bar);
        mSubmit = findViewById(R.id.submitButton);
    }
}
