package com.ouilift.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.adapter.CarAdapter;
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
            case R.id.navigation_setting:
                finish();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
        }
        return true;
    };
    MaterialButton btnAddCar;
    LinearLayout addCarContainer;
    CarAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
        adapter = new CarAdapter();
        bindView();
    }

    private void bindView() {
        BottomNavigationView navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        btnAddCar = findViewById(R.id.btn_add_car);
        btnAddCar.setOnClickListener(v -> {
            addCarContainer.setVisibility(View.VISIBLE);
        });
        addCarContainer = findViewById(R.id.add_car_container);
        RecyclerView carRecycler = findViewById(R.id.car_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        carRecycler.setLayoutManager(manager);
        carRecycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.registeredCar(makeJson()).observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                adapter.setData(result.response);

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
