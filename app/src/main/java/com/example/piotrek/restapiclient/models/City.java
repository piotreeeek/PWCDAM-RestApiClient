package com.example.piotrek.restapiclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class City {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

}
