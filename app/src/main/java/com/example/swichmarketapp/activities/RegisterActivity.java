package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.models.User;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.example.swichmarketapp.utlities.Utilitie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public static final String USERS_TABLE = "users";
    //    public static final String ITEM_TABLE = "items";
    private EditText mUserNameEditText, mPasswordEditText, mEmailEditText, mPhoneEditText;
    private Button mRegisterButton;
    private ProgressBar mProgressBar;

    //Firebase
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    private void performRegister() {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        String userName = mUserNameEditText.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Username or phone is  empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Utilitie.isEmailAndPasswordValid(this, email, password)) {
            handleProgressBar(true);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                handleProgressBar(false);
                if (task.isSuccessful()) {
                    User user = new User(mAuth.getCurrentUser().getUid(), email, userName, phone);
                    CacheUtilities.cacheUserName(this, userName);
                    CacheUtilities.cachePhoneNumber(this, phone);
                    mDbUser.child(mAuth.getCurrentUser().getUid()).setValue(user);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initViews() {
        mUserNameEditText = findViewById(R.id.userNameEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mRegisterButton = findViewById(R.id.registerButton);
        mPhoneEditText = findViewById(R.id.phoneEditText);
        mEmailEditText = findViewById(R.id.emailEditText);
        mProgressBar = findViewById(R.id.progressBar);
        mRegisterButton.setOnClickListener(v -> performRegister());


    }

    private void handleProgressBar(boolean shouldDisplay) {
        mProgressBar.setVisibility(shouldDisplay ? View.VISIBLE : View.INVISIBLE);
    }

}
