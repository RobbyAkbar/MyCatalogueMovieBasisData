package es.esy.android_inyourhand.mycataloguemoviebasisdata;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.helpers.MovieHelper;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.models.MovieItem;

import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.CONTENT_URI;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.MovieColumns.IMAGE_BACKDROP;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.MovieColumns.IMAGE_POSTER;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.MovieColumns.OVERVIEW;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.MovieColumns.POPULARITY;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.MovieColumns.RELEASE_DATE;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.MovieColumns.TITLE_MOVIE;

public class DetailMovieActivity extends AppCompatActivity {

    private MovieItem movieItem;
    private CheckBox favouriteCheck;
    private MovieHelper movieHelper;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        String from = getIntent().getStringExtra("from");

        favouriteCheck = findViewById(R.id.checkBox);
        favouriteCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favouriteCheck.isChecked()){
                    showAlertDialog(ALERT_DIALOG_ADD);
                } else if (!favouriteCheck.isChecked()){
                    showAlertDialog(ALERT_DIALOG_DELETE);
                }
            }
        });

        if (from.equals("MovieAdapter")){
            movieItem = getIntent().getParcelableExtra("item");
        } else if (from.equals("FavouriteAdapter")){
            movieHelper = new MovieHelper(this);
            movieHelper.open();
            Uri uri = getIntent().getData();
            if (uri!=null){
                Cursor cursor = getContentResolver().query(uri, null,null,null,null);
                if (cursor!=null){
                    if (cursor.moveToFirst()){
                        movieItem = new MovieItem(cursor);
                        cursor.close();
                    }
                }
            }
            favouriteCheck.setChecked(true);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(movieItem.getTitleMovie());
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView titleTextView = findViewById(R.id.tv_title);
        TextView descriptionTextView = findViewById(R.id.tv_description);
        TextView releaseTextView = findViewById(R.id.tv_release);
        TextView popularityTextView = findViewById(R.id.tv_popularity);
        ImageView backdropImageView = findViewById(R.id.img_backdrop);
        ImageView posterImageView = findViewById(R.id.img_poster);

        if (favouriteCheck.isChecked()){
            isEdit = true;
        }

        titleTextView.setText(movieItem.getTitleMovie());
        descriptionTextView.setText(movieItem.getOverView());
        releaseTextView.setText(movieItem.getReleaseDate());
        popularityTextView.setText(String.valueOf(movieItem.getPopularity()));

        Picasso
                .with(this)
                .load(movieItem.getImagePoster())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(posterImageView);

        Picasso
                .with(this)
                .load(movieItem.getImageBackdrop())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(backdropImageView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null){
            movieHelper.close();
        }
    }

    final int ALERT_DIALOG_ADD = 10;
    final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type) {

        final boolean isDialogAdd = type == ALERT_DIALOG_ADD;
        String dialogTitle, dialogMessage;

        if (isDialogAdd){
            dialogTitle = getString(R.string.dialog_title_add);
            dialogMessage = getString(R.string.dialog_message_add);
        }else{
            dialogTitle = getString(R.string.dialog_title_remove);
            dialogMessage = getString(R.string.dialog_message_remove);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(dialogMessage).setTitle(dialogTitle)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogAdd){
                            Toast.makeText(DetailMovieActivity.this, R.string.toast_add, Toast.LENGTH_SHORT).show();
                            addFavourite();
                        }else{
                            getContentResolver().delete(getIntent().getData(),null,null);
                            Toast.makeText(DetailMovieActivity.this, R.string.toast_remove, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogAdd){
                            dialogInterface.cancel();
                            favouriteCheck.setChecked(false);
                        } else {
                            dialogInterface.cancel();
                            favouriteCheck.setChecked(true);
                        }
                    }
                })
                .show();
    }

    private void addFavourite() {
        String titleMovie = movieItem.getTitleMovie();
        String overView = movieItem.getOverView();
        String releaseDate = movieItem.getReleaseDate();
        String imagePoster = movieItem.getImagePoster();
        String imageBackdrop = movieItem.getImageBackdrop();
        double popularity = movieItem.getPopularity();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_MOVIE, titleMovie);
        contentValues.put(OVERVIEW, overView);
        contentValues.put(RELEASE_DATE, releaseDate);
        contentValues.put(IMAGE_POSTER, imagePoster);
        contentValues.put(IMAGE_BACKDROP, imageBackdrop);
        contentValues.put(POPULARITY, popularity);
        if (isEdit){
            getContentResolver().update(getIntent().getData(),contentValues,null,null);
        }else{
            getContentResolver().insert(CONTENT_URI,contentValues);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, movieItem.getTitleMovie()+" \n"+movieItem.getOverView());
                intent.setType("text/plain");
                startActivity(intent);
                Toast.makeText(this, "Share "+ movieItem.getTitleMovie(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
