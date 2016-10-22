package com.cs180.team2.caloriecounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseTable {

        private static final String TAG = "CalorieDatabase";

        //The columns we'll include in the dictionary table
        public static final String COL_FOOD = "FOOD";
        public static final String COL_CALORIES = "CALORIES";
        public static final String COL_DATE = "DATE";

        private static final String DATABASE_NAME = "USER CALORIE JOURNAL";
        private static final String FTS_VIRTUAL_TABLE = "FTS";
        private static final int DATABASE_VERSION = 1;

        private final DatabaseOpenHelper mDatabaseOpenHelper;

        public DatabaseTable(Context context) {
            mDatabaseOpenHelper = new DatabaseOpenHelper(context);
        }

        private static class DatabaseOpenHelper extends SQLiteOpenHelper {

            private final Context mHelperContext;
            private SQLiteDatabase mDatabase;

            private static final String FTS_TABLE_CREATE =
                    "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                            " USING fts3 (" +
                            COL_FOOD + ", " +
                            COL_CALORIES + ", " +
                            COL_DATE + ")";

            DatabaseOpenHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
                mHelperContext = context;
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                mDatabase = db;
                mDatabase.execSQL(FTS_TABLE_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
                db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
                onCreate(db);
            }
        }
    }
