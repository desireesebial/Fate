package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class home extends AppCompatActivity {
    ImageButton ibTokens, ibGifts, ibChat, ibQuiz, ibNotif;
    ImageView ivMessage, ivProfile;
    TextView tvNameOne, tvNameTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ibTokens = findViewById(R.id.tokens);
        ibGifts = findViewById(R.id.gift);
        ibChat = findViewById(R.id.chats);
        ibQuiz = findViewById(R.id.quiz);
        ibNotif = findViewById(R.id.Notif);
        ivMessage = findViewById(R.id.message);
        ivProfile = findViewById(R.id.profile);
        tvNameOne = findViewById(R.id.nameOne);
        tvNameTwo = findViewById(R.id.nameTwo);

        displayNicknames();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ibTokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, shop.class));
            }
        });
        ibGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, shop.class));
            }
        });
        ibChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, chat.class));
            }
        });
        ibQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, question.class));
            }
        });
        ibNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, notifications.class));
            }
        });
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, chat.class));
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, Profile.class));
            }
        });
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
