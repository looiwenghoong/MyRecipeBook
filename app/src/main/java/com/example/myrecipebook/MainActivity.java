package com.example.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String[] dropDown = {"Title", "Rating"};
    private Spinner dropDownSpinner;
    private Button addNewRecipeBtn;
    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupItems();

        final Intent RecipeIntent = new Intent(this, ViewRecipe.class);
        testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
//                startActivity(RecipeIntent);
            }
        });
    }

    private void setupItems() {

        // Spinner
        dropDownSpinner = findViewById(R.id.dropDownSpinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, dropDown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropDownSpinner.setAdapter(adapter);
        dropDownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Add new recipe button
        final Intent newRecipeIntent = new Intent(this, NewRecipe.class);
        addNewRecipeBtn = findViewById(R.id.AddNewRecipeBtn);
        addNewRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newRecipeIntent);
            }
        });
    }

    private void loadData() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ArrayList<Recipe> recipe = dbHandler.allRecipe("Cake");
        if(recipe != null) {
            for(Recipe r: recipe) {
                System.out.println(r.get_recipeRating());
            }
        } else {
            System.out.println("No Match Found");
        }
    }
}
