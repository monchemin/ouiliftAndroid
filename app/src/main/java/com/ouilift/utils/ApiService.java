package com.ouilift.utils;

import com.google.gson.JsonObject;
import com.ouilift.presenter.CarColorModelPresenter;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.ReservationPresenter;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.presenter.RouteStation;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiService {

    private static Retrofit retrofit = null;
    private static String BASE_API = Config.apiHost();

    public static EndPoint getApiService() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_API)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(EndPoint.class);

    }

    public interface EndPoint {

        //customer
        @POST("/login.php")
        Call<PresenterFactory<CustomerPresenter>> performLogin(@Body JsonObject data);

        @POST("/customer.php")
        Call<PresenterFactory<Void>> performRegister(@Body JsonObject data);

        @PUT("/customer.php")
        Call<PresenterFactory<Void>> performChange(@Body JsonObject data);

        @POST("/driver.php")
        Call<PresenterFactory<Void>> performDriver(@Body JsonObject data);

        @POST("/change-password.php")
        Call<PresenterFactory<Void>> changePassword(@Body JsonObject data);

        @POST("/change-mail.php")
        Call<PresenterFactory<Void>> changeMail(@Body JsonObject data);

        @POST("/active-activate.php")
        Call<PresenterFactory<Void>> activateAccount(@Body JsonObject data);

//route
        @POST("/internal-routes.php")
        Call<PresenterFactory<RouteDetailPresenter>> getInternalRoute(@Body JsonObject data);

        @GET("/route-station.php")
        Call<PresenterFactory<RouteStation>> getRouteStation();

        @GET("/route-details.php/{PK}")
        Call<PresenterFactory<RouteDetailPresenter>> routeDetail(@Path("PK") int pk);

        @POST("/car.php")
        Call<PresenterFactory<CarPresenter>> registeredCar(@Body JsonObject data);

        @POST("/car-create.php")
        Call<PresenterFactory<CarPresenter>> carCreate(@Body JsonObject data);

        @GET("/car-colors.php")
        Call<PresenterFactory<CarColorModelPresenter>> carColor();

        @GET("/car-brand-model.php")
        Call<PresenterFactory<CarColorModelPresenter>> carModel();

        @GET("/pickup-hour.php")
        Call<PresenterFactory<CarColorModelPresenter>> hours();

        @POST("/routes.php")
        Call<PresenterFactory<RouteDetailPresenter>> createRoute(@Body JsonObject data);

        @POST("/published-routes.php")
        Call<PresenterFactory<RouteDetailPresenter>> ownerRoutes(@Body JsonObject data);

// reservation
        @POST("/reservations.php")
        Call<PresenterFactory<ReservationPresenter>> performReservation(@Body JsonObject data);

        @GET("/reservations.php/{PK}")
        Call<PresenterFactory<ReservationPresenter>> getReservation(@Path("PK") int pk);

        @GET("/reservation-list.php/{PK}")
        Call<PresenterFactory<ReservationPresenter>> getReservationList(@Path("PK") int pk);

        @DELETE("/reservations.php/{PK}")
        Call<PresenterFactory<Void>> deleteReservation(@Path("PK") int pk);

    }
}
