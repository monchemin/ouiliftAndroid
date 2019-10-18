package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteStation;

import retrofit2.Call;

public class SearchRepository extends Repository {


    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getInternalRoute(JsonObject data) {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.getInternalRoute(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<RouteStation>> getRouteStation() {
        Call<PresenterFactory<RouteStation>> call = api.getRouteStation();
        return getData(call);
    }

}

