package es.esy.android_inyourhand.mycataloguemoviebasisdata.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.CustomOnItemClickListener;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.DetailMovieActivity;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.R;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.models.MovieItem;

import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.CONTENT_URI;

/**
  Created by robby on 02/01/18.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MovieViewHolder> {

    private Cursor listMovies;
    private Context context;

    public FavouriteAdapter(Context context){
        this.context = context;
    }

    public void setListMovies(Cursor listMovies){
        this.listMovies = listMovies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final MovieItem movieItem = getItem(position);
        holder.textViewTitle.setText(movieItem.getTitleMovie());
        holder.textViewOverview.setText(movieItem.getOverView());
        holder.textViewRelease.setText(movieItem.getReleaseDate());
        Picasso.with(context).load(movieItem.getImagePoster()).placeholder(R.drawable.loading).error(R.drawable.error).into(holder.imgPoster);
        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra("from", "FavouriteAdapter");
                intent.putExtra("item", movieItem);
                Uri uri = Uri.parse(CONTENT_URI+"/"+movieItem.getId());
                intent.setData(uri);
                context.startActivity(intent);
                Toast.makeText(context, "Detail "+ movieItem.getTitleMovie(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, movieItem.getTitleMovie()+" \n"+movieItem.getOverView());
                intent.setType("text/plain");
                context.startActivity(intent);
                Toast.makeText(context, "Share "+ movieItem.getTitleMovie(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (listMovies == null){
            return 0;
        }
        return listMovies.getCount();
    }

    private MovieItem getItem(int position){
        if (!listMovies.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(listMovies);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewOverview, textViewRelease;
        ImageView imgPoster;
        Button btnDetail, btnShare;
        MovieViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewOverview = itemView.findViewById(R.id.tv_description);
            textViewRelease = itemView.findViewById(R.id.tv_release);
            imgPoster = itemView.findViewById(R.id.img_poster);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnShare = itemView.findViewById(R.id.btn_share);
        }
    }
}
