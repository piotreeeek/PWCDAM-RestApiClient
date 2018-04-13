package com.example.piotrek.restapiclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.piotrek.restapiclient.models.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectActivity extends AppCompatActivity {
    private ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        cityList = findViewById(R.id.city_list);

        MyApiEndpointInterface myApiEndpointInterface = MyApiEndpointInterface.retrofit.create(MyApiEndpointInterface.class);
        Call<List<City>> call = myApiEndpointInterface.loadCities();
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(@NonNull Call<List<City>> call, @NonNull Response<List<City>> response) {
                List values = new ArrayList();
                if(response.body() != null) {
                    for (City city : Objects.requireNonNull(response.body())) {
                        values.add(city.getName());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
                    cityList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<City>> call, @NonNull Throwable t) {

            }
        });
    }
}
