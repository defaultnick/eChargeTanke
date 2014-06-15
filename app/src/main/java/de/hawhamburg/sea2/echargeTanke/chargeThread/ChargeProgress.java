package de.hawhamburg.sea2.echargeTanke.chargeThread;

import java.util.Observable;

/**
 * Created by Daniel on 11.06.2014.
 */
public class ChargeProgress extends Observable {

    // wird vom Observer abgegriffen
    private int progressStatus;

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

    }

    public boolean getIsDone(){
        return isDone;
    }
}

