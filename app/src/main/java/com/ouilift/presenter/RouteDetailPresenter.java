package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RouteDetailPresenter {
    @SerializedName("PK")
    public int PK;
    @SerializedName("fStation")
    public String fromStation;
    @SerializedName("fStationDetail")
    public String fromStationDetail;
    @SerializedName("fZone")
    public String fromromZone;
    @SerializedName("hour")
    public String hour;
    @SerializedName("remainingPlace")
    public int remainingPlace;
    @SerializedName("routeDate")
    public Date routeDate;
    @SerializedName("routePrice")
    public Double routePrice;
    @SerializedName("tStation")
    public String toStation;
    @SerializedName("tStationDetail")
    public String toStationDetail;
    @SerializedName("tZone")
    public String toZone;
    @SerializedName("routePlace")
    public int routePlace;
}
