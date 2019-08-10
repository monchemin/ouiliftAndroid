package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.ouilift.presenter.PresenterFactory;

import retrofit2.Call;

public class LoginRepository extends Repository {

    public MutableLiveData<PresenterFactory<Void>> register(JsonObject data) {
        Call<PresenterFactory<Void>> call = api.performRegister(data);
        return getData(call);
    }

    public MutableLiveData<PresenterFactory<Void>> login(JsonObject data) {
        Call<PresenterFactory<Void>> call = api.performLogin(data);
        return getData(call);
    }
}
