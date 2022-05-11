package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.resource.CocktailDBResponse;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CocktailService {

    private CocktailDBService cocktailDBService;

    public CocktailService (CocktailDBService cocktailDBService){
        this.cocktailDBService = cocktailDBService;
    }


    public List<CocktailResource> getCocktails(String search) {

        CocktailDBResponse cocktailDBResponse = cocktailDBService.searchCocktails(search);

        List<CocktailResource> cocktailResources = new ArrayList<>();

        for (CocktailDBResponse.DrinkResource drinkResource : cocktailDBResponse.getDrinks()) {

            CocktailResource cocktailResource = new CocktailResource(
                    drinkResource.getIdDrink(),
                    drinkResource.getStrDrink(),
                    drinkResource.getStrGlass(),
                    drinkResource.getStrInstructions(),
                    drinkResource.getStrImage(),
                    Arrays.asList(
                            drinkResource.getStrIngredient1(),
                            drinkResource.getStrIngredient2(),
                            drinkResource.getStrIngredient3(),
                            drinkResource.getStrIngredient4(),
                            drinkResource.getStrIngredient5(),
                            drinkResource.getStrIngredient6(),
                            drinkResource.getStrIngredient7(),
                            drinkResource.getStrIngredient8(),
                            drinkResource.getStrIngredient9(),
                            drinkResource.getStrIngredient10(),
                            drinkResource.getStrIngredient11(),
                            drinkResource.getStrIngredient12(),
                            drinkResource.getStrIngredient13(),
                            drinkResource.getStrIngredient14(),
                            drinkResource.getStrIngredient15()
                    ));

            cocktailResources.add(cocktailResource);
        }

        return cocktailResources;
    }
}
