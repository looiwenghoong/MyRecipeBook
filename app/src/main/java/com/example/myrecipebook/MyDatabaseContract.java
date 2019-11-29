package com.example.myrecipebook;

import android.provider.BaseColumns;

public class MyDatabaseContract  {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipeDb.db";


    private MyDatabaseContract() {}

    public static abstract class RecipeTable implements BaseColumns {
        public static final String TABLE_RECIPES = "Recipes";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RECIPE_TITLE = "recipe_title";
        public static final String COLUMN_RECIPE_INSTRUCTION = "recipe_instruction";
        public static final String COLUMN_RECIPE_RATING = "recipe_rating";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_RECIPES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_RECIPE_TITLE + " TEXT," + COLUMN_RECIPE_INSTRUCTION + " TEXT," + COLUMN_RECIPE_RATING + " FLOAT" + ") ";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECIPES;
    }


}
