package com.example.fate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class chat extends AppCompatActivity {
    ImageButton ibBack, ibGifts, ibTokens, ibNotif, ibSend;
    ImageView ivHome, ivProfile, ivChat;
    EditText etMessageBox;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ibBack = findViewById(R.id.back);
        ibGifts = findViewById(R.id.gift);
        ibTokens = findViewById(R.id.tokens);
        ibNotif = findViewById(R.id.Notif);
        ivChat = findViewById(R.id.message);
        ivHome = findViewById(R.id.home);
        ivProfile = findViewById(R.id.profile);
        etMessageBox = findViewById(R.id.messagebox);
        ibSend = findViewById(R.id.send);

        fetchPairedUserId();
    }
    @Override
    protected void onStart() {
        super.onStart();
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ibGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat.this, shop.class));
            }
        });
        ibTokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat.this, shop.class));
            }
        });
        ibNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat.this, notifications.class));
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat.this, Profile.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat.this, home.class));
            }
        });
        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    private void sendMessage() {
        String messageText = etMessageBox.getText().toString().trim();

        if (!messageText.isEmpty()) {
            sendMessageToPairedWith(messageText);
            etMessageBox.setText("");
        }
    }
    private String pairedWithUserId;
    private void fetchPairedUserId() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            DocumentReference pairRef = db.collection("users").document(currentUserId);
            pairRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        pairedWithUserId = documentSnapshot.getString("pairedWith");
                    }
                }
            });
        }
    }
    private void sendMessageToPairedWith(String messageText) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && pairedWithUserId != null) {
            String currentUserId = currentUser.getUid();

            Map<String, Object> message = new HashMap<>();
            message.put("senderId", currentUserId);
            message.put("recipientId", pairedWithUserId);
            message.put("content", messageText);
            message.put("timestamp", new Date());

            CollectionReference messagesRef = db.collection("messages");
            messagesRef.add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(chat.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(chat.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}