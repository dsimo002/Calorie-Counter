package com.cs180.team2.caloriecounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.cs180.team2.caloriecounter.LoginActivity.username;


/**
 * Created by oakami on 11/3/16.
 */

public class FoodEntryAdapter extends ArrayAdapter<FoodEntry> {

    private static class ViewHolder {
        TextView name;
        TextView calories;
        TextView description;
        TextView tag;
        TextView user;
    }

    public FoodEntryAdapter(Context context, ArrayList<FoodEntry> foods) {
        super(context, R.layout.item_foodentry, foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FoodEntry foods = getItem(position);
        final ViewHolder viewHolder; //view lookup cache stored in tag
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_foodentry, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.foodName);
            viewHolder.calories = (TextView) convertView.findViewById(R.id.foodCalories);
            viewHolder.description = (TextView) convertView.findViewById(R.id.foodDescription);
            viewHolder.tag = (TextView) convertView.findViewById(R.id.foodTag);
            viewHolder.user = (TextView) convertView.findViewById(R.id.foodUser);
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate data from data object via the viewHolder object into template view.
        viewHolder.name.setText("Name: " + foods.Name);
        viewHolder.calories.setText("Calories: " + foods.Calories.toString());
        viewHolder.description.setText("Description: " + foods.Description);
        viewHolder.tag.setText("Search Tag: " + foods.Tag);
        viewHolder.user.setText("Created by: " + foods.User);
        // Return the completed view to render on screen
        return convertView;
    }


}