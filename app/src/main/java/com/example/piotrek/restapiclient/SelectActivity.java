package com.example.piotrek.restapiclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.piotrek.restapiclient.models.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String CITY_VARIABLE_NAME = "city";
    private ListView cityList;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        setTitle(R.string.select_city);

        cityList = new ListView(this);
        cityList.setOnItemClickListener(this);
        mainLayout = findViewById(R.id.select_layout);

        MyApiEndpointInterface myApiEndpointInterface = MyApiEndpointInterface.retrofit.create(MyApiEndpointInterface.class);
        Call<List<City>> call = myApiEndpointInterface.loadCities();
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(@NonNull Call<List<City>> call, @NonNull Response<List<City>> response) {
                List<String> values = new ArrayList<>();
                if (response.body() != null) {
                    if (response.body().size() > 0) {
                        for (City city : Objects.requireNonNull(response.body())) {
                            values.add(city.getName());

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
                        cityList.setAdapter(adapter);
                        RelativeLayout.LayoutParams cityListParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT);
                        cityList.setLayoutParams(cityListParams);
                        mainLayout.addView(cityList);
                    } else {
                        this.setError(R.string.error_loading_city);
                    }
                } else {
                    this.setError(R.string.error_loading_city);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<City>> call, @NonNull Throwable t) {
                this.setError(R.string.error_loading_city);
            }

            private void setError(int resid) {
                TextView errorText = new TextView(getApplicationContext());
                errorText.setText(resid);

                RelativeLayout.LayoutParams errorTextParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                errorText.setLayoutParams(errorTextParams);
                mainLayout.addView(errorText);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(this, WeatherActivity.class);
        intent.putExtra(CITY_VARIABLE_NAME, (String) cityList.getItemAtPosition(i));
        startActivity(intent);
    }
}
