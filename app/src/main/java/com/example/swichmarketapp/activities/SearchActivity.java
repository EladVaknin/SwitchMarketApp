package com.example.swichmarketapp.activities;

import static com.example.swichmarketapp.activities.RegisterActivity.USERS_TABLE;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText mSearchBar ;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    private List <String> SaveResults = new ArrayList<String>();
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
    }

    private void initViews() {
        mProgressBar =findViewById(R.id.progressBar3);
        mSearchBar = findViewById(R.id.editTextSearchBar);
        mSearchBar.setOnClickListener(v -> SearchDataInFireBase ());
    }




    private void SearchDataInFireBase() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDbUser.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot UserSnapshot : dataSnapshot.getChildren()) {
                    if (dataSnapshot.child("items").exists()) {
                        for (DataSnapshot items : dataSnapshot.getChildren()){
                            if (dataSnapshot.getChildren().equals(items.child("desc"))) {
                                SaveResults.add(dataSnapshot.getValue().toString());
                            }
                        }
                    }else{
                        Toast.makeText(SearchActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
            });
}
}
