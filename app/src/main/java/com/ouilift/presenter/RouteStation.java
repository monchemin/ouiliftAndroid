package com.ouilift.presenter;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class RouteStation {
    @SerializedName("PK")
    public int PK;
    @SerializedName("stationName")
    public String stationName;
    @SerializedName("stationAddress")
    public String StationAddress;



    @NonNull
    @Override
    public java.lang.String toString() {
        return stationName;
    }
}
