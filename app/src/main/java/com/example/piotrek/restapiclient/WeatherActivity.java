package com.example.piotrek.restapiclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.piotrek.restapiclient.models.Weather;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    private static final String IMG_URL = "http://openweathermap.org/img/w/";
    private TextView temp;
    private ImageView icon;
    private TextView city_weather;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Bundle bundle = getIntent().getExtras();

        temp = findViewById(R.id.temp);
        icon = findViewById(R.id.icon);
        city_weather = findViewById(R.id.weather);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);


        String cityString = (String) bundle.get(SelectActivity.CITY_VARIABLE_NAME);
        setTitle(cityString);

        MyApiEndpointInterface myApiEndpointInterface = MyApiEndpointInterface.retrofit.create(MyApiEndpointInterface.class);
        Call<Weather> call = myApiEndpointInterface.loadCity(cityString);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                if (response.body() != null) {
                    Weather weather = response.body();
                    temp.setText(getString(R.string.temp, weather.getTemp()));
                    Picasso.get().load(IMG_URL + weather.getIcon() + ".png")
                            .into(icon);
                    city_weather.setText(getString(R.string.weather, weather.getDescription()));
                    humidity.setText(getString(R.string.humidity, weather.getHumidity()));
                    pressure.setText(getString(R.string.pressure, weather.getPressure()));
                    wind.setText(getString(R.string.wind, weather.getSpeed()));
                } else {
                    temp.setText(R.string.error_loading_weather);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                Log.d("pogoda", Arrays.toString(t.getStackTrace()));
                temp.setText(R.string.error_loading_weather);
            }
        });
    }
}
