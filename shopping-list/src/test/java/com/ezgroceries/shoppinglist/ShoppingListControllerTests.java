package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.controller.ShoppingListController;
import com.ezgroceries.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.resource.CocktailResource;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import java.util.*;

import static org.hamcrest.Matchers.*;
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

    @MockBean
    private CocktailDBClient cocktailDBClient;


    @Test
    public void createShoppingList() throws Exception {

        UUID shoppingListId = UUID.fromString("e1932fa0-9445-49e1-beca-dedf8393977c");

        ShoppingListEntity shoppingList = new ShoppingListEntity(shoppingListId, "Stephanie's birthday");

        given(shoppingListService.create(any(ShoppingListEntity.class)))
                .willReturn(shoppingList);

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(shoppingList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Stephanie's birthday"));

        verify(shoppingListService).create(any(ShoppingListEntity.class));
    }


    @Test
    public void addCocktail() throws Exception {

        List<CocktailResource> cocktails = new ArrayList<>(Arrays.asList(
                new CocktailResource(UUID.fromString("93868b3f-f561-45e8-b3da-8151de3d7b88")),
                new CocktailResource(UUID.fromString("22d0d4ca-110d-4291-b6f4-f5ed18adf032"))));

        given(shoppingListService.addCocktails(any(UUID.class),any(List.class))).willReturn(cocktails);

        mockMvc.perform(put("/shopping-lists/{shoppingListId}/cocktails", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cocktails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cocktailId", hasSize(2)))
                .andExpect(jsonPath("$..cocktailId", hasItem("93868b3f-f561-45e8-b3da-8151de3d7b88")))
                .andExpect(jsonPath("$..cocktailId", hasItem("22d0d4ca-110d-4291-b6f4-f5ed18adf032")));
                //.andDo(MockMvcResultHandlers.print());

        verify(shoppingListService).addCocktails(any(UUID.class),any(List.class));
    }


    @Test
    public void getShoppingList() throws Exception {

        ShoppingListIngredients shoppingListIngredients = new ShoppingListIngredients(
                UUID.randomUUID(),
                "Stephanie's birthday",
                new HashSet<>(Arrays.asList("Blue Curacao", "Salt", "Tequila"))
                );

        given(shoppingListService.getShoppingList(any(UUID.class))).willReturn(shoppingListIngredients);

        mockMvc.perform(get("/shopping-lists/{shoppingListId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.ingredients.length()").value(3))
                        .andExpect(jsonPath("$.ingredients[0]").value("Blue Curacao"))

                        .andDo(MockMvcResultHandlers.print());

        verify(shoppingListService).getShoppingList(any(UUID.class));

    }


    @Test
    public void getShoppingLists() throws Exception {

        List<ShoppingListIngredients> shoppingListIngredients = Arrays.asList(
                new ShoppingListIngredients(
                        UUID.randomUUID(),
                        "Stephanie's birthday",
                        new HashSet<>(Arrays.asList("Blue Curacao", "Salt", "Tequila"))),
                new ShoppingListIngredients(
                        UUID.randomUUID(),
                        "Eddies birthday",
                        new HashSet<>(Arrays.asList("Tequila", "Triple sec")))
        );


        given(shoppingListService.getShoppingLists()).willReturn(shoppingListIngredients);

        mockMvc.perform(get("/shopping-lists")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].ingredients.length()").value(3))
                        .andExpect(jsonPath("$[0].ingredients[0]").value("Blue Curacao"))
                        .andDo(MockMvcResultHandlers.print());

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
