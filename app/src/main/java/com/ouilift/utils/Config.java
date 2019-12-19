package com.ouilift.utils;

import com.ouilift.BuildConfig;

public final class Config {

    public static String apiHost() {

        switch (BuildConfig.FLAVOR) {
            case "qa":
                return "http://api-test.toncopilote.com";
            case "staging":
                return "http://api-staging.toncopilote.com";
            default:
                return "http://api.toncopilote.com";
        }

    }

}



