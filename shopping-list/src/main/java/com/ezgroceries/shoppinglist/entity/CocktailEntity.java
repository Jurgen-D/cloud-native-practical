package com.ezgroceries.shoppinglist.entity;

import com.ezgroceries.shoppinglist.converter.StringSetConverter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="COCKTAIL")
public class CocktailEntity {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "ID_DRINK")
    private String idDrink;

    @Column(name = "NAME")
    private String name;

    @Column(name = "INGREDIENTS")
    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }
}