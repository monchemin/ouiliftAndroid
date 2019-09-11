package com.ouilift.utils;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CarBrandPresenter;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteStation;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

        @POST("/internal-routes.php")
        Call<PresenterFactory<RouteDetailPresenter>> getInternalRoute(@Body JsonObject data);

        @GET("/car-brands.php")
        Call<PresenterFactory<CarBrandPresenter>> getCarBrand();

        @GET("/route-station.php")
        Call<PresenterFactory<RouteStation>> getRouteStation();

        @POST("/login.php")
        Call<PresenterFactory<CustomerPresenter>> performLogin(@Body JsonObject data);

        @POST("/customer.php")
        Call<PresenterFactory<Void>> performRegister(@Body JsonObject data);

        @GET("/route-details.php/{PK}")
        Call<PresenterFactory<RouteDetailPresenter>> routeDetail(@Path("PK") int pk);

        @POST("/reservations.php")
        Call<PresenterFactory<ReservationPresenter>> performReservation(@Body JsonObject data);

        @GET("/reservations.php/{PK}")
        Call<PresenterFactory<ReservationPresenter>> getReservation(@Path("PK") int pk);

    }
}
