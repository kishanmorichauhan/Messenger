package com.example.chatapp.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.example.chatapp.databinding.ActivityPhoneNumberBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PhoneNumberActivity extends AppCompatActivity {

    ActivityPhoneNumberBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Login to your account...");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(PhoneNumberActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        getSupportActionBar().hide();

        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                // validation

                if(TextUtils.isEmpty(binding.loginEmail.getText().toString()) || TextUtils.isEmpty(binding.loginPassword.getText().toString())){
                    progressDialog.dismiss();
                    Toast.makeText(PhoneNumberActivity.this, "Enter All Detail", Toast.LENGTH_SHORT).show();
                }else if(!binding.loginEmail.getText().toString().contains("@paruluniversity.ac.in")){
                    progressDialog.dismiss();
                    binding.loginEmail.setError("Please Enter Parul University Email");
                    Toast.makeText(PhoneNumberActivity.this, "Please Enter Parul University Email", Toast.LENGTH_SHORT).show();
                }else if (binding.loginPassword.getText().toString().length()<6){
                    progressDialog.dismiss();
                    binding.loginPassword.setError("Invalid Password");
                    Toast.makeText(PhoneNumberActivity.this, "Password length is too short", Toast.LENGTH_SHORT).show();
                }else {

                    //Authentication

                    auth.signInWithEmailAndPassword(binding.loginEmail.getText().toString(),binding.loginPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(PhoneNumberActivity.this , MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(PhoneNumberActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }

            }
        });

        binding.txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneNumberActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}