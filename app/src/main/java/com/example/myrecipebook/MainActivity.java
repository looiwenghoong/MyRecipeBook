package com.example.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String[] dropDown = {"Title", "Rating"};
    private Spinner dropDownSpinner;
    private Button addNewRecipeBtn;
    private Button testBtn;
    private ListView recipeList;
    private ArrayList<Recipe> allRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeList = findViewById(R.id.recipeList);

//        final Intent RecipeIntent = new Intent(this, ViewRecipe.class);
//        testBtn = findViewById(R.id.testBtn);
//        testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadData();
////                startActivity(RecipeIntent);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Started");
        setupItems();
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
                if(position == 0) {
                    System.out.println("Title Pressed");

                    // Load data sorted with title
                    // sortType 1 == title
                    allRecipe = loadData(1);

                    // if the data array is not null then set adapter to view the data
                    if(allRecipe != null) {
                        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), allRecipe);
                        recipeList.setAdapter(customAdapter);
                        recipeList.setItemsCanFocus(true);

                        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int _id = allRecipe.get(position).get_id();
                                String _recipeTitle = allRecipe.get(position).get_recipeTitle();
                                String _recipeInstruction = allRecipe.get(position).get_recipeInstruction();
                                Float _recipeRating = allRecipe.get(position).get_recipeRating();

                                navigateToViewRecipe(_id, _recipeTitle, _recipeInstruction, _recipeRating);
                            }
                        });
                    } else {
                        System.out.println("No Match Found");
                    }
                } else {
                    System.out.println("Rating Pressed");

                    allRecipe = loadData(2);

                    // if the data array is not null then set adapter to view the data
                    if(allRecipe != null) {
                        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), allRecipe);
                        recipeList.setAdapter(customAdapter);
                        recipeList.setItemsCanFocus(true);

                        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int _id = allRecipe.get(position).get_id();
                                String _recipeTitle = allRecipe.get(position).get_recipeTitle();
                                String _recipeInstruction = allRecipe.get(position).get_recipeInstruction();
                                Float _recipeRating = allRecipe.get(position).get_recipeRating();

                                navigateToViewRecipe(_id, _recipeTitle, _recipeInstruction, _recipeRating);
                            }
                        });
                    } else {
                        System.out.println("No Match Found");
                    }
                }
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

    private ArrayList<Recipe> loadData(int sortType) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ArrayList<Recipe> recipe = dbHandler.allRecipe(sortType);
        return recipe;
    }

    private void navigateToViewRecipe(int id, String title, String instruction, Float rating) {
        Intent viewRecipeIntent = new Intent(this, ViewRecipe.class);
        viewRecipeIntent.putExtra("recipe_id", id);
        viewRecipeIntent.putExtra("recipe_title", title);
        viewRecipeIntent.putExtra("recipe_instruction", instruction);
        viewRecipeIntent.putExtra("recipe_rating", rating);
        startActivity(viewRecipeIntent);
    }
}
