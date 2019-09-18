package com.ouilift.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.SearchViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.account.ReservationListActivity;
import com.ouilift.ui.account.SettingsActivity;
import com.ouilift.ui.adapter.RouteDetailAdapter;
import com.ouilift.utils.Preference;

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
    LinearLayout container;

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

}
