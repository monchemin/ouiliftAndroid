package com.ouilift.presenter;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class RouteStation {
    @SerializedName("stationId")
    public int stationId;
    @SerializedName("stationName")
    public String stationName;
    @SerializedName("stationAddress")
    public String stationAddress;



    @NonNull
    @Override
    public java.lang.String toString() {
        return stationName;
    }
}
