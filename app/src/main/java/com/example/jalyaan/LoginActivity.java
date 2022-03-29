package com.example.jalyaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout t1,t2;
    ProgressBar bar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        t1 = findViewById(R.id.login_email);
        t2 = findViewById(R.id.login_pwd);
        bar = findViewById(R.id.login_progressBar);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view) {
        bar.setVisibility(View.VISIBLE);
        String email = t1.getEditText().getText().toString().trim();
        String password = t2.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            haveProfile(mAuth.getUid().toString());
                            /*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                            intent.putExtra("name",mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                            finishAffinity();*/

                        } else {
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            Log.w("Fail",task.getException());
                            Toast.makeText(LoginActivity.this, "Invalid Email Or Password \n "+task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void haveProfile(String uid){
        DatabaseReference node = FirebaseDatabase.getInstance().getReference("Users");
        node.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finishAffinity();

                    }else{
                        startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                        finishAffinity();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}