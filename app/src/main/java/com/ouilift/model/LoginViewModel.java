package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private LoginRepository repository = new LoginRepository();

    public MutableLiveData<PresenterFactory<Void>> register(JsonObject data) {
        return repository.register(data);
    }

    public MutableLiveData<PresenterFactory<CustomerPresenter>> login(JsonObject data) {
        return repository.login(data);
    }

    public MutableLiveData<PresenterFactory<Void>> change(JsonObject data) {
        return repository.change(data);
    }

    public MutableLiveData<PresenterFactory<Void>> changePassword(JsonObject data) {
        return repository.changePassword(data);
    }
}
