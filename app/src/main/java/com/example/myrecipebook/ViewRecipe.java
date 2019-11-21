package com.example.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewRecipe extends AppCompatActivity {

    private TextView recipeTitle;
    private EditText recipeInstruction;
    private RatingBar ratingBar;
    private Button deletebtn;
    private int recipe_id;
    private Float recipe_rating;
    private String recipe_title, recipe_instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        recipeTitle = findViewById(R.id.RecipeTitleText);
        recipeInstruction = findViewById(R.id.RecipeInstructionEditText);
        ratingBar = findViewById(R.id.RecipeRatingBar);
        deletebtn = findViewById(R.id.RecipeDeleteBtn);

        populateData();
        setupRatingBar();

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecipe();
            }
        });
    }

    public void populateData() {
        recipe_id = getIntent().getIntExtra("recipe_id", 0);
        recipe_title = getIntent().getStringExtra("recipe_title");
        recipe_instruction = getIntent().getStringExtra("recipe_instruction");
        recipe_rating = getIntent().getFloatExtra("recipe_rating", 0);

        recipeTitle.setText(recipe_title);
        recipeInstruction.setText(recipe_instruction);
        ratingBar.setRating(recipe_rating);
    }

    public void setupRatingBar() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                dbHandler.updateRating(recipe_id, rating);
            }
        });
    }

    public void deleteRecipe() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Boolean deletedRow = dbHandler.deleteRecipe(recipe_id);

        if(deletedRow) {
            System.out.println("Recipe deleted");
        } else {
            System.out.println("Recipe Not deleted");
        }
    }
}
