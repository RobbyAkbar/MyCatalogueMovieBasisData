package es.esy.android_inyourhand.mycataloguemoviebasisdata.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.CustomOnItemClickListener;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.DetailMovieActivity;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.R;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.models.MovieItem;

/**
  Created by robby on 01/01/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CardViewViewHolder> {

    private ArrayList<MovieItem> movieItems;
    private Context context;

    private ArrayList<MovieItem> getMovieItems(){
        return movieItems;
    }

    public void setMovieItems(ArrayList<MovieItem> items){
        this.movieItems = items;
    }

    public MovieAdapter(Context context){
        this.context = context;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_movie, parent, false);
        return new CardViewViewHolder(view);
    }

    public void clearMovie(){
        movieItems.clear();
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {
        holder.textViewTitle.setText(getMovieItems().get(position).getTitleMovie());
        holder.textViewOverview.setText(getMovieItems().get(position).getOverView());
        holder.textViewRelease.setText(getMovieItems().get(position).getReleaseDate());
        Picasso.with(context).load(getMovieItems().get(position).getImagePoster()).placeholder(R.drawable.loading).error(R.drawable.error).into(holder.imgPoster);

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra("from", "MovieAdapter");
                intent.putExtra("item", getMovieItems().get(position));
                context.startActivity(intent);
                Toast.makeText(context, "Detail "+ getMovieItems().get(position).getTitleMovie(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, getMovieItems().get(position).getTitleMovie()+" \n"+getMovieItems().get(position).getOverView());
                intent.setType("text/plain");
                context.startActivity(intent);
                Toast.makeText(context, "Share "+ getMovieItems().get(position).getTitleMovie(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        Log.d("getCount", String.valueOf(movieItems.size()));
        if (movieItems == null){
            return 0;
        }
        return movieItems.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewOverview, textViewRelease;
        ImageView imgPoster;
        Button btnDetail, btnShare;

        CardViewViewHolder(View itemView) {
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