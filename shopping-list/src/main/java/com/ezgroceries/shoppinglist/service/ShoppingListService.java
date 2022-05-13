package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.ShoppingListIngredients;
import com.ezgroceries.shoppinglist.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.repository.ShoppingListRepository;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import com.ezgroceries.shoppinglist.resource.ShoppingListResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private ShoppingListRepository shoppingListRepository;
    private CocktailService cocktailService;

    public ShoppingListService( ShoppingListRepository shoppingListRepository,
                                CocktailService cocktailService) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailService = cocktailService;

        cocktailService.getCocktails("Margarita");
    }

    // create a shoppingList
    public ShoppingListEntity create(ShoppingListEntity newShoppingList) {

        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(UUID.randomUUID(), newShoppingList.getName());

        shoppingListRepository.save(shoppingListEntity);

        return shoppingListEntity;
    }


    // add cocktail(s) to a shoppinglist
    public List<CocktailEntity> addCocktails(UUID shoppingListId, List<CocktailEntity> cocktails) {

        ShoppingListEntity shoppingListEntity = shoppingListRepository.findShoppingListEntityById(shoppingListId);

        shoppingListEntity.setCocktailEntities(cocktails);

        shoppingListRepository.save(shoppingListEntity);

        return cocktails;
    }


    //get a shoppingList
    public ShoppingListIngredients getShoppingList(UUID shoppingListId) {

        return(getShoppingListIngredients(shoppingListId));
    }


    //get shoppingLists
    public List<ShoppingListIngredients> getShoppingLists() {

        List<ShoppingListIngredients> shoppingListIngredientsList = new ArrayList<>();

        for (ShoppingListEntity shoppingListEntity : shoppingListRepository.findAll()) {
            shoppingListIngredientsList.add(getShoppingListIngredients(shoppingListEntity.getId()));
        }

        return shoppingListIngredientsList;
    }


    // get the ingredientsList for the cocktails for a shoppingList
    public ShoppingListIngredients getShoppingListIngredients(UUID shoppingListId) {

        ShoppingListEntity shoppingList = shoppingListRepository.findShoppingListEntityById(shoppingListId);

        Set<String> ingredients = new HashSet<>();

        // loop through shoppingList cocktails
        for (CocktailEntity cocktailEntity : shoppingList.getCocktailEntities()) {

            ingredients.addAll(cocktailEntity.getIngredients());
        }

        // create new shoppingListIngredients for output reasons
        ShoppingListIngredients shoppingListIngredients = new ShoppingListIngredients(
                shoppingList.getId(),
                shoppingList.getName(),
                ingredients);

        return shoppingListIngredients;
    }
}
