package com.example.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewRecipe extends AppCompatActivity {

    private EditText recipeTitle, recipeInstruction;
    private Button AddNewRecipeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        recipeTitle = findViewById(R.id.RecipeTitleText);
        recipeInstruction = findViewById(R.id.RecipeInstructionEditText);
        AddNewRecipeBtn = findViewById(R.id.AddNewRecipeBtn);

        AddNewRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewRecipe();
            }
        });
    }

    public void addNewRecipe() {
        String title = recipeTitle.getText().toString();
        String instruction = recipeInstruction.getText().toString();

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Recipe recipe = new Recipe(title, instruction);
        dbHandler.addRecipe(recipe);
        finish();
    }
}
