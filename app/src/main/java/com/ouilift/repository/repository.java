package com.ouilift.repository;

import androidx.lifecycle.MutableLiveData;

import com.ouilift.presenter.PresenterFactory;
import com.ouilift.utils.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class repository {

    ApiService.EndPoint api = ApiService.getApiService();

    <T> MutableLiveData<PresenterFactory<T>> getData(Call<PresenterFactory<T>> call) {
        final MutableLiveData<PresenterFactory<T>> data = new MutableLiveData<>();
        call.enqueue(new Callback<PresenterFactory<T>>() {
            @Override
            public void onResponse(Call<PresenterFactory<T>> call, Response<PresenterFactory<T>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PresenterFactory<T>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
