package es.esy.android_inyourhand.mycataloguemoviebasisdata.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static es.esy.android_inyourhand.mycataloguemoviebasisdata.DatabaseContract.TABLE_MOVIE;

/**
  Created by robby on 02/01/18.
 */

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null,_ID+"=?",new String[]{id},null,null,null,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE, null,null,null,null,null,_ID+" DESC");
    }

    public long insertProvider(ContentValues contentValues){
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public int updateProvider(String id,ContentValues contentValues){
        return database.update(DATABASE_TABLE, contentValues,_ID+"=?",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID+"=?",new String[]{id});
    }

}
