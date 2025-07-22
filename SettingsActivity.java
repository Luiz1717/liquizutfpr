package com.liquiz.utfprliquiz;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SwitchCompat colorBlindSwitch = findViewById(R.id.colorBlindSwitch);
        SwitchCompat hapticSwitch = findViewById(R.id.hapticSwitch);
        SeekBar vibrationSeekBar = findViewById(R.id.vibrationSeekBar);

        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Carregar configurações
        colorBlindSwitch.setChecked(prefs.getBoolean("color_blind_mode", false));
        hapticSwitch.setChecked(prefs.getBoolean("haptic_feedback", true));
        vibrationSeekBar.setProgress(prefs.getInt("vibration_duration", 200));

        // Listeners
        colorBlindSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("color_blind_mode", isChecked).apply();
        });

        hapticSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("haptic_feedback", isChecked).apply();
        });

        vibrationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prefs.edit().putInt("vibration_duration", progress).apply();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}