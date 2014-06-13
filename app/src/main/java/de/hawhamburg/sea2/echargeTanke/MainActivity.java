package de.hawhamburg.sea2.echargeTanke;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


import de.hawhamburg.sea2.echargeTanke.library.DatabaseHandler;

public class MainActivity extends ActionBarActivity {
    private CharSequence mTitle;
    private TextView tvBegruessungName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = getTitle();

        // Soll in der Main Activity als Begruessung den Vornamen aus der Datenbank lesen
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        HashMap hm = db.getUserDetails();
        tvBegruessungName = (TextView) findViewById(R.id.viewBegruessungName);
        tvBegruessungName.setText((String) hm.get("fname"));

        // Bereitet die ActionBar auf den Navigation Drawer vor
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //   getActionBar().setHomeButtonEnabled(true);
    }

    // Fügt das Menü hinzu / ActionBar Einträge
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Versteckt die ActionBar-Einträge, sobald der Drawer ausgefahren is
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_recover_password).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Öffnet und schließt den Navigation Drawer bei Klick auf den Titel/das Icon in der ActionBar


        // Gibt den ActionBar-Buttons Funktionen
        switch (item.getItemId()) {
            case R.id.action_recover_password:
                Intent pr = new Intent(this, PasswordReset.class);
                startActivity(pr);
                break;

            case R.id.action_abmelden:
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.resetTables();
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
