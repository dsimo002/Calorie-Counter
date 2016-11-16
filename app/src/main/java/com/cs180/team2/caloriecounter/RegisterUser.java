package com.cs180.team2.caloriecounter;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    public static class User
    {
        public String Name;
        public String Password;
        public Secret secretq;

        public User(String fullName, String password, String question, String answer) {
            // ...
            this.Name = fullName;
            this.Password = password;
            this.secretq = new Secret(question, answer);
        }

    }

    public static class UserByName
    {
        public String username;
        public String password;
        public Secret secretq;

        public UserByName(String username, String password, String question, String answer)
        {
            this.username = username;
            this.password = password;
            this.secretq = new Secret(question,answer);
        }
    }

    public static class Secret
    {
        public String question;
        public String answer;

        public Secret(String question, String answer)
        {
            this.question = question;
            this.answer = answer;
        }
    }

    private AutoCompleteTextView iFullName;
    private AutoCompleteTextView iUserName;
    private AutoCompleteTextView iPassword;
    private AutoCompleteTextView iRePassword;
    private AutoCompleteTextView iSecretQ;
    private AutoCompleteTextView iSecretA;
    //private Button verifyInfo;
    private TextView testText;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iFullName = (AutoCompleteTextView) findViewById(R.id.regFullName);
        iUserName = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        iPassword = (AutoCompleteTextView) findViewById(R.id.ipassword);
        iRePassword = (AutoCompleteTextView) findViewById(R.id.irepassword);
        iSecretQ = (AutoCompleteTextView) findViewById(R.id.secretq);
        iSecretA = (AutoCompleteTextView) findViewById(R.id.secreta);

        testText = (TextView) findViewById(R.id.textView7);

        Button verifyPassword = (Button) findViewById(R.id.verifypass);

        verifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkFields();
            }
        });
    }

    public String getString(AutoCompleteTextView view) {
        return view.getText().toString();
    }

    private boolean isShort()       //checks if password is less than six characters long
    {
        String password = iPassword.getText().toString();

        if (password.length() < 6)   //change later
        {
            Context context = getApplicationContext();
            CharSequence text = "Password too short! Minimum length is six";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return true;
        }

        return false;
    }

    private void registerUser()
    {
        final String fName = getString(iFullName);
        final String uName = getString(iUserName);
        final String pw = getString(iPassword);

        final String sq = getString(iSecretQ);
        final String sa = getString(iSecretA);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference registeredusers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaloriekounterk.firebaseio.com/registeredusers");
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaloriekounterk.firebaseio.com/usersByName");

        registeredusers.child(uName).setValue(new User (fName, pw, sq, sa));    //registeruser branch
        userRef.child(fName).setValue(new UserByName(uName,pw, sq, sa));        //userByName branch

        Context context = getApplicationContext();
        CharSequence text = "Registration Successful";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        loginActivity();
    }

    public void loginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    private void checkFields()
    {
        String pw = iPassword.getText().toString();
        String verifyPass = iRePassword.getText().toString();

        if (!isShort()) {
            if (pw.equals(verifyPass)) {

                final DatabaseReference registeredusers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kaloriekounterk.firebaseio.com/registeredusers");
                ValueEventListener valueEventListener = registeredusers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(getString(iUserName))) {
                            Context context = getApplicationContext();
                            CharSequence text = "Not a unique username please try again";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        else
                        {
                            //testText.setText(getString(iFullName));
                            if((!getString(iSecretQ).isEmpty() && !getString(iSecretA).isEmpty()))
                            {
                                registerUser();
                            }
                            else
                            {
                                Context context1 = getApplicationContext();
                                CharSequence text1 = "Secret Question and Secret Answer must be entered";
                                int duration1 = Toast.LENGTH_SHORT;
                                Toast toast1 = Toast.makeText(context1, text1, duration1);
                                toast1.show();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            else
            {
                Context context = getApplicationContext();
                CharSequence text = "Passwords don't match";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

}