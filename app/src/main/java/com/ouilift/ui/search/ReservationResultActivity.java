package com.ouilift.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.constant.DataConstant;
import com.ouilift.constant.IntentConstant;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.account.ReservationListActivity;
import com.ouilift.ui.account.SettingsActivity;
import com.ouilift.utils.DateUtils;
import com.ouilift.utils.Preference;

public class ReservationResultActivity extends BaseActivity {

    private int reservationId;
    private ReservationViewModel viewModel;
    private ReservationPresenter presenter;
    private TextView routeInfo, routeHour, routeFrom, routeTo, routeCar, routeDriver;
    private ContentLoadingProgressBar loadingIndicator;
    private MaterialButton cancelReservation;
    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {

                    case R.id.navigation_setting:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        break;
                    case R.id.navigation_search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        break;
                    case R.id.navigation_reservation:
                        startActivity(new Intent(getApplicationContext(), ReservationListActivity.class));
                        break;
                }
                return true;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_result);
        viewBind();
        reservationId = getIntent().getIntExtra(IntentConstant.RESERVATION_ID, 0);
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
        loadingIndicator = findViewById(R.id.loading_indicator);
        cancelReservation = findViewById(R.id.cancel_reservation_button);
        cancelReservation.setOnClickListener(v -> cancelAction());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Preference.IsConnected(this)) {
            findViewById(R.id.navigation_setting).setEnabled(false);
            findViewById(R.id.navigation_reservation).setEnabled(false);
            findViewById(R.id.navigation_route).setEnabled(false);
        }

        if (reservationId == 0 ) return;
        loadingIndicator.show();
        viewModel.getReservation(reservationId).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200 && !result.response.isEmpty()) {
                presenter = result.response.get(0);
                updateFields();
            }
        });
    }

    private void updateFields() {
        String to = presenter.toStation + "\n" + presenter.toZone + "\n" + (presenter.toStationDetail == null ? "" : presenter.toStationDetail) ;
        String from = presenter.fromStation + "\n" + presenter.fromromZone + "\n" + (presenter.fromStationDetail == null ? "" : presenter.fromStationDetail);
        String timer = DateUtils.dateToString(presenter.routeDate, getString(R.string.date_format)) + "\n" + presenter.hour;
        String driver = (presenter.driverLastName == null ? "" : presenter.driverLastName) + " " + (presenter.driverFistName == null ? "" : presenter.driverFistName);
        String car = presenter.brandName + " " + presenter.modelName + "\n"
                + presenter.year +  " " + presenter.colorName()  + "\n" +
                presenter.registrationNumber;
        routeTo.setText(to);
        routeFrom.setText(from);
        routeHour.setText(timer);
        routeDriver.setText(driver);
        routeCar.setText(car);
    }
    private void cancelAction() {
        loadingIndicator.show();
        JsonObject data = new JsonObject();
        data.addProperty(DataConstant.CUSTOMER_ID, Preference.getConnection(this).Id);
        data.addProperty(DataConstant.RESERVATION_ID, reservationId);
        viewModel.deleteReservation(data).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200 ){
                onDeletedReservation();
            } else {
                error(getString(R.string.error_message));
            }
        });
    }

    private void onDeletedReservation() {
        success(getString(R.string.success_message));
        startActivity(new Intent(this, ReservationListActivity.class));
    }
}
