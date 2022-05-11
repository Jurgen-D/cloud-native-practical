package com.ezgroceries.shoppinglist.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingListResource {

    private UUID shoppingListId;
    private String name;
    private List<String> cocktails = new ArrayList<>();

    public ShoppingListResource(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCocktails() {
        return cocktails;
    }

    public void setCocktails(List<String> cocktails) {
        this.cocktails = cocktails;
    }

    public void addCocktails(List<String> cocktailIds) {
        this.cocktails.addAll(cocktailIds);
    }
}
