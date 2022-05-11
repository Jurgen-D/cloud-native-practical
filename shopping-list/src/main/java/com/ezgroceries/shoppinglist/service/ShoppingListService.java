package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.ShoppingListIngredients;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import com.ezgroceries.shoppinglist.resource.ShoppingListResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    List<ShoppingListResource> shoppingLists = new ArrayList<>();

    private CocktailService cocktailService;

    public ShoppingListService( CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }


    // create a shoppingList
    public ShoppingListResource create(ShoppingListResource newShoppingList) {

        ShoppingListResource shoppingList = new ShoppingListResource(UUID.randomUUID(), newShoppingList.getName());

        shoppingLists.add(shoppingList);

        return shoppingList;
    }


    // add cocktail(s) to a shoppinglist
    public List<CocktailResource> addCocktails(UUID shoppingListId, List<CocktailResource> cocktails) {

        ShoppingListResource shoppingList = retrieveShoppingList(shoppingListId);
        shoppingList.addCocktails(cocktails.stream().map(CocktailResource::getCocktailId).collect(Collectors.toList()));

        return cocktails;
    }


    //get a shoppingList
    public ShoppingListIngredients getShoppingList(UUID shoppingListId) {

        return(getShoppingListIngredients(shoppingListId));
    }


    //get shoppingLists
    public List<ShoppingListIngredients> getShoppingLists() {

        List<ShoppingListIngredients> shoppingListIngredientsList = new ArrayList<>();

        for (ShoppingListResource shoppingList : shoppingLists) {
            shoppingListIngredientsList.add(getShoppingListIngredients(shoppingList.getShoppingListId()));
        }

        return shoppingListIngredientsList;
    }


    // get the shoppingList via the shoppingListId
    public ShoppingListResource retrieveShoppingList(UUID shoppingListId) {
        return shoppingLists.stream().filter(shoppingList -> shoppingListId.equals(shoppingListId)).findAny().orElse(null);
    }


    // get the ingredientsList for the cocktails for a shoppingList
    private ShoppingListIngredients getShoppingListIngredients(UUID shoppingListId) {

        ShoppingListResource shoppingList = retrieveShoppingList(shoppingListId);

        List<String> allIngredients = new ArrayList<>();

        for (String cocktailId : shoppingList.getCocktails()) {
            allIngredients.addAll(getIngredients(cocktailId));
        }

        ShoppingListIngredients shoppingListIngredients = new ShoppingListIngredients(
                shoppingList.getShoppingListId(),
                shoppingList.getName(),
                allIngredients.stream().distinct().collect(Collectors.toList())
        );

        return shoppingListIngredients;
    }


    // get the ingredients for a cocktail via the cocktailId
    private List<String> getIngredients(String cocktailId) {

        // replace hardcoding : Mapping UUID cocktailId to string search??
        List<CocktailResource> cocktails = cocktailService.getCocktails("russian");

        CocktailResource cocktail = cocktails.stream()
                .filter(x -> x.getCocktailId().equals(cocktailId)).findAny().orElse(null);

        List<String> ingredients = new ArrayList<>();

        for (String ingredient : cocktail.getIngredients()) {

            if (ingredient!=null) {
                ingredients.add(ingredient);
            }
        }

        return ingredients;
    }
}
