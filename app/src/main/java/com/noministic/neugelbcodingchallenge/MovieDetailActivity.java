package com.noministic.neugelbcodingchallenge;

import static com.noministic.neugelbcodingchallenge.constants.Constants.API_KEY;
import static com.noministic.neugelbcodingchallenge.constants.Constants.IMDB_PRE_IMAGE_PATH;
import static com.noministic.neugelbcodingchallenge.constants.Constants.MOVIE_ID_PARAM;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.noministic.neugelbcodingchallenge.api.RequestInterface;
import com.noministic.neugelbcodingchallenge.api.RetrofitClientInstance;
import com.noministic.neugelbcodingchallenge.databinding.ActivityMovieDetailBinding;
import com.noministic.neugelbcodingchallenge.models.MovieDetailModel;
import com.noministic.neugelbcodingchallenge.models.ProductionCountries;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    ActivityMovieDetailBinding movieDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailBinding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        View view = movieDetailBinding.getRoot();
        setContentView(view);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (!getIntent().getExtras().isEmpty()) {
            int movie_id = getIntent().getExtras().getInt(MOVIE_ID_PARAM);
            getMovie(movie_id);
        }
    }

    void getMovie(int movie_id) {
        RequestInterface myAPIService = RetrofitClientInstance.getRetrofitInstance().create(RequestInterface.class);
        Call<MovieDetailModel> call = myAPIService.getSingleMovie(movie_id, API_KEY);
        call.enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {
                if (response.body() != null) {
                    MovieDetailModel movieDetailModel = response.body();
                    movieDetailBinding.textviewTitle.setText(movieDetailModel.getTitle());
                    movieDetailBinding.textviewReleaseDate.setText(MessageFormat.format("Release date - {0}", movieDetailModel.getReleaseDate()));
                    movieDetailBinding.textViewRatingCount.setText(MessageFormat.format("Rating - {0} Rating count - {1}", movieDetailModel.getRating(), movieDetailModel.getTotalRating()));
                    movieDetailBinding.textviewRevenue.setText(MessageFormat.format("Revenue - {0}", movieDetailModel.getRevenue()));
                    movieDetailBinding.textviewStatus.setText(MessageFormat.format("Status - {0}", movieDetailModel.getReleaseStatus()));
                    movieDetailBinding.textviewDesc.setText(movieDetailModel.getDesc());

                    StringBuilder stringBuilder = new StringBuilder();
                    for (ProductionCountries countries : movieDetailModel.getProductCountries()) {
                        stringBuilder.append(countries.getCountryName()).append(", ");
                    }
                    movieDetailBinding.textviewPCountries.setText(MessageFormat.format("Production Countries - {0}", stringBuilder.toString()));
                    Picasso.get().load(IMDB_PRE_IMAGE_PATH + movieDetailModel.getPosterImage()).into(movieDetailBinding.imageviewMovie);
                }
                //myProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailModel> call, @NonNull Throwable throwable) {
                //myProgressBar.setVisibility(View.GONE);
                Toast.makeText(MovieDetailActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}