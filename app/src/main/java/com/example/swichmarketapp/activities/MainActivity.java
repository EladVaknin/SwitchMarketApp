package com.example.swichmarketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private Button mSearchButton, mProfileButton, mLogoutButton, mAddItemButton;
    private Button mSwitchButton;
    private Button mUpdateToPremiumButton;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }



    private void initViews() {
        mSwitchButton =findViewById(R.id.switchButton);
        mSwitchButton.setOnClickListener(v -> redirectToSwitch());
        mUpdateToPremiumButton = findViewById(R.id.UpdateToPremiumButton);
        mLogoutButton = findViewById(R.id.Logoutbutton);
        mProfileButton = findViewById(R.id.profileButton);
        mSearchButton = findViewById(R.id.Searchbutton);
        mAddItemButton = findViewById(R.id.addItemButton);
//        mToolbar =findViewById(R.id.toolbarbutton);
        mProfileButton.setOnClickListener(v -> redirectToProfileScreen());
        mLogoutButton.setOnClickListener(v -> redirectToLogout());
        mSearchButton.setOnClickListener(v -> redirectToSearch());
        mAddItemButton.setOnClickListener(v -> redirectToAddItemScreen());
        mUpdateToPremiumButton.setOnClickListener(v -> redirectToPremiumRegister());
    }

    private void redirectToSwitch() {
        Intent intent = new Intent(MainActivity.this, SwitchActivity.class);
        startActivity(intent);

    }


    public void redirectToProfileScreen() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void redirectToLogout() {
        FirebaseAuth.getInstance().signOut();
        CacheUtilities.clearAll(this);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void redirectToSearch() {
        /// implement of search items
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void redirectToAddItemScreen() {
        Intent intent = new Intent(MainActivity.this, ItemActivity.class);
        startActivity(intent);
    }

    public void redirectToPremiumRegister() {
        Intent intent = new Intent(MainActivity.this, RegisterPremiumActivity.class);
        startActivity(intent);
    }


//       mDbUser.child("mor").addListenerForSingleValueEvent(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               snapshot.getValue();
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//
//           }
//       });


}