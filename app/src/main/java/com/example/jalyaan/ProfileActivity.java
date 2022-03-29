package com.example.jalyaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    TextInputLayout nameInput,mobileInput,streetInput,cityInput,pincodeInput,emailInput;
    AppCompatButton btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Set Up Your Profile");
        nameInput = findViewById(R.id.profile_name);
        mobileInput = findViewById(R.id.mobile_number);
        streetInput = findViewById(R.id.street);
        cityInput = findViewById(R.id.city);
        pincodeInput = findViewById(R.id.pincode);
        emailInput = findViewById(R.id.profile_email);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        //DatabaseReference root = db.getReference();
        btn = findViewById(R.id.profile_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProfileToDatabase();
            }
        });

    }
    public User makeUser(){
        String name = nameInput.getEditText().getText().toString().trim();
        String mobile = mobileInput.getEditText().getText().toString().trim();
        String street = streetInput.getEditText().getText().toString().trim();
        String city = cityInput.getEditText().getText().toString().trim();
        String pincode = pincodeInput.getEditText().getText().toString().trim();
        String uid = auth.getUid().toString();
        String email =emailInput.getEditText().getText().toString().trim();
        User user = new User(uid,name,mobile,street,city,pincode,"No Image",email);
        return user;
    }
    public void insertProfileToDatabase(){
        User user = makeUser();
        DatabaseReference node = db.getReference("Users");
        node.child(user.getUid()).setValue(user);
        Toast.makeText(this, "Profile Info Inserted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        finishAffinity();
    }
}