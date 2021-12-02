package com.noministic.neugelbcodingchallenge.models;

import com.google.gson.annotations.SerializedName;

public class ProductionCompanies {
    @SerializedName("name")
    String companyName;
    @SerializedName("origin_country")
    String countryName;
}
