package com.teslasoft.jarvis.auth;

import java.util.ArrayList;

public class UserModel {
    private final String username;
    private final String useremail;
    private final String userid;

    public UserModel(String name, String email, String uid) {
        username = name;
        useremail = email;
        userid = uid;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return useremail;
    }

    public String getUid() {
        return userid;
    }

    public static ArrayList<UserModel> addItem(String username, String useremail, String userid) {
        ArrayList<UserModel> users = new ArrayList<UserModel>();
        users.add(new UserModel(username, useremail, userid));

        return users;
    }
}
