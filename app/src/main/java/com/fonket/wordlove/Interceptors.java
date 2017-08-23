package com.fonket.wordlove;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felip on 12-08-2017.
 */

public class Interceptors {

    private static final String BASE_URL = "https://love-calculator.p.mashape.com/";

    public Requests commonPostInterceptor() {
    /*Mostly the same of what is done with post, but this time the waiting time for response after post is increase
    and, very important, there are no retry. Here there is a 1 min waiting period, if for any reason the server did
    got processed the request but took 1 min and 1 sec to response, you dont want to retry cause it would create
    another object duplicated. One min waiting time for a server is a lot, it should work with this basis. If it doesnt
    then dont make it worse by doing retry*/
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("X-Mashape-Key", "wtK51SSziImsheEws5crgvuRGj5ip1jFovgjsnieFe6dXuAyUZ")
                        .header("Accept", "application/json")
                        .build();

                okhttp3.Response response = chain.proceed(request);

                return response;
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Requests service = interceptor.create(Requests.class);
        return service;
    }

}


