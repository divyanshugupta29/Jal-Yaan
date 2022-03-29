package com.example.jalyaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ComplaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        getSupportActionBar().setTitle("Complaints");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}