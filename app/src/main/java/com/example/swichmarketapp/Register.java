package com.example.swichmarketapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText Name, Password, Email, Phone;
    TextView Loginbutton;
    Button RegButton;
    FirebaseAuth Authentication;
    ProgressBar progressBar;
    DatabaseReference dbUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connects the xml to java file
        setContentView(R.layout.activity_register);
        //Buttons id from xml
        Name = findViewById(R.id.RegisterNameButton);
        Email = findViewById(R.id.RegisterMailButton);
        Password = findViewById(R.id.RegisterPasswordButton);
        Phone = findViewById(R.id.RegisterphoneButton);
        RegButton = findViewById(R.id.FinishRegisterButton);
        Loginbutton = findViewById(R.id.LoginFronRegister);
        progressBar = findViewById(R.id.LoadingprogressBar);

        // faireBase part
        dbUser= FirebaseDatabase.getInstance().getReference("users");
        Authentication = FirebaseAuth.getInstance();
        if(Authentication.getCurrentUser()!=null)
        {
//            Intent open  = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String allName = Name.getText().toString().trim();
                String phone=Phone.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("password is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Phone.setError("phone is Required");
                    return;
                }
                if (password.length() < 8) {
                    Password.setError("Invalid password - your password must be greater than 8");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //fairebase part

                Authentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
//                             id="";
//                            if(mhuth.getCurrentUser()!=null)
//                            {
                            String id =Authentication.getCurrentUser().getUid();
//                            }
//                            String id=dbUser.push().getKey();
                           // User user = new User(id,Name,Email,Phone,"","0",0.0,0,);
                            //  User user = new User(id,Name,Email,Phone);
                            User user = new User("","","","","",id,0.0,0,"EladVak8");
                            dbUser.child(id).setValue(user);
                            dbUser= FirebaseDatabase.getInstance().getReference("users").child(id).child("packages");
//                            dbUser= FirebaseDatabase.getInstance().getReference("users").child(id).child("picture");
                            Toast.makeText(Register.this, "User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(Register.this, "Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }}
                });
            }
        });
        Loginbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));


            }

        });

  }

}
