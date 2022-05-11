package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.resource.CocktailDBResponse;
import org.springframework.stereotype.Service;

@Service
public class CocktailDBService {

    private CocktailDBClient cocktailDBClient;

    public CocktailDBService (CocktailDBClient cocktailDBClient) {
        this.cocktailDBClient = cocktailDBClient;
    }

    public CocktailDBResponse searchCocktails(String search) {
        return cocktailDBClient.searchCocktails(search);
    }
}
