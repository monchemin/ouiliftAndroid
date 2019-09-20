package com.ouilift.ui.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.SearchViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.account.ReservationListActivity;
import com.ouilift.ui.account.SettingsActivity;
import com.ouilift.ui.adapter.RouteDetailAdapter;
import com.ouilift.utils.DateUtils;
import com.ouilift.utils.Preference;

import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.route_detail_recycler_view);
        adapter = new RouteDetailAdapter();
        LinearLayoutManager manager  = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        loadingIndicator = findViewById(R.id.loading_indicator);
        container = findViewById(R.id.container);
        searchDateView = findViewById(R.id.search_date);
        searchDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        searchFrom = findViewById(R.id.search_from);
        searchFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        setData(null, 0, 0);

    }

    private void setData(String rDate, int from, int to) {
        loadingIndicator.show();
        viewModel.getInternalRoute(makeJson(rDate, from, to)).observe(this, new Observer<PresenterFactory<RouteDetailPresenter>>() {
            @Override
            public void onChanged(PresenterFactory<RouteDetailPresenter> result) {

                if (result != null) {
                    adapter.setData(result.response);
                }
                loadingIndicator.hide();
            }
        });

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
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int selectM = monthOfYear + 1;
                        Date date = DateUtils.stringToDate(year + "-" + selectM + "-" + dayOfMonth);
                        searchDate = DateUtils.dateToString(date, "yyyy-MM-dd");
                        searchDateView.setText(DateUtils.dateToString(date, getString(R.string.date_format)));
                    }
                }, year, month, day);
        picker.show();
    }

    private void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RouteSearchDialog newFragment = new RouteSearchDialog();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

}
