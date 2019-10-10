package com.ouilift.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

public class DriverActivity extends BaseActivity {
    private ReservationViewModel viewModel;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {

            case R.id.navigation_reservation:
                finish();
                startActivity(new Intent(getApplicationContext(), ReservationListActivity.class));
                break;
            case R.id.navigation_search:
                finish();
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.navigation_route:
                finish();
                startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                break;
        }
        return true;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        BottomNavigationView navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.registeredCar(makeJson()).observe(this, result -> {
            if (result.status == 200) {
                //displayMessage(getString(R.string.btn_change_settings));

            } else {
                //displayMessage(result.errorMessage);
            }
        });
    }

    private JsonObject makeJson() {
        JsonObject data = new JsonObject();
        data.addProperty("PK", Preference.getConnection(this).PK);
        return data;
    }
}
