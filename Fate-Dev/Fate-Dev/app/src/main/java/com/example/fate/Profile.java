package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {
    ImageView ivBack, ivMessage, ivHome;
    ImageButton ibSurprises, ibNotes, ibMemories, ibSettings;
    Button btnCoolOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBack = findViewById(R.id.back);
        ibSurprises = findViewById(R.id.surprises);
        ibNotes = findViewById(R.id.notes);
        ibMemories = findViewById(R.id.memories);
        ibSettings = findViewById(R.id.goSettings);
        btnCoolOff = findViewById(R.id.CoolOff);
        ivMessage = findViewById(R.id.message);
        ivHome = findViewById(R.id.home);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibSurprises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, surprises.class));
            }
        });
        ibNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Notes.class));
            }
        });
        ibMemories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, memories.class));
            }
        });
        ibSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, settings.class));
            }
        });
        btnCoolOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, chat.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, chat.class));
            }
        });
    }
}