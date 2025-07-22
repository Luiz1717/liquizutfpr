package com.liquiz.utfprliquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Anima os elementos da tela
        ImageView logo = findViewById(R.id.appLogo);
        TextView title = findViewById(R.id.splash_title);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        logo.startAnimation(fadeIn);
        title.startAnimation(bounce);

        // Redireciona diretamente para StartActivity após o delay
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, StartActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, SPLASH_DURATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Evita que o usuário volte para a splash screen
        finish();
    }
}
