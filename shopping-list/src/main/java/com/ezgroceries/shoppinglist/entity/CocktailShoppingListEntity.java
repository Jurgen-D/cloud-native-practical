package com.ezgroceries.shoppinglist.entity;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name="COCKTAIL_SHOPPING_LIST")
@IdClass(CocktailShoppingListEntityCompositeKey.class)
public class CocktailShoppingListEntity {

    @Id
    @Column(name = "COCKTAIL_ID")
    private UUID cocktailId;

    @Id
    @Column(name = "SHOPPING_LIST_ID")
    private UUID shoppingListId;
}
