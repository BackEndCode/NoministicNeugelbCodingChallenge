package com.noministic.neugelbcodingchallenge.models;

import com.google.gson.annotations.SerializedName;

public class ProductionCountries {
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @SerializedName("name")
    String countryName;
}
