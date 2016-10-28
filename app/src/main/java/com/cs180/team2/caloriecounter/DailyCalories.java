package com.cs180.team2.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.util.Date;

public class DailyCalories extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_daily_calories);
        layout.addView(textView);

        String currentDateString = DateFormat.getDateInstance().format(new Date());
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText(currentDateString);

        String usrnme = "Username: " + LoginActivity.username.toString();
        TextView textViewUserName = (TextView) findViewById(textView7);
        textViewUserName.setText(usrnme);

        //View mChangePasswordView;
        Button mChangePassword = (Button) findViewById(R.id.change_password_button);
        mChangePassword.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            changePassword();
          }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Daily Calories Page")
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("https://caloriecounter-93b96.firebaseio.com/"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void addEntryBreakfast(View view) {
        Intent intent = new Intent(this, AddEntryBreakfast.class);
        startActivity(intent);

    }

    public void addEntryLunch(View view) {
        Intent intent = new Intent(this, AddEntryLunch.class);
        startActivity(intent);

    }

    public void addEntryDinner(View view) {
        Intent intent = new Intent(this, AddEntryDinner.class);
        startActivity(intent);

    }

    public void addEntrySnacks(View view) {
        Intent intent = new Intent(this, AddEntrySnacks.class);
        startActivity(intent);

    }

    public void logOut(View view)
    {
        Context context = getApplicationContext();
        CharSequence text = "Logging out...";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration); //Chanho: Toast is a popup notification that disappears automatically after a period of time
        toast.show();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void changePassword(){
     Intent intent = new Intent(this, ChangePasswordActivity.class);
     startActivity(intent);
   }
}
