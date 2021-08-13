package com.teslasoft.jarvis.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        CustomAuthenticator authenticator = new CustomAuthenticator(this);
        Intent intent1 = new Intent(this, AuthActivity.class);
        intent1.putExtra("appId", "com.teslasoft.libraries.support");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent1);
        return authenticator.getIBinder();
    }
}
