package com.example.fate;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        ImageView imageView = findViewById(R.id.threadImageView);
        ImageView imageView1 = findViewById(R.id.imageViewFa);
        ImageView imageView2 = findViewById(R.id.imageViewTe);

        Drawable imageDrawable = ContextCompat.getDrawable(this, R.drawable.heart);
        Drawable imageDrawable1 = ContextCompat.getDrawable(this, R.drawable.fa);
        Drawable imageDrawable2 = ContextCompat.getDrawable(this, R.drawable.te);

        ClipDrawable clipDrawable = new ClipDrawable(imageDrawable, Gravity.START, ClipDrawable.HORIZONTAL);
        ClipDrawable clipDrawable1 = new ClipDrawable(imageDrawable1, Gravity.START, ClipDrawable.HORIZONTAL);
        ClipDrawable clipDrawable2 = new ClipDrawable(imageDrawable2, Gravity.START, ClipDrawable.HORIZONTAL);

        imageView.setImageDrawable(clipDrawable);
        imageView1.setImageDrawable(clipDrawable1);
        imageView2.setImageDrawable(clipDrawable2);

        ObjectAnimator animator = ObjectAnimator.ofInt(clipDrawable, "level", 0, 10000);
        ObjectAnimator animator1 = ObjectAnimator.ofInt(clipDrawable1, "level", 0, 10000);
        ObjectAnimator animator2 = ObjectAnimator.ofInt(clipDrawable2, "level", 0, 10000);

        animator.setDuration(3000);
        animator1.setDuration(3000);
        animator2.setDuration(3000);

        animator.start();
        animator1.start();
        animator2.start();

    }
    TextView tvTouchToContinue;
    @Override
    protected void onStart() {
        super.onStart();
        tvTouchToContinue = findViewById(R.id.tvTouchToContinue);

        FirebaseUser user = mAuth.getCurrentUser();
        startPulseAnimation();

        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = user.getUid();
            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String nickname = document.getString("nickname");
                                    String birthdate = document.getString("birthdate");
                                    String annivdate = document.getString("anniversaryDate");
                                    String pairingCode = document.getString("pairedWith");

                                    if (!TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(birthdate) && !TextUtils.isEmpty(annivdate)) {
                                        tvTouchToContinue.setOnClickListener(view ->{
                                            startActivity(new Intent(MainActivity.this, Welcome.class));
                                        });
                                    } else {
                                        tvTouchToContinue.setOnClickListener(view ->{
                                            startActivity(new Intent(MainActivity.this, nickname.class));
                                        });
                                    }
                                    if(!TextUtils.isEmpty(pairingCode)){
                                        tvTouchToContinue.setOnClickListener(view ->{
                                            startActivity(new Intent(MainActivity.this, home.class));
                                        });
                                    }
                                }
                            }
                        }
                    });
        } else {
            tvTouchToContinue.setOnClickListener(view ->{
                startActivity(new Intent(MainActivity.this, RegisterPage.class));
            });
        }
    }

    private void startPulseAnimation() {
        TextView textView = findViewById(R.id.textView);

        ObjectAnimator pulseAnimator = ObjectAnimator.ofFloat(textView, "alpha", 0.5f, 1f);
        pulseAnimator.setDuration(1000); // duration of one pulse
        pulseAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        pulseAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        pulseAnimator.start();
    }

}