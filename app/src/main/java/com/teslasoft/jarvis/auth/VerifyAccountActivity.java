package com.teslasoft.jarvis.auth;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.teslasoft.libraries.support.R;

public class VerifyAccountActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        Start();
    }

    public void Start() {
        try {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            int accId = extras.getInt("accId");
            verifyAccountById(accId);
        } catch (Exception e) {
            Intent intent = new Intent(this, AccountPickerActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            verifyAccountById(resultCode - 20);
        }
    }

    public void verifyAccountById(int id) {
        try {
            AccountManager am =  (AccountManager)this.getSystemService(Context.ACCOUNT_SERVICE);
            Account[] accountsList = am.getAccountsByType("org.teslasoft.id.JARVIS_ACCOUNT");
            String token = am.getUserData(accountsList[id], "auth_token");
            String uid = am.getUserData(accountsList[id], "user_id");
            String url = "https://usercontent.teslasoft.org/a/ValidateAuthState.php?token=".concat(token).concat("&uid=").concat(uid);
            new DownloadAccountInfo().execute(url);
        } catch (Exception e) {
            this.setResult(3);
            finish();
        }
    }

    class DownloadAccountInfo extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                return bufferedReader.readLine();
            } catch (Exception e) {
                VerifyAccountActivity.this.setResult(3);
                finish();
            }

            return null;
        }

        protected void onPostExecute(String data) {
            try {
                VerifyAccountActivity.this.setResult(data.equals("true") ? Activity.RESULT_OK : 3);
                finish();
            } catch (Exception e) {
                VerifyAccountActivity.this.setResult(3);
                finish();
            }
        }
    }
}
