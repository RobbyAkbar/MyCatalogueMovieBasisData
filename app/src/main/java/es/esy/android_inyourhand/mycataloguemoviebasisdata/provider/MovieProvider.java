package es.esy.android_inyourhand.mycataloguemoviebasisdata.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.helpers.MovieHelper;

import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.AUTHORITY;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.CONTENT_URI;

/**
  Created by robby on 02/01/18.
 */

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE+"/#",MOVIE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor!=null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added;
        switch (URI_MATCHER.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (URI_MATCHER.match(uri)){
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
