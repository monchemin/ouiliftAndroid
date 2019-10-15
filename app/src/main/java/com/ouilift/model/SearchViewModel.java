package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CarModelPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteStation;
import com.ouilift.repository.SearchRepository;

public class SearchViewModel extends ViewModel {
    private SearchRepository repository = new SearchRepository();


    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getInternalRoute(JsonObject data) {
        return repository.getInternalRoute(data);
    }

    public MutableLiveData<PresenterFactory<CarModelPresenter>> getCarBrand() {
        return repository.getCarBrand();
    }

    public MutableLiveData<PresenterFactory<RouteStation>> getRouteStation() {
        return repository.getRouteStation();
    }
}
