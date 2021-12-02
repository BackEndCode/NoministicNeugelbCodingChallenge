package com.noministic.neugelbcodingchallenge.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailModel {
    @SerializedName("original_title")
    String title;
    @SerializedName("poster_path")
    String posterImage;
    @SerializedName("overview")
    String desc;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("vote_average")
    String rating;
    @SerializedName("id")
    int id;
    @SerializedName("vote_count")
    int totalRating;
    @SerializedName("status")
    String releaseStatus;
    @SerializedName("revenue")
    double revenue;

    public List<ProductionCountries> getProductCountries() {
        return productCountries;
    }

    public void setProductCountries(List<ProductionCountries> productCountries) {
        this.productCountries = productCountries;
    }

    public List<ProductionCompanies> getProductCompanies() {
        return productCompanies;
    }

    public void setProductCompanies(List<ProductionCompanies> productCompanies) {
        this.productCompanies = productCompanies;
    }

    @SerializedName("production_countries")
    List<ProductionCountries> productCountries;
    @SerializedName("production_companies")
    List<ProductionCompanies> productCompanies;

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
