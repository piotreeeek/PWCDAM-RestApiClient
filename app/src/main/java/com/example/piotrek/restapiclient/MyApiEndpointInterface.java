package com.example.piotrek.restapiclient;

import com.example.piotrek.restapiclient.models.City;
import com.example.piotrek.restapiclient.models.Weather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface MyApiEndpointInterface {
    String BASE_URL = "http://139.59.150.98:8081";
    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    Gson gson = new GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @GET("cities/")
    Call<List<City>> loadCities();

    @GET("city/{city}/")
    Call<Weather> loadCity(@Path("city") String city);
}
