package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.w3c.dom.ProcessingInstruction;

public class surprises extends AppCompatActivity {
    ImageButton ibExit, ibShop;
    ImageView ivHome, ivProfile, ivMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprises);

        ibExit = findViewById(R.id.exit);
        ibShop = findViewById(R.id.shop);
        ivHome = findViewById(R.id.home);
        ivMessage = findViewById(R.id.message);
        ivProfile = findViewById(R.id.profile);
    }
    @Override
    protected void onStart() {
        super.onStart();

        ibExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(surprises.this, shop.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(surprises.this, home.class));
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(surprises.this, Profile.class));
            }
        });
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(surprises.this, chat.class));
            }
        });
    }
}