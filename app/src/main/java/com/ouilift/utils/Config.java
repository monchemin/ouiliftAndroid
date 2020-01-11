package com.ouilift.utils;

import com.ouilift.BuildConfig;

public final class Config {

    public static String apiHost() {

        switch (BuildConfig.FLAVOR) {
            case "qa":
                return "http://mercure.ouilift.com";
            case "staging":
                return "http://jupiter.ouilift.com";
            default:
                return "http://neptune.ouilift.com";
        }

    }

}



