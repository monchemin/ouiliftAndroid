package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.presenter.RouteDetailPresenter;

import retrofit2.Call;

public class ReservationRepository extends Repository {

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getRoute(int pk) {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.routeDetail(pk);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<ReservationPresenter>> makeReservation(JsonObject data) {
        Call<PresenterFactory<ReservationPresenter>> call = api.performReservation(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<ReservationPresenter>> getReservation(int pk) {
        Call<PresenterFactory<ReservationPresenter>> call = api.getReservation(pk);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<ReservationPresenter>> getReservationList(int pk) {
        Call<PresenterFactory<ReservationPresenter>> call = api.getReservationList(pk);
        return getData(call);
    }

}

