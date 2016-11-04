package com.cs180.team2.caloriecounter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

import static com.cs180.team2.caloriecounter.R.id.textView2;
import static java.security.AccessController.getContext;


public class AddEntryBreakfast extends AppCompatActivity {

    private DatabaseReference myRef;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_breakfast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    public void searchDatabase(View view) {
        EditText inputTxt = (EditText) findViewById(R.id.text);
        final String str = inputTxt.getText().toString().trim().toLowerCase();

        final DatabaseReference mFoodRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://caloriecounter-93b96.firebaseio.com/Food");
        mFoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FoodEntry> results = new ArrayList<FoodEntry>();
                int matches = 0;

                for (DataSnapshot foodSnapshot: dataSnapshot.getChildren()) {
                    String tag = (String)foodSnapshot.child("Tag").getValue();
                    String Foodname = foodSnapshot.getKey();
                    if (tag.equals(str) || Foodname.toLowerCase().equals(str)) {
                        String User = foodSnapshot.child("User").getValue(String.class);
                        Long FoodCalories = foodSnapshot.child("Calories").getValue(Long.class);
                        String FoodDescription = foodSnapshot.child("Description").getValue(String.class);
                        FoodEntry foodresult = new FoodEntry(Foodname, FoodCalories, FoodDescription, tag, User);
                        //results += tag + "\n";
                        results.add(foodresult);
                        matches++;
                    }
                }
                if (matches == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "No matches found!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                FoodEntryAdapter adapter = new FoodEntryAdapter(AddEntryBreakfast.this, results);


                ListView textView2 = (ListView) findViewById(R.id.textView2);
                textView2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        /*final DatabaseReference mSearchedFoodRef = mFoodRef.child(str);


        mSearchedFoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Foodname = mSearchedFoodRef.getKey();
                Long FoodCalories = dataSnapshot.child("Calories").getValue(Long.class);
                String FoodDescription = dataSnapshot.child("Description").getValue(String.class);
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                //textView2.setText("Name: " + Foodname + "\nCalories: " + FoodCalories + "\nDescription: " + FoodDescription);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });*/

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddEntryBreakfast Page") // TODO: Define a title for the content shown.
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

    public void addFood(View view) {
        Intent intent = new Intent(this, AddFood.class);
        startActivity(intent);

    }
}
