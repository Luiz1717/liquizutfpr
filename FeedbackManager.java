package com.liquiz.utfprliquiz;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.widget.Button;

public class FeedbackManager {
    private final Vibrator vibrator;

    public FeedbackManager(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            this.vibrator = vibratorManager != null ? vibratorManager.getDefaultVibrator() : null;
        } else {
            this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
    }
    public void showFeedback(boolean isCorrect, Button button) {
        // Muda cor do botão
        button.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);

        // Feedback tátil
        if(isCorrect) {
            vibrate(true); // Vibração curta para acerto
        } else {
            vibrate(false); // Vibração longa para erro
        }
    }
    public void vibrate(boolean isSuccess) {
        if (!hasVibrator()) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int amplitude = isSuccess ? VibrationEffect.DEFAULT_AMPLITUDE : VibrationEffect.DEFAULT_AMPLITUDE / 2;
            long duration = isSuccess ? 50 : 100;
            vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude));
        } else {
            vibrator.vibrate(isSuccess ? 50 : 100);
        }
    }

    // Método para padrões de vibração (ADICIONE ESTE MÉTODO)
    public void vibratePattern(long[] pattern) {
        if (!hasVibrator()) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
        } else {
            vibrator.vibrate(pattern, -1);
        }
    }

    private boolean hasVibrator() {
        return vibrator != null && vibrator.hasVibrator();
    }
}