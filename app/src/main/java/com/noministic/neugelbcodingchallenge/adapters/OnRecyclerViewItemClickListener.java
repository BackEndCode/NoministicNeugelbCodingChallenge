package com.noministic.neugelbcodingchallenge.adapters;

public interface OnRecyclerViewItemClickListener {
    /**
     * Called when any item with in recyclerview or any item with in item
     * clicked
     *
     * @param id The id of the movie item which is clicked
     */
    void onRecyclerViewItemClicked(int id);
}
