package de.hawhamburg.sea2.echargeTanke;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import de.hawhamburg.sea2.echargeTanke.chargingActivities.ChargingProgressActivityOption1;
import de.hawhamburg.sea2.echargeTanke.chargingActivities.ChargingProgressActivityOption2;
import de.hawhamburg.sea2.echargeTanke.chargingActivities.ChargingProgressActivityOption3;
import de.hawhamburg.sea2.echargeTanke.chargingActivities.ChargingProgressActivityOption4;


public class ChargingMenuActivity extends ActionBarActivity {

    private RadioButton radioButtonOption1;
    private RadioButton radioButtonOption2;
    private RadioButton radioButtonOption3;
    private RadioButton radioButtonOption4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_menu);
        this.setTitle("Ladezyklus auswählen");

        radioButtonOption1 = (RadioButton) findViewById(R.id.radioButtonOption1);
        radioButtonOption2 = (RadioButton) findViewById(R.id.radioButtonOption2);
        radioButtonOption3 = (RadioButton) findViewById(R.id.radioButtonOption3);
        radioButtonOption4 = (RadioButton) findViewById(R.id.radioButtonOption4);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.charging_menu, menu);
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
     * RadioButton klick 30 Minuten
     * @param view
     */
    public void onRadioButtonClickOption1(View view) {
        radioButtonOption1.setChecked(false);

        Intent optionProgress1 = new Intent(this, ChargingProgressActivityOption1.class);
        startActivity(optionProgress1);


    }

    /**
     * RadioButton klick 1 Stunde
     * @param view
     */
    public void onRadioButtonClickOption2(View view) {
        radioButtonOption2.setChecked(false);
        Intent optionProgress2 = new Intent(this, ChargingProgressActivityOption2.class);
        startActivity(optionProgress2);
    }

    /**
     * RadioButton klick 2 Stunden
     * @param view
     */
    public void onRadioButtonClickOption3(View view) {
        radioButtonOption3.setChecked(false);
        Intent optionProgress3 = new Intent(this, ChargingProgressActivityOption3.class);
        startActivity(optionProgress3);
    }

    /**
     * RadioButton klick voll laden
     * @param view
     */
    public void onRadioButtonClickOption4(View view) {
        radioButtonOption4.setChecked(false);
        Intent optionProgress4 = new Intent(this, ChargingProgressActivityOption4.class);
        startActivity(optionProgress4);
    }

    @Override
    public void onBackPressed(){
        Context aContext = getApplicationContext();
        CharSequence aTipp = "Bitte einen Ladezyklus wählen!";
        int duration = Toast.LENGTH_LONG;
        Toast aToastBrot = Toast.makeText(aContext,aTipp,duration);
    }
}
