package com.example.myrecipebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipeDB.db";
    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPE_TITLE = "recipe_title";
    public static final String COLUMN_RECIPE_INSTRUCTION = "recipe_instruction";

    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_RECIPE_TITLE + " TEXT," + COLUMN_RECIPE_INSTRUCTION + " TEXT" + ") ";
        db.execSQL(CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECIPES;
        db.execSQL(DROP_TABLE);

    }
}
