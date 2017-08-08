package com.divyanshgoenka.pccwcodingchallenge.android.util;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by divyanshgoenka on 08/08/17.
 */

public class BundleUtils {
    public static String getString(Bundle bundle, String key, String fallback) {
        return bundle==null?fallback:bundle.getString(key,fallback);
    }

    public static int getInt(Bundle bundle, String key, int fallback) {
        return bundle==null?fallback:bundle.getInt(key,fallback);
    }

    public static Object getSerializable(Bundle bundle, String key, Object fallback) {
        Object value =  bundle==null?fallback:bundle.getSerializable(key);
        return value==null?fallback:value;
    }
}
