package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class CarPresenter {
    @SerializedName("Id")
    public int Id;

    @SerializedName("number")
    public String number;

    @SerializedName("model")
    public String model;

    @SerializedName("brand")
    public String brand;

    @SerializedName("color")
    public String color;

    @SerializedName("year")
    public int year;

    public boolean checked;


}
