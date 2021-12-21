package com.example.swichmarketapp.activities;

import static com.example.swichmarketapp.activities.RegisterActivity.USERS_TABLE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(USERS_TABLE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
    }

    private void initViews() {
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


}
