package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.adapter.ChatAdapter;
import com.example.swichmarketapp.models.ChatMessage;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessengerActivity extends AppCompatActivity {
    public static final String SEND_TO_KEY = "send_to";
    public static final String KEY_COLLECTION_CHAT = "Chat";
    public static final String KEY_SEND_TO = "send_to";
    public static final String KEY_FROM_MSG = "from";
    public static final String KEY_MSG = "message";

    private String mToUser;
    private List<ChatMessage> mChatMessages;
    private ChatAdapter mChatAdapter;
    private RecyclerView mRecyclerView;
    private EditText mMsgEditText;
    private ImageView mSendMsgImageView;
    private ImageView mRatingButton;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mToUser = getIntent().getStringExtra(SEND_TO_KEY);
        initViews();
        listenMessages();
    }

    private void listenMessages() {
        mDb.collection(KEY_COLLECTION_CHAT).whereEqualTo(KEY_SEND_TO, CacheUtilities.getUserName(this))
                .whereEqualTo(KEY_FROM_MSG, mToUser).addSnapshotListener(eventListener);
        mDb.collection(KEY_COLLECTION_CHAT).whereEqualTo(KEY_SEND_TO, mToUser)
                .whereEqualTo(KEY_FROM_MSG, CacheUtilities.getUserName(this)).addSnapshotListener(eventListener);

    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = mChatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(KEY_SEND_TO);
                    chatMessage.reciverId = documentChange.getDocument().getString(KEY_FROM_MSG);
                    chatMessage.message = documentChange.getDocument().getString(KEY_MSG);
                    mChatMessages.add(chatMessage);
                }
            }
            if (count == 0) {
                mChatAdapter.notifyDataSetChanged();
            } else {
                mChatAdapter.notifyItemRangeChanged(mChatMessages.size(), mChatMessages.size());
                mRecyclerView.smoothScrollToPosition(mChatMessages.size());
            }
        }
    };


    private void initViews() {
        mRatingButton = findViewById(R.id.rating_button);
        TextView header = findViewById(R.id.header);
        header.setText(mToUser);
        mMsgEditText = findViewById(R.id.msgEditBox);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatMessages = new ArrayList<>();
        mChatAdapter = new ChatAdapter(mChatMessages, mToUser);
        mRecyclerView.setAdapter(mChatAdapter);
        mSendMsgImageView = findViewById(R.id.send_msg_button);
        mSendMsgImageView.setOnClickListener(v -> sendMessage());
        mRatingButton.setOnClickListener(v -> redirectToActivity(RatingProfileActivity.class));
    }




    private void sendMessage() {
        String msg = mMsgEditText.getText().toString();
        if (!TextUtils.isEmpty(msg)) {
            HashMap<String, Object> message = new HashMap<>();
            message.put(KEY_SEND_TO, mToUser);
            message.put(KEY_FROM_MSG, CacheUtilities.getUserName(this));
            message.put(KEY_MSG, msg);
            mDb.collection(KEY_COLLECTION_CHAT).add(message);
            mMsgEditText.setText(null);
        }
    }


    private void redirectToActivity(Class<?> cls) {
        Intent intent = new Intent(MessengerActivity.this, cls);
        startActivity(intent);
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
        if(id==R.id.nav_additem)
        {
            Intent intent=new Intent(this, ItemActivity.class);
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
        if(id==R.id.nav_messanger)
        {
            Intent intent=new Intent(this, ClodMessengerActivity.class);
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
