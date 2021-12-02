package com.noministic.neugelbcodingchallenge.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.noministic.neugelbcodingchallenge.R;

public class SearchCursorAdaptor extends SimpleCursorAdapter {

    private Context appContext;
    private final int layout;
    private final LayoutInflater inflater;

    public SearchCursorAdaptor(Context context,int layout, Cursor c,String[] from,int[] to) {
        super(context,layout,c,from,to,0);
        this.layout=layout;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView titleS=(TextView)view.findViewById(R.id.textView_search_title);
        String title=cursor.getString(1);
        titleS.setText(title);
    }

}
