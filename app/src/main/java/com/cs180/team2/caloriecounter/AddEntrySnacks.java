package com.cs180.team2.caloriecounter;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.cs180.team2.caloriecounter.R.id.textView2;


public class AddEntrySnacks extends AppCompatActivity {
    private DatabaseReference myRef;
    public static class FoodEntry {
        public FoodEntry() {
            this.Name = "";
            this.Calories = "";
            this.Description = "";
        }
        public String getName() {
            return this.Name;
        }
        public String getCalories() {
            return this.Calories;
        }
        public String getDescription() {
            return  this.Description;
        }
        public String Name;
        public String Calories;
        public String Description;
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_snacks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    public void searchDatabase(View view) {
        EditText inputTxt = (EditText) findViewById(R.id.text);
        String str = inputTxt.getText().toString();

        DatabaseReference mFoodRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://caloriecounter-93b96.firebaseio.com/Food");
        final DatabaseReference mSearchedFoodRef = mFoodRef.child(str);

        mSearchedFoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Foodname = mSearchedFoodRef.getKey();
                Long FoodCalories = dataSnapshot.child("Calories").getValue(Long.class);
                String FoodDescription = dataSnapshot.child("Description").getValue(String.class);
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setText("Name: " + Foodname + "\nCalories: " + FoodCalories + "\nDescription: " + FoodDescription);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddEntrySnacks Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
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
}
