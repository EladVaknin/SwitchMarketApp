package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.adapter.ItemRecyclerAdapter;
import com.example.swichmarketapp.models.Admin;
import com.example.swichmarketapp.models.Item;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.example.swichmarketapp.utlities.Utilitie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(RegisterActivity.USERS_TABLE);
    private Button mLoginButton, mRegisterButton, mUpdateToPremiumButtonLogin;
    private EditText mEmailEditText, mPasswordEditText;
    private ItemRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }


    private void initViews() {
        mUpdateToPremiumButtonLogin = findViewById(R.id.UpdateToPrimiumLogin);
        mEmailEditText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mPasswordEditText = (EditText) findViewById(R.id.editTextNumberPassword);
        mLoginButton = (Button) findViewById(R.id.Loginbutton);
        mLoginButton.setOnClickListener(v -> performLogin());
        mRegisterButton = (Button) findViewById(R.id.Registerbutton);
        mRegisterButton.setOnClickListener(v -> redirectToRegisterScreen());
        mUpdateToPremiumButtonLogin.setOnClickListener(v -> redirectToPremiumRegister());
    }

    private void performLogin() {
        String Admin = "Admin@gmail.com";
        String adminPassword = "12345678";
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        if (Utilitie.isEmailAndPasswordValid(this, email, password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    if (email.equals(Admin)&&password.equals(adminPassword)){
                        redirectToAdminScreen();
                    }
                    else{
                        getUserDetailsFromFireBaseAndRedirectToMainActivity();
                }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void redirectToRegisterScreen() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void redirectToAdminScreen() {
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    public void redirectToPremiumRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterPremiumActivity.class);
        startActivity(intent);
    }

    private void getUserDetailsFromFireBaseAndRedirectToMainActivity() {
        mDbUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                CacheUtilities.cacheUserName(LoginActivity.this, (String) userSnapshot.child("userName").getValue());
                CacheUtilities.cachePhoneNumber(LoginActivity.this, (String) userSnapshot.child("phone").getValue());
                CacheUtilities.cacheRating(LoginActivity.this, ((Long)userSnapshot.child("rating").getValue()).floatValue());
                if (userSnapshot.child("profileUrl").exists()) {
                    CacheUtilities.cacheImageProfile(LoginActivity.this, (String) userSnapshot.child("profileUrl").getValue());
                }
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}


