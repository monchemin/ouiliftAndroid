package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.repository.ReservationRepository;

public class ReservationViewModel extends ViewModel {
    private ReservationRepository repository = new ReservationRepository();

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getRoute(int pk) {
        return repository.getRoute(pk);
    }

    public MutableLiveData<PresenterFactory<ReservationPresenter>> makeResevation(JsonObject data) {
        return repository.makeReservation(data);
    }

}
