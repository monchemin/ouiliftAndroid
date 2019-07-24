package com.ouilift.ui.search;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ouilift.R;
import com.ouilift.model.SearchViewModel;
import com.ouilift.presenter.CarBrandPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.ui.adapter.RouteDetailAdapter;


public class ResultFragment extends Fragment {

    RouteDetailAdapter adapter;
    SearchViewModel viewModel;

    public ResultFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.route_detail_recycler_view);
        adapter = new RouteDetailAdapter();
        LinearLayoutManager  manager  = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.getInternalRoute().observe(this, new Observer<PresenterFactory<RouteDetailPresenter>>() {
            @Override
            public void onChanged(PresenterFactory<RouteDetailPresenter> routeDetailPresenterPresenterFactory) {
                adapter.setData(routeDetailPresenterPresenterFactory.response);
            }
        });

        return view;
    }

}
