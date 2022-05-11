package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.controller.ShoppingListController;
import com.ezgroceries.shoppinglist.resource.CocktailDBResponse;
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

    @MockBean
    private CocktailDBClient cocktailDBClient;

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
                new CocktailResource("11102"),
                new CocktailResource("12528")));

        given(shoppingListService.addCocktails(any(UUID.class),any(List.class))).willReturn(cocktails);

        mockMvc.perform(put("/shopping-lists/{shoppingListId}/cocktails", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cocktails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cocktailId", hasSize(2)))
                .andExpect(jsonPath("$..cocktailId", hasItem("11102")))
                .andExpect(jsonPath("$..cocktailId", hasItem("12528")));
                //.andDo(MockMvcResultHandlers.print());

        verify(shoppingListService).addCocktails(any(UUID.class),any(List.class));
    }

    /*
    @Test
    public void getShoppingList() throws Exception {

        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();

        cocktailDBResponse.setDrinks(Arrays.asList(
                new CocktailDBResponse.DrinkResource(
                        "11102",
                        "Black Russian",
                        "Old-fashioned glass",
                        "Pour the ingredients into an old fashioned glass filled with ice cubes. Stir gently.",
                        "https://commons.wikimedia.org/wiki/File:Black_Russian.jpg",
                        "https://www.thecocktaildb.com/images/media/drink/8oxlqf1606772765.jpg",
                        "Coffee Liqueur",
                        "Vodka",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null),
                new CocktailDBResponse.DrinkResource(
                        "12528",
                        "White Russian",
                        "Old-fashioned glass",
                        "Pour vodka and coffee liqueur over ice cubes in an old-fashioned glass. Fill with light cream and serve.",
                        null,
                        "https://www.thecocktaildb.com/images/media/drink/vsrupw1472405732.jpg",
                        "Vodka",
                        "Coffee liqueur",
                        "Light cream",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)));

        UUID uuid = UUID.randomUUID();

        ShoppingListResource shoppingListResource = new ShoppingListResource(
                UUID.randomUUID(),
                "Stephanie's birthday");

        shoppingListResource.addCocktails(Arrays.asList("11102","12528"));

        given(cocktailDBClient.searchCocktails(any(String.class))).willReturn(cocktailDBResponse);
        given(shoppingListService.retrieveShoppingList(any(UUID.class))).willReturn(shoppingListResource);

        mockMvc.perform(get("/shopping-lists/{shoppingListId}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$..ingredients", hasSize(4)))  >> result is array of arrays ?
                        //.andExpect(jsonPath("$..ingredients[0]").value("Tequila"));
                .andDo(MockMvcResultHandlers.print());

        verify(cocktailDBClient).searchCocktails(any(String.class));
        verify(shoppingListService).retrieveShoppingList(any(UUID.class));

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
    */

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
