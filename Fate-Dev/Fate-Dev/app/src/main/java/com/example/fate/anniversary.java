package com.example.fate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class anniversary extends AppCompatActivity {

    ImageButton ibNext;
    TextView tvAnniversaryDate, x;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary);

        ibNext = findViewById(R.id.ibNext);
        tvAnniversaryDate = findViewById(R.id.tvAnniversaryDate);
        x = findViewById(R.id.tvX);
        datePicker = findViewById(R.id.datePicker);
    }

    @Override
    protected void onStart() {
        super.onStart();

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedAnnivDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                    tvAnniversaryDate.setText(selectedAnnivDate);
                });

        ibNext.setOnClickListener(view ->{
            String anniversarydate = tvAnniversaryDate.getText().toString();
            saveAnniversaryDate(anniversarydate);
            if (tvAnniversaryDate.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter your anniversary date.", Toast.LENGTH_SHORT).show();
            }
            else{
                startActivity(new Intent(anniversary.this, preview1.class));
            }
        });
        x.setOnClickListener(view ->{
            startActivity(new Intent(anniversary.this, birthdate.class));
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        fetchAnniversaryDate();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void saveAnniversaryDate(String anniversaryDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("users").document(userId)
                    .update("anniversaryDate", anniversaryDate);
        }
    }
    private void fetchAnniversaryDate() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String anniversaryDate = document.getString("anniversaryDate");
                                    tvAnniversaryDate.setText(anniversaryDate);
                                }
                            }
                        }
                    });
        }
    }
}