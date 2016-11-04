package com.cs180.team2.caloriecounter;

/**
 * Created by oakami on 11/3/16.
 */

public class FoodEntry {

    public FoodEntry(String Name, Long Calories, String Description, String Tag, String User) {
        this.Name = Name;
        this.Calories = Calories;
        this.Description = Description;
        this.Tag = Tag;
        this.User = User;
    }
    public String Name;
    public Long Calories;
    public String Description;
    public String Tag;
    public String User;
};
