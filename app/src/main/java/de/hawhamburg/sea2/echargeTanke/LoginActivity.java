package de.hawhamburg.sea2.echargeTanke;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import de.hawhamburg.sea2.echargeTanke.library.Consts;
import de.hawhamburg.sea2.echargeTanke.library.DatabaseHandler;
import de.hawhamburg.sea2.echargeTanke.library.UserFunctions;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends ActionBarActivity {

    // Values for email and password at the time of the login attempt.
    private String mEmail;
    private String mPassword;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextView mLogin_status_message;
    private View mLoginFormView;
    private View mLoginStatusView;
    private Button mButtonLogin;

    private Toast toast1;
    private ProgressDialog nDialog;

    /**
     * Called when the activity is first created.
     */
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    private static String KEY_BIRTHDAY = "birthday";
    private static String KEY_STREET = "street";
    private static String KEY_NUMBER = "number";
    private static String KEY_PLZ = "plz";
    private static String KEY_CITY = "city";
    private static String KEY_COUNTRY = "country";
    private static String KEY_MOBILE = "mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setupActionBar();

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
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

        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);

        mButtonLogin = (Button) findViewById(R.id.sign_in_button);
        mLogin_status_message = (TextView) findViewById(R.id.login_status_message);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        String[] str = LoginDatenLesen();
        if (str != null) {
            mEmailView.setText(str[0]);
            mPasswordView.setText(str[1]);
            mPasswordView.requestFocus();
            attemptLogin();
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_forgot_password:
                Intent i = new Intent(getApplicationContext(), PasswordReset.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

        /*if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            // TODO: If Settings has multiple levels, Up should navigate up
            // that hierarchy.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }     */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }


    private void ShowDialog(String Title, String Message, Boolean tr) {
        if (tr == true) {
            if (nDialog != null) nDialog.dismiss();

            nDialog = new ProgressDialog(LoginActivity.this);
            nDialog.setTitle(Title);
            nDialog.setMessage(Message);
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        } else {
            nDialog.setTitle(Title);
            nDialog.setMessage(Message);
        }

    }

    private void ShowDialog(Boolean show) {
        if (show == false) {
            nDialog.dismiss();
        }
    }

    private String[] LoginDatenLesen() {

        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput(Consts.LoginDatei);
            byte[] reader = new byte[inputStream.available()];
            while (inputStream.read(reader) != -1) {
            }

            String tmp = new String(reader);
            String[] userpass = tmp.split(":");
            if (userpass.length == 2)
                return userpass;
            else
                return null;
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
        return null;
    }

    private void LoginDatenSchreiben(String UserPass) {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(Consts.LoginDatei, Context.MODE_PRIVATE);
            outputStream.write(UserPass.toString().getBytes());
        } catch (IOException e) {
            Log.e("log", e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    Log.e("log", e.getMessage());
                }
            }
        }
    }

    private void LoginDatenLoeschen() {
        // if(getFile)
    }

    public void NetAsync() {
        new NetCheck().execute();
    }


    private class NetCheck extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowDialog("Internet", "prüfe...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            /**
             * Gets current device state and checks for working internet connection by trying Google.
             **/
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL(Consts.netCheckUrl);
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean th) {
            if (th == true) {
                ShowDialog(false);
                new ProcessLogin().execute();
            } else {
                ShowDialog(false);
                mLogin_status_message.setText(getString(R.string.error_network_access));
                mLogin_status_message.setVisibility(1);
            }
        }
    }

    private class ProcessLogin extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowDialog("kontaktiere Server", "Anmelden...", true);
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUser(mEmail, mPassword);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        ShowDialog("Lade Benutzer", "Daten holen...", false);
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        db.resetTables();
                        JSONObject json_user = json.getJSONObject("user");
                        /**
                         * Clear all previous data in SQlite database.
                         **/
                        //   UserFunctions logout = new UserFunctions();
                        //  logout.logoutUser(getApplicationContext());
                        // db.addUser(json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME),json_user.getString(KEY_EMAIL),json_user.getString(KEY_USERNAME),json_user.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));
                        System.err.println(json_user.getString(KEY_FIRSTNAME));
                        db.addUser(json_user.getString(KEY_FIRSTNAME),
                                json_user.getString(KEY_LASTNAME),
                                json_user.getString(KEY_EMAIL),
                                json_user.getString(KEY_BIRTHDAY),
                                json_user.getString(KEY_STREET),
                                json_user.getString(KEY_NUMBER),
                                json_user.getString(KEY_PLZ),
                                json_user.getString(KEY_CITY),
                                json_user.getString(KEY_COUNTRY),
                                json_user.getString(KEY_MOBILE),
                                json_user.getString(KEY_USERNAME),
                                json_user.getString(KEY_UID),
                                json_user.getString(KEY_CREATED_AT));

                        /**
                         *If JSON array details are stored in SQlite it launches the User Panel.
                         **/
                        Intent upanel = new Intent(getApplicationContext(), MainActivity.class);
                        upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ShowDialog(false);
                        startActivity(upanel);
                        /**
                         * Close Login Screen
                         **/
                        finish();
                    } else {
                        ShowDialog(false);
                        mLogin_status_message.setText(getString(R.string.error_incorrect_userpass));
                        mLogin_status_message.setVisibility(1);
                        mEmailView.requestFocus();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!mEmail.contains("@")) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            NetAsync();
        }
    }

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        CharSequence text = "Die App kann nicht beendet werden!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
