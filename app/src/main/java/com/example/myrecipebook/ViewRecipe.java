package com.example.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewRecipe extends AppCompatActivity {

    private TextView recipeTitle;
    private EditText recipeInstruction;
    private RatingBar ratingBar;
    private Button deletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        recipeTitle = findViewById(R.id.RecipeTitleText);
        recipeInstruction = findViewById(R.id.RecipeInstructionEditText);
        ratingBar = findViewById(R.id.RecipeRatingBar);
        deletebtn = findViewById(R.id.RecipeDeleteBtn);

        ratingBar.setRating(5);
        setupRatingBar();
    }

    public void setupRatingBar() {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                System.out.println(String.valueOf(rating));
            }
        });
    }
}
