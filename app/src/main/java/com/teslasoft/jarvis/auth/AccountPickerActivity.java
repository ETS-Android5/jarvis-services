package com.teslasoft.jarvis.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.teslasoft.libraries.support.R;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.SmartToast;
import android.widget.TextView;

public class AccountPickerActivity<Accounts> extends Activity {
    public RecyclerView account_list;
    public List<UserModel> accounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.account_picker);
        account_list = (RecyclerView) findViewById(R.id.account_list);
        loadAccounts();;
    }

    public void loadAccounts() {
        accounts = new ArrayList<UserModel>();
        try {
            accounts.clear();
        } catch (Exception e) {}
        AccountManager am =  (AccountManager)this.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accountsList = am.getAccountsByType("org.teslasoft.id.JARVIS_ACCOUNT");
        for (int i = 0; i < accountsList.length; i++) {
            accounts.add(new UserModel(accountsList[i].name, am.getUserData(accountsList[i], "user_email"), am.getUserData(accountsList[i], "user_id")));
        }

        AccountAdapter acadapter = new AccountAdapter(accounts);
        account_list.setAdapter(acadapter);
        account_list.setLayoutManager(new LinearLayoutManager(this));
    }

    public void DoNothing(View v) {

    }

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
            loadAccounts();
        } else {

        }
    }

    public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

        private List<UserModel> UsersList;

        public AccountAdapter(List<UserModel> userlist) {
            UsersList = userlist;
        }


        @Override
        public AccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View contactView = inflater.inflate(R.layout.account_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AccountAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            UserModel users = UsersList.get(position);
            TextView textUsername = holder.textName;
            TextView textUseremail = holder.textEmail;
            ImageView userIcon = holder.userIcon;
            LinearLayout rClickable = holder.r_clickable;
            textUsername.setText(users.getName());
            textUseremail.setText(users.getEmail());
            Glide.with(AccountPickerActivity.this).load(Uri.parse("https://usercontent.teslasoft.org/a/".concat(users.getUid()).concat("/icon.png"))).into(userIcon);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnAccount(position);
                }
            });

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
        // SmartToast.create(Integer.toString(id + 20), this);
        finish();
    }
}