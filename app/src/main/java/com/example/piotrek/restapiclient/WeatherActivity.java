package com.example.piotrek.restapiclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.piotrek.restapiclient.models.City;
import com.example.piotrek.restapiclient.models.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Bundle bundle= getIntent().getExtras();

        TextView cityName = findViewById(R.id.city_name);
        temp = findViewById(R.id.temp);

        String cityString = (String) bundle.get(SelectActivity.CITY_VARIABLE_NAME);
        cityName.setText(cityString);

        MyApiEndpointInterface myApiEndpointInterface = MyApiEndpointInterface.retrofit.create(MyApiEndpointInterface.class);
        Call<Weather> call = myApiEndpointInterface.loadCity(cityString);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.body() != null){
                    Weather weather = response.body();
                    temp.setText(weather.getDescription());
                }else{
                    temp.setText(R.string.error_loading_weather);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d("dupa", t.getStackTrace().toString());
                temp.setText(R.string.error_loading_weather);
            }
        });
    }
}
