package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class AboutAsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_as);
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
