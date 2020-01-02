package com.khangse.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoriteDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favorite.db";
    public static final int DATABASE_VERSION= 1;
    public static final String LOGTAG="FAVORITE";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.d(LOGTAG, "Database Opened");
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        Log.d(LOGTAG, "Database Closed");
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE "+FavoriteContract.FavoriteEntry.TABLE_NAME+"( "+
                FavoriteContract.FavoriteEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+" INTEGER, "+
                FavoriteContract.FavoriteEntry.COLUMN_TITLE+" TEXT NOT NULL, "+
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING+" REAL NOT NULL, "+
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH+" TEXT NOT NULL, "+
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS+" TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
