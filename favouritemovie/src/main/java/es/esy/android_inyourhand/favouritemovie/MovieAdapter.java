package es.esy.android_inyourhand.favouritemovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.IMAGE_POSTER;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.OVERVIEW;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.RELEASE_DATE;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.getColumnString;

/**
  Created by robby on 02/01/18.
 */

public class MovieAdapter extends CursorAdapter {

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor!=null){
            TextView textViewTitle, textViewOverview, textViewRelease;
            ImageView imgPoster;
            Button btnShare;
            textViewTitle = view.findViewById(R.id.tv_title);
            textViewOverview = view.findViewById(R.id.tv_description);
            textViewRelease = view.findViewById(R.id.tv_release);
            imgPoster = view.findViewById(R.id.img_poster);
            textViewTitle.setText(getColumnString(cursor,TITLE_MOVIE));
            textViewOverview.setText(getColumnString(cursor,OVERVIEW));
            textViewRelease.setText(getColumnString(cursor,RELEASE_DATE));
            Picasso.with(context).load(getColumnString(cursor, IMAGE_POSTER)).placeholder(R.drawable.loading).error(R.drawable.error).into(imgPoster);
            btnShare = view.findViewById(R.id.btn_share);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, getColumnString(cursor, TITLE_MOVIE)+" \n"+getColumnString(cursor,OVERVIEW));
                    intent.setType("text/plain");
                    context.startActivity(intent);
                    Toast.makeText(context, "Share "+ getColumnString(cursor,TITLE_MOVIE), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
