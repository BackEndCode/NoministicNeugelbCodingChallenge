package com.noministic.neugelbcodingchallenge.api;

import com.noministic.neugelbcodingchallenge.models.MovieArray;
import com.noministic.neugelbcodingchallenge.models.MovieDetailModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {

    //List of trending movies api call
    @GET("/3/trending/movie/week")
    Call<MovieArray> getTrendingMovies(@Query("api_key") String apikey, @Query("page") int pageNum);

    //Movie search
    @GET("/3/search/movie")
    Call<MovieArray> searchMovie(@Query("api_key") String apikey, @Query("query") String query);

    //Single movie api call
    @GET("/3/movie/{movie_id}")
    Call<MovieDetailModel> getSingleMovie(@Path("movie_id") int id, @Query("api_key") String apikey);


}
