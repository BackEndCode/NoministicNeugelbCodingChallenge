package com.noministic.neugelbcodingchallenge.models;

import com.google.gson.annotations.SerializedName;

public class MovieModel {
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
}
