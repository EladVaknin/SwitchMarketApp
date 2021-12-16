package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.models.Item;
import com.example.swichmarketapp.models.User;
import com.example.swichmarketapp.utlities.Utilitie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemActivity extends AppCompatActivity {
    private EditText mDescriptionEditText, mPricedEditText ,mSwitchEditText;
    private ImageView mPhoto;
    public static final String ITEM_TABLE = "items";
    private final DatabaseReference mDbItem = FirebaseDatabase.getInstance().getReference(RegisterActivity.ITEM_TABLE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initViews();
    }

    private void initViews() {
        mDescriptionEditText  = (EditText) findViewById(R.id.editTextDescription);
        mPricedEditText = (EditText) findViewById(R.id.editTextPrice);
        mSwitchEditText = (EditText) findViewById(R.id.editTextSwitch);
        mPhoto = findViewById(R.id.uploadPhotoButton);
    }

    private void performRegister() {
        String Description =mDescriptionEditText.getText().toString();
        String ToSwitch = mSwitchEditText.getText().toString();
        String price = mPricedEditText.getText().toString();
        String photo = mPhoto.toString();
        if (TextUtils.isEmpty(Description) || TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Description or price is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // create the item
        Item item = new Item(Description, photo,ToSwitch, price);
        // upload to the fireBase
        mDbItem.child(photo).setValue(item);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }



}
