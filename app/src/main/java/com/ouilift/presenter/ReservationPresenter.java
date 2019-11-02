package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class ReservationPresenter extends RouteDetailPresenter {
    @SerializedName("registrationNumber")
    public String registrationNumber;
    @SerializedName("year")
    public String year;
    @SerializedName("modelName")
    public String modelName;
    @SerializedName("brandName")
    public String brandName;
    @SerializedName("colorName")
    public String colorName;
    @SerializedName("driverFistName")
    public String driverFistName;
    @SerializedName("driverLastName")
    public String driverLastName;
    @SerializedName("reservationId")
    public int reservationId;
    @SerializedName("place")
    public int place;
}
