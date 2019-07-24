package com.beadcore.employeenotification.Retrofit;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String BASE_URL = "http://"+Constant.SERVER_IP +":"+Constant.SERVER_PORT;
   // public static String BASE_URL = "http://192.168.12.123:8000/api/employee/";
    private static Retrofit retrofit = null;






    public static Retrofit getClient(Context context){
        if (retrofit==null){


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(3,TimeUnit.MINUTES)
                    .readTimeout(3,TimeUnit.MINUTES)
                    .writeTimeout(3,TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL+"/api/employee/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getAuthorizationClient(Context context ,final String token){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (retrofit==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(3,TimeUnit.MINUTES)
                    .readTimeout(3,TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new ConnectivityInterceptor(context))
                    .writeTimeout(3,TimeUnit.MINUTES);

            if(token!=null){
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request().newBuilder().addHeader("Authorization", "bearer "+token).build();
                        return chain.proceed(request);
                    }
                });
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL+"/api/employee/")
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


    public static void ResetClient(){

        retrofit= null;
    }

}
