package com.ouilift.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.utils.Preference;

public class ReservationResultActivity extends BaseActivity {

    private int reservationId;
    private ReservationViewModel viewModel;
    private ReservationPresenter presenter;
    private TextView routeInfo, routeHour, routeFrom, routeTo, routeCar, routeDriver;
    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_result);
        viewBind();
        reservationId = getIntent().getIntExtra("reservationId", 0);
        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
    }

    private void viewBind() {
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        routeHour = findViewById(R.id.route_hour);
        routeFrom = findViewById(R.id.route_from);
        routeTo = findViewById(R.id.route_to);
        routeInfo = findViewById(R.id.route_info);
        routeCar = findViewById(R.id.route_car);
        routeDriver = findViewById(R.id.route_driver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        if(!Preference.IsConnected(this)) {
            findViewById(R.id.navigation_setting).setVisibility(View.INVISIBLE);
            findViewById(R.id.navigation_reservation).setVisibility(View.INVISIBLE);
        }
        if (reservationId == 0 ) return;
        viewModel.getReservation(reservationId).observe(this, new Observer<PresenterFactory<ReservationPresenter>>() {
            @Override
            public void onChanged(PresenterFactory<ReservationPresenter> result) {
                if (result.status == 200 && !result.response.isEmpty()) {
                    presenter = result.response.get(0);
                    updateFields();
                }
            }
        });
    }

    private void updateFields() {
        String to = presenter.toStation + "\n" + presenter.toZone + "\n" + presenter.toStationDetail ;
        String from = presenter.fromStation + "\n" + presenter.fromromZone + "\n" + presenter.fromStationDetail;
        String timer = presenter.routeDate + "\n" + presenter.hour;
        String driver = presenter.customerLastName + "\n" + presenter.customerFistName;
        String car = presenter.brandName + "\n" + presenter.modelName + "\n" + presenter.carYear + "\n" +
                presenter.carRegistrationNumber;
        routeTo.setText(to);
        routeFrom.setText(from);
        routeHour.setText(timer);
        routeDriver.setText(driver);
        routeCar.setText(car);
    }
}
