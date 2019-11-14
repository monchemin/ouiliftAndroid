package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CarColorModelPresenter;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteReservationPresenter;
import com.ouilift.repository.RouteRepository;

public class RouteViewModel extends ViewModel {
    private RouteRepository repository = new RouteRepository();



    public MutableLiveData<PresenterFactory<CarPresenter>> registeredCar(JsonObject data) {
        return repository.registeredCar(data);
    }

    public MutableLiveData<PresenterFactory<CarPresenter>> carCreate(JsonObject data) {
        return repository.carCreate(data);
    }

    public MutableLiveData<PresenterFactory<CarColorModelPresenter>> getCarColor() {
        return repository.carColor();
    }

    public MutableLiveData<PresenterFactory<CarColorModelPresenter>> getCarModel() {
        return repository.carModel();
    }

    public MutableLiveData<PresenterFactory<CarColorModelPresenter>> gethour() {
        return repository.hours();
    }

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> createRoute(JsonObject data) {
        return repository.createRoute(data);
    }

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getOwnerRoutes(JsonObject data) {
        return repository.ownerRoutes(data);
    }

    public MutableLiveData<PresenterFactory<RouteReservationPresenter>> getRouteReservations(JsonObject data) {
        return repository.routeReservations(data);
    }

    public MutableLiveData<PresenterFactory<Void>> cancelRoute(JsonObject data) {
        return repository.cancelRoute(data);
    }

}
