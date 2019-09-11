package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class ReservationPresenter extends RouteDetailPresenter {
    @SerializedName("carRegistrationNumber")
    public String carRegistrationNumber;
    @SerializedName("carYear")
    public String carYear;
    @SerializedName("modelName")
    public String modelName;
    @SerializedName("brandName")
    public String brandName;
    @SerializedName("colorName")
    public String colorName;
    @SerializedName("customerFistName")
    public String customerFistName;
    @SerializedName("customerLastName")
    public String customerLastName;
    @SerializedName("reservation")
    public int reservation;
}
