package com.ouilift.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.LoginActivity;
import com.ouilift.ui.account.DriverActivity;
import com.ouilift.ui.account.ReservationListActivity;
import com.ouilift.ui.account.SettingsActivity;
import com.ouilift.utils.DateUtils;
import com.ouilift.utils.Preference;

public class ReservationActivity extends BaseActivity {
    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
                    finish();
                    startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                    break;
            }
            return true;
        }

    };
    private MaterialButton reservationBtn;
    private TextInputEditText reservationPlace;
    private TextView fromHour, routeHour, routeFrom, routeTo, routePrice, routePlace, routeFromDetail, routeToDetail;
    private int routeId;
    private ReservationViewModel viewModel;
    private RouteDetailPresenter presenter;
    private int place;
    private ContentLoadingProgressBar loadingIndicator;
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
       setMenuEnable();
        if (routeId == 0 ) return;
        loadingIndicator.show();
        viewModel.getRoute(routeId).observe(this, new Observer<PresenterFactory<RouteDetailPresenter>>() {
            @Override
            public void onChanged(PresenterFactory<RouteDetailPresenter> result) {
                loadingIndicator.hide();
                if (result.status == 200 && !result.response.isEmpty()) {
                    presenter = result.response.get(0);
                    updateFields();
                }
            }
        });
    }

    private void setMenuEnable() {
        if (!Preference.IsConnected(this)) {
            findViewById(R.id.navigation_setting).setEnabled(false);
            findViewById(R.id.navigation_reservation).setEnabled(false);
            findViewById(R.id.navigation_route).setEnabled(false);
        }
    }

    private void updateFields() {
        reservationPlace.setText("1");
        routeFrom.setText(getString(R.string.route_from) + ": " + presenter.fromStation);
        routeFromDetail.setText(presenter.fromStationDetail);
        routeTo.setText(getString(R.string.route_to)+ ": " + presenter.toStation);
        routeToDetail.setText(presenter.toStationDetail);
        String fromHourStr = getString(R.string.departure_on) + " " + DateUtils.dateToString(presenter.routeDate, getString(R.string.date_format))
                            + " " + getString(R.string.departure_at) + " " + presenter.hour;

        fromHour.setText(fromHourStr);
        String strPlace = presenter.remainingPlace + " " + (presenter.remainingPlace <= 1 ? getString(R.string.place_singular) : getString(R.string.place_plural));
        routePlace.setText( strPlace);
        routePrice.setText(String.valueOf(presenter.routePrice));
    }

    private void viewBind() {
        loadingIndicator = findViewById(R.id.loading_indicator);
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        reservationPlace = findViewById(R.id.input_reservation);
        fromHour = findViewById(R.id.from_hour);
        routeFrom = findViewById(R.id.route_from);
        routeTo = findViewById(R.id.route_to);
        routeFromDetail = findViewById(R.id.route_from_detail);
        routeToDetail = findViewById(R.id.route_to_detail);
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
        JsonObject data = new JsonObject();
        data.addProperty("FK_Customer", Preference.getConnection(this).PK);
        data.addProperty("FK_Route", routeId);
        data.addProperty("place", place);
        loadingIndicator.show();
        viewModel.makeReservation(data).observe(this, new Observer<PresenterFactory<ReservationPresenter>>() {
            @Override
            public void onChanged(PresenterFactory<ReservationPresenter> result) {
                loadingIndicator.hide();
                if (result.status == 200 && !result.response.isEmpty()) {
                   DisplayReservation(result.response.get(0).reservation);
                }
            }
        });
    }

    private void DisplayReservation(int pk) {
        Intent intent = new Intent(this, ReservationResultActivity.class);
        intent.putExtra("reservationId", pk);
        startActivity(intent);
    }
}
