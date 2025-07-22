package com.liquiz.utfprliquiz;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    // Constants
    private static final String PREFS_NAME = "AppPrefs";
    private static final String FIRST_RUN_KEY = "first_run";
    private static final String QUIZ_CATEGORY_KEY = "QUIZ_CATEGORY";
    private static final int VIBRATION_DURATION = 30;
    private static final int VIBRATION_AMPLITUDE = 150;
    private static final int ANIMATION_DELAY = 200;

    // Components
    private Vibrator vibrator;
    private Animation scaleAnimation;
    private SharedPreferences preferences;
    private Spinner quizCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initializeComponents();
        checkFirstRun();
    }

    private void initializeComponents() {
        // View initialization
        quizCategorySpinner = findViewById(R.id.quizCate);

        // System services
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Animations
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up);
    }

    private void checkFirstRun() {
        if (preferences.getBoolean(FIRST_RUN_KEY, true)) {
            showWelcomeMessage();
            preferences.edit().putBoolean(FIRST_RUN_KEY, false).apply();
        }
    }

    public void startQuiz(View view) {
        handleButtonInteraction(view, () -> {
            int selectedCategory = quizCategorySpinner.getSelectedItemPosition();

            if (selectedCategory == 0) {
                Toast.makeText(this, R.string.select_category_warning, Toast.LENGTH_SHORT).show();
                return;
            }

            startActivityWithTransition(
                    new Intent(this, MainActivity.class)
                            .putExtra(QUIZ_CATEGORY_KEY, selectedCategory),
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        });
    }

    public void exitQuiz(View view) {
        handleButtonInteraction(view, () -> {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();
        });
    }

    public void openSettings(View view) {
        handleButtonInteraction(view, () -> {
            startActivityWithTransition(
                    new Intent(this, SettingsActivity.class),
                    R.anim.slide_in_bottom,
                    R.anim.fade_out
            );
        });
    }

    private void handleButtonInteraction(View view, Runnable action) {
        vibrateButton();
        view.startAnimation(scaleAnimation);

        new Handler().postDelayed(action, ANIMATION_DELAY);
    }

    private void startActivityWithTransition(Intent intent, int enterAnim, int exitAnim) {
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void vibrateButton() {
        if (vibrator == null || !vibrator.hasVibrator()) return;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VIBRATION_AMPLITUDE));
        } else {
            vibrator.vibrate(VIBRATION_DURATION);
        }
    }

    private void showWelcomeMessage() {
        Toast.makeText(this, R.string.welcome_message, Toast.LENGTH_LONG).show();
    }
}