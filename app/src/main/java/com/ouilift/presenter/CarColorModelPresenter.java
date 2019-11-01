package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class CarColorModelPresenter {
    @SerializedName("Id")
    public int Id;

    @SerializedName("colorName")
    public String colorName;

    @SerializedName("model")
    public String model;

    @SerializedName("hour")
    public String hour;

    public String customOne;

    public String customTwo;
}
