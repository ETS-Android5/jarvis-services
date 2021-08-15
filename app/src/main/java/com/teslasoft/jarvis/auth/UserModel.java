package com.teslasoft.jarvis.auth;

import java.util.ArrayList;

public class UserModel {
    private String username;
    private String useremail;
    private String userid;

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

    private static int lastContactId = 0;

    public static ArrayList<UserModel> addItem(/*int numContacts,*/ String username, String useremail, String userid) {
        ArrayList<UserModel> users = new ArrayList<UserModel>();

        // for (int i = 1; i <= numContacts; i++) {
            users.add(new UserModel(username, useremail, userid));
        //}

        return users;
    }
}
