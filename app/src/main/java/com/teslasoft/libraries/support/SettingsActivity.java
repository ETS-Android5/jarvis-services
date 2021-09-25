package com.teslasoft.libraries.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.SmartToast;
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
            // User tried to disable or bypass license checking service, exit
            this.setResult(Activity.RESULT_CANCELED);
            finishAndRemoveTask();
        }

        SharedPreferences settings = this.getSharedPreferences("core_settings", Context.MODE_PRIVATE);
        try {
            String isDarkTheme = settings.getString("dark_theme", null);
            // SmartToast.create(isDarkTheme, this);
            if (isDarkTheme.equals("true")) {
                theme.setChecked(true);
            } else {
                theme.setChecked(false);
            }
        } catch (Exception e) {
            theme.setChecked(true);
            setDarkTheme();
        }
        theme.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // SmartToast.create(String.valueOf(theme.isChecked()), SettingsActivity.this);
                        if(theme.isChecked()) {
                            setDarkTheme();
                            SettingsActivity.this.recreate();
                        } else {
                            setLightTheme();
                            SettingsActivity.this.recreate();
                        }
                    }
                }
        );
    }

    public void setDarkTheme() {
        SharedPreferences settings = this.getSharedPreferences("core_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("dark_theme", "true");
        editor.apply();
    }

    public void setLightTheme() {
        SharedPreferences settings = this.getSharedPreferences("core_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("dark_theme", "false");
        editor.apply();
    }

    /* Piracy check starts */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // License check passed
        } else {
            // License check failed, exit
            this.setResult(Activity.RESULT_CANCELED);
            finishAndRemoveTask();
        }
    }
    /* Piracy check ends */

    public void DoNothing(View v) {

    }
}
