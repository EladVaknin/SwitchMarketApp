package com.example.swichmarketapp.activities;

import static com.example.swichmarketapp.activities.RegisterActivity.USERS_TABLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.adapter.ItemRecyclerAdapter;
import com.example.swichmarketapp.models.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SwitchActivity extends AppCompatActivity implements ItemRecyclerAdapter.ItemClickListener {
    private static final String TAG = "SearchActivity";
    private EditText mSearchEditText;
    private Button mSearchButton;
    private ProgressBar mProgressBar;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    private ItemRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toswitch);
        initViews();
    }


    private void initViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mSearchEditText = findViewById(R.id.search_edit_text);
        mSearchButton = findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(v -> performSearch());
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemRecyclerAdapter(this);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void recyclerViewShow(boolean shouldShow) {
        if (mRecyclerView != null && mProgressBar != null) {
            mRecyclerView.setVisibility(shouldShow ? View.VISIBLE : View.INVISIBLE);
            mProgressBar.setVisibility(shouldShow ? View.INVISIBLE : View.VISIBLE);
        }
    }

    private void performSearch() {
        recyclerViewShow(false);
        final String searchString = mSearchEditText.getText().toString();
        mDbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Item> itemList = new ArrayList<>();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if (user.child("items").exists()) {
                        for (DataSnapshot item : user.child("items").getChildren()) {
                            String description = (String) item.child("toSwitch").getValue();
//                            Log.d(TAG, "Description -" + description);
                            if (description.contains(searchString)) {
                                String price = (String) item.child("price").getValue();
                                String imageUrl = (String) item.child("imageItem").getValue();
                                String toSwitch = (String) item.child("toSwitch").getValue();
                                String userName = (String) user.child("userName").getValue();
                                itemList.add(new Item(description, imageUrl, toSwitch, price, userName));
                            }
                        }

                    }
                }
                mAdapter.setNewItems(itemList);
                recyclerViewShow(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mAdapter.clearAllData();
                recyclerViewShow(true);
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(SwitchActivity.this, MessengerActivity.class);
        intent.putExtra(MessengerActivity.SEND_TO_KEY, item.getUser());
        startActivity(intent);
    }
}
