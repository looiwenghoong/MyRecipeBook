package com.example.myrecipebook;

public class Recipe {

    private int _id;
    private String _recipeTitle;
    private String _recipeInstruction;

    private int _recipeRating;

    public Recipe() {

    }

    public Recipe(int id, String title, String instruction, int rating) {
        this._id = id;
        this._recipeTitle = title;
        this._recipeInstruction = instruction;
        this._recipeRating = rating;
    }

    public Recipe(String title, String instruction) {
        this._recipeTitle = title;
        this._recipeInstruction = instruction;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_recipeTitle() {
        return _recipeTitle;
    }

    public void set_recipeTitle(String _recipeTitle) {
        this._recipeTitle = _recipeTitle;
    }

    public String get_recipeInstruction() {
        return _recipeInstruction;
    }

    public void set_recipeInstruction(String _recipeInstruction) {
        this._recipeInstruction = _recipeInstruction;
    }

    public int get_recipeRating() {
        return _recipeRating;
    }

    public void set_recipeRating(int _recipeRating) {
        this._recipeRating = _recipeRating;
    }

}
