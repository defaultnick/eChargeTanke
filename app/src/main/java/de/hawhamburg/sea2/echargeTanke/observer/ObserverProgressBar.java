package de.hawhamburg.sea2.echargeTanke.observer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import java.util.Observable;
import java.util.Observer;

import de.hawhamburg.sea2.echargeTanke.ChargingMenuActivity;
import de.hawhamburg.sea2.echargeTanke.MainActivity;
import de.hawhamburg.sea2.echargeTanke.R;
import de.hawhamburg.sea2.echargeTanke.chargeThread.ChargeProgress;
import de.hawhamburg.sea2.echargeTanke.chargingActivities.ChargingProgressActivityOption1;
import de.hawhamburg.sea2.echargeTanke.chargingActivities.ChargingProgressDoneActivity;

/**
 * Created by Daniel on 15.06.2014.
 */
public class ObserverProgressBar implements Observer {

    private ProgressBar myProgressBar;
    private ChargeProgress myObservableProgress;
    private ActionBarActivity myActivity;

    public ObserverProgressBar(ProgressBar aProgressBar, ChargeProgress aObservableProgress, ActionBarActivity aActivity){
        myProgressBar = aProgressBar;
        myObservableProgress = aObservableProgress;


        myActivity = aActivity;
    }



    /**
     * Stößt Änderungsprozess an
     *
     * Wird bei Änderungen von dem zu beobachtetem Objekt aufgerufen durch
     * setChanged();
     * notifyObservers();
     */
    @Override
    public void update(Observable arg0, Object arg1) {

        // Stößt den Refresh-Vorgang in der ProgressBar an
        myProgressBar.setProgress(myObservableProgress.getProgressStatus());

        // Wenn die ProgressBar voll ist, wird kommuniziert über getIsDone..., soll der buttonNext enabled werden
        // klappt leider nicht....
        if(myObservableProgress.getIsDone()){
            myActivity.findViewById(R.id.buttonNext).setEnabled(true);
        }
    }


}