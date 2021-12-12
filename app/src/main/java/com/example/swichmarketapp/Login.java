package com.example.swichmarketapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText Email, password;
    Button MainLogin;
    TextView CreateUser;
    FirebaseAuth Authentication;
    String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextNumberPassword);
        MainLogin = findViewById(R.id.Loginbutton);
        CreateUser = (TextView)findViewById(R.id.Registerbutton);
        flag = "0";
        Authentication = FirebaseAuth.getInstance();

        // Login check
        MainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("password is Required");
                    return;
                }
                if (password.length() < 8) {
                    password.setError("Invalid password - your password must be greater than 8");
                    return;
                }

                //Check if the user exist in the firebase part

                Authentication.signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "successful connecting ", Toast.LENGTH_SHORT).show();
//                          check_admin();
                            String admin_mail= Authentication.getCurrentUser().getEmail();
//                            Toast.makeText(Login.this, email, Toast.LENGTH_SHORT).show();
                            switch (email)
                            {
                                case "admin1@gmail.com":
                                    startActivity(new Intent(getApplicationContext(), Admin.class));
                                    break;
                                default:
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    break;
                            }
                        } else {
                            Toast.makeText(Login.this, "Error try again" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
//
            }
        });
        CreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            // to create a new user
            public void onClick(View v) {
                create_User();
            }
        });



    }


    public void create_User() {
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }
        @Override
    public void onStart(){
        super.onStart();
        if(Authentication.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }

}

