package com.example.swichmarketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.swichmarketapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private  Button mSearchButton ,mProfileButton ,mLogoutButton ,mAddItemButton;
    private Toolbar mToolbar;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(RegisterActivity.USERS_TABLE);

    private void initViews() {
        mLogoutButton = findViewById(R.id.Logoutbutton);
        mProfileButton =findViewById(R.id.profileButton);
        mSearchButton =findViewById(R.id.profileButton);
        mAddItemButton =findViewById(R.id.addItemButton);
//        mToolbar =findViewById(R.id.toolbarbutton);
        mProfileButton.setOnClickListener(v -> redirectToProfileScreen());
        mLogoutButton.setOnClickListener(v -> redirectToLogout());
        mSearchButton.setOnClickListener(v -> redirectToSearch());
        mAddItemButton.setOnClickListener(v -> redirectToAddItemScreen());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
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
    public void redirectToProfileScreen() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
    public void redirectToLogout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void redirectToSearch(){
          /// implement of search items
    }
    public void redirectToAddItemScreen() {
        Intent intent = new Intent(MainActivity.this, ItemActivity.class);
        startActivity(intent);
    }


}