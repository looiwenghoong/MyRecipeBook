package com.example.myrecipebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Recipe> recipeList;
    LayoutInflater inflater;

    public CustomAdapter (Context context, ArrayList<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_view_cell, null);
        TextView listViewCell = convertView.findViewById(R.id.RecipeTitle);
        RatingBar ratingBar = convertView.findViewById(R.id.RatingBar);
        listViewCell.setText(recipeList.get(position).get_recipeTitle());
        listViewCell.setTextSize(30);
        ratingBar.setRating(recipeList.get(position).get_recipeRating());
        ratingBar.setIsIndicator(true);
        return convertView;
    }
}
