package com.noministic.neugelbcodingchallenge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieArray {

    @SerializedName("page")
    @Expose
    public String pageNum;
    @SerializedName("results")
    @Expose
    public List<MovieModel> movies;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
}
