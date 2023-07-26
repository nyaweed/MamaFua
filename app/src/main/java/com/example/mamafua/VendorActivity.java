package com.example.mamafua;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class VendorActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTopic, uploadPhone, uploadDesc, uploadLang;
    String imageURL;
    Uri uri;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        uploadImage = findViewById(R.id.uploadImage);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadLang = findViewById(R.id.uploadLang);
        uploadPhone = findViewById(R.id.uploadPhone);
        saveButton = findViewById(R.id.saveButton);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(VendorActivity.this, "Please Select Your Photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToDatabase();
            }
        });
    }

    public void saveData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("VendorRequests")
                .child(uri.getLastPathSegment());


        AlertDialog.Builder builder = new AlertDialog.Builder(VendorActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

/*    public void uploadData() {

        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        String lang = uploadLang.getText().toString();
        String phone = uploadPhone.getText().toString();

        User dataClass = new User(title, phone, desc, lang, imageURL);

        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        String userId = firebaseUser.getUid();
        String email = firebaseUser.getEmail();

        FirebaseDatabase.getInstance().getReference("VendorRequests").child(userId)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VendorActivity.this, "You will receive an email shortly.. ", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VendorActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }  */

    public void saveDataToDatabase() {

        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        String lang = uploadLang.getText().toString();
        String phone = uploadPhone.getText().toString();
        String email = firebaseUser.getEmail();
        String userId = firebaseUser.getUid();

        User dataClass = new User(title, phone, desc, lang, imageURL);

        // Check if the email exists in the "users" reference
        checkEmailExists(userId, emailExists -> {
            if (emailExists) {
                // Email exists in the "users" reference
                // Notify the user that the email is already registered
                Toast.makeText(VendorActivity.this, "Email Exists", Toast.LENGTH_LONG).show();
            } else {
                // Email does not exist in the "users" reference
                // Proceed with saving data to "VendorRequests"
                FirebaseDatabase.getInstance().getReference("VendorRequests").child(userId)
                        .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(VendorActivity.this, "You will receive an email shortly.. ", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(VendorActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    private interface EmailExistsCallback {
        void onEmailChecked(boolean emailExists);
    }

    private void checkEmailExists(String email, EmailExistsCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.child("Users").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean emailExists = dataSnapshot.exists();
                callback.onEmailChecked(emailExists);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value, handle error if needed
            }
        });
    }
}