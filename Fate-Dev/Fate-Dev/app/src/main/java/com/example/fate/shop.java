package com.example.fate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.icu.text.Replaceable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class shop extends AppCompatActivity {
    ImageView ivItems, ivResources;
    ImageButton ibExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        ivResources = findViewById(R.id.resources);
        ivItems = findViewById(R.id.items);
        ibExit = findViewById(R.id.exit);

        replaceFragment(new shopItems());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ivResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new shopResources());
            }
        });
        ivItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new shopItems());
            }
        });
        ibExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentForShop, fragment);
        fragmentTransaction.commit();
    }
}