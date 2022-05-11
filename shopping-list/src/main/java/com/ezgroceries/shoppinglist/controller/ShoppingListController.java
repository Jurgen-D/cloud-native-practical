package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.ShoppingListIngredients;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import com.ezgroceries.shoppinglist.resource.ShoppingListResource;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json")
public class ShoppingListController {

    private ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }


    @PostMapping(path="shopping-lists")
    public ResponseEntity<ShoppingListResource> createShoppingList(@RequestBody ShoppingListResource newShoppingList) {

        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingListService.create(newShoppingList));
    }


    @PutMapping(path="shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<List<CocktailResource>> addCocktails(@PathVariable UUID shoppingListId, @RequestBody List<CocktailResource> cocktails) {

        return ResponseEntity.ok(shoppingListService.addCocktails(shoppingListId, cocktails));
    }


    @GetMapping(path="shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingListIngredients> getShoppingList(@PathVariable UUID shoppingListId) {

        return ResponseEntity.ok(shoppingListService.getShoppingList(shoppingListId));
    }


    @GetMapping(path="shopping-lists")
    public ResponseEntity<List<ShoppingListIngredients>> getShoppingLists() {

        return ResponseEntity.ok(shoppingListService.getShoppingLists());
    }
}

