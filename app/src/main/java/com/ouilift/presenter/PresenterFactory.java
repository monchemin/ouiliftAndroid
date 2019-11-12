package com.ouilift.presenter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PresenterFactory<T> {
     @SerializedName("status")
     public int status;
     @SerializedName("lastIndex")
     public int lastIndex;
     @SerializedName("errorMessage")
     public String errorMessage;
     @SerializedName("response")
     public List<T> response = new ArrayList<>();
}
