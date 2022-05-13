package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.resource.CocktailDBResponse;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CocktailDBService {

    private CocktailDBClient cocktailDBClient;
    private CocktailRepository cocktailRepository;

    public CocktailDBService (CocktailDBClient cocktailDBClient,
                              CocktailRepository cocktailRepository) {
        this.cocktailDBClient = cocktailDBClient;
        this.cocktailRepository = cocktailRepository;
    }

    public CocktailDBResponse searchCocktails(String search) {
        return cocktailDBClient.searchCocktails(search);
    }

    @Transactional
    public List<CocktailResource> mergeCocktails(List<CocktailDBResponse.DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                CocktailEntity newCocktailEntity = new CocktailEntity();
                newCocktailEntity.setId(UUID.randomUUID());
                newCocktailEntity.setIdDrink(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                newCocktailEntity.setIngredients(getIngredients(drinkResource));

                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<CocktailResource> mergeAndTransform(List<CocktailDBResponse.DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getId(), drinkResource.getStrDrink(), drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), getIngredients(drinkResource))).collect(Collectors.toList());
    }

    private Set<String> getIngredients(CocktailDBResponse.DrinkResource drinkResource) {

        Set<String> ingredients = new HashSet<>(Arrays.asList(
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
                drinkResource.getStrIngredient12(),
                drinkResource.getStrIngredient13(),
                drinkResource.getStrIngredient14(),
                drinkResource.getStrIngredient15()));

        return ingredients;
    }
}