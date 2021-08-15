package com.teslasoft.jarvis.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.accounts.AccountManager;

public class AccountService extends Service {
    private static CustomAuthenticator AUTHENTICATOR = null;

    @Override
    public IBinder onBind(Intent intent) {
        return intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT) ? getAuthenticator().getIBinder() : null;
    }

    private CustomAuthenticator getAuthenticator() {
        if (AUTHENTICATOR == null)
            AUTHENTICATOR = new CustomAuthenticator(this);
        return AUTHENTICATOR;
    }
}
