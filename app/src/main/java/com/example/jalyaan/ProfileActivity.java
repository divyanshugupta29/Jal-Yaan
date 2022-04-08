package com.example.jalyaan;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    TextInputLayout nameInput,mobileInput,streetInput,cityInput,pincodeInput,emailInput;
    AppCompatButton btn;
    FirebaseStorage storage;
    ActivityResultLauncher<String> mGetContent;
    Uri selectedImage;
    ProgressDialog progressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Set Up Your Profile");
        storage = FirebaseStorage.getInstance();
        nameInput = findViewById(R.id.profile_name);
        mobileInput = findViewById(R.id.mobile_number);
        streetInput = findViewById(R.id.street);
        cityInput = findViewById(R.id.city);
        imageView = findViewById(R.id.profile_image);
        pincodeInput = findViewById(R.id.pincode);
        emailInput = findViewById(R.id.profile_email);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        //DatabaseReference root = db.getReference();
        btn = findViewById(R.id.profile_submit);
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageView.setImageURI(result);
                selectedImage = result;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                mGetContent.launch("image/*");
            }
        });
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
        String image = "NO IMAGE";
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