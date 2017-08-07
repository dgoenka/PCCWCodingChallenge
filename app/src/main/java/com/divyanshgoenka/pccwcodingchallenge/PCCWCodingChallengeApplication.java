package com.divyanshgoenka.pccwcodingchallenge;

import android.app.Application;

import com.divyanshgoenka.pccwcodingchallenge.retrofit.GitHubApi;
import com.divyanshgoenka.pccwcodingchallenge.retrofit.ServiceGenerator;
import com.divyanshgoenka.pccwcodingchallenge.util.Constants;

/**
 * Created by divyanshgoenka on 06/08/17.
 */

public class PCCWCodingChallengeApplication extends Application {
    private static PCCWCodingChallengeApplication instance;

    public static PCCWCodingChallengeApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
