package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class question extends AppCompatActivity {
    ImageButton ibBack, ibGifts, ibTokens, ibNotif;
    ImageView ivHome, ivProfile, ivChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ibBack = findViewById(R.id.back);
        ibGifts = findViewById(R.id.gift);
        ibTokens = findViewById(R.id.tokens);
        ibNotif = findViewById(R.id.Notif);
        ivChat = findViewById(R.id.message);
        ivHome = findViewById(R.id.home);
        ivProfile = findViewById(R.id.profile);
    }
    @Override
    protected void onStart() {
        super.onStart();
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(question.this, shop.class));
            }
        });
        ibTokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(question.this, shop.class));
            }
        });
        ibNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(question.this, notifications.class));
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(question.this, Profile.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(question.this, home.class));
            }
        });
        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(question.this, chat.class));
            }
        });
    }
}