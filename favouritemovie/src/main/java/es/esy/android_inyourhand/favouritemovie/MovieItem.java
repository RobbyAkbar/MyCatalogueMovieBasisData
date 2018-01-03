package es.esy.android_inyourhand.favouritemovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.IMAGE_BACKDROP;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.IMAGE_POSTER;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.OVERVIEW;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.POPULARITY;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.RELEASE_DATE;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.getColumnInt;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.getColumnLong;
import static es.esy.android_inyourhand.favouritemovie.DatabaseContract.getColumnString;

/**
  Created by robby on 02/01/18.
 */

public class MovieItem implements Parcelable {

    private int id;
    private double popularity;
    private String titleMovie, overView, releaseDate, imagePoster, imageBackdrop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    private void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getOverView() {
        return overView;
    }

    private void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    private void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    private void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }

    public String getImageBackdrop() {
        return imageBackdrop;
    }

    private void setImageBackdrop(String imageBackdrop) {
        this.imageBackdrop = imageBackdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titleMovie);
        dest.writeString(this.overView);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.popularity);
        dest.writeString(this.imagePoster);
        dest.writeString(this.imageBackdrop);
    }

    public MovieItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.titleMovie = getColumnString(cursor, TITLE_MOVIE);
        this.overView = getColumnString(cursor, OVERVIEW);
        this.releaseDate = getColumnString(cursor, RELEASE_DATE);
        this.popularity = getColumnLong(cursor, POPULARITY);
        this.imagePoster = getColumnString(cursor, IMAGE_POSTER);
        this.imageBackdrop = getColumnString(cursor, IMAGE_BACKDROP);
    }

    private MovieItem(Parcel in) {
        this.id = in.readInt();
        this.titleMovie = in.readString();
        this.overView = in.readString();
        this.releaseDate = in.readString();
        this.popularity = in.readDouble();
        this.imagePoster = in.readString();
        this.imageBackdrop = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };


}
