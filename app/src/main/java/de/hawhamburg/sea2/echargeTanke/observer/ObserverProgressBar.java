package de.hawhamburg.sea2.echargeTanke.observer;

import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;

import java.util.Observable;
import java.util.Observer;

import de.hawhamburg.sea2.echargeTanke.chargeThread.ChargeProgress;
import de.hawhamburg.sea2.echargeTanke.chargingActivities.ChargeProgressActivityOptionAbstract;


/**
 * Created by Daniel on 15.06.2014.
 */
public class ObserverProgressBar implements Observer {

    private ProgressBar myProgressBar;
    private ChargeProgress myObservableProgress;
    private ChargeProgressActivityOptionAbstract myActivity;
    private double myChargingPrice = 0;

    public ObserverProgressBar(ProgressBar aProgressBar, ChargeProgress aObservableProgress, ChargeProgressActivityOptionAbstract aActivity){
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
            myChargingPrice = (myObservableProgress.getPriceKwH()*myObservableProgress.getKwHour());
            myActivity.startDoneActivity(myChargingPrice);
        }
    }
    public double getChargingPrice(){
        return myChargingPrice;
    }


}