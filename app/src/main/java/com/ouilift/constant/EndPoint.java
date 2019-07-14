package com.ouilift.constant;

public enum EndPoint {
    ROUTE("route"),
    LOGIN("login");

    private String value;

    EndPoint(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
