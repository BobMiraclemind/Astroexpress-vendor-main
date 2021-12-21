package com.ashishandroid.ashishkumar.ModelClass;

public class customdialogmodel {

    private static String uid_users;

    public customdialogmodel(String uid_users) {
        this.uid_users = uid_users;
    }

    public static String getUid_users() {
        return uid_users;
    }

    public void setUid_users(String uid_users) {
        this.uid_users = uid_users;
    }
}
