package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.ouilift.presenter.CarBrandPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteStation;

import retrofit2.Call;

public class SearchRepository extends Repository {


    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getInternalRoute() {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.getInternalRoute();
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> postInternalRoute(String date, int from, int to) {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.postInternalRoute(date, from, to);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<CarBrandPresenter>> getCarBrand() {
        Call<PresenterFactory<CarBrandPresenter>> call = api.getCarBrand();
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<RouteStation>> getRouteStation() {
        Call<PresenterFactory<RouteStation>> call = api.getRouteStation();
        return getData(call);
    }


}

