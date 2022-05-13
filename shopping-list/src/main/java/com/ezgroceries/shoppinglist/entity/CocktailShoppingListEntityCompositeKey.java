package com.ezgroceries.shoppinglist.entity;

import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class CocktailShoppingListEntityCompositeKey implements Serializable {
    private UUID cocktailId;
    private UUID shoppingListId;

    @Override
    public int hashCode() {
        return Objects.hash(cocktailId, shoppingListId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CocktailShoppingListEntityCompositeKey entity = (CocktailShoppingListEntityCompositeKey) o;
        return Objects.equals(this.cocktailId, entity.cocktailId) &&
                Objects.equals(this.shoppingListId, entity.shoppingListId);
    }
}