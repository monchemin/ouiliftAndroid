package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CarColorModelPresenter;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.repository.ReservationRepository;

public class ReservationViewModel extends ViewModel {
    private ReservationRepository repository = new ReservationRepository();

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getRoute(int pk) {
        return repository.getRoute(pk);
    }

    public MutableLiveData<PresenterFactory<ReservationPresenter>> makeReservation(JsonObject data) {
        return repository.makeReservation(data);
    }

    public MutableLiveData<PresenterFactory<ReservationPresenter>> getReservation(int pk) {
        return repository.getReservation(pk);
    }

    public MutableLiveData<PresenterFactory<ReservationPresenter>> getReservationList(int pk) {
        return repository.getReservationList(pk);
    }

    public MutableLiveData<PresenterFactory<Void>> deleteReservation(int pk) {
        return repository.deleteReservation(pk);
    }

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

}
