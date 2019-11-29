package com.example.myrecipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.example.myrecipebook.Provider.MyContentProvider;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private ContentResolver myCR;

//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "recipeDb.db";
//    public static final String TABLE_RECIPES = "Recipes";
//    public static final String COLUMN_ID = "_id";
//    public static final String COLUMN_RECIPE_TITLE = "recipe_title";
//    public static final String COLUMN_RECIPE_INSTRUCTION = "recipe_instruction";
//    public static final String COLUMN_RECIPE_RATING = "recipe_rating";

    private ArrayList<Recipe> allRecipe;

    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, MyDatabaseContract.DATABASE_NAME, factory, MyDatabaseContract.DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MyDatabaseContract.RecipeTable
        .CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MyDatabaseContract.RecipeTable.DROP_TABLE);

    }

    // Add new recipe function
    public void addRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseContract.RecipeTable.COLUMN_RECIPE_TITLE, recipe.get_recipeTitle());
        values.put(MyDatabaseContract.RecipeTable.COLUMN_RECIPE_INSTRUCTION, recipe.get_recipeInstruction());
        values.put(MyDatabaseContract.RecipeTable.COLUMN_RECIPE_RATING, 0);
        myCR.insert(MyContentProvider.Content_URI, values);
    }

    public boolean deleteRecipe(int id) {
        boolean result = false;
        String query = "_id = \"" + id + "\"";
        int rowsDeleted = myCR.delete(MyContentProvider.Content_URI, query, null);
        if(rowsDeleted > 0) {
            result = true;
        }
        return result;
    }

    public void updateRating(int id, float ratingValue) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseContract.RecipeTable.COLUMN_RECIPE_RATING, ratingValue);
        Uri updateURI = Uri.parse("content://com.example.myrecipebook.MyContentProvider/Recipes/" + "/" + id);
        myCR.update(updateURI, values, null, null);
    }

    public ArrayList<Recipe> allRecipe(int sortType) {
        allRecipe = new ArrayList<Recipe>();
        String[] projection = {
                MyDatabaseContract.RecipeTable.COLUMN_ID,
                MyDatabaseContract.RecipeTable.COLUMN_RECIPE_TITLE,
                MyDatabaseContract.RecipeTable.COLUMN_RECIPE_INSTRUCTION,
                MyDatabaseContract.RecipeTable.COLUMN_RECIPE_RATING
        };

        Cursor cursor;
        if(sortType == 1) {
            cursor = myCR.query(MyContentProvider.Content_URI, projection, null, null, "recipe_title ASC");
        } else {
            cursor = myCR.query(MyContentProvider.Content_URI, projection, null, null, "recipe_rating DESC");
        }

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe();
                recipe.set_id(Integer.parseInt(cursor.getString(0)));
                recipe.set_recipeTitle(cursor.getString(1));
                recipe.set_recipeInstruction(cursor.getString(2));
                recipe.set_recipeRating(Float.parseFloat(cursor.getString(3)));
                allRecipe.add(recipe);
            }
            cursor.close();
        }
        return allRecipe;
    }
}
