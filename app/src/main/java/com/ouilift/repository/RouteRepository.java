package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CarColorModelPresenter;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteReservationPresenter;

import retrofit2.Call;

public class RouteRepository extends Repository {


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

    public MutableLiveData<PresenterFactory<CarColorModelPresenter>> hours() {
        Call<PresenterFactory<CarColorModelPresenter>> call = api.hours();
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> createRoute(JsonObject data) {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.createRoute(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> ownerRoutes(JsonObject data) {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.ownerRoutes(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<RouteReservationPresenter>> routeReservations(JsonObject data) {
        Call<PresenterFactory<RouteReservationPresenter>> call = api.routeReservations(data);
        return getData(call);
    }


}

