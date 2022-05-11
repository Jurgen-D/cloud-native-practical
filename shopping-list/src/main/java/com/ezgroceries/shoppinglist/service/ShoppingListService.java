package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.ShoppingListIngredients;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import com.ezgroceries.shoppinglist.resource.ShoppingListResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    List<ShoppingListResource> shoppingLists = new ArrayList<>();

    private List<CocktailResource> cocktailLists = new ArrayList(Arrays.asList(
            new CocktailResource(
                    UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"), "Margerita",
                    "Cocktail glass",
                    "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                    "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                    Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")),
            new CocktailResource(
                    UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"), "Blue Margerita",
                    "Cocktail glass",
                    "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                    "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                    Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt"))));


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
    private ShoppingListResource retrieveShoppingList(UUID shoppingListId) {
        return shoppingLists.stream().filter(shoppingList -> shoppingListId.equals(shoppingListId)).findAny().orElse(null);
    }


    // get the ingredientsList for the cocktails for a shoppingList
    private ShoppingListIngredients getShoppingListIngredients(UUID shoppingListId) {

        ShoppingListResource shoppingList = retrieveShoppingList(shoppingListId);

        List<String> allIngredients = new ArrayList<>();

        for (UUID cocktailId : shoppingList.getCocktails()) {
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
    private List<String> getIngredients(UUID cocktailId) {

        CocktailResource cocktail = cocktailLists.stream()
                .filter(x -> x.getCocktailId().equals(cocktailId)).findAny().orElse(null);

        return cocktail.getIngredients();
    }
}
