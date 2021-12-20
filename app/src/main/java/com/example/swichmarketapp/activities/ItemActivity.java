package com.example.swichmarketapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.models.Item;
import com.example.swichmarketapp.models.User;
import com.example.swichmarketapp.utlities.Utilitie;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class ItemActivity extends AppCompatActivity {
    private EditText mDescriptionEditText, mPricedEditText ,mSwitchEditText;
    private ImageView mPhoto;
    private Button mUpload ,mChoseFile;
    private Uri mImageUri;
    private ProgressBar mProgressBar;
    public static final String ITEM_TABLE = "items";
 //   private final DatabaseReference mDbItem = FirebaseDatabase.getInstance().getReference(RegisterActivity.ITEM_TABLE);
    DatabaseReference mDbItem ;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private DatabaseReference dbUserpicture;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initViews();
        initFaireBase();
    }

    private void initViews() {
        mChoseFile =findViewById(R.id.ChoseFileButton);
        mUpload =findViewById(R.id.UploadButton);
        mDescriptionEditText  = (EditText) findViewById(R.id.editTextDescription);
        mPricedEditText = (EditText) findViewById(R.id.editTextPrice);
        mSwitchEditText = (EditText) findViewById(R.id.editTextSwitch);
        mPhoto = findViewById(R.id.ItemimageView);
        mProgressBar = findViewById(R.id.progressBarItem);
        mUpload.setOnClickListener(v -> UploadFileButton());
        mChoseFile.setOnClickListener(v -> ChoseFile());
    }
    private void initFaireBase(){
        FirebaseUser take_id= FirebaseAuth.getInstance().getCurrentUser();
        String userId= take_id.getUid();
        dbUserpicture= FirebaseDatabase.getInstance().getReference("users").child(userId).child("picture");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads"+userId);
        mDbItem = FirebaseDatabase.getInstance().getReference("users").child(userId).child("picture");
    }



//    private void performRegister() {
//        String Description =mDescriptionEditText.getText().toString();
//        String ToSwitch = mSwitchEditText.getText().toString();
//        String price = mPricedEditText.getText().toString();
//        String photo = mPhoto.toString();
//        if (TextUtils.isEmpty(Description) || TextUtils.isEmpty(price)) {
//            Toast.makeText(this, "Description or price is empty", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        // create the item
//        Item item = new Item(Description, photo,ToSwitch, price);
//        // upload to the fireBase
//        mDbItem.child(photo).setValue(item);
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//    }



    private void UploadFileButton() {
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(ItemActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        } else {
            uploadFile();
        }
    }


    private void uploadFile() {
        if (mImageUri != null)
        {
            mStorageRef.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return mStorageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
//                        Log.e(TAG, "then: " + downloadUri.toString());
                        String Discription =mDescriptionEditText.getText().toString().trim();
                        String ImageURL = downloadUri.toString();
                        Item item = new Item(Discription,ImageURL);
                        mDbItem.push().setValue(item);
                    } else
                    {
                        Toast.makeText(ItemActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    sendTo();
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void sendTo()
    {
        Intent intent=new Intent(this, ProfileActivity.class);
        startActivity(intent);

    }

    private void ChoseFile (){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
          //  Picasso.get().load(mImageUri).into(mImageView);
        }
    }


}
