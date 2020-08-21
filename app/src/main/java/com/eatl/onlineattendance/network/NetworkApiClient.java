package com.eatl.onlineattendance.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkApiClient {

    private static Retrofit retrofit = null;
    private static String baseUrl = "http://128.199.215.102:4040/";

    public static Retrofit getClient() {

        if (retrofit==null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()

                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();


                    // long t1 = System.nanoTime();
                    Log.d("Log", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()).toString());

                    Response response = chain.proceed(request);

                    // long t2 = System.nanoTime();
                     Log.e("Log", ""+response.request().url());

                    return response;
                }
            });


            OkHttpClient okHttpClient = httpClient.build()
                    .newBuilder()
                    .connectTimeout(4, TimeUnit.MINUTES)
                    .readTimeout(4, TimeUnit.MINUTES)
                    .writeTimeout(4, TimeUnit.MINUTES)
                    .build();

            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setLenient()
                    .create();

//            okHttpClient.interceptors().add(new Interceptor() {
//                @Override
//                public ResponseStatus intercept(Chain chain) throws IOException {
//
//                    Request request = chain.request();
//
//                    long t1 = System.nanoTime();
//                    Log.e("Log", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
//                                    request.headers()).toString());
//
//                    ResponseStatus response = chain.proceed(request);
//
//                    long t2 = System.nanoTime();
//                    Log.e("Log", String.format("Received response for %s in %.1fms%n%s", response.request().url(),
//                                    (t2 - t1) / 1e6d, response.headers()).toString());
//
//                    return response;
//                }
//            });

            retrofit= new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
