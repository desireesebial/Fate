package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class notifications extends AppCompatActivity {
    TextView tvNameOne, tvNameTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        tvNameOne = findViewById(R.id.nameOne);
        tvNameTwo = findViewById(R.id.nameTwo);

        displayNicknames();
    }
    private void displayNicknames() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            return;
        }

        String currentUserId = currentUser.getUid();

        db.collection("users").document(currentUserId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String currentUserNickname = document.getString("nickname");
                    String pairedUserId = document.getString("pairedWith");
                    tvNameOne.setText(currentUserNickname);

                    if (pairedUserId != null && !pairedUserId.isEmpty()) {
                        db.collection("users").document(pairedUserId).get().addOnCompleteListener(pairedTask -> {
                            if (pairedTask.isSuccessful()) {
                                DocumentSnapshot pairedDocument = pairedTask.getResult();
                                if (pairedDocument.exists()) {
                                    String pairedUserNickname = pairedDocument.getString("nickname");
                                    tvNameTwo.setText(pairedUserNickname);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}