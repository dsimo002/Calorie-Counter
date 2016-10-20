package com.cs180.team2.caloriecounter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    public final static String EXTRA_MESSAGE = "com.cs180.team2.caloriecounter.MESSAGE";


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    public String username = "";
    private String pw = "";
    public String fullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {


            final DatabaseReference registeredusers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://caloriecounter-93b96.firebaseio.com/registeredusers");
            //final DatabaseReference User = registeredusers.child(email);  // Chanho: access firebase and search by email

            ValueEventListener valueEventListener = registeredusers.addValueEventListener(new ValueEventListener() {  //Chanho: Create listener that will obtain values of user
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String email = mEmailView.getText().toString();
                    final String password = mPasswordView.getText().toString();

                    if (dataSnapshot.hasChild(email)) {
                        username = dataSnapshot.child(email).getKey();
                        fullName = dataSnapshot.child(email).child("Name").getValue(String.class);
                        pw = dataSnapshot.child(email).child("Password").getValue(String.class);

                        TextView textView = (TextView) findViewById(R.id.databaseOutput);
                        textView.setText("Username: " + username + "\nFull Name: " + fullName + "\nPassword: " + pw);

                        if (password == pw) {
                            dailyCalories(); //TODO: REMEMBER TO KEEP USERNAME/FULL NAME VALUES GLOBALLY FROM HERE ON
                            //TODO: FIX THIS IT DOESNT GO TO NEXT ACTIVITY
                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "Wrong password!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration); //Chanho: Toast is a popup notification that disappears automatically after a period of time
                            toast.show();                                          //This toast tells the user that the password for the username is wrong


                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()); //Chanho: Dialogs are popup notifications that require users to interact with to get rid of.
                        builder.setMessage("Username not found. Register new user?"); //This dialog asks the user if they want to register a new user
                        //TODO: FIX DIALOG DOES NOT POP UP


                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                registerUser();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                        AlertDialog dialog = builder.create();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getMessage());
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email != "";
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }




    public void dailyCaloriesGUI(View view) {
        Intent intent = new Intent(this, DailyCalories.class);
        startActivity(intent);
    }

    public void dailyCalories() { //TODO: FIX IT DOESNT GO TO NEXT ACTIVITY
        Intent intent = new Intent(this, DailyCalories.class);
        startActivity(intent);
    }

    public void registerUser() {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }





}

