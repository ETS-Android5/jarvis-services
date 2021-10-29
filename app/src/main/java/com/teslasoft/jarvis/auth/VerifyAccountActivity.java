package com.teslasoft.jarvis.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SmartToast;
import android.widget.TextView;

import com.teslasoft.libraries.support.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {

        } else {
            verifyAccountById(resultCode - 20);
        }
    }

    public void verifyAccountById(int id) {
        // SmartToast.create(Integer.toString(id), VerifyAccountActivity.this);
        try {
            // SmartToast.create("No errors", VerifyAccountActivity.this);
            AccountManager am =  (AccountManager)this.getSystemService(Context.ACCOUNT_SERVICE);
            Account[] accountsList = am.getAccountsByType("org.teslasoft.id.JARVIS_ACCOUNT");
            String token = am.getUserData(accountsList[id], "auth_token");
            String uid = am.getUserData(accountsList[id], "user_id");
            String url = "https://usercontent.teslasoft.org/a/ValidateAuthState.php?token=".concat(token).concat("&uid=").concat(uid);
            // SmartToast.create(url, VerifyAccountActivity.this);
            new DownloadAccountInfo().execute(url);
        } catch (Exception e) {
            // SmartToast.create("Exception caught", VerifyAccountActivity.this);
            this.setResult(3);
            finish();
        }
    }

    class DownloadAccountInfo extends AsyncTask<String, String, String> {
        private Exception exception;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls)
        {
            try {
                URL url = new URL(urls[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                // SmartToast.create("connection error", VerifyAccountActivity.this);
                VerifyAccountActivity.this.setResult(3);
                finish();
            }

            return null;
        }

        protected void onPostExecute(String data) {
            // SmartToast.create(data, VerifyAccountActivity.this);
            try {
                // SmartToast.create(data, VerifyAccountActivity.this);
                if (data.equals("true")) {
                    VerifyAccountActivity.this.setResult(Activity.RESULT_OK);
                    // SmartToast.create("Account has been verified successfully", VerifyAccountActivity.this);
                    finish();
                } else {
                    VerifyAccountActivity.this.setResult(3);
                    // SmartToast.create("Error: this account has no auth token", VerifyAccountActivity.this);
                    finish();
                }
            } catch (Exception e) {
                VerifyAccountActivity.this.setResult(3);
                finish();
            }
        }
    }
}
