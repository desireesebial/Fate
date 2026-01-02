package com.example.fate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class RegisterPage extends AppCompatActivity {
    private static final String TAG = "RegisterPage";
    EditText etRegEmail;
    EditText etUserName;
    EditText etPassword;
    ImageButton ibCancel;
    ImageButton ibSubmit;
    FirebaseAuth mAuth;
    CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        etRegEmail = findViewById(R.id.etEmailAddress);
        etUserName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        ibCancel = findViewById(R.id.ibCancel);
        ibSubmit = findViewById(R.id.ibSubmit);
        cbTerms = findViewById(R.id.cbTerms);

        mAuth = FirebaseAuth.getInstance();
        ibSubmit.setEnabled(false);
        ibSubmit.setOnClickListener(view ->{
            createUser();
        });
        ibCancel.setOnClickListener(view ->{
            startActivity(new Intent(RegisterPage.this, MainActivity.class));
        });
        cbTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ibSubmit.setEnabled(isChecked);
                ibSubmit.setAlpha(isChecked ? 1.0f : 0.5f);
            }
        });
    }
    private void createUser(){
        String email = etRegEmail.getText().toString();
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            etRegEmail.setError("Email cannot be empty.");
            etRegEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(userName)){
            etUserName.setError("Username cannot be empty.");
            etUserName.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            etPassword.setError("Password cannot be empty.");
            etPassword.requestFocus();
        }
        else if (password.length() <= 5){
            etPassword.setError("6 characters minimum.");
            etPassword.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            String pairingCode = generatePairingCode();
                            saveUser(userId, userName, pairingCode);
                        }

                        Toast.makeText(RegisterPage.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterPage.this, nickname.class));
                    }
                    else{
                        Toast.makeText(RegisterPage.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private String generatePairingCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    private void saveUser(String userId, String username, String code) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("pairing code", code);

        db.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User saved successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error saving user.", e);
                    }
                });
    }
}