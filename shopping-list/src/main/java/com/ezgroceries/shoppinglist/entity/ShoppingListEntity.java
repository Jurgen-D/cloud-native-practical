package com.ezgroceries.shoppinglist.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="SHOPPING_LIST")
public class ShoppingListEntity {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="COCKTAIL_SHOPPING_LIST",
            joinColumns=@JoinColumn(name="COCKTAIL_ID", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="SHOPPING_LIST_ID", referencedColumnName="id"))
    private List<CocktailEntity> cocktailEntities;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShoppingListEntity() {
    }

    public ShoppingListEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<CocktailEntity> getCocktailEntities() {
        return cocktailEntities;
    }

    public void setCocktailEntities(List<CocktailEntity> cocktailEntities) {
        this.cocktailEntities = cocktailEntities;
    }
}
