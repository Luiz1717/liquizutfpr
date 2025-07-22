package com.liquiz.utfprliquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    // Constants
    private static final String SHARED_PREFS_NAME = "QUIZ_DATA";
    private static final String TOTAL_SCORE_KEY = "TOTAL_SCORE";
    private static final String RIGHT_ANSWER_KEY = "RIGHT_ANSWER_COUNT";
    private static final int MAX_QUESTIONS = 15;

    // Vibration Patterns
    private static final long[] VICTORY_PATTERN = {0, 100, 30, 100, 30, 200};

    private FeedbackManager feedbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initializeComponents();
        processScore();
    }

    private void initializeComponents() {
        feedbackManager = new FeedbackManager(this);
    }

    private void processScore() {
        int score = getIntent().getIntExtra(RIGHT_ANSWER_KEY, 0);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        // Update total score
        int totalScore = prefs.getInt(TOTAL_SCORE_KEY, 0) + score;
        prefs.edit().putInt(TOTAL_SCORE_KEY, totalScore).apply();

        updateUI(score, totalScore);
        provideFeedback(score);
    }

    private void updateUI(int score, int totalScore) {
        TextView resultLabel = findViewById(R.id.resultLabel);
        TextView totalScoreLabel = findViewById(R.id.totalScoreLabel);

        resultLabel.setText(String.format(Locale.getDefault(),
                "%d / %d ACERTOS", score, MAX_QUESTIONS));

        totalScoreLabel.setText(String.format(Locale.getDefault(),
                "PONTOS: %d", totalScore));
    }

    private void provideFeedback(int score) {
        if (feedbackManager == null) return;

        float percentage = (float) score / MAX_QUESTIONS;

        if (percentage > 0.8f) {
            feedbackManager.vibratePattern(VICTORY_PATTERN);  // Usando o novo método
        } else if (percentage > 0.5f) {
            feedbackManager.vibrate(true);  // Feedback positivo
        } else {
            feedbackManager.vibrate(false); // Feedback negativo
        }
    }

    public void returnTop(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}