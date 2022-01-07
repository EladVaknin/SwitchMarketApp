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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemActivity extends AppCompatActivity {
    private EditText mDescriptionEditText, mPricedEditText, mSwitchEditText;
    private ImageView mItemImage;
    private Uri mUriImage;
    private Button mUpload, mChoseFile;
    private ProgressBar mProgressBar;
    private final DatabaseReference mDbUser = FirebaseDatabase.getInstance().getReference(USERS_TABLE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initViews();

    }

    private void initViews() {
        mChoseFile = findViewById(R.id.ChoseFileButton);
        mChoseFile.setOnClickListener(v -> chosePhoto());
        mUpload = findViewById(R.id.UploadButton);
        mDescriptionEditText = (EditText) findViewById(R.id.editTextDescription);
        mPricedEditText = (EditText) findViewById(R.id.editTextPrice);
        mSwitchEditText = (EditText) findViewById(R.id.editTextSwitch);
        mItemImage = findViewById(R.id.ItemimageView);
        mProgressBar = findViewById(R.id.progressBarItem);
        mUpload.setOnClickListener(v -> uploadFile());

    }

    private void handleProgressBar(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    private void uploadFile() {

        if (mUriImage == null || TextUtils.isEmpty(mDescriptionEditText.getText().toString()) || TextUtils.isEmpty(mPricedEditText.getText().toString()) || TextUtils.isEmpty(mSwitchEditText.getText().toString())) {
            Toast.makeText(this, "Please fill all relevatns fields/choose image", Toast.LENGTH_SHORT).show();
            return;
        }
        handleProgressBar(true);
        uploadDataToFireBase(mUriImage, mDescriptionEditText.getText().toString(), mPricedEditText.getText().toString());


    }

    private void uploadDataToFireBase(Uri imageUri, String desc, String price) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("upload");
        final String randomUUid = UUID.randomUUID().toString();
        String imageName = randomUUid + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(imageUri));
        final StorageReference imageRef = storageReference.child(imageName);
        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.continueWithTask(task -> imageRef.getDownloadUrl()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, String> itemMap = new HashMap<>();
                itemMap.put("desc", desc);
                itemMap.put("toSwitch", mSwitchEditText.getText().toString());
                itemMap.put("price", price);
                itemMap.put("imageItem", task.getResult().toString());
                mDbUser.child(FirebaseAuth.getInstance().getUid()).child("items").child(randomUUid).setValue(itemMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ItemActivity.this, "Item uploaded", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            handleProgressBar(false);
                            Toast.makeText(ItemActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void chosePhoto() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        galleryActivityLauncher.launch(openGalleryIntent);
    }

    ActivityResultLauncher<Intent> galleryActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            mUriImage = data.getData();
                            mItemImage.setImageURI(data.getData());
                        }
                    }
                }
            });








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
