package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class CarColorModelPresenter {
    @SerializedName("PK")
    public int PK;

    @SerializedName("colorName")
    public String colorName;

    @SerializedName("model")
    public String model;
}
