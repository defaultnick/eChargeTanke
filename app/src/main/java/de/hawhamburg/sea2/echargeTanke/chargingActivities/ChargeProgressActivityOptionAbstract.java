package de.hawhamburg.sea2.echargeTanke.chargingActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hawhamburg.sea2.echargeTanke.ChargingMenuActivity;

/**
 * Created by Daniel on 15.06.2014.
 */
public class ChargeProgressActivityOptionAbstract extends ActionBarActivity {

    private double myPrice = 0;

    public void startDoneActivity(double aPrice){

        Intent chargingDoneActivity = new Intent(this, ChargingProgressDoneActivity.class);
        chargingDoneActivity.putExtra("myPrice", myPrice);
        startActivity(chargingDoneActivity);
    }

    @Override
    public void onBackPressed(){
        Context aContext = getApplicationContext();
        CharSequence aTipp = "Bitte einen Ladezyklus wählen!";
        int duration = Toast.LENGTH_LONG;
        Toast aToastBrot = Toast.makeText(aContext,aTipp,duration);
    }

    /**
     * Bei klick auf den next Button (der ist vorerst disabled)
     * Soll die DoneActivity aufgerufen werden
     *
     * */
    public void onButtonKlickAbbruch(View v){

        Intent chargingMenuActivity = new Intent(this, ChargingMenuActivity.class);
        startActivity(chargingMenuActivity);
    }

    public void setPrice(double aPrice){

        aPrice += aPrice*0.3;
        myPrice = aPrice;}
    public double getMyPrice(){return  myPrice;}
}
