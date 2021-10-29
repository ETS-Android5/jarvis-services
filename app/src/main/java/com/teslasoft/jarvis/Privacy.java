package com.teslasoft.jarvis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.view.View;
import android.content.SharedPreferences;

import com.teslasoft.libraries.support.R;

public class Privacy extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consent);
    }

    public void dismissWindow(View v) {
        this.setResult(3);
        SharedPreferences privacy = this.getSharedPreferences("privacy_accepted", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = privacy.edit();
        editor.remove("privacy");
        editor.apply();
        finish();
    }

    public void unlockApp(View v) {
        this.setResult(Activity.RESULT_OK);
        SharedPreferences privacy = this.getSharedPreferences("privacy_accepted", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = privacy.edit();
        editor.putString("privacy", "yes");
        editor.apply();
        finish();
    }

    public void openSite(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://teslasoft.org/privacy"));
        startActivity(intent);
    }

    public void mailMe(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:contact.teslasoft@gmail.com"));
        startActivity(intent);
    }
}
