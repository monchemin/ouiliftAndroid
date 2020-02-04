package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

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
    private String colorName;

    @SerializedName("colorLabel")
    private String colorLabel;

    @SerializedName("driverFistName")
    public String driverFistName;

    @SerializedName("driverLastName")
    public String driverLastName;

    @SerializedName("reservationId")
    public int reservationId;

    @SerializedName("place")
    public int place;

    public String colorName() {
        if (this.colorLabel == null || this.colorLabel.equals("")) {
            return this.colorName;
        }
        try {
            JSONObject label = new JSONObject(this.colorLabel);
            return label.getString(Locale.getDefault().getLanguage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return colorName;
    }
}
