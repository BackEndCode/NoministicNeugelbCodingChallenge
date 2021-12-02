package com.noministic.neugelbcodingchallenge;

import static com.noministic.neugelbcodingchallenge.constants.Constants.API_KEY;
import static com.noministic.neugelbcodingchallenge.constants.Constants.MOVIE_ID_PARAM;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noministic.neugelbcodingchallenge.adapters.MoviesAdapter;
import com.noministic.neugelbcodingchallenge.api.RequestInterface;
import com.noministic.neugelbcodingchallenge.api.RetrofitClientInstance;
import com.noministic.neugelbcodingchallenge.databinding.ActivityMainBinding;
import com.noministic.neugelbcodingchallenge.models.MovieArray;
import com.noministic.neugelbcodingchallenge.models.MovieModel;
import com.noministic.neugelbcodingchallenge.views.EndlessRecyclerViewScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Movies Recycleview lists and adapter
    List<MovieModel> movieModelList;
    private MoviesAdapter adapter;
    MovieArray movieArray;

    int currentPage = 1;

    ActivityMainBinding activityMainBinding;

    //Search list and adapter
    List<String> suggestions;
    CursorAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        initViews();
    }

    private void initViews() {
        activityMainBinding.moviesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        activityMainBinding.moviesRecyclerView.setLayoutManager(layoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //checks if current page is less the total page count that we can fetch
                if (currentPage <= movieArray.totalPages) {
                    currentPage++;
                    loadMoviesList(currentPage);
                }
            }
        };
        activityMainBinding.moviesRecyclerView.addOnScrollListener(scrollListener);
        loadMoviesList(currentPage);
    }

    private void loadMoviesList(int pageNum) {
        activityMainBinding.progressBar.setVisibility(View.VISIBLE);
        RequestInterface myAPIService = RetrofitClientInstance.getRetrofitInstance().create(RequestInterface.class);
        Call<MovieArray> call = myAPIService.getTrendingMovies(API_KEY, pageNum);
        call.enqueue(new Callback<MovieArray>() {
            @Override
            public void onResponse(@NonNull Call<MovieArray> call, @NonNull Response<MovieArray> response) {
                if (response.body() != null) {
                    movieArray = response.body();
                    if (movieArray.movies.size() > 0)
                        //if it is first time that we just assign the movieModelList to the movies loaded from imdb
                        //otherwse we just add all the movies to already created movieModelList
                        if (movieModelList != null)
                            movieModelList.addAll(movieArray.movies);
                        else
                            movieModelList = movieArray.movies;
                }
                //movies are loaded so we are removing progressbar
                activityMainBinding.progressBar.setVisibility(View.GONE);

                activityMainBinding.textViewNoData.setVisibility(View.GONE);
                //if it's first time and adapter is not initialised then we create it otherwise we just notify adapter that new items are inserted
                if (adapter == null) {
                    adapter = new MoviesAdapter(MainActivity.this, movieModelList);
                    activityMainBinding.moviesRecyclerView.setAdapter(adapter);
                } else
                    adapter.notifyItemRangeInserted(adapter.getItemCount(), movieModelList.size() - 1);
                adapter.setOnItemClickListener(id -> gotoMovieDetailActivity(id));
            }

            @Override
            public void onFailure(@NonNull Call<MovieArray> call, @NonNull Throwable throwable) {
                activityMainBinding.progressBar.setVisibility(View.GONE);
                activityMainBinding.textViewNoData.setVisibility(View.VISIBLE);
                if (throwable instanceof IOException) {
                    activityMainBinding.textViewNoData.setText(R.string.Internet_failure);
                } else {
                    activityMainBinding.textViewNoData.setText(throwable.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        suggestionAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);
        suggestions = new ArrayList<>();

        mSearchView.setSuggestionsAdapter(suggestionAdapter);
        mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                mSearchView.setQuery(suggestions.get(position), false);
                mSearchView.clearFocus();
                Cursor cursor = (Cursor) suggestionAdapter.getItem(position);
                String movie_id = cursor.getString(2);
                //Log.e("NOMI", cursor.getString(2));
                gotoMovieDetailActivity(Integer.parseInt(movie_id));
                return true;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchMovie(query);
                return true;

            }

        });
        return true;
    }

    void gotoMovieDetailActivity(int movie_id) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID_PARAM, Integer.valueOf(movie_id));
        startActivity(intent);
    }

    void searchMovie(String query) {
        RequestInterface myAPIService = RetrofitClientInstance.getRetrofitInstance().create(RequestInterface.class);
        Call<MovieArray> call = myAPIService.searchMovie(API_KEY, query);
        call.enqueue(new Callback<MovieArray>() {

            @Override
            public void onResponse(@NonNull Call<MovieArray> call, @NonNull Response<MovieArray> response) {
                if (response.body() != null) {
                    List<MovieModel> mMovieModelList = response.body().movies;
                    suggestions.clear();
                    if (mMovieModelList.size() > 0) {
                        String[] columns = {
                                BaseColumns._ID,
                                SearchManager.SUGGEST_COLUMN_TEXT_1,
                                SearchManager.SUGGEST_COLUMN_INTENT_DATA};
                        MatrixCursor cursor = new MatrixCursor(columns);

                        for (int i = 0; i < mMovieModelList.size(); i++) {
                            suggestions.add(mMovieModelList.get(i).getTitle());
                            String[] tmp = {Integer.toString(i), mMovieModelList.get(i).getTitle(), String.valueOf(mMovieModelList.get(i).getId())};
                            cursor.addRow(tmp);
                        }
                        suggestionAdapter.swapCursor(cursor);
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieArray> call, @NonNull Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}