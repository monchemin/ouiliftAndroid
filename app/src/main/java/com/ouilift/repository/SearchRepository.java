package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.ouilift.presenter.CarBrandPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;

import retrofit2.Call;

public class SearchRepository extends repository {


    public MutableLiveData<PresenterFactory<RouteDetailPresenter>> getInternalRoute() {
        Call<PresenterFactory<RouteDetailPresenter>> call = api.getInternalRoute();
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<CarBrandPresenter>> get() {
        Call<PresenterFactory<CarBrandPresenter>> call = api.getCarBrand();
        return getData(call);
    }


}

