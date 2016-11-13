package com.cs180.team2.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.cs180.team2.caloriecounter.LoginActivity.username;
import static com.cs180.team2.caloriecounter.R.id.textView7;



public class DailyCalories extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static String choice = "";


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


        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        Calendar c = Calendar.getInstance();
        String Filename = Integer.toString(c.get(Calendar.MONTH)) + "." + Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "." + Integer.toString(c.get(Calendar.YEAR));
        Filename = Filename + "_" + username + ".txt";
        File file = new File(dir, Filename);
        if(!file.getParentFile().exists()) {
            if(file.getParentFile().mkdirs()) {
                System.out.println(file.getParentFile() + " created!");
            }

            try {
                System.out.println(file.getCanonicalPath() + " about to be created!");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        TextView logtest = (TextView) findViewById(R.id.textView10);
        if(file.exists()) {
            try {
                InputStream in = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                logtest.setText(line);
            } catch (FileNotFoundException e) {
                Context context = getApplicationContext();
                CharSequence text = "Log file not found!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration); //Chanho: Toast is a popup notification that disappears automatically after a period of time
                toast.show();
            } catch (IOException e) {
                Context context = getApplicationContext();
                CharSequence text = "Log file not found!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration); //Chanho: Toast is a popup notification that disappears automatically after a period of time
                toast.show();
            }
        }


        String currentDateString = DateFormat.getDateInstance().format(new Date());
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText(currentDateString);

        String usrnme = "Welcome back, " + username.toString() + "!";
        TextView textViewUserName = (TextView) findViewById(textView7);

        Button mChangePassword = (Button) findViewById(R.id.change_password_button);
        //Button statButton = (Button) findViewById(R.id.statGraphButton);

        boolean isGuest = false;

        if (!username.isEmpty())
        {
            textViewUserName.setText(usrnme);
            mChangePassword.setVisibility(View.VISIBLE);
          //  statButton.setVisibility(View.VISIBLE);
        }
        else
        {
            textViewUserName.setText("Guest");      //sign in as guest user
            isGuest = true;
        }

        if (isGuest)     //if sign in as guest, don't show change password
        {
            mChangePassword.setVisibility(View.INVISIBLE);
            //statButton.setVisibility(View.INVISIBLE);

        }
        //View mChangePasswordView;

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
        choice = "Breakfast";
        Intent intent = new Intent(this, AddEntry.class);
        startActivity(intent);

    }

    public void addEntryLunch(View view) {
        choice = "Lunch";
        Intent intent = new Intent(this, AddEntry.class);
        startActivity(intent);

    }

    public void addEntryDinner(View view) {
        choice = "Dinner";
        Intent intent = new Intent(this, AddEntry.class);
        startActivity(intent);

    }

    public void addEntrySnacks(View view) {
        choice = "Snacks";
        Intent intent = new Intent(this, AddEntry.class);
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

    public void viewStatsGraph(View view)
    {
        Intent intent = new Intent(this, Graph.class );
        startActivity(intent);
    }

}
