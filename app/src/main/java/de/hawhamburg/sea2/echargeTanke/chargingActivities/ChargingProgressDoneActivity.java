package de.hawhamburg.sea2.echargeTanke.chargingActivities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;
import android.widget.TextView;

import de.hawhamburg.sea2.echargeTanke.ChargingMenuActivity;
import de.hawhamburg.sea2.echargeTanke.R;

public class ChargingProgressDoneActivity extends ActionBarActivity {

    private TextView myTextViewChargingPrice;
    private double myPrice = 0;
    public double getMyPrice(){return myPrice;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_progress_done);
        this.setTitle("Ladevorgang erfolgreich!");

        myTextViewChargingPrice = (TextView) findViewById(R.id.textViewChargingCosts);

        myPrice = getIntent().getExtras().getDouble("myPrice",myPrice);

        String priceString = String.valueOf(myPrice)+" €";
        myTextViewChargingPrice.setText(priceString);

        ThreadWaitTime aWaitTime = new ThreadWaitTime();
        aWaitTime.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.charging_progress_done, menu);
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

    public class ThreadWaitTime extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                Thread.sleep(5000);
                startChargingMenu();

            } catch (InterruptedException e1) {

            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean th) {

        }
    }

    public void startChargingMenu(){
        Intent chargeMenu = new Intent(this, ChargingMenuActivity.class);
        startActivity(chargeMenu);
    }
}
