package com.doug.cashflow.controller;

//import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doug.cashflow.R;
import com.doug.cashflow.model.db.User;
import com.doug.cashflow.model.loaders.InputLabLoadingTask;

import static com.doug.cashflow.R.drawable.ic_menu_icon;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    // UI references.
    private EditText mEmail;
    private EditText mPassword;
    private TextView mError;
    private Button   mLogin;
    private Button   mNew;
    private LoaderManager.LoaderCallbacks<String> loaderCallbacks;

    static boolean onCreateLoader = false;


    // needed for onPause & onResume
    private SharedPreferences sharedPreferences;
    private User user = new User();

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private InputLabLoadingTask mInputLabLoadingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportLoaderManager().destroyLoader(0);

        LoginLab.setCurrentActivity(this);
        LoginLab.getInstance(getApplicationContext(), false);

        mInputLabLoadingTask = new InputLabLoadingTask(getApplicationContext(), getSupportFragmentManager());

        sharedPreferences = getSharedPreferences("SavedValues", MODE_PRIVATE);

        loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {

            @Override
            public android.support.v4.content.Loader<String> onCreateLoader(int id, Bundle args) {
                Log.d(TAG, "onCreateLoader()");

                if (onCreateLoader == false){
                    onCreateLoader = true;
                    Log.d(TAG, "onCreateLoader() called just once");

                    // Do some work while waiting for the username/password to be entered
//                    mInputLabLoadingTask.loadInBackground(); // Need userId to read the data

                }

                return null;
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {
                Log.d(TAG, "onLoadFinished()");
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<String> loader) {
                Log.d(TAG, "LoginActivity.onLoaderReset()");
            }
        };

        getSupportLoaderManager().initLoader(0, null, loaderCallbacks);

        setContentView(R.layout.activity_login);

        this.
                // Set up the login form.
                mEmail = (EditText) findViewById(R.id.email_username);

        mEmail.setText("2017_doug");

        mPassword = (EditText) findViewById(R.id.password);
        mPassword.setText("2017_doug");

        mError = (TextView) findViewById(R.id.login_error);
        mError.setText("");

        mEmail.requestFocus();

        mLogin = (Button) findViewById(R.id.button);
        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLoginParameters() == true) {
                    String email = ((EditText)mEmail).getText().toString();
                    String password = ((EditText)mPassword).getText().toString();

                    user = LoginLab.getDb().getUser(email);
                    if (user.getPassword().equals(password)) {

                        Log.d(TAG, "onClickListener()");
                        Log.d(TAG, "    user.getId(): " + user.getId());
                        InputLab.setUserId(user.getId());

//                        mInputLabLoadingTask.loadInBackground();
                        InputLab.getInstance(getApplicationContext(), getSupportFragmentManager(), null);

                        Intent intent;
                        intent = InputPagerActivity.newIntent(getApplicationContext(), user.getId());
                        startActivity(intent);
                    }
                }
                else {
                    mError.setText(R.string.error_invalid_username_password_combination);
                }
            }
        });

        mNew = (Button) findViewById(R.id.new_account_button);
        mNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "New Account Button pressed");

                String email = ((EditText)mEmail).getText().toString();
                String password = ((EditText)mPassword).getText().toString();

                user = LoginLab.getDb().getUser(email);
                //Log.d(TAG, "    user: " + user);
                if (user == null) {

                    Integer user_id = LoginLab.getDb().getNumberOfRecordsInUserTable() + 1;
                    //Log.d(TAG, "    user_id: " + user_id);
                    //Log.d(TAG, "    email: " + email);
                    //Log.d(TAG, "    password: " + password);

                    user = new User(user_id, email, password); // Insert my data
                    LoginLab.getDb().insertUser(user);
                    LoginLab.getDb().populateDataTable(user.getId());
                    InputLab.setUserId(user.getId());

                    mInputLabLoadingTask.loadInBackground();

                    Intent intent;
                    intent = InputPagerActivity.newIntent(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
                else {
                    mError.setText(R.string.error_duplicate_username);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Find the root view
            String root = mEmail.getRootView().toString(); // this is what is suggested on stackoverflow.com
            Log.d(TAG, "******************** root: " + root);
            Resources.Theme theme = this.getTheme();
            Log.d(TAG, "******************** theme (this.getTheme()): " + theme);

            // Set the color drawable
//            mEmail.getRootView().setBackground(getResources().getDrawable(R.drawable.ic_menu_icon, getTheme()));
//            mEmail.getRootView().setBackgroundResource(R.drawable.ic_menu_icon);
//            mEmail.getRootView().setBackgroundColor(Color.BLACK);
            mEmail.getRootView().setBackgroundColor(getResources().getColor(android.R.color.black, this.getTheme()));
            this.mEmail.setBackgroundColor(getResources().getColor(android.R.color.black, null));
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     */
    private boolean validateLoginParameters() {
        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

            return false;
        }
        else {
            return true;
        }
    }

    private boolean isEmailValid(String email) {
        return email.length() > 0;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 1;
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");

        Editor editor = sharedPreferences.edit();
        editor.putString("user_email", user.getEmail());
        editor.putString("user_password", user.getPassword());
        editor.putInt("user_id", user.getId());
        editor.commit();

        getSupportLoaderManager().destroyLoader(0);

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume()");

        user.setEmail(sharedPreferences.getString("user_email", ""));
        user.setPassword(sharedPreferences.getString("user_password", ""));
        user.setId(sharedPreferences.getInt("user_id", -1));

//        getSupportLoaderManager().initLoader(0, null, loaderCallbacks);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy()");
    }
}

