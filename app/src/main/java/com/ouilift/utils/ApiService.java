package com.ouilift.utils;

import com.ouilift.presenter.CarBrandPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ApiService {

    private static Retrofit retrofit = null;
    private static String BASE_API = "http://api-test.toncopilote.com";

    public static EndPoint getApiService() {

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(EndPoint.class);

    }

    public interface EndPoint {


        @GET("/internal-routes.php")
        Call<PresenterFactory<RouteDetailPresenter>> getInternalRoute();

        @GET("/car-brands.php")
        Call<PresenterFactory<CarBrandPresenter>> getCarBrand();

    }
}
