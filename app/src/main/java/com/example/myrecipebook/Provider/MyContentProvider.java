package com.example.myrecipebook.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.content.UriMatcher;
import android.text.TextUtils;

import com.example.myrecipebook.MyDBHandler;

public class MyContentProvider extends ContentProvider {

    private MyDBHandler myDB;
    private static final String AUTHORITY = "com.example.myrecipebook.MyContentProvider";
    private static final String Recipe_Table = "Recipes";
    public static final Uri Content_URI = Uri.parse("content://" + AUTHORITY + "/" + Recipe_Table);

    public static final int RECIPE = 1;
    public static final int RECIPE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, Recipe_Table, RECIPE);
        sUriMatcher.addURI(AUTHORITY, Recipe_Table + "/#", RECIPE_ID);
    }


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case RECIPE:
                rowsDeleted = sqlDB.delete(MyDBHandler.TABLE_RECIPES, selection,
                    selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MyDBHandler.TABLE_RECIPES, MyDBHandler.COLUMN_ID + "=" + id,
                        null);
                } else {
                    rowsDeleted = sqlDB.delete(MyDBHandler.TABLE_RECIPES,MyDBHandler.COLUMN_ID + "=" + id + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case RECIPE:
                id = sqlDB.insert(MyDBHandler.TABLE_RECIPES, null, values);
                break;
            default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(Recipe_Table + "/" + id);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        myDB = new MyDBHandler(getContext(),  null, null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MyDBHandler.TABLE_RECIPES);
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case RECIPE_ID:
                queryBuilder.appendWhere(MyDBHandler.COLUMN_ID + "="
                    + uri.getLastPathSegment());
                break;
            case RECIPE:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case RECIPE:
                rowsUpdated = sqlDB.update(MyDBHandler.TABLE_RECIPES, values,
                            selection,
                            selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(MyDBHandler.TABLE_RECIPES, values,
                            MyDBHandler.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(MyDBHandler.TABLE_RECIPES, values,
                            MyDBHandler.COLUMN_ID + "=" + id + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,
                null);
        return rowsUpdated;
    }
}
