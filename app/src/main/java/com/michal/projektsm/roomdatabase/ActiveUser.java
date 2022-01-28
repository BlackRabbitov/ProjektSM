package com.michal.projektsm.roomdatabase;

public class ActiveUser {
    private static ActiveUser instance = null;
    private UserEntity User = null;
    protected ActiveUser() { }

    public static ActiveUser getInstance() {
        if(instance == null) {
            instance = new ActiveUser();
        }
        return instance;
    }

    public void setUser(UserEntity user) {
        this.User = user;
    }

    public UserEntity getUser() {
        return this.User;
    }
}
