package com.example.chatapp.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.chatapp.Models.User;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    Uri imageUri;
    FirebaseStorage storage;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        auth = FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("users").child(Objects.requireNonNull(auth.getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              User user = snapshot.getValue(User.class);
                if (user != null) {

                    Glide.with(SettingActivity.this).load(user.getProfileImage())
                            .placeholder(R.drawable.avatar)
                            .into(binding.profileImage);

                    binding.etName.setText(user.getName());
                    binding.etEmail.setText(user.getPhoneNumber());
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1);
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                DatabaseReference reference = database.getReference().child("users").child(auth.getUid());
                StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());

                if(imageUri!=null){

                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    image = uri.toString();
                                    User users =new User(auth.getUid(),binding.etName.getText().toString(),binding.etEmail.getText().toString(),image);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SettingActivity.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(SettingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            progressDialog.dismiss();
                                        }
                                    });

                                }
                            });
                        }
                    });
                }else {
                    image ="https://firebasestorage.googleapis.com/v0/b/messenger-5ed06.appspot.com/o/user%20(2).png?alt=media&token=4e2527ca-91bc-4396-9246-31e830b11bc7";
                    User users =new User(auth.getUid(),binding.etName.getText().toString(),binding.etEmail.getText().toString(),image);
                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull  Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SettingActivity.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SettingActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();

                        }
                    });

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(data!=null){
                imageUri = data.getData();
                Glide.with(SettingActivity.this).load(imageUri)
                        .placeholder(R.drawable.avatar)
                        .into(binding.profileImage);

            }
        }
    }
}