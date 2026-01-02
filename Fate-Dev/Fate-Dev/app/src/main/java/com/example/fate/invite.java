package com.example.fate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class invite extends AppCompatActivity {
    private static final String TAG = "invite";
    ImageButton ibGift, ibTokens;
    ImageView ivMessage, ivProfile, ivHome, ivInvite;
    TextView tvPairingCode;
    EditText etPairingCodeInput;
    Button btnPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        fetchPairingCode();

        ibGift = findViewById(R.id.gift);
        ibTokens = findViewById(R.id.tokens);
        ivMessage = findViewById(R.id.message);
        ivProfile = findViewById(R.id.profile);
        ivHome = findViewById(R.id.home);
        ivInvite = findViewById(R.id.copyInvite);
        tvPairingCode = findViewById(R.id.code);
        etPairingCodeInput = findViewById(R.id.etPairingCodeInput);
        btnPair = findViewById(R.id.btnPair);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ibGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(invite.this, shop.class));
            }
        });

        ibTokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(invite.this, shop.class));
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(invite.this, profileButNoPartnerYet.class));
            }
        });
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(invite.this, chatButNoPartnerYet.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(invite.this, Welcome.class));
            }
        });
        ivInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyPairingCode();
            }
        });
        btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairWithUser();
            }
        });
    }

    private void fetchPairingCode() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String pairingCode = document.getString("pairing code");
                                tvPairingCode.setText(pairingCode);
                            }
                        } else {
                            Log.d(TAG, "Failed to fetch pairing code: ", task.getException());
                        }
                    }
                });
    }

    private void pairWithUser() {
        String pairingCode = etPairingCodeInput.getText().toString().trim();
        if (TextUtils.isEmpty(pairingCode)) {
            etPairingCodeInput.setError("Pairing code cannot be empty.");
            etPairingCodeInput.requestFocus();
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentUserId = currentUser.getUid();

        db.collection("users").whereEqualTo("pairing code", pairingCode)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String pairedUserId = document.getId();

                        if (!pairedUserId.equals(currentUserId)) {
                            updateUserPairing(currentUserId, pairedUserId);
                        } else {
                            Toast.makeText(this, "Cannot pair with yourself", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "The pairing code is invalid.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserPairing(String currentUserId, String pairedUserId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentUserId)
                .update("pairedWith", pairedUserId)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Paired successfully!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating pairing for current user.", e));

        db.collection("users").document(pairedUserId)
                .update("pairedWith", currentUserId)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(invite.this, "Paired successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(invite.this, home.class));
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error updating pairing for paired user.", e));
    }
    private void copyPairingCode() {
        String pairingCode = tvPairingCode.getText().toString();
        if (!TextUtils.isEmpty(pairingCode)) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Pairing Code", pairingCode);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Text copied to Clipboard.", Toast.LENGTH_SHORT).show();
        }
    }
}

