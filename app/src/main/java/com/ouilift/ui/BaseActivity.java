package com.ouilift.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ouilift.R;
import com.ouilift.ui.account.DriverActivity;
import com.ouilift.ui.account.ReservationListActivity;
import com.ouilift.ui.account.SettingsActivity;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

public class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView navView;
    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            setMenuEnable();
            switch (item.getItemId()) {

                case R.id.navigation_setting:
                    finish();
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    break;
                case R.id.navigation_search:
                    finish();
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    break;
                case R.id.navigation_reservation:
                    finish();
                    startActivity(new Intent(getApplicationContext(), ReservationListActivity.class));
                    break;
                case R.id.navigation_route:
                    navigationRouteClick();
                    break;
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    protected void navigationRouteClick() {
        if(Preference.IsDriver(this) && Preference.IsActive(this)) {
            startActivity(new Intent(getApplicationContext(), DriverActivity.class));
        } else {
            Toast.makeText(this, "You must active your account or set your driving licence  before", Toast.LENGTH_LONG).show();
        }
    }

    protected void setMenuEnable() {
        if (!Preference.IsConnected(this)) {
            findViewById(R.id.navigation_setting).setEnabled(false);
            findViewById(R.id.navigation_reservation).setEnabled(false);
            findViewById(R.id.navigation_route).setEnabled(false);
        }

    }

}
