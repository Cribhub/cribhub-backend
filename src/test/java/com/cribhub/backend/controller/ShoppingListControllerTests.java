package com.cribhub.backend.controller;

import com.cribhub.backend.controllers.ShoppingListController;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingList;
import com.cribhub.backend.services.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ShoppingListControllerTests {

    @InjectMocks
    ShoppingListController shoppingListController;

    @Mock
    ShoppingListService shoppingListService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getShoppingListByIdTest() {
        ShoppingList shoppingList = new ShoppingList();

        when(shoppingListService.getShoppingListById(1L)).thenReturn(Optional.of(shoppingList));

        ResponseEntity<ShoppingList> result = shoppingListController.getShoppingListById(1L);

        assertNotNull(result);
        assertEquals(shoppingList, result.getBody());
        verify(shoppingListService, times(1)).getShoppingListById(1L);
    }

    @Test
    public void createShoppingListForCribTest() {
        Crib crib = new Crib();
        ShoppingList shoppingList = new ShoppingList();

        when(shoppingListService.createShoppingListForCrib(1L, shoppingList)).thenReturn(Optional.of(shoppingList));

        ResponseEntity<ShoppingList> result = shoppingListController.createShoppingListForCrib(1L, shoppingList);

        assertNotNull(result);
        assertEquals(shoppingList, result.getBody());
        verify(shoppingListService, times(1)).createShoppingListForCrib(1L, shoppingList);
    }

    @Test
    public void updateShoppingListTest() {
        ShoppingList shoppingList = new ShoppingList();

        when(shoppingListService.getShoppingListById(1L)).thenReturn(Optional.of(shoppingList));
        when(shoppingListService.createOrUpdateShoppingList(shoppingList)).thenReturn(shoppingList);

        ResponseEntity<ShoppingList> result = shoppingListController.updateShoppingList(1L, shoppingList);

        assertNotNull(result);
        assertEquals(shoppingList, result.getBody());
        verify(shoppingListService, times(1)).getShoppingListById(1L);
        verify(shoppingListService, times(1)).createOrUpdateShoppingList(shoppingList);
    }

    @Test
    public void deleteShoppingListTest() {
        doNothing().when(shoppingListService).deleteShoppingList(1L);

        ResponseEntity<Void> result = shoppingListController.deleteShoppingList(1L);

        assertEquals(204, result.getStatusCodeValue());
        verify(shoppingListService, times(1)).deleteShoppingList(1L);
    }
}