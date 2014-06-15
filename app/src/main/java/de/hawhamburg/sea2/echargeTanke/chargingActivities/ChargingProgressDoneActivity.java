package de.hawhamburg.sea2.echargeTanke.chargingActivities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import de.hawhamburg.sea2.echargeTanke.ChargingMenuActivity;
import de.hawhamburg.sea2.echargeTanke.R;

public class ChargingProgressDoneActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_progress_done);
        this.setTitle("Done");
        ThreadWaitTime aWaitTime = new ThreadWaitTime();
        aWaitTime.start();
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

    public class ThreadWaitTime extends Thread{

        @Override
        public void run() {

            try {
                sleep(4000);
                startChargingMenu();
            } catch (InterruptedException e1) {
                this.interrupt();
            }
        }

    }

    public void startChargingMenu(){
        Intent pr = new Intent(this, ChargingMenuActivity.class);
        startActivity(pr);
    }
}
