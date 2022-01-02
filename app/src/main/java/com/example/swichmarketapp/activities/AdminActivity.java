package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminActivity extends AppCompatActivity{

    private Button mRemovData,mRemoveSrorage ,mLinkToFirebase;
    private Button mLogout;
    private TextView mLink;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(RegisterActivity.USERS_TABLE);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);
            initViews();

        }

    private void initViews() {
            mLinkToFirebase =findViewById(R.id.firebsase_button);
            mLinkToFirebase.setOnClickListener(v -> redirectToLinkToFirebase());
            mRemovData =findViewById(R.id.rimoveData_button);
            mRemovData.setOnClickListener(v -> redirectToRemoveData());
            mRemoveSrorage = findViewById(R.id.removeStorage_button);
            mRemoveSrorage.setOnClickListener(v -> redirectToRemoveStorage());
            mLogout =findViewById(R.id.logout_button);
            mLogout.setOnClickListener(v -> redirectToLogout());
            mLink =findViewById(R.id.firebsaseLink_textView);
            mLink.setOnClickListener(v -> redirectToLinkToFirebase());
    }

    public void redirectToLogout() {
        FirebaseAuth.getInstance().signOut();
        CacheUtilities.clearAll(this);
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void redirectToRemoveStorage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("upload");
        storageReference.delete();
    }
    public void redirectToRemoveData() {


    }
    public void redirectToLinkToFirebase() {
            mLink.setText("https://console.firebase.google.com/u/0/project/switchmarketapp/overview");
    }






    // navigation menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_mainpage)
        {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_profile)
        {
            Intent intent=new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_search)
        {
            Intent intent=new Intent(this, SearchActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_toswitch)
        {
            Intent intent=new Intent(this, SwitchActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_aboutas)
        {
            Intent intent=new Intent(this, AboutAsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_messanger)
        {
            Intent intent=new Intent(this, ClodMessengerActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id==R.id.nav_logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
