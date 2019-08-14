package com.ouilift.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.LoginActivity;
import com.ouilift.utils.Preference;

public class ReservationActivity extends BaseActivity {
    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            return false;
        }
    };
    private MaterialButton reservationBtn;
    private TextInputEditText reservationPlace;
    private TextView routeDate, routeHour, routeFrom, routeTo, routePrice, routePlace;
    private int routeId;
    private ReservationViewModel viewModel;
    private RouteDetailPresenter presenter;
    private int place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        viewBind();
        routeId = getIntent().getIntExtra("routeId", 0);
        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (routeId == 0 ) return;
        viewModel.getRoute(routeId).observe(this, new Observer<PresenterFactory<RouteDetailPresenter>>() {
            @Override
            public void onChanged(PresenterFactory<RouteDetailPresenter> result) {
                if (result.status == 200 && !result.response.isEmpty()) {
                    presenter = result.response.get(0);
                    updateFields();
                }
            }
        });
    }

    private void updateFields() {
        reservationPlace.setText("1");
        routeFrom.setText(String.valueOf(presenter.fromStation));
        routeTo.setText(String.valueOf(presenter.toStation));
        routeDate.setText(String.valueOf(presenter.routeDate));
        routeHour.setText(String.valueOf(presenter.hour));
        routePlace.setText(String.valueOf(presenter.remainingPlace));
        routePrice.setText(String.valueOf(presenter.routePrice));
    }

    private void viewBind() {
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        reservationPlace = findViewById(R.id.input_reservation);
        routeDate = findViewById(R.id.route_date);
        routeHour = findViewById(R.id.route_hour);
        routeFrom = findViewById(R.id.route_from);
        routeTo = findViewById(R.id.route_to);
        routePrice = findViewById(R.id.route_price);
        routePlace = findViewById(R.id.route_place);
        reservationBtn = findViewById(R.id.reservation_button);
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick();
            }
        });
        reservationBtn.setEnabled(false);

        reservationPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onPlaceChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onPlaceChange() {
        try {
            place = Integer.parseInt(reservationPlace.getText().toString());
            if(place > 0 && place <= presenter.remainingPlace) {
                reservationBtn.setEnabled(true);
            }

        } catch (NumberFormatException ex) {
            reservationBtn.setEnabled(false);
        }
    }

    private void handleClick() {
        if(Preference.IsConnected(this)) {
            performReservation();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("routeId", routeId);
            intent.putExtra("place", place);
            intent.putExtra("forRoute", true);
           startActivity(intent);
        }
    }

    private void performReservation() {
    }
}
