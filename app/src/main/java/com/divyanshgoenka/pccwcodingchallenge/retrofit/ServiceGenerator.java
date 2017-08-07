package com.divyanshgoenka.pccwcodingchallenge.retrofit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.divyanshgoenka.pccwcodingchallenge.util.Constants;

public class ServiceGenerator {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(new OkHttpClient.Builder().build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(String baseUrl,
            Class<S> serviceClass) {
        Retrofit retrofit = builder.baseUrl(baseUrl).build();
        return retrofit.create(serviceClass);
    }
}
