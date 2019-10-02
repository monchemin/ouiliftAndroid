package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.presenter.PresenterFactory;

import retrofit2.Call;

public class LoginRepository extends Repository {

    public MutableLiveData<PresenterFactory<Void>> register(JsonObject data) {
        Call<PresenterFactory<Void>> call = api.performRegister(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<CustomerPresenter>> login(JsonObject data) {
        Call<PresenterFactory<CustomerPresenter>> call = api.performLogin(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<Void>> change(JsonObject data) {
        Call<PresenterFactory<Void>> call = api.performChange(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<Void>> changePassword(JsonObject data) {
        Call<PresenterFactory<Void>> call = api.changePassoword(data);
        return getData(call);
    }
}
