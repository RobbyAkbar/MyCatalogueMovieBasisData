package es.esy.android_inyourhand.mycataloguemoviebasisdata.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract;

/**
  Created by robby on 02/01/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"+
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s INT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE_MOVIE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.RELEASE_DATE,
            DatabaseContract.MovieColumns.POPULARITY,
            DatabaseContract.MovieColumns.IMAGE_POSTER,
            DatabaseContract.MovieColumns.IMAGE_BACKDROP
    );

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_MOVIE);
        onCreate(db);
    }
}
