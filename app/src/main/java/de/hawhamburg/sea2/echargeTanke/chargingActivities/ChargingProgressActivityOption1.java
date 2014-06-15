package de.hawhamburg.sea2.echargeTanke.chargingActivities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import de.hawhamburg.sea2.echargeTanke.R;
import de.hawhamburg.sea2.echargeTanke.chargeThread.ChargeProgress;
import de.hawhamburg.sea2.echargeTanke.observer.ObserverProgressBar;

public class ChargingProgressActivityOption1 extends ActionBarActivity {

    private ProgressBar myProgressBar;
    private Button myStartButton, myNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_progress_activity_option1);
        this.setTitle("Ladevorgang 30 Minuten");
        myProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        myStartButton = (Button) findViewById(R.id.buttonStartProgress);
//        myNextButton = (Button) findViewById(R.id.buttonNext);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.charging_progress_activity_option1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Bei klick auf Start charging Progress
     *
     * Erzeugung neuer Progress der progressStatus hochzählt, Wartezeit wird mit angegegben in Millisekunden
     * @param v
     */
    public void onButtonKlickStartProgress(View v){

        int timeZyklus = 50;
        double priceKwh = 1.20;
        double kwHour = 0.5;

        //  myActivity.findViewById(R.id.buttonNext).setEnabled(true);
        this.findViewById(R.id.buttonStartProgress).setEnabled(false);

        // Zu Testzwecken erstmal auf 50 Millisekunden gesetzt, später dann 200ms
        ChargeProgress aProgress = new ChargeProgress(timeZyklus, kwHour, priceKwh);
        ObserverProgressBar aOberserverProgressBar = new ObserverProgressBar(myProgressBar, aProgress, this);
        aProgress.addObserver(aOberserverProgressBar);
    }

    /**
     * Bei klick auf den next Button (der ist vorerst disabled)
     * Soll die DoneActivity aufgerufen werden
     *
     * */
    public void onButtonKlickNext(View v){

        Intent charginDoneActivity = new Intent(this, ChargingProgressDoneActivity.class);
        startActivity(charginDoneActivity);
    }

}
