package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class RouteReservationPresenter {
    @SerializedName("place")
    public int place;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("eMail")
    public String mail;
    @SerializedName("phoneNumber")
    public String phoneNumber;

}
