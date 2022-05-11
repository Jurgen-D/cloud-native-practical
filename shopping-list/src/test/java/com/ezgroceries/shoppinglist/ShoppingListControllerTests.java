package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.controller.ShoppingListController;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import com.ezgroceries.shoppinglist.resource.ShoppingListResource;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingListController.class)
public class ShoppingListControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService;

    @Test
    public void createShoppingList() throws Exception {

        UUID shoppingListId = UUID.fromString("e1932fa0-9445-49e1-beca-dedf8393977c");

        ShoppingListResource shoppingList = new ShoppingListResource(shoppingListId, "Stephanie's birthday");

        given(shoppingListService.create(any(ShoppingListResource.class)))
                .willReturn(shoppingList);

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(shoppingList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Stephanie's birthday"));

        verify(shoppingListService).create(any(ShoppingListResource.class));
    }


    @Test
    public void addCocktail() throws Exception {

        List<CocktailResource> cocktails = new ArrayList<>(Arrays.asList(
                new CocktailResource(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4")),
                new CocktailResource(UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"))));

        given(shoppingListService.addCocktails(any(UUID.class),any(List.class))).willReturn(cocktails);

        mockMvc.perform(put("/shopping-lists/{shoppingListId}/cocktails", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cocktails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cocktailId", hasSize(2)))
                .andExpect(jsonPath("$..cocktailId", hasItem("23b3d85a-3928-41c0-a533-6538a71e17c4")))
                .andExpect(jsonPath("$..cocktailId", hasItem("d615ec78-fe93-467b-8d26-5d26d8eab073")));
                //.andDo(MockMvcResultHandlers.print());

        verify(shoppingListService).addCocktails(any(UUID.class),any(List.class));
    }


    @Test
    public void getShoppingList() throws Exception {

        UUID uuid = UUID.randomUUID();

        ShoppingListIngredients shoppingListIngredients = new ShoppingListIngredients(
                uuid,
                "Stephanie's birthday",
                new ArrayList<>(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")));

        given(shoppingListService.getShoppingList(any(UUID.class))).willReturn(shoppingListIngredients);

        mockMvc.perform(get("/shopping-lists/{shoppingListId}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$..ingredients", hasSize(4)))  >> result is array of arrays ?
                        .andExpect(jsonPath("$..ingredients[0]").value("Tequila"));

        verify(shoppingListService).getShoppingList(any(UUID.class));
    }


    @Test
    public void getShoppingLists() throws Exception {

        List<ShoppingListIngredients> shoppingListIngredients = Arrays.asList(
                new ShoppingListIngredients(
                        UUID.randomUUID(),
                        "Stephanie's birthday",
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")),
                new ShoppingListIngredients(
                        UUID.randomUUID(),
                        "Eddies birthday",
                        Arrays.asList("Ginger")
                ));


        given(shoppingListService.getShoppingLists()).willReturn(shoppingListIngredients);

        mockMvc.perform(get("/shopping-lists")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$..ingredients", hasSize(2)));
                        //.andDo(MockMvcResultHandlers.print());

        verify(shoppingListService).getShoppingLists();
    }


    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
