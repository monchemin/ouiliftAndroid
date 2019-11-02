package com.ouilift.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.adapter.ReservationAdapter;
import com.ouilift.utils.Preference;

public class ReservationListActivity extends BaseActivity {

    private ReservationViewModel viewModel;
    RelativeLayout noReservation;
    ReservationAdapter adapter;
    ContentLoadingProgressBar loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        noReservation = findViewById(R.id.no_reservation);
        loadingIndicator = findViewById(R.id.loading_indicator);
        RecyclerView recyclerView = findViewById(R.id.route_detail_recycler_view);
        adapter = new ReservationAdapter();
        LinearLayoutManager manager  = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Preference.IsDriver(this) || !Preference.IsActive(this)) {
            findViewById(R.id.navigation_route).setEnabled(false);
        }
        int userId = Preference.getConnection(this).Id;
        loadingIndicator.show();
        viewModel.getReservationList(userId).observe(this, new Observer<PresenterFactory<ReservationPresenter>>() {
            @Override
            public void onChanged(PresenterFactory<ReservationPresenter> result) {
                loadingIndicator.hide();
                if (result != null & result.status == 200 && !result.response.isEmpty()) {
                    adapter.setData(result.response);
                    noReservation.setVisibility(View.INVISIBLE);
                }
                else {
                    noReservation.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
