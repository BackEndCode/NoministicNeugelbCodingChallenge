package com.noministic.neugelbcodingchallenge.adapters;

import static com.noministic.neugelbcodingchallenge.constants.Constants.IMDB_PRE_IMAGE_PATH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noministic.neugelbcodingchallenge.R;
import com.noministic.neugelbcodingchallenge.models.MovieModel;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final List<MovieModel> movieModelList;
    OnRecyclerViewItemClickListener recyclerViewItemClickListener;
    Context context;
    public MoviesAdapter(Context context,List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
        this.context=context;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_recycle_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.recyclerViewItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.movie_title.setText(movieModelList.get(i).getTitle());
        viewHolder.movie_desc.setText(movieModelList.get(i).getDesc());
        viewHolder.movie_release_date.setText(MessageFormat.format(context.getString(R.string.release_date), movieModelList.get(i).getReleaseDate()));
        viewHolder.movie_rating.setText(MessageFormat.format(context.getString(R.string.rating), movieModelList.get(i).getRating()));
        Picasso.get().load(IMDB_PRE_IMAGE_PATH + movieModelList.get(i).getPosterImage()).into(viewHolder.movie_imageView);
        viewHolder.itemView.setOnClickListener(v -> recyclerViewItemClickListener.onRecyclerViewItemClicked(movieModelList.get(i).getId()));
    }
    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView movie_imageView;
        private final TextView movie_title;
        private final TextView movie_rating;
        private final TextView movie_desc;
        private final TextView movie_release_date;

        public ViewHolder(View view) {
            super(view);
            movie_imageView = (ImageView) view.findViewById(R.id.movie_imageView);
            movie_title = (TextView) view.findViewById(R.id.movie_title);
            movie_rating = (TextView) view.findViewById(R.id.movie_rating);
            movie_desc = (TextView) view.findViewById(R.id.movie_desc);
            movie_release_date = (TextView) view.findViewById(R.id.movie_release_date);
        }
    }

}

