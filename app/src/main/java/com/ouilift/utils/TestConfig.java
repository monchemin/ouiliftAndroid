package com.ouilift.utils;

public class TestConfig implements Config {
    @Override
    public String apiHost() {
        return "http://api-test.toncopilote.com";
    }
}
