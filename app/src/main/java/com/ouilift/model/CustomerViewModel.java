package com.ouilift.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.repository.CustomerRepository;

public class CustomerViewModel extends ViewModel {
    private CustomerRepository repository = new CustomerRepository();

    public MutableLiveData<PresenterFactory<Void>> register(JsonObject data) {
        return repository.register(data);
    }

    public MutableLiveData<PresenterFactory<CustomerPresenter>> login(JsonObject data) {
        return repository.login(data);
    }

    public MutableLiveData<PresenterFactory<Void>> change(JsonObject data) {
        return repository.change(data);
    }

    public MutableLiveData<PresenterFactory<Void>> driver(JsonObject data) {
        return repository.driver(data);
    }

    public MutableLiveData<PresenterFactory<Void>> changePassword(JsonObject data) {
        return repository.changePassword(data);
    }

    public MutableLiveData<PresenterFactory<Void>> activateAccount(JsonObject data) {
        return repository.activateAccount(data);
    }

    public MutableLiveData<PresenterFactory<Void>> changeMail(JsonObject data) {
        return repository.changeMail(data);
    }

    public MutableLiveData<PresenterFactory<Void>> passwordRecovery(JsonObject data) {
        return repository.passwordRecovery(data);
    }
}
