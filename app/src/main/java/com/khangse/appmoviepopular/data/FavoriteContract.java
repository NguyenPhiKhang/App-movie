package com.khangse.appmoviepopular.data;

import android.provider.BaseColumns;

public class FavoriteContract {
    public static final class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE ="title";
        public static final String COLUMN_BACKDROP_PATH ="backdrop_path";
        public static final String COLUMN_RELEASE_DATE="release_date";
        public static final String COLUMN_POSTER_PATH ="poster_path";
        public static final String COLUMN_VOTE_COUNT ="vote_count";
        public static final String COLUMN_VOTE_AVERAGE ="vote_average";
        public static final String COLUMN_ORIGINAL_TITLE ="original_title";
        public static final String COLUMN_OVERVIEW ="overview";
    }
}
