package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.utlities.Utilitie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //m = member
    private Button mLoginButton, mRegisterButton;
    private EditText mEmailEditText, mPasswordEditText;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        mEmailEditText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mPasswordEditText = (EditText) findViewById(R.id.editTextNumberPassword);
        mLoginButton = (Button) findViewById(R.id.Loginbutton);
        mLoginButton.setOnClickListener(v -> performLogin());
        mRegisterButton = (Button) findViewById(R.id.Registerbutton);
        mRegisterButton.setOnClickListener(v -> redirectToRegisterScreen());
    }

    private void performLogin() {
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        if (Utilitie.isEmailAndPasswordValid(this,email, password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
    }

    public void redirectToRegisterScreen() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}


