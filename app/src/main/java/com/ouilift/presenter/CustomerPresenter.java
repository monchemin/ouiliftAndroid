package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class CustomerPresenter {
    @SerializedName("PK")
    public int PK;

    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;

    @SerializedName("eMail")
    public String eMail;

    @SerializedName("phoneNumber")
    public String phone;

    @SerializedName("drivingNumber")
    public String drivingNumber;

}
