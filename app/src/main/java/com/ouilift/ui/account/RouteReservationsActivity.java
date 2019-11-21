package com.ouilift.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.RouteViewModel;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.adapter.RouteReservationAdapter;
import com.ouilift.utils.Preference;

import java.util.Locale;


public class RouteReservationsActivity extends BaseActivity {

    RelativeLayout noReservation;
    RouteReservationAdapter adapter = new RouteReservationAdapter();
    ContentLoadingProgressBar loadingIndicator;
    private RouteViewModel viewModel;
    private int routeId;
    private MaterialButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_reservations);
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        noReservation = findViewById(R.id.no_reservation);
        loadingIndicator = findViewById(R.id.loading_indicator);
        viewModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        routeId = getIntent().getIntExtra("routeId", 0);
        RecyclerView recyclerView = findViewById(R.id.route_detail_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        cancelButton = findViewById(R.id.btn_cancel_route);
        cancelButton.setOnClickListener(v -> cancelRoute());

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadingIndicator.show();
        viewModel.getRouteReservations(makeJson()).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200 && result.response != null) {
                noReservation.setVisibility(View.GONE);
                adapter.setData(result.response);

            } else {
                noReservation.setVisibility(View.VISIBLE);
            }
        });
    }

    private JsonObject makeJson() {
        JsonObject data = new JsonObject();
        data.addProperty("customerId", Preference.getConnection(this).Id);
        data.addProperty("routeId", routeId);
        data.addProperty("language", Locale.getDefault().getLanguage());

        return data;
    }

    private void cancelRoute() {
        loadingIndicator.show();
        viewModel.cancelRoute(makeJson()).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200) {
                success(getString(R.string.success_message));
                startActivity(new Intent(this, DriverActivity.class));
                finish();
            } else {
                error(getString(R.string.error_message));
            }
        });
    }
}
