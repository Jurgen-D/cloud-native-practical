package com.ezgroceries.shoppinglist.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CocktailResource {

    private UUID cocktailId;

    @JsonIgnore
    private String name;
    @JsonIgnore
    private String glass;
    @JsonIgnore
    private String instructions;
    @JsonIgnore
    private String image;
    @JsonIgnore
    private Set<String> ingredients;

    public CocktailResource(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    public CocktailResource(UUID cocktailId, String name, String glass, String instructions, String image, Set<String> ingredients) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }
}
