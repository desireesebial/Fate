package com.example.fate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class birthdate extends AppCompatActivity {
    ImageButton ibNext;
    TextView tvBirthDate, tvX;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdate);

        ibNext = findViewById(R.id.ibNext);
        tvBirthDate = findViewById(R.id.tvBirthDate);
        datePicker = findViewById(R.id.datePicker);
        tvX = findViewById(R.id.tvX);
    }
    @Override
    protected void onStart() {
        super.onStart();

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedBirthDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                    tvBirthDate.setText(selectedBirthDate);
                });

        ibNext.setOnClickListener(view ->{
            String birthdate = tvBirthDate.getText().toString();
            saveBirthDate(birthdate);
            if (tvBirthDate.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter your birthdate.", Toast.LENGTH_SHORT).show();
            }
            else{
                startActivity(new Intent(birthdate.this, anniversary.class));
            }
        });

        tvX.setOnClickListener(view ->{
            startActivity(new Intent(birthdate.this, nickname.class));
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        fetchBirthDate();
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

    private void saveBirthDate(String birthDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("users").document(userId)
                    .update("birthdate", birthDate);
        }
    }
    private void fetchBirthDate() {
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
                                    String birthdate = document.getString("birthdate");
                                    tvBirthDate.setText(birthdate);
                                }
                            }
                        }
                    });
        }
    }
}