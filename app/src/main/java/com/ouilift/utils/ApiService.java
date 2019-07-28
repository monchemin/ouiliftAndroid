package com.ouilift.utils;

import com.ouilift.presenter.CarBrandPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteStation;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

        @POST("/internal-routes.php")
        Call<PresenterFactory<RouteDetailPresenter>> postInternalRoute(@Field("startDate") String startDate,
                                                                       @Field("fromStation") int fromStation,
                                                                       @Field("toStation") int toStation);

        @GET("/car-brands.php")
        Call<PresenterFactory<CarBrandPresenter>> getCarBrand();

        @GET("/route-station.php")
        Call<PresenterFactory<RouteStation>> getRouteStation();

        @POST("/login.php")
        Call<PresenterFactory<RouteStation>> performLogin(@Field("login") String login,
                                                          @Field("password") String password);
        @POST("/login.php")
        Call<PresenterFactory<RouteStation>> performRegister(@Field("customerFistName") String firstName,
                                                             @Field("customerLastName") String lastName,
                                                             @Field("customerEMailAddress") String eMail,
                                                             @Field("customerPhoneNumber") String phone,
                                                             @Field("customerPassword") String password);

    }
}
