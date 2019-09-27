package com.ouilift.ui.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.SearchViewModel;
import com.ouilift.presenter.RouteStation;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.account.ReservationListActivity;
import com.ouilift.ui.account.SettingsActivity;
import com.ouilift.ui.adapter.RouteDetailAdapter;
import com.ouilift.utils.DateUtils;
import com.ouilift.utils.Preference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends BaseActivity implements RouteSearchDialog.OnInputListener {

    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            navView.setVisibility(View.INVISIBLE);

            switch (item.getItemId()) {

                case R.id.navigation_reservation:
                    startActivity(new Intent(getApplicationContext(), ReservationListActivity.class));
                    break;
                case R.id.navigation_search:
                    container.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_setting:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    break;

            }
            return true;
        }


    };

    private SearchViewModel viewModel;
    private RouteDetailAdapter adapter;
    private ContentLoadingProgressBar loadingIndicator;
    private LinearLayout container;
    private TextInputEditText searchDateView, searchFrom, searchTo;
    String searchDate;
    private List<RouteStation> stations = new ArrayList<>();
    boolean from, fromFocusDisable, toFocusDisable;
    int fromPK, toPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        navView = findViewById(R.id.dashboard_nav_view);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.route_detail_recycler_view);
        adapter = new RouteDetailAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        loadingIndicator = findViewById(R.id.loading_indicator);
        container = findViewById(R.id.container);
        searchDateView = findViewById(R.id.search_date);
        searchFrom = findViewById(R.id.search_from);
        searchTo = findViewById(R.id.search_to);

        MaterialButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> setData());
        MaterialButton resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> setEmptyResearch());
        setViewListeners();

    }

    private void setEmptyResearch() {
        searchDate = "";
        fromPK = 0;
        toPK = 0;
        searchDateView.setText("");
        searchFrom.setText("");
        searchTo.setText("");
        setData();
    }

    private void setViewListeners() {
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        searchDateView.setOnClickListener(v -> showDatePicker());
        searchDateView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });
        searchFrom.setOnClickListener(v -> {
            from = true;
            showDialog();
        });
        searchFrom.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !fromFocusDisable) {
                from = true;
                showDialog();
            }
        });
        searchTo.setOnClickListener(v -> {
            from = false;
            showDialog();
        });
        searchTo.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !toFocusDisable) {
                from = false;
                showDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        navView.setVisibility(View.VISIBLE);
        if (!Preference.IsConnected(this)) {
            findViewById(R.id.navigation_setting).setVisibility(View.INVISIBLE);
            findViewById(R.id.navigation_reservation).setVisibility(View.INVISIBLE);
        }
        setData();

    }

    private void setData() {
        loadingIndicator.show();
        viewModel.getInternalRoute(makeJson(searchDate, fromPK, toPK)).observe(this, result -> {
            if (result != null && result.status == 200 && !result.response.isEmpty()) {
                adapter.setData(result.response);
            } else {
                Toast.makeText(getApplicationContext(), R.string.no_route, Toast.LENGTH_LONG).show();
            }

        });

        if (stations.isEmpty()) {
            viewModel.getRouteStation().observe(this, result -> {
                if (result != null && result.status == 200 && !result.response.isEmpty()) {
                    stations = result.response;
                }
                loadingIndicator.hide();
            });
        } else {
            loadingIndicator.hide();
        }

    }

    private JsonObject makeJson(String rDate, int from, int to) {
        JsonObject data = new JsonObject();
        data.addProperty("startDate", rDate);
        data.addProperty("fromStation", from);
        data.addProperty("toStation", to);
        return data;
    }

    private void showDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        final int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        DatePickerDialog picker = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    int selectM = monthOfYear + 1;
                    Date date = DateUtils.stringToDate(year1 + "-" + selectM + "-" + dayOfMonth);
                    searchDate = DateUtils.dateToString(date, "yyyy-MM-dd");
                    searchDateView.setText(DateUtils.dateToString(date, getString(R.string.date_format)));
                }, year, month, day);
        picker.show();
    }

    private void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RouteSearchDialog newFragment = new RouteSearchDialog(stations);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    @Override
    public void sendInput(int input) {

        for (RouteStation station : stations) {
            if (station.PK == input) {
                if (from) {
                    fromFocusDisable = true;
                    searchFrom.setText(station.stationName);
                    fromPK = input;
                } else {
                    toFocusDisable = true;
                    searchTo.setText(station.stationName);
                    toPK = input;
                }

                break;
            }
        }
    }
}
