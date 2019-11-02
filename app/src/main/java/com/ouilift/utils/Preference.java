package com.ouilift.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ouilift.R;
import com.ouilift.presenter.CustomerPresenter;

public class Preference {

    static String IS_CONNECTED = "IS_CONNECTED";
    static String DRIVING_LICENCE = "DRIVING_LICENCE";
    static String FIRST_NAME = "FIRST_NAME";
    static String LAST_NAME = "LAST_NAME";
    static String ID = "ID";
    static String EMAIL = "EMAIL";
    static String PHONE = "PHONE";
    static String ACTIVE = "ACTIVE";

    public static boolean IsConnected(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_CONNECTED, false);
    }

    public static boolean IsDriver(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        return !sharedPref.getString(DRIVING_LICENCE, "").isEmpty();
    }

    public static boolean IsActive(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        return sharedPref.getInt(ACTIVE, 0) == 1;
    }

    public static void setActive(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ACTIVE, 1);
        editor.apply();
    }

    public static void setMail(Context context, String mail) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EMAIL, mail);
        editor.apply();
    }

    public static void setNumber(Context context, String number) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(DRIVING_LICENCE, number);
        editor.apply();
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
        editor.putBoolean(IS_CONNECTED, true);
        editor.putString(FIRST_NAME, customer.firstName);
        editor.putString(LAST_NAME, customer.lastName);
        editor.putInt(ID, customer.Id);
        editor.putString(EMAIL, customer.eMail);
        editor.putString(PHONE, customer.phone);
        editor.putString(DRIVING_LICENCE, customer.drivingNumber);
        editor.putInt(ACTIVE, customer.active);
        editor.apply();
    }

    public static CustomerPresenter getConnection(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.key_file), Context.MODE_PRIVATE);
        CustomerPresenter customer = new CustomerPresenter();
        customer.firstName = sharedPref.getString(FIRST_NAME, customer.firstName);
        customer.lastName = sharedPref.getString(LAST_NAME, customer.lastName);
        customer.Id = sharedPref.getInt(ID, customer.Id);
        customer.eMail = sharedPref.getString(EMAIL, customer.eMail);
        customer.phone = sharedPref.getString(PHONE, customer.phone);
        customer.drivingNumber = sharedPref.getString(DRIVING_LICENCE, customer.drivingNumber);
        customer.active = sharedPref.getInt(ACTIVE, customer.active);

        return customer;
    }
}
