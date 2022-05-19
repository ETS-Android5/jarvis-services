package com.teslasoft.jarvis.auth;

import android.app.Activity;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

import com.teslasoft.libraries.support.R;


public class AccountPickerActivity<Accounts> extends Activity {
    public RecyclerView account_list;
    public List<UserModel> accounts;

    public boolean initialized = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences privacy = this.getSharedPreferences("privacy_accepted", Context.MODE_PRIVATE);
        try {
            String privacy_is_accepted = privacy.getString("privacy", null);
            if (privacy_is_accepted.equals("yes")) {
                Start();
            } else {
                finishAndRemoveTask();
            }
        } catch (Exception e) {
            try {
                Intent i = new Intent(this, com.teslasoft.jarvis.Privacy.class);
                startActivityForResult(i, 1);
            } catch (Exception _e) {
                finishAndRemoveTask();
            }
        }
    }

    public void Start() {
        initialized = true;
        SharedPreferences settings = this.getSharedPreferences("activity_core_settings", Context.MODE_PRIVATE);
        try {
            String isDarkTheme = settings.getString("dark_theme", null);
            if (isDarkTheme.equals("true")) {
                setContentView(R.layout.activity_account_picker);
            } else {
                setContentView(R.layout.activity_account_picker_light);
            }
        } catch (Exception e) {
            setContentView(R.layout.activity_account_picker);
        }

        account_list = (RecyclerView) findViewById(R.id.account_list);
        loadAccounts();;
    }

    public void loadAccounts() {
        accounts = new ArrayList<UserModel>();

        AccountManager am =  (AccountManager)this.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accountsList = am.getAccountsByType("org.teslasoft.id.JARVIS_ACCOUNT");
        for (Account account : accountsList) {
            accounts.add(new UserModel(account.name, am.getUserData(account, "user_email"), am.getUserData(account, "user_id")));
        }

        AccountAdapter acadapter = new AccountAdapter(accounts);
        account_list.setAdapter(acadapter);
        account_list.setLayoutManager(new LinearLayoutManager(this));
    }

    public void DoNothing(View v) {}

    public void Dismiss(View v) {
        this.setResult(Activity.RESULT_CANCELED);
        finishAndRemoveTask();
    }

    public void AddAccount(View v) {
        Intent intent = new Intent(this, AuthEntryActivity.class);
        intent.putExtra("appId", "com.teslasoft.libraries.support");
        this.startActivityForResult(intent, 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(Activity.RESULT_CANCELED);
        finishAndRemoveTask();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            if (initialized) {
                loadAccounts();
            } else {
                Start();
            }
        } else {
            if (!initialized) {
                finishAndRemoveTask();
            }
        }
    }

    public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

        private final List<UserModel> UsersList;

        public AccountAdapter(List<UserModel> userlist) {
            UsersList = userlist;
        }

        @NonNull
        @Override
        public AccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SharedPreferences settings = AccountPickerActivity.this.getSharedPreferences("activity_core_settings", Context.MODE_PRIVATE);
            try {
                String isDarkTheme = settings.getString("dark_theme", null);

                if (isDarkTheme.equals("true")) {
                    Context context = parent.getContext();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View contactView = inflater.inflate(R.layout.fragment_account_item, parent, false);

                    return new ViewHolder(contactView);
                } else {
                    Context context = parent.getContext();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View contactView = inflater.inflate(R.layout.fragment_account_item_light, parent, false);

                    return new ViewHolder(contactView);
                }
            } catch (Exception e) {
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View contactView = inflater.inflate(R.layout.fragment_account_item, parent, false);

                return new ViewHolder(contactView);
            }
        }

        @Override
        public void onBindViewHolder(AccountAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            UserModel users = UsersList.get(position);
            TextView textUsername = holder.textName;
            TextView textUseremail = holder.textEmail;
            ImageView userIcon = holder.userIcon;
            textUsername.setText(users.getName());
            textUseremail.setText(users.getEmail());
            Glide.with(AccountPickerActivity.this).load(Uri.parse("https://usercontent.teslasoft.org/a/".concat(users.getUid()).concat("/icon.png"))).into(userIcon);
            holder.itemView.setOnClickListener(v -> returnAccount(position));
        }

        @Override
        public int getItemCount() {
            return UsersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textName;
            public TextView textEmail;
            public ImageView userIcon;
            public LinearLayout r_clickable;

            public ViewHolder(View itemView) {
                super(itemView);

                textName = (TextView) itemView.findViewById(R.id.user_name);
                textEmail = (TextView) itemView.findViewById(R.id.user_email);
                userIcon = (ImageView) itemView.findViewById(R.id.user_icon);
                r_clickable = (LinearLayout) findViewById(R.id.r_clickable);
            }
        }
    }

    public void returnAccount(int id) {
        this.setResult(id + 20);
        finish();
    }
}