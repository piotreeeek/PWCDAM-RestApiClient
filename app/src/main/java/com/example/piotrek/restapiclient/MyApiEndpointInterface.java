package com.example.piotrek.restapiclient;

import com.example.piotrek.restapiclient.models.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

interface MyApiEndpointInterface {
    String BASE_URL = "http://165.227.154.243:8081";
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
}
