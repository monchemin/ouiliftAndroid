package com.ouilift.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.constant.DataConstant;
import com.ouilift.constant.IntentConstant;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.LoginActivity;
import com.ouilift.utils.DateUtils;
import com.ouilift.utils.Preference;

import java.util.Locale;

public class ReservationActivity extends BaseActivity {

    private MaterialButton reservationBtn;
    private TextInputEditText reservationPlace;
    private TextView fromHour, routeHour, routeFrom, routeTo, routePrice, routePlace, routeFromDetail, routeToDetail;
    private int routeId;
    private ReservationViewModel viewModel;
    private RouteDetailPresenter presenter;
    private int place;
    private ContentLoadingProgressBar loadingIndicator;
    private boolean isFirstReservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        viewBind();
        routeId = getIntent().getIntExtra(IntentConstant.ROUTE_ID, 0);
        isFirstReservation = getIntent().getBooleanExtra(IntentConstant.IS_FIRST_REGISTRATION, false);
        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
       setMenuEnable();
        if (routeId == 0 ) return;
        loadingIndicator.show();
        viewModel.getRoute(routeId).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200 && !result.response.isEmpty()) {
                presenter = result.response.get(0);
                updateFields();
            }
        });
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
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadingIndicator = findViewById(R.id.loading_indicator);
        reservationPlace = findViewById(R.id.input_reservation);
        fromHour = findViewById(R.id.from_hour);
        routeFrom = findViewById(R.id.route_from);
        routeTo = findViewById(R.id.route_to);
        routeFromDetail = findViewById(R.id.route_from_detail);
        routeToDetail = findViewById(R.id.route_to_detail);
        routePrice = findViewById(R.id.route_price);
        routePlace = findViewById(R.id.route_place);
        reservationBtn = findViewById(R.id.reservation_button);
        reservationBtn.setOnClickListener(v -> handleClick());
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
            intent.putExtra(IntentConstant.ROUTE_ID, routeId);
            intent.putExtra(IntentConstant.ROUTE_PLACE, place);
            intent.putExtra(IntentConstant.FOR_ROUTE, true);
           startActivity(intent);
        }
    }

    private void performReservation() {
        JsonObject data = new JsonObject();
        data.addProperty(DataConstant.CUSTOMER, Preference.getConnection(this).Id);
        data.addProperty(DataConstant.ROUTE, routeId);
        data.addProperty(DataConstant.PLACE, place);
        data.addProperty(DataConstant.LANGUAGE, Locale.getDefault().getLanguage());
        data.addProperty(DataConstant.IS_FIRST_REGISTRATION, isFirstReservation);
        loadingIndicator.show();
        viewModel.makeReservation(data).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200 && !result.response.isEmpty()) {
               DisplayReservation(result.response.get(0).reservationId);
            } else {
                error(getString(R.string.error_message));
            }
        });
    }

    private void DisplayReservation(int reservationId) {
        success(getString(R.string.success_message));
        Intent intent = new Intent(this, ReservationResultActivity.class);
        intent.putExtra(IntentConstant.RESERVATION_ID, reservationId);
        startActivity(intent);
    }
}
