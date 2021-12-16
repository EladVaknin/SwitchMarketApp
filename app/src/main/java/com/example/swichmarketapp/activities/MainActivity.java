package com.example.swichmarketapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swichmarketapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button sing_out ,Profile,Search;
    ImageView ProfilePicture;
    DatabaseReference All_name;
    String x ="";
    String x2="";
    TextView text;
    DrawerLayout drawerLayouta;
    NavigationView navigationView;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(RegisterActivity.USERS_TABLE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mDbUser.child("mor").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               snapshot.getValue();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

//        sing_out = findViewById(R.id.Logoutbutton);
//        Profile =findViewById(R.id.profileButton);
//        Search=findViewById(R.id.Searchbutton);
//        ProfilePicture =findViewById(R.id.imageView);
//        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");





       // DatabaseReference user2 = user.child();
        //chek = (Button)findViewById(R.id.Profilebutton);
//        signout=(Button)findViewById(R.id.Logoutbutton);
//        search = (Button) findViewById(R.id.Searchbutton);
//        image = (ImageView)findViewById(R.id.imageView);
//       // text = (TextView)findViewById(R.id.userName_);
//        FirebaseUser take_id= FirebaseAuth.getInstance().getCurrentUser();
//
//        String userId= take_id.getUid();
//
//        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
      //  DatabaseReference user1 = user.child(userId);
//        DatabaseReference all_name = user1.child("allName");
////
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("picture");
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        x2 = snapshot.child("imageUrl").getValue().toString();
//                  //      Picasso.get().load(x2).into(image);
//                        break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
//        all_name.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                x = dataSnapshot.getValue().toString();
//                text.setText("Hi "+x+"!");
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });



//        chek.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Login.class));
//            }
//        });





    }

}