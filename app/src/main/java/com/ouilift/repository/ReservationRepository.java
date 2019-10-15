package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CarColorModelPresenter;
import com.ouilift.presenter.CarPresenter;
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

    public MutableLiveData<PresenterFactory<Void>> deleteReservation(int pk) {
        Call<PresenterFactory<Void>> call = api.deleteReservation(pk);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<CarPresenter>> registeredCar(JsonObject data) {
        Call<PresenterFactory<CarPresenter>> call = api.registeredCar(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<CarPresenter>> carCreate(JsonObject data) {
        Call<PresenterFactory<CarPresenter>> call = api.carCreate(data);
        return getData(call);
    }


    public MutableLiveData<PresenterFactory<CarColorModelPresenter>> carColor() {
        Call<PresenterFactory<CarColorModelPresenter>> call = api.carColor();
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<CarColorModelPresenter>> carModel() {
        Call<PresenterFactory<CarColorModelPresenter>> call = api.carModel();
        return getData(call);
    }

}

