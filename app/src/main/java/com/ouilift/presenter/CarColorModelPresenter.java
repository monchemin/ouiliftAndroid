package com.ouilift.presenter;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class CarColorModelPresenter {
    @SerializedName("Id")
    public int Id;

    @SerializedName("colorName")
    private String colorName;

    @SerializedName("colorLabel")
    private String colorLabel;

    @SerializedName("model")
    public String model;

    @SerializedName("hour")
    public String hour;

    public String customOne;

    public String customTwo;

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

