package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class preview1 extends AppCompatActivity {
    TextView tvClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview1);

        tvClick = findViewById(R.id.click);
    }
    @Override
    protected void onStart() {
        super.onStart();

        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(preview1.this, preview2.class));
            }
        });
    }
}