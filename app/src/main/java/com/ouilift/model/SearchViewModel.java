package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ouilift.presenter.CarBrandPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteStation;
import com.ouilift.repository.SearchRepository;

public class SearchViewModel extends ViewModel {
    private SearchRepository repository = new SearchRepository();


    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getInternalRoute() {
        return repository.getInternalRoute();
    }

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> postInternalRoute(String date, int from, int to) {
        return repository.postInternalRoute(date, from, to);
    }

    public MutableLiveData<PresenterFactory<CarBrandPresenter>> getCarBrand() {
        return repository.getCarBrand();
    }

    public MutableLiveData<PresenterFactory<RouteStation>> getRouteStation() {
        return repository.getRouteStation();
    }

}
