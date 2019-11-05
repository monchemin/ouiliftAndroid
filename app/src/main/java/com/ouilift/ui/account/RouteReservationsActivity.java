package com.ouilift.ui.account;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.core.widget.ContentLoadingProgressBar;

import com.ouilift.R;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.adapter.ReservationAdapter;


public class RouteReservationsActivity extends BaseActivity {

    RelativeLayout noReservation;
    ReservationAdapter adapter;
    ContentLoadingProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_reservations);
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        noReservation = findViewById(R.id.no_reservation);
        loadingIndicator = findViewById(R.id.loading_indicator);
    }
}
