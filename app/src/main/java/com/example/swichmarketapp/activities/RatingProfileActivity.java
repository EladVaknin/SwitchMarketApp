package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingProfileActivity extends AppCompatActivity {
    private RatingBar mRatingBar;
    private Button mSubmit;
    private String id_cur = "";
    private String count_rates;
    private String rates;
    private int c = 0;
    private double r = 0.0;
    private boolean flag = false;
    private boolean flag2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_profile);
        initViews();

    }

    private void initViews() {
        mRatingBar = findViewById(R.id.rating_bar);
        mSubmit = findViewById(R.id.submitButton);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(mRatingBar.getRating());
                Toast.makeText(getApplicationContext(), s + " Stars", Toast.LENGTH_SHORT).show();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(id_cur);
                DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference user1 = user.child(id_cur);
                DatabaseReference rate = user1.child("rating");
                DatabaseReference countrate = user1.child("counter");
                countrate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (flag == false) {
                            count_rates = dataSnapshot.getValue().toString();
                            c = Integer.parseInt(count_rates);
                            flag = true;
                            int g = c + 1;
                            c = g;
                            Toast.makeText(RatingProfileActivity.this, g + "x", Toast.LENGTH_LONG).show();
                            dataSnapshot.getRef().setValue(g);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                rate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (flag2 == false) {
                            rates = dataSnapshot.getValue().toString();
                            r = Double.parseDouble(rates);
                            double curr = Double.parseDouble(s);
                            if (c > 0) {
                                double temp = ((c - 1) * r + curr) / c;
                                dataSnapshot.getRef().setValue(temp);
                            } else {
                                dataSnapshot.getRef().setValue(curr);
                            }
                            flag2 = true;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                Intent intent = new Intent(RatingProfileActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
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
