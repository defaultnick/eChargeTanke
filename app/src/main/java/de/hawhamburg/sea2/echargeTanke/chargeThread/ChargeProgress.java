package de.hawhamburg.sea2.echargeTanke.chargeThread;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

import de.hawhamburg.sea2.echargeTanke.R;
import de.hawhamburg.sea2.echargeTanke.library.Consts;
import de.hawhamburg.sea2.echargeTanke.library.UserFunctions;

/**
 * Created by Daniel on 11.06.2014.
 */
public class ChargeProgress extends Observable {

    // wird vom Observer abgegriffen
    private int progressStatus;
    private double price;
    private double kws;

    // Wartezeit für unterschiedliche Ladezyklen
    private int sleepTime;

    private boolean isDone = false;


    public ChargeProgress(int aSleepTime){

        progressStatus = 0;
        sleepTime = aSleepTime;
        ExecutiveProgress exectuiveThread = new ExecutiveProgress();
        exectuiveThread.start();
    }

    /*******************************************************
     * Innere Klase ExecutiveProgress
     *
     * Erbt von der Klasse Thread
     * Zählt beim start die Variable progressStatus in 0.05'er Schritten
     * bis 1 hoch
     * Benachrichtigt bei nach jedem erhöhen die Observer
     * Benachrichtigt die Observer, wenn progressStatus 1 erreicht hat
     *******************************************************/
    private class ExecutiveProgress extends Thread{

        @Override
        public void run(){

            boolean deltaReached = false;

            while (!isInterrupted()) {
                try {

                    // ProgressStatus auf 0
                    progressStatus = 0;

                    for(int i = 0; i < 100; i++){

                        // 100 Millisekunden schlafen
                        sleep(sleepTime);

                        // Schrittweise um speed faktor erhöhen
                        progressStatus += 1;

                        // doneText = "Working...";
                        // Observer benachrichtigen
                        setChanged();
                        notifyObservers();

                        // deltaReached bestimmen
                        deltaReached = ( progressStatus <= 100 );
                    }
                    if(deltaReached){

                        // ProgressStatus reset
                        progressStatus = 0;
                        isDone = true;

                        //Ladevorgang zur Rechnung addieren
                        // TODO
                        // BITTE IRGENDWIE RANDOM SETZEN DANIEL!
                        price = 12.22;
                        kws = 2.3;
                        addToBudget();

                        // Observer benachrichtigen
                        setChanged();
                        notifyObservers();

                        // Thread beenden
                        this.interrupt();
                    }

                } catch (InterruptedException e1) {
                    this.interrupt();
                }
            }
        }
    }

    /**
     * Getter progressStatus, represent the current Status
     * @return integer progressStatus
     */
    public int getProgressStatus(){
        return progressStatus;
    }

    /**
     * Erhöht den Betrag der Rechnung, um den Aufladebetrag
     */
    public void addToBudget(){
        new Budget().execute();
    }

    public boolean getIsDone(){
        return isDone;
    }


    private class Budget extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            UserFunctions userFunction = new UserFunctions();
            userFunction.addBudget(kws, price);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean th) {
            if (th == true) {
            } else {
            }
        }
    }


}

