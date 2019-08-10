package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;

import retrofit2.Call;

public class ReservationRepository extends Repository {

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getRoute(int pk) {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.routeDetail(pk);
        return getData(call);
    }

}

