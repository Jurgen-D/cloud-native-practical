package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.resource.CocktailResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailService {

    private CocktailDBService cocktailDBService;

    public CocktailService (CocktailDBService cocktailDBService){
        this.cocktailDBService = cocktailDBService;
    }


    public List<CocktailResource> getCocktails(String search) {

        List<CocktailResource> cocktailResources = cocktailDBService.mergeCocktails(cocktailDBService.searchCocktails(search).getDrinks());

        return cocktailResources;
    }
}
