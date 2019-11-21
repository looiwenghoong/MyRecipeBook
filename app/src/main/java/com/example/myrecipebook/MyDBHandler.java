package com.example.myrecipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myrecipebook.Provider.MyContentProvider;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private ContentResolver myCR;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipedb.db";
    public static final String TABLE_RECIPES = "Recipes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPE_TITLE = "recipe_title";
    public static final String COLUMN_RECIPE_INSTRUCTION = "recipe_instruction";
    public static final String COLUMN_RECIPE_RATING = "recipe_rating";

    private ArrayList<Recipe> allRecipe;

    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_RECIPE_TITLE + " TEXT," + COLUMN_RECIPE_INSTRUCTION + " TEXT," + COLUMN_RECIPE_RATING + " INTEGER" + ") ";
        System.out.println(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECIPES;
        db.execSQL(DROP_TABLE);

    }

    // Add new recipe function
    public void addRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_TITLE, recipe.get_recipeTitle());
        values.put(COLUMN_RECIPE_INSTRUCTION, recipe.get_recipeInstruction());
        values.put(COLUMN_RECIPE_RATING, 0);
        myCR.insert(MyContentProvider.Content_URI, values);
    }

    // Find recipe function
    public Recipe findRecipe(String recipeTitle) {
        String[] projection = {COLUMN_ID, COLUMN_RECIPE_TITLE, COLUMN_RECIPE_INSTRUCTION};
        String selection = "recipe_title = \"" + recipeTitle + "\"";
        Cursor cursor = myCR.query(MyContentProvider.Content_URI, projection, selection, null, null);
        Recipe recipe = new Recipe();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            recipe.set_id(Integer.parseInt(cursor.getString(0)));
            recipe.set_recipeTitle(cursor.getString(1));
            recipe.set_recipeInstruction(cursor.getString(2));
            cursor.close();
        } else {
            recipe = null;
        }
        return recipe;
    }

    public boolean deleteRecipe(String recipeTitle) {
        boolean result = false;
        String query = "recipe_title = \"" + recipeTitle + "\"";
        int rowsDeleted = myCR.delete(MyContentProvider.Content_URI, query, null);
        if(rowsDeleted > 0) {
            result = true;
        }
        return result;
    }

    public void updateRating(int id, int ratingValue) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_RATING, ratingValue);
        myCR.update(MyContentProvider.Content_URI, values, null, null);
    }

    public ArrayList<Recipe> allRecipe(String recipeTitle) {
        allRecipe = new ArrayList<Recipe>();
        String[] projection = {COLUMN_ID, COLUMN_RECIPE_TITLE, COLUMN_RECIPE_INSTRUCTION};

        Cursor cursor = myCR.query(MyContentProvider.Content_URI, projection, null, null, "recipe_title ASC");
        if(cursor != null) {
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe();
                recipe.set_id(Integer.parseInt(cursor.getString(0)));
                recipe.set_recipeTitle(cursor.getString(1));
                recipe.set_recipeInstruction(cursor.getString(2));
                allRecipe.add(recipe);
            }
            cursor.close();
        }
        return allRecipe;
    }
}
