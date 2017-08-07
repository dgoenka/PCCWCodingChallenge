package com.divyanshgoenka.pccwcodingchallenge.android.util;

import android.os.Bundle;

/**
 * Created by divyanshgoenka on 08/08/17.
 */

public class BundleUtils {
    public static String getString(Bundle bundle, String key, String fallback) {
        return bundle==null?fallback:bundle.getString(key,fallback);
    }
}
