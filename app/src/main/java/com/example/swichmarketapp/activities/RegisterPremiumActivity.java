package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.models.PremiumUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPremiumActivity extends AppCompatActivity {

    private EditText mUserNameEditTextPremium, mPasswordEditTextPremium, mEmailEditTextPremium, mPhoneEditTextPremium;
    private EditText mAddresseEditTextPremium, mCreditCardEditTextPremium;
    private Button mRegisterButtonPremium;
    private ProgressBar mProgressBar3;
    public static final String PREMIUM_USERS_TABLE = "PremiumUsers";

    //Firebase
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(PREMIUM_USERS_TABLE);
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_user);
        initViews();
    }

    private void initViews() {
        mAddresseEditTextPremium = findViewById(R.id.editTextAdressPrimum);
        mCreditCardEditTextPremium =findViewById(R.id.editTextCreditCardPrimum);
        mEmailEditTextPremium =findViewById(R.id.emailEditText);
        mUserNameEditTextPremium =findViewById(R.id.userNameEditText);
        mPasswordEditTextPremium = findViewById(R.id.passwordEditText);
        mPhoneEditTextPremium=findViewById(R.id.phoneEditText);
        mRegisterButtonPremium=findViewById(R.id.PremiumRegisterButtn);
        mRegisterButtonPremium.setOnClickListener(v -> performRegisterPremium());
    }


    private void performRegisterPremium() {
        String id = mAuth.getCurrentUser().getUid();
        String email = mEmailEditTextPremium.getText().toString();
        String password = mPasswordEditTextPremium.getText().toString();
        String phone = mPhoneEditTextPremium.getText().toString();
        String userName = mUserNameEditTextPremium.getText().toString();
        String address = mAddresseEditTextPremium.getText().toString();
        String creditCard = mCreditCardEditTextPremium.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(phone)|| TextUtils.isEmpty(address)|| TextUtils.isEmpty(creditCard)) {
            Toast.makeText(this, "Username / phone / address / credit card  is  empty", Toast.LENGTH_SHORT).show();
            return;
        }
        {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    PremiumUser PremiumUser = new PremiumUser(id,email,userName,phone,address,creditCard);
                    // upload User Object to the firebase
                    mDbUser.child(userName).setValue(PremiumUser);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(RegisterPremiumActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}
