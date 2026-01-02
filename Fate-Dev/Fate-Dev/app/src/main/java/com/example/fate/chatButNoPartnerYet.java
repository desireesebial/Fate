package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class chatButNoPartnerYet extends AppCompatActivity {

    ImageView ivBack, ivHome, ivProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_but_no_partner_yet);

        ivBack = findViewById(R.id.back);
        ivHome = findViewById(R.id.home);
        ivProfile = findViewById(R.id.profile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chatButNoPartnerYet.this, profileButNoPartnerYet.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chatButNoPartnerYet.this, Welcome.class));
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}