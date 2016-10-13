package com.cs180.team2.caloriecounter;

/**
 * Created by Kevin on 10/12/2016.
 */

public class User {

    public String Name;
    public String Password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String password) {
        this.Name = username;
        this.Password = password;
    }

}
