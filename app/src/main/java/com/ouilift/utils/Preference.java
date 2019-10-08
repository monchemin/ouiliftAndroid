package com.ouilift.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ouilift.R;
import com.ouilift.presenter.CustomerPresenter;

public class Preference {
    public static boolean IsConnected(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        return sharedPref.getBoolean("IS_CONNECTED", false);
    }

    public static boolean IsDriver(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        return sharedPref.getBoolean("IS_DRIVER", false);
    }

    public static void disConnected(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public static void makeConnect(Context context, CustomerPresenter customer) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("IS_CONNECTED", true);
        editor.putString("FiRST_NAME", customer.firstName);
        editor.putString("LAST_NAME", customer.lastName);
        editor.putInt("PK", customer.PK);
        editor.putString("EMAIL", customer.eMail);
        editor.putString("PHONE", customer.phone);
        editor.putBoolean("IS_DRIVER", true);
        editor.apply();
    }

    public static CustomerPresenter getConnection(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        CustomerPresenter customer = new CustomerPresenter();
        customer.firstName = sharedPref.getString("FiRST_NAME", customer.firstName);
        customer.lastName = sharedPref.getString("LAST_NAME", customer.lastName);
        customer.PK = sharedPref.getInt("PK", customer.PK);
        customer.eMail = sharedPref.getString("EMAIL", customer.eMail);
        customer.phone = sharedPref.getString("PHONE", customer.phone);

        return customer;
    }
}
