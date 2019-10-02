package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

public class CustomerPresenter {
    @SerializedName("PK")
    public int PK;

    @SerializedName("customerFistName")
    public String firstName;

    @SerializedName("customerLastName")
    public String lastName;

    @SerializedName("customerEMailAddress")
    public String eMail;

    @SerializedName("customerPhoneNumber")
    public String phone;

}
