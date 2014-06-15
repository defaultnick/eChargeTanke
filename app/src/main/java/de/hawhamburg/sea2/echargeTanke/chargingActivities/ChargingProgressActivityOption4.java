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

public class ChargingProgressActivityOption4 extends ActionBarActivity {

    private ProgressBar myProgressBar;
    private Button myStartButton, myNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_progress_activity_option4);
        this.setTitle("Ladevorgang voll aufladen");

        myProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        myStartButton = (Button) findViewById(R.id.buttonStartProgress);
        //myNextButton = (Button) findViewById(R.id.buttonNext);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.charging_progress_activity_option4, menu);
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

        int timeZyklus = 1600;
        double priceKwh = 1.20;
        double kwHour = 6;

        this.findViewById(R.id.buttonStartProgress).setEnabled(false);

        ChargeProgress aProgress = new ChargeProgress(timeZyklus, kwHour, priceKwh);
        ObserverProgressBar aOberserverProgressBar = new ObserverProgressBar(myProgressBar, aProgress, this);
        aProgress.addObserver(aOberserverProgressBar);
    }
}
