package com.example.swichmarketapp.activities;

import static com.example.swichmarketapp.activities.RegisterActivity.USERS_TABLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.models.ChatMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessengerActivity extends AppCompatActivity {
    private EditText mSendMessage;
    private  FloatingActionButton mFab;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(USERS_TABLE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initViews();

    }

    private void initViews() {
        mSendMessage = findViewById(R.id.editTextWireHereMessage);
        mSendMessage.setOnClickListener(v -> sendMessage ());
        mFab = (FloatingActionButton)findViewById(R.id.fab);
        mFab.setOnClickListener(v -> sendMessage());

    }

    private void sendMessage() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.editTextWireHereMessage);
                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                mDbUser.child(FirebaseAuth.getInstance().getUid()).push().setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                        );
                // Clear the input bar
                input.setText("");
            }
        });

    }


}
