package com.teslasoft.libraries.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends Activity {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public Switch theme;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        theme = (Switch) findViewById(R.id.theme);

        try {
            Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
            startActivityForResult(licenseIntent, 1);
        } catch (Exception e) {
            this.setResult(Activity.RESULT_CANCELED);
            finishAndRemoveTask();
        }

        SharedPreferences settings = this.getSharedPreferences("activity_core_settings", Context.MODE_PRIVATE);
        try {
            String isDarkTheme = settings.getString("dark_theme", null);
            theme.setChecked(isDarkTheme.equals("true"));
        } catch (Exception e) {
            theme.setChecked(true);
            setDarkTheme();
        }
        theme.setOnClickListener(
                view -> {
                    if(theme.isChecked()) {
                        setDarkTheme();
                    } else {
                        setLightTheme();
                    }
                    SettingsActivity.this.recreate();
                }
        );
    }

    public void setDarkTheme() {
        SharedPreferences settings = this.getSharedPreferences("activity_core_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("dark_theme", "true");
        editor.apply();
    }

    public void setLightTheme() {
        SharedPreferences settings = this.getSharedPreferences("activity_core_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("dark_theme", "false");
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            this.setResult(Activity.RESULT_CANCELED);
            finishAndRemoveTask();
        }
    }

    public void DoNothing(View v) {}
}
