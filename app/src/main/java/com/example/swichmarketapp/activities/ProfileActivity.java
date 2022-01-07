package com.example.swichmarketapp.activities;

import static com.example.swichmarketapp.activities.RegisterActivity.USERS_TABLE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private TextView mFullName, mPhone;
    private ImageView mPictureImageView;
    private RatingBar mRating;
    private Button mMyItems;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(USERS_TABLE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
    }

    private void initViews() {
        mMyItems = findViewById(R.id.MyItemsButton);
        mMyItems.setOnClickListener(v -> redictToMyItems ());
        mPictureImageView = findViewById(R.id.profile_image_view);
        mPictureImageView.setOnClickListener(v -> choosePictureFromGalleryAndUploadToTheFireBase());
        if (!TextUtils.isEmpty(CacheUtilities.getImageProfile(this))) {
            Picasso.get().load(CacheUtilities.getImageProfile(this)).noPlaceholder().into(mPictureImageView);
        }
        mFullName = findViewById(R.id.userNameTextView);
        mFullName.setText(mFullName.getText() + CacheUtilities.getUserName(this));
        mPhone = findViewById(R.id.phoneNumberTextView);
        mPhone.setText(mPhone.getText() + CacheUtilities.getPhoneNumber(this));
        mRating = findViewById(R.id.rating_bar);
        mRating.setRating((float) CacheUtilities.getRating(this));

    }

    private void redictToMyItems() {
        Intent intent = new Intent(ProfileActivity.this, MyItemsActivity.class);
        startActivity(intent);

    }

    ActivityResultLauncher<Intent> galleryActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            mPictureImageView.setImageURI(data.getData());
                            uploadImageToTheFireBase(data.getData());
                        } else {
                        }
                    }
                }
            });


    private void uploadImageToTheFireBase(Uri imageUri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images");
        String imageName = FirebaseAuth.getInstance().getCurrentUser().getUid() + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(imageUri));
        final StorageReference imageRef = storageReference.child(imageName);
        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.continueWithTask(task -> imageRef.getDownloadUrl()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mDbUser.child(FirebaseAuth.getInstance().getUid()).child("profileUrl").setValue(task.getResult().toString());
                CacheUtilities.cacheImageProfile(ProfileActivity.this, task.getResult().toString());
            }
        });
    }


    public void choosePictureFromGalleryAndUploadToTheFireBase() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        galleryActivityLauncher.launch(openGalleryIntent);
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
        if(id==R.id.nav_additem)
        {
            Intent intent=new Intent(this, ItemActivity.class);
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
