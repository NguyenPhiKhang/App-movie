package com.khangse.appmoviepopular.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.khangse.appmoviepopular.model.Movie;

import java.util.ArrayList;
import java.util.List;

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
                FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH +" TEXT NOT NULL, "+
                FavoriteContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE +" TEXT NOT NULL, "+
                FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW +" TEXT NOT NULL, "+
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH+" TEXT NOT NULL, "+
                FavoriteContract.FavoriteEntry.COLUMN_VOTE_COUNT+" INTEGER, "+
                FavoriteContract.FavoriteEntry.COLUMN_VOTE_AVERAGE+" REAL, "+
                FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE+" TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addFavorite(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, movie.getTitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+"="+id, null);
    }

    public void deleteAllFavorite(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, null, null);
    }

    public List<Movie> getAllFavorite(){
        String[] columns={
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH,
                FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,
                FavoriteContract.FavoriteEntry.COLUMN_VOTE_AVERAGE,
                FavoriteContract.FavoriteEntry.COLUMN_VOTE_COUNT,
                FavoriteContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW
        };

        String sortOrder=
                FavoriteContract.FavoriteEntry._ID+" ASC";
        List<Movie> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if(cursor.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH)));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_VOTE_AVERAGE)));
                movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_VOTE_COUNT)));

                favoriteList.add(movie);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteList;
    }
}
