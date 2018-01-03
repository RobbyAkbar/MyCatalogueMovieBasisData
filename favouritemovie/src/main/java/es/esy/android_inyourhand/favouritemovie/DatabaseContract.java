package es.esy.android_inyourhand.favouritemovie;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
  Created by robby on 02/01/18.
 */

public class DatabaseContract {

    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {
        public static String TITLE_MOVIE = "title";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String POPULARITY = "popularity";
        public static String IMAGE_POSTER = "image_poster";
        public static String IMAGE_BACKDROP = "image_backdrop";
    }

    public static final String AUTHORITY = "es.esy.android_inyourhand.mycataloguemoviebasisdata";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
