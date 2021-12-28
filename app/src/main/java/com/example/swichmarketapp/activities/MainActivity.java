package com.example.swichmarketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private Button mSearchButton, mProfileButton, mLogoutButton, mAddItemButton, mChat, mUpdateToPremiumButton;
    private Button mToSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    private void initViews() {
        mChat = findViewById(R.id.chat_button);
        mUpdateToPremiumButton = findViewById(R.id.update_to_premium_user_button);
        mLogoutButton = findViewById(R.id.logout_button);
        mProfileButton = findViewById(R.id.profile_button);
        mSearchButton = findViewById(R.id.search_button);
        mAddItemButton = findViewById(R.id.add_item_button);
        mToSwitch = findViewById(R.id.toSwitch_button);
        mChat.setOnClickListener(v -> redirectToActivity(MessengerActivity.class));
        mProfileButton.setOnClickListener(v -> redirectToActivity(ProfileActivity.class));
        mLogoutButton.setOnClickListener(v -> redirectToLogout());
        mSearchButton.setOnClickListener(v ->  redirectToActivity(SearchActivity.class));
        mAddItemButton.setOnClickListener(v ->  redirectToActivity(ItemActivity.class));
        mUpdateToPremiumButton.setOnClickListener(v ->  redirectToActivity(RegisterPremiumActivity.class));
        mToSwitch.setOnClickListener(v -> redirectToActivity(SwitchActivity.class));

    }


    private void redirectToActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

    public void redirectToLogout() {
        FirebaseAuth.getInstance().signOut();
        CacheUtilities.clearAll(this);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}