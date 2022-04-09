package com.example.jalyaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText subjectET,descriptionET;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        getSupportActionBar().setTitle("Complaints");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.complaint_submitBtn);
        subjectET = findViewById(R.id.complaint_subject);
        descriptionET = findViewById(R.id.complaint_description);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = subjectET.getText().toString();
                String description = descriptionET.getText().toString();
                ComplaintModel complaint = new ComplaintModel(subject,description);
                FirebaseDatabase.getInstance().getReference("Complaints").child(auth.getUid().toString()).push().setValue(complaint).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ComplaintActivity.this, "Complaint Registered", Toast.LENGTH_SHORT).show();
                        subjectET.setText("");
                        descriptionET.setText("");
                    }
                });
            }
        });
    }
}