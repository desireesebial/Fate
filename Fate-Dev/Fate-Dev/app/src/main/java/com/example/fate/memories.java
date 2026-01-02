package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.firestore.core.OnlineState;

public class memories extends AppCompatActivity {
    ImageView ivMessage, ivProfile, ivHome;
    ImageButton ibExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        ivMessage = findViewById(R.id.message);
        ivProfile = findViewById(R.id.profile);
        ivHome = findViewById(R.id.home);
        ibExit = findViewById(R.id.exit);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(memories.this, chat.class));
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(memories.this, Profile.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(memories.this, chat.class));
            }
        });
        ibExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}