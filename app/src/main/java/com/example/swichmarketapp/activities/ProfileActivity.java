package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private TextView mFullName,mEmail,mPhone ;
    private ImageView mImage,mPicture;
    private RatingBar mRating;
    private DatabaseReference mDatabaseRef;
    private  String temp ="" ;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        initFireBase();




    }
    private void initViews() {
        mFullName =findViewById(R.id.ProfileUserNAmeTextView);
        mEmail =findViewById(R.id.ProfileTextViewPhone);
        mPhone =findViewById(R.id.ProfileTextViewPhone);
        mImage =findViewById(R.id.ProfileimageView);
        mPicture =findViewById(R.id.ProfileimageView);
        mRating =findViewById(R.id.ProfileratingBar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
    }
    private void initFireBase() {
        FirebaseUser take_id= FirebaseAuth.getInstance().getCurrentUser();
        String userId= take_id.getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference user1 = user.child(userId);
        //pull fields
        DatabaseReference allname = user1.child("userName");
        DatabaseReference allemail = user1.child("mEmail");
        DatabaseReference allphone = user1.child("phone");
        DatabaseReference allrat = user1.child("rating");
        // upload the profile picture
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("picture");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        temp = snapshot.child("imageUrl").getValue().toString();
                   //     Picasso.get().load(temp).into(mImage);

                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // init the fields
        allname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                mFullName.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allemail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                mEmail.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allphone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString();
                mPhone.setText(x);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        allrat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  x = dataSnapshot.getValue().toString()+"";
                int r = Integer.parseInt(x);
                Toast.makeText(ProfileActivity.this,x+r, Toast.LENGTH_LONG).show();
                mRating.setRating(r);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        mImage.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Storage.class));
//
//                String uploadId = mDatabaseRef.push().getKey();
//
//            }});



    }

}
