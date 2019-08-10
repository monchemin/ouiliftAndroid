package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private LoginRepository repository = new LoginRepository();

    public MutableLiveData<PresenterFactory<Void>> register(JsonObject data) {
        return repository.register(data);
    }

    public MutableLiveData<PresenterFactory<Void>> login(JsonObject data) {
        return repository.login(data);
    }
}
